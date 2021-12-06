package by.zborovskaya.final_project.dao.impl;

import by.zborovskaya.final_project.dao.DaoException;
import by.zborovskaya.final_project.dao.MessageDao;
import by.zborovskaya.final_project.dao.pool.ConnectionPool;
import by.zborovskaya.final_project.dao.pool.ConnectionPoolException;
import by.zborovskaya.final_project.entity.Message;
import by.zborovskaya.final_project.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {

    private static final Logger logger = LogManager.getLogger(MessageDaoImpl.class);
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String SELECT_MESSAGES;
    private final static String SELECT_USER_FROM;
    private final static String ADD_MESSAGE;
    private final static String SELECT_MESSAGE_BY_ID;

    private final static String MESSAGE_ID = "id_message";
    private final static String USER_ID_FROM = "user_id_from";
    private final static String USER_ID_TO = "user_id_to";
    private final static String MESSAGE = "message";
    private final static String TIME = "time";

    private final static String FATAL_ERROR_RESULT_SET = "Fatal error closing resultSet";
    private final static String FATAL_ERROR_PREPARED_STATEMENT = "Fatal error closing preparedStatement";

    static{
        SELECT_USER_FROM="SELECT `user_id_from`" +
                "  FROM `messages`" +
                "  WHERE `user_id_to`=?" +
                "  ORDER BY `time` DESC";
        SELECT_MESSAGES="SELECT `id_message`,`user_id_from`,`user_id_to`,`message`,`time`" +
                "  FROM `messages`" +
                "  WHERE `user_id_from`=? AND `user_id_to`=?" +
                "  ORDER BY `time` DESC";
        ADD_MESSAGE = "INSERT INTO `messages`(`id_message`,`user_id_from`,`user_id_to`,`message`,`time`) " +
                "VALUES(?,?,?,?,?)";
        SELECT_MESSAGE_BY_ID="SELECT `user_id_from`,`user_id_to`,`message`,`time`" +
                "  FROM `messages`" +
                "  WHERE id_message=?";
    }


    @Override
    public List<Message> getMessages(int userIdFrom, int userIdTo) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Message> messages=null;
        try {
            connectionPool.init();
            con = connectionPool.takeConnection();

            st = con.prepareStatement(SELECT_MESSAGES);
            st.setInt(1, userIdFrom);
            st.setInt(2, userIdTo);
            rs = st.executeQuery();
            messages = new ArrayList<Message>();
            while (rs.next()){
                int idMessage = rs.getInt(MESSAGE_ID);
                int idUserFrom = rs.getInt(USER_ID_FROM);
                int idUserTo = rs.getInt(USER_ID_TO);
                String messageText = rs.getString(MESSAGE);
                String time = rs.getString(TIME);

               Message message=new Message(idMessage,new User(idUserFrom),new User (idUserTo),messageText,
                       time);

                if(!messages.contains(message)){
                    messages.add(message);
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
        return messages;
    }

    @Override
    public  List<Integer> getUsersIdFrom(int userIdTo) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Integer> usersId=null;
        try {
            connectionPool.init();
            con = connectionPool.takeConnection();

            st = con.prepareStatement(SELECT_USER_FROM);
            st.setInt(1, userIdTo);
            rs = st.executeQuery();
            usersId = new ArrayList<Integer>();
            while (rs.next()){
                Integer idUserFrom = rs.getInt(USER_ID_FROM);
                if(!usersId.contains(idUserFrom)){
                    usersId.add(idUserFrom);
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
        return usersId;
    }

    @Override
    public boolean addMessage(Message message) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        boolean isMessageAdded = false;
        try{
            connectionPool.init();
            con = connectionPool.takeConnection();

            st = con.prepareStatement(ADD_MESSAGE);
            st.setInt(1, message.getIdMessage());
            st.setInt(2, message.getUserFrom().getIdUser());
            st.setInt(3, message.getUserTo().getIdUser());
            st.setString(4,  message.getMessage());
            st.setString(5, message.getTime());
            st.executeUpdate();
            isMessageAdded=true;
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
        return isMessageAdded;
    }

    @Override
    public Message messageByID(int id) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Message message=null;
        try {
            connectionPool.init();
            con = connectionPool.takeConnection();

            st = con.prepareStatement(SELECT_MESSAGE_BY_ID);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()){
                int idUserFrom = rs.getInt(USER_ID_FROM);
                int idUserTo = rs.getInt(USER_ID_TO);
                String messageText = rs.getString(MESSAGE);
                String time = rs.getString(TIME);
                message=new Message(id,new User(idUserFrom),new User (idUserTo),messageText,
                        time);
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
        return message;
    }

    public static void main(String[] args){
//        получить все сообщения
//        List<Message> messages = new ArrayList<Message>();
//        try {
//            MessageDaoImpl messageDao = new MessageDaoImpl();
//            messages=messageDao.getMessage(1,4);
//            for(Message message:messages)
//            System.out.println(message.toString());
//        }catch(DaoException ex){}

        // получить пользователей от которых приходят сообщения
//        List<Integer> usersId = new ArrayList<Integer>();
//        try {
//            MessageDaoImpl messageDao = new MessageDaoImpl();
//            usersId=messageDao.getUserIdFrom(4);
//            for(Integer userId:usersId)
//                System.out.println(userId.toString());
//        }catch(DaoException ex){}

        //добавить сообщение
//        Message message= new Message(new User(3),new User(5),"Хочу купить вашу машину","2021-09-11 18:45:05.983");
//        try {
//            MessageDaoImpl messageDao = new MessageDaoImpl();
//            System.out.println(messageDao.addMessage(message));
//        }catch(DaoException ex){}

        //получить сообщение по id
        try {
            MessageDaoImpl messageDao = new MessageDaoImpl();
            System.out.println(messageDao.messageByID(2).toString());
        }catch(DaoException ex){}
    }
}
