package by.zborovskaya.final_project.dao.impl;

import by.zborovskaya.final_project.dao.DaoException;
import by.zborovskaya.final_project.dao.UserDao;
import by.zborovskaya.final_project.dao.pool.ConnectionPool;
import by.zborovskaya.final_project.dao.pool.ConnectionPoolException;
import by.zborovskaya.final_project.entity.User;
import by.zborovskaya.final_project.entity.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String UPDATE_PASSWORD= "UPDATE `users`SET `password` = ? WHERE id_user = ? ";
    private final static String SELECT_BY_LOGIN= "SELECT id_user,`login`," +
            "`password`,`is_active`,`role` FROM users WHERE `login` = ?";
    private final static String COUNT_SUCH_LOGIN= "SELECT COUNT(id_user) AS loginEquals FROM `users` WHERE `login` = ? ";
    private final static String USER_LOGIN = "login";
    private final static String USER_PASSWORD = "password";
    private final static String USER_ID = "id_user";
    private final static String CITY_ID = "id_city";
    private final static String USER_ROLE = "role";
    private final static String USER_ACTIVE = "is_active";
    private final static String LOGIN_EQUALS = "loginEquals";
    private final static String SELECT_USER_BY_ID = "SELECT * FROM `users` WHERE `id_user`=?";
    private final static String SELECT_CITY_BY_ID = "SELECT id_city FROM `cities` WHERE `name`=?";
    private final static String REGISTRSATE_USER = "INSERT INTO `users`(`login`,`password`,`is_active`,`role`)" +
            " VALUES(?,?,?,?)";
    private final static String REGISTRSATE_CLIENT = "INSERT INTO `clients`(`user_id`,`email`,`first_name`," +
            "`last_name`,`phone`,`city_id`VALUES(?, ?, ?, ?,?,?);";
    private final static String FATAL_ERROR_RESULT_SET = "Fatal error closing resultSet";
    private final static String FATAL_ERROR_PREPARED_STATEMENT = "Fatal error closing preparedStatement";


    @Override
    public User authorization(String login, String password) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        User user = null;

        try{
            con = connectionPool.takeConnection();
            st = con.prepareStatement(SELECT_BY_LOGIN);
            st.setString(1, login);
            rs = st.executeQuery();

            while(rs.next()){
                String userPassword = rs.getString(USER_PASSWORD);
                boolean userActive=rs.getBoolean(USER_ACTIVE);
                String userRole = null;
                int userID = rs.getInt(USER_ID);
                if(rs.getInt(USER_ROLE)==0){
                    userRole = UserRole.ADMINISTRATOR.getValue();
                } else{
                    userRole = UserRole.CLIENT.getValue();
                }

                if(userPassword.equals(password) && userActive){
                    user = new User(userID,userRole.toUpperCase(), login,password,userActive);
                }else{
                    return null;
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, FATAL_ERROR_RESULT_SET, e);
                }
            }
            if (st != null) {
                try {
                    st.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, FATAL_ERROR_PREPARED_STATEMENT, e);
                }
            }
            if(con != null){
                connectionPool.releaseConnection(con);
            }
        }

        return user;
    }

    @Override
    public boolean registration(User user) throws DaoException {

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = connectionPool.takeConnection();

            st = con.prepareStatement(COUNT_SUCH_LOGIN);
            st.setString(1, user.getLogin());
            rs = st.executeQuery();
            while(rs.next()) {
                int usersWithSuchLogin = rs.getInt(LOGIN_EQUALS);
                if (usersWithSuchLogin != 0) {
                    return false;
                }
                break;
            }

            st = con.prepareStatement(REGISTRSATE_USER);
            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());
            st.setBoolean(3,user.isActive());
            UserRole userRole=user.getUserRole();
            if(userRole==UserRole.CLIENT) {
                st.setInt(4, 1);
            }else{
                st.setInt(4,0);
            }
            st.executeUpdate();
        }catch (SQLException | ConnectionPoolException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, FATAL_ERROR_RESULT_SET, e);
                }
            }
            if (st != null) {
                try {
                    st.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, FATAL_ERROR_PREPARED_STATEMENT, e);
                }
            }
            if(con != null){
                connectionPool.releaseConnection(con);
            }
        }

        return true;
    }

    @Override
    public User getUserID(int userID) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        User user=null;
        try {
            con = connectionPool.takeConnection();

            st = con.prepareStatement(SELECT_USER_BY_ID);
            st.setInt(1, userID);
            rs = st.executeQuery();

            while (rs.next()){
                String userPassword = rs.getString(USER_PASSWORD);
                String userLogin=rs.getString(USER_LOGIN);
                boolean userActive=rs.getBoolean(USER_ACTIVE);
                String userRole = null;
                if(rs.getInt(USER_ROLE)==0){
                    userRole = UserRole.ADMINISTRATOR.getValue();
                } else{
                    userRole = UserRole.CLIENT.getValue();
                }
                if(userActive){
                    user = new User(userID,userRole.toUpperCase(),userLogin,userPassword,userActive);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, FATAL_ERROR_RESULT_SET, e);
                }
            }
            if (st != null) {
                try {
                    st.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, FATAL_ERROR_PREPARED_STATEMENT, e);
                }
            }
            if(con != null){
                connectionPool.releaseConnection(con);
            }
        }
        return user;
    }

    @Override
    public boolean changePassword(String oldPassword, String newPassword, int userID) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

//        boolean isPasswordChanged = false;
        try{
            con = connectionPool.takeConnection();
            st = con.prepareStatement(SELECT_USER_BY_ID);
            st.setInt(1, userID);
            rs = st.executeQuery();

            if(rs.next()){
                String currentPassword = rs.getString(USER_PASSWORD);
                if(!currentPassword.equals(oldPassword)){
                    return false;
                }
            }

            st = con.prepareStatement(UPDATE_PASSWORD);
            st.setString(1, newPassword);
            st.setInt(2, userID);
            st.executeUpdate();

//            isPasswordChanged = true;

        } catch (SQLException | ConnectionPoolException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, FATAL_ERROR_RESULT_SET, e);
                }
            }
            if (st != null) {
                try {
                    st.close();
                }catch (SQLException e){
                    logger.log(Level.FATAL, FATAL_ERROR_PREPARED_STATEMENT, e);
                }
            }
            if(con != null){
                connectionPool.releaseConnection(con);
            }
        }
        return true;
    }

    public static void main(String[] args){
        //вход пользователя
//        User user=null;
//        try {
//            UserDaoImpl userDaoImpl = new UserDaoImpl();
//            user=userDaoImpl.authorization("user1", "21232F297A57A5A7678");
//        }catch (DaoException ex){}
//        System.out.println(user.toString());

        //регистрация пользователя
//        User user=new User("CLIENT","user6","45678GHJKASF23451",true);
//        try {
//            UserDaoImpl userDaoImpl = new UserDaoImpl();
//            System.out.println(userDaoImpl.registration(user));
//        }catch (Exception ex){}

        //получить пользователя по id
//        User user=null;
//        try {
//            UserDaoImpl userDaoImpl = new UserDaoImpl();
//            user=userDaoImpl.getUserID(2);
//        }catch (DaoException ex){}
//        System.out.println(user.toString());

        //сменить пароль
//        try {
//            UserDaoImpl userDaoImpl = new UserDaoImpl();
//            System.out.println(userDaoImpl.changePassword("21232F297A86342",
//                    "777777777777777",4));
//        }catch (DaoException ex){}

    }
}
