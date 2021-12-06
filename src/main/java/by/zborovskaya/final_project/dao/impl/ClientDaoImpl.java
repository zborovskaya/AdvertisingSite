package by.zborovskaya.final_project.dao.impl;

import by.zborovskaya.final_project.dao.ClientDao;
import by.zborovskaya.final_project.dao.DaoException;
import by.zborovskaya.final_project.dao.pool.ConnectionPool;
import by.zborovskaya.final_project.dao.pool.ConnectionPoolException;
import by.zborovskaya.final_project.entity.ClientAdv;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClientDaoImpl implements ClientDao {
    private static final Logger logger = LogManager.getLogger(ClientDaoImpl.class);

    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String CITY_ID = "id_city";
    private final static String SELECT_CITY_BY_ID = "SELECT * FROM `cities` WHERE `name`=?";
    private final static String REGISTRSATE_CLIENT = "INSERT INTO `clients`(`user_id`,`email`,`first_name`," +
            "`last_name`,`phone`,`city_id`)VALUES(?, ?, ?, ?,?,?);";
    private final static String FATAL_ERROR_PREPARED_STATEMENT = "Fatal error closing preparedStatement";


    @Override
    public boolean addClientData(ClientAdv client, int userID) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;

        boolean isClientRegistrated = false;

        try {
            connectionPool.init();
            con = connectionPool.takeConnection();

            st = con.prepareStatement(REGISTRSATE_CLIENT);
            st.setInt(1, userID);
            st.setString(2,client.getEmail());
            st.setString(3, client.getFirstName());
            st.setString(4, client.getLastName());
            st.setInt(5, client.getPhone());
            st.setInt(6, getIdCiyByName(client.getCity()));
            st.executeUpdate();
            isClientRegistrated = true;
        } catch (SQLException | ConnectionPoolException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
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
        return isClientRegistrated;
    }

    @Override
    public boolean updateClientData(int userID, ClientAdv newClient) throws DaoException {
        return false;
    }

    @Override
    public List<ClientAdv> findAll() throws DaoException {
        return null;
    }

    @Override
    public int findClientById(int idClient) throws DaoException {
        return 0;
    }
    public int getIdCiyByName(String city) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int cityId = 0;
        try {
            con = connectionPool.takeConnection();
            st = con.prepareStatement(SELECT_CITY_BY_ID);
            st.setString(1,city);
            rs = st.executeQuery();
            while(rs.next()) {
                cityId = rs.getInt(CITY_ID);
                break;
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
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
        return cityId;
    }
    public static void main(String[] args){
        //регистрация клиента
//        ClientAdv client=new ClientAdv("kate@gmail.com","Катя","Стасюк",
//                234568794,"Брест");
//        try {
//            ClientDaoImpl clientDaoImpl = new ClientDaoImpl();
//            System.out.println(clientDaoImpl.addClientData(client,6));
//        }catch (Exception ex){}
    }
}
