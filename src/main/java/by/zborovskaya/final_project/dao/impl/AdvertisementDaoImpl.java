package by.zborovskaya.final_project.dao.impl;

import by.zborovskaya.final_project.dao.AdvertisementDao;
import by.zborovskaya.final_project.dao.DaoException;
import by.zborovskaya.final_project.dao.pool.ConnectionPool;
import by.zborovskaya.final_project.dao.pool.ConnectionPoolException;
import by.zborovskaya.final_project.entity.Advertisement;
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

public class AdvertisementDaoImpl implements AdvertisementDao {
    private static final Logger logger = LogManager.getLogger(AdvertisementDaoImpl.class);
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String SELECT_CATEGORY_BY_ID;
    private final static String SELECT_AMOUNT_OF_LIKES;
    private final static String SELECT_ALL_ADVERTISEMENTS;
    private final static String SELECT_ALL_CATEGORIES;
    private final static String SELECT_CATEGORY_BY_NAME;
    private final static String ADD_ADVERTISEMENT;
    private final static String ADD_LIKE;
    private final static String SELECT_ADVERTISEMENTS_BY_TITLE;
    private final static String SELECT_ADVERTISEMENTS_BY_CATEGORY;
    private final static String SELECT_ADVERTISEMENTS_BY_CATEGORY_AND_NAME;
    private final static String SELECT_ADVERTISEMENTS_BY_ID;
    private final static String SELECT_AMOUNT_OF_ADVERTISEMENTS;
    private final static String DELETE_ADVERTISEMENT;
    private final static String DELETE_LIKE;
    private final static String UPDATE_ADVERTISEMENT;

    private final static String ADVERTISEMENT_ID = "id_advertisement";
    private final static String USER_ID = "user_id";
    private final static String ADVERTISEMENT_CATEGORY_ID = "category_id";
    private final static String TITLE = "title";
    private final static String TEXT = "text";
    private final static String DATE = "date";
    private final static String EXPIRY = "expiry";
    private final static String IS_ACTIVE = "is_active";
    private final static String CATEGORY_NAME = "name";
    private final static String CATEGORY_ID = "id_category";
    private final static String AMOUNT_LIKES = "amount_likes";
    private final static String AMOUNT_ADVERTISEMENTS = "amount_advertisements";


    private final static String FATAL_ERROR_RESULT_SET = "Fatal error closing resultSet";
    private final static String FATAL_ERROR_PREPARED_STATEMENT = "Fatal error closing preparedStatement";


    static {
        SELECT_CATEGORY_BY_ID = "SELECT `name` FROM `category` WHERE `id_category`=?";
        SELECT_ALL_ADVERTISEMENTS = "SELECT advertisements.id_advertisement,advertisements.user_id,category.`name`," +
                "advertisements.title, advertisements.`text`," +
                "  advertisements.is_active, advertisements.`date`,advertisements.expiry " +
                "  FROM advertisements JOIN category ON category.id_category =advertisements.category_id" +
                "  ORDER BY advertisements.`date`";
        SELECT_AMOUNT_OF_LIKES = "SELECT COUNT(*) AS amount_likes FROM `likes` WHERE `advertisement_id`=?";
        SELECT_ALL_CATEGORIES="SELECT * FROM `category`";
        SELECT_CATEGORY_BY_NAME="SELECT `id_category` FROM `category` WHERE `name`=?";
        ADD_ADVERTISEMENT = "INSERT INTO `advertisements`(user_id,category_id,title,text,is_active,date,expiry) " +
                "VALUES(?,?,?,?,?,?,?)";
        ADD_LIKE="INSERT INTO `likes`(user_id,advertisement_id) " +
                "VALUES(?,?)";
        SELECT_ADVERTISEMENTS_BY_TITLE="SELECT advertisements.id_advertisement,advertisements.user_id,category.`name`," +
                "advertisements.title, advertisements.`text`," +
                "  advertisements.is_active, advertisements.`date`,advertisements.expiry " +
                "  FROM advertisements JOIN category ON category.id_category =advertisements.category_id" +
                "  WHERE advertisements.title LIKE ?" +
                "  ORDER BY advertisements.`date`";
        SELECT_ADVERTISEMENTS_BY_CATEGORY="SELECT advertisements.id_advertisement,advertisements.user_id," +
                "category.`name`," +
                " advertisements.title, advertisements.`text`,  advertisements.is_active, advertisements.`date`," +
                "  advertisements.expiry " +
                "FROM advertisements JOIN category ON category.id_category =advertisements.category_id" +
                " WHERE category.`name`=?"+
                " ORDER BY advertisements.`date`";
        SELECT_ADVERTISEMENTS_BY_CATEGORY_AND_NAME="SELECT advertisements.id_advertisement,advertisements.user_id," +
                "category.`name`,advertisements.title, advertisements.`text`," +
                "  advertisements.is_active, advertisements.`date`,advertisements.expiry " +
                "  FROM advertisements JOIN category ON category.id_category =advertisements.category_id" +
                "  WHERE category.`name`=? AND advertisements.title LIKE ?" +
                "  ORDER BY advertisements.`date`";
        SELECT_ADVERTISEMENTS_BY_ID="SELECT advertisements.id_advertisement,advertisements.user_id,category.`name`," +
                "advertisements.title, advertisements.`text`," +
                "  advertisements.is_active, advertisements.`date`,advertisements.expiry " +
                "  FROM advertisements JOIN category ON category.id_category =advertisements.category_id" +
                "  WHERE advertisements.id_advertisement=?";
        SELECT_AMOUNT_OF_ADVERTISEMENTS ="SELECT COUNT(id_advertisement) AS amount_advertisements FROM `advertisements`";
        DELETE_ADVERTISEMENT="DELETE FROM `advertisements`" +
                "WHERE id_advertisement = ?";
        DELETE_LIKE="DELETE FROM `likes` WHERE advertisement_id = ? AND user_id=?";
        UPDATE_ADVERTISEMENT = "UPDATE `advertisements` " +
                "SET category_id=?,title=?,text=?,is_active=?,date=?,expiry=? " +
                "WHERE id_advertisement = ?";

    }

    @Override
    public int getAllLike(int idAdvertisement) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        int amountOfLike=0;
        try {
            con = connectionPool.takeConnection();

            st = con.prepareStatement(SELECT_AMOUNT_OF_LIKES);
            st.setInt(1,idAdvertisement);
            rs = st.executeQuery();

            while(rs.next()) {
                amountOfLike = rs.getInt(AMOUNT_LIKES);
                break;
            }
        } catch (SQLException | ConnectionPoolException e) {
            //TODO DEBUG
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
        return amountOfLike;
    }

    @Override
    public boolean deleteLike(int idAdvertisement,int idUser) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        boolean isLikeDeleted = false;
        try {
            con = connectionPool.takeConnection();

            st = con.prepareStatement(DELETE_LIKE);
            st.setInt(1, idAdvertisement);
            st.setInt(2, idUser);
            st.executeUpdate();
            isLikeDeleted = true;
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
        return isLikeDeleted;
    }

    @Override
    public List<Advertisement> all() throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Advertisement> advertisements = null;
        try {
            con = connectionPool.takeConnection();

            st = con.prepareStatement(SELECT_ALL_ADVERTISEMENTS);
            rs = st.executeQuery();

            advertisements = new ArrayList<Advertisement>();
            while(rs.next()) {
                int idAdvertisement = rs.getInt(ADVERTISEMENT_ID);
                int idUser = rs.getInt(USER_ID);
                String categoryName = rs.getString(CATEGORY_NAME);
                String title = rs.getString(TITLE);
                String text = rs.getString(TEXT);
                String date = rs.getString(DATE);
                String expire = rs.getString(EXPIRY);
                boolean isActive = rs.getBoolean(IS_ACTIVE);
                int amountOfLikes=getAllLike(idAdvertisement);

                Advertisement advertisement = new Advertisement(idAdvertisement,new User(idUser),
                        categoryName,title,text,date,expire,amountOfLikes,isActive);

                if(!advertisements.contains(advertisement)){
                    advertisements.add(advertisement);
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

        return advertisements;
    }
    public String getCategoryById(int categoryId) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String category=null;
        try {
            con = connectionPool.takeConnection();
            st = con.prepareStatement(SELECT_CATEGORY_BY_ID);
            st.setInt(1,categoryId);
            rs = st.executeQuery();
            while(rs.next()) {
                category = rs.getString(CATEGORY_NAME);
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
        return category;
    }
    public int getCategoryIdByName(String name) throws DaoException{
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int id=0;
        try {
            con = connectionPool.takeConnection();
            st = con.prepareStatement(SELECT_CATEGORY_BY_NAME);
            st.setString(1,name);
            rs = st.executeQuery();
            while(rs.next()) {
                id = rs.getInt(CATEGORY_ID);
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
        return id;
    }

    @Override
    public List<String> allCategory() throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        List<String> categories = null;
        try {
            con = connectionPool.takeConnection();

            st = con.prepareStatement(SELECT_ALL_CATEGORIES);
            rs = st.executeQuery();

            categories = new ArrayList<String>();
            while(rs.next()) {
                categories.add(rs.getString(CATEGORY_NAME));
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

        return categories;
    }

    @Override
    public List<Advertisement> foundByTitle(String search) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Advertisement> advertisements = null;
        try {
            con = connectionPool.takeConnection();

            st = con.prepareStatement(SELECT_ADVERTISEMENTS_BY_TITLE);
            st.setString(1,"%"+search+"%");
            rs = st.executeQuery();

            advertisements = new ArrayList<Advertisement>();
            while(rs.next()) {
                int idAdvertisement = rs.getInt(ADVERTISEMENT_ID);
                int idUser = rs.getInt(USER_ID);
                String categoryName = rs.getString(CATEGORY_NAME);
                String title = rs.getString(TITLE);
                String text = rs.getString(TEXT);
                String date = rs.getString(DATE);
                String expire = rs.getString(EXPIRY);
                boolean isActive = rs.getBoolean(IS_ACTIVE);
                int amountOfLikes=getAllLike(idAdvertisement);

                Advertisement advertisement = new Advertisement(idAdvertisement,new User(idUser),
                        categoryName,title,text,date,expire,amountOfLikes,isActive);

                if(!advertisements.contains(advertisement)){
                    advertisements.add(advertisement);
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

        return advertisements;
    }

    @Override
    public List<Advertisement> foundByCategory(String category) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Advertisement> advertisements = null;
        try {
            con = connectionPool.takeConnection();

            st = con.prepareStatement(SELECT_ADVERTISEMENTS_BY_CATEGORY);
            st.setString(1,category);
            rs = st.executeQuery();

            advertisements = new ArrayList<Advertisement>();
            while(rs.next()) {
                int idAdvertisement = rs.getInt(ADVERTISEMENT_ID);
                int idUser = rs.getInt(USER_ID);
                String categoryName = rs.getString(CATEGORY_NAME);
                String title = rs.getString(TITLE);
                String text = rs.getString(TEXT);
                String date = rs.getString(DATE);
                String expire = rs.getString(EXPIRY);
                boolean isActive = rs.getBoolean(IS_ACTIVE);
                int amountOfLikes=getAllLike(idAdvertisement);

                Advertisement advertisement = new Advertisement(idAdvertisement,new User(idUser),
                        categoryName,title,text,date,expire,amountOfLikes,isActive);

                if(!advertisements.contains(advertisement)){
                    advertisements.add(advertisement);
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

        return advertisements;
    }

    @Override
    public List<Advertisement> foundByCategoryTitle(String category, String search) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Advertisement> advertisements = null;
        try {
            con = connectionPool.takeConnection();

            st = con.prepareStatement(SELECT_ADVERTISEMENTS_BY_CATEGORY_AND_NAME);
            st.setString(1,category);
            st.setString(2,"%"+search+"%");
            rs = st.executeQuery();

            advertisements = new ArrayList<Advertisement>();
            while(rs.next()) {
                int idAdvertisement = rs.getInt(ADVERTISEMENT_ID);
                int idUser = rs.getInt(USER_ID);
                String categoryName = rs.getString(CATEGORY_NAME);
                String title = rs.getString(TITLE);
                String text = rs.getString(TEXT);
                String date = rs.getString(DATE);
                String expire = rs.getString(EXPIRY);
                boolean isActive = rs.getBoolean(IS_ACTIVE);
                int amountOfLikes=getAllLike(idAdvertisement);

                Advertisement advertisement = new Advertisement(idAdvertisement,new User(idUser),
                        categoryName,title,text,date,expire,amountOfLikes,isActive);

                if(!advertisements.contains(advertisement)){
                    advertisements.add(advertisement);
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

        return advertisements;
    }

    @Override
    public Advertisement advertisementByID(int id) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Advertisement advertisement=null;
        try {
            con = connectionPool.takeConnection();

            st = con.prepareStatement(SELECT_ADVERTISEMENTS_BY_ID);
            st.setInt(1, id);
            rs = st.executeQuery();

            while (rs.next()){
                int idAdvertisement = rs.getInt(ADVERTISEMENT_ID);
                int idUser = rs.getInt(USER_ID);
                String categoryName = rs.getString(CATEGORY_NAME);
                String title = rs.getString(TITLE);
                String text = rs.getString(TEXT);
                String date = rs.getString(DATE);
                String expire = rs.getString(EXPIRY);
                boolean isActive = rs.getBoolean(IS_ACTIVE);
                int amountOfLikes=getAllLike(idAdvertisement);

                advertisement = new Advertisement(idAdvertisement,new User(idUser),
                        categoryName,title,text,date,expire,amountOfLikes,isActive);
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
        return advertisement;
    }

    @Override
    public boolean addAdvertisement(Advertisement advertisement) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        boolean isAdvertisementAdded = false;
        try{
            con = connectionPool.takeConnection();

            st = con.prepareStatement(ADD_ADVERTISEMENT);
            st.setInt(1, advertisement.getUser().getIdUser());
            st.setInt(2, getCategoryIdByName(advertisement.getCategory()) );
            st.setString(3, advertisement.getTitle());
            st.setString(4, advertisement.getText());
            st.setBoolean(5, advertisement.isActive());
            st.setString(6, advertisement.getAdvertisementDate());
            st.setString(7, advertisement.getExpire());
            st.executeUpdate();
            isAdvertisementAdded=true;
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
        return isAdvertisementAdded;
    }

    @Override
    public boolean addLike(int idUser, int idAdvertisement) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        boolean isLikeAdded = false;
        try{
            con = connectionPool.takeConnection();

            st = con.prepareStatement(ADD_LIKE);
            st.setInt(1, idUser);
            st.setInt(2, idAdvertisement );
            st.executeUpdate();
            isLikeAdded=true;
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
        return isLikeAdded;
    }

    @Override
    public boolean updateAdvertisement(int idAdvertisement, Advertisement newAdvertisement) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        boolean isAdvertisementUpdated = false;
        try{
            con = connectionPool.takeConnection();

            st = con.prepareStatement(UPDATE_ADVERTISEMENT);
            st.setInt(1, getCategoryIdByName(newAdvertisement.getCategory()));
            st.setString(2, newAdvertisement.getTitle());
            st.setString(3, newAdvertisement.getText());
            st.setBoolean(4, newAdvertisement.isActive());
            st.setString(5, newAdvertisement.getAdvertisementDate());
            st.setString(6, newAdvertisement.getExpire());
            st.setInt(7, idAdvertisement);
            st.executeUpdate();
            isAdvertisementUpdated=true;
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
        return isAdvertisementUpdated;
    }

    @Override
    public boolean deleteAdvertisement(int id) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        boolean isBookDeleted = false;
        try {
            con = connectionPool.takeConnection();

            st = con.prepareStatement(DELETE_ADVERTISEMENT);
            st.setInt(1, id);
            st.executeUpdate();
            isBookDeleted = true;
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
        return isBookDeleted;
    }

    @Override
    public int advertisementAmount() throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        int amountOfBooks = 0;
        try{
            con = connectionPool.takeConnection();

            st = con.prepareStatement(SELECT_AMOUNT_OF_ADVERTISEMENTS);
            rs = st.executeQuery();
            if(rs.next()){
                amountOfBooks = rs.getInt(AMOUNT_ADVERTISEMENTS);
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
        return amountOfBooks;
    }
    public static void main(String[] args){
        //получить все объявления
//        List<Advertisement> advertisements = new ArrayList<Advertisement>();
//        try {
//            AdvertisementDaoImpl advertisementDao = new AdvertisementDaoImpl();
//            advertisements=advertisementDao.all();
//            for(Advertisement advertisement:advertisements)
//            System.out.println(advertisement.toString());
//        }catch(DaoException ex){}

        //добавить объявление
//        User user= new User(5);
//        Advertisement advertisement=new Advertisement(user,"Авто и транспорт","рлыалыапва",
//                "gffshsh","2015-12-25 15:32:06.427","2015-12-25 15:32:06.427",
//                0,true);
//        try {
//            AdvertisementDaoImpl advertisementDao = new AdvertisementDaoImpl();
//            System.out.println(advertisementDao.addAdvertisement(advertisement));
//        }catch(DaoException ex){}

        //добавить лайк
//        try {
//            AdvertisementDaoImpl advertisementDao = new AdvertisementDaoImpl();
//            System.out.println(advertisementDao.addLike(6,4));
//        }catch(DaoException ex){}

        //получить список объявлений по слову из заголовка
//                List<Advertisement> advertisements = new ArrayList<Advertisement>();
//        try {
//            AdvertisementDaoImpl advertisementDao = new AdvertisementDaoImpl();
//            advertisements=advertisementDao.foundByTitle("пва");
//            for(Advertisement advertisement:advertisements)
//            System.out.println(advertisement.toString());
//        }catch(DaoException ex){}

        // получить список объявлений по категории
//        List<Advertisement> advertisements = new ArrayList<Advertisement>();
//        try {
//            AdvertisementDaoImpl advertisementDao = new AdvertisementDaoImpl();
//            advertisements=advertisementDao.foundByCategory("Животные");
//            for(Advertisement advertisement:advertisements)
//            System.out.println(advertisement.toString());
//        }catch(DaoException ex){}

        //получить список объявлений по категории И имени
//        List<Advertisement> advertisements = new ArrayList<Advertisement>();
//        try {
//            AdvertisementDaoImpl advertisementDao = new AdvertisementDaoImpl();
//            advertisements=advertisementDao.foundByCategoryTitle("hj","кот");
//            for(Advertisement advertisement:advertisements)
//            System.out.println(advertisement.toString());
//        }catch(DaoException ex){}

        //кол-во объявлений
//        try {
//            AdvertisementDaoImpl advertisementDao = new AdvertisementDaoImpl();
//            System.out.println(advertisementDao.advertisementAmount());
//        }catch(DaoException ex){}

        // удалить объявление
//        try {
//            AdvertisementDaoImpl advertisementDao = new AdvertisementDaoImpl();
//            System.out.println(advertisementDao.deleteAdvertisement(3));
//        }catch(DaoException ex){}
        //удалить лайк
//        try {
//            AdvertisementDaoImpl advertisementDao = new AdvertisementDaoImpl();
//            System.out.println(advertisementDao.deleteLike(4,6));
//        }catch(DaoException ex){}

        //изменить объявление
//        User user= new User(5);
//        Advertisement advertisement=new Advertisement(user,"Авто и транспорт","Продаю машину",
//                "Продаю машину","2020-10-12 16:32:06.427","2021-10-12 16:32:06.427",
//                0,true);
//        try {
//            AdvertisementDaoImpl advertisementDao = new AdvertisementDaoImpl();
//            System.out.println(advertisementDao.updateAdvertisement(4,advertisement));
//        }catch(DaoException ex){}
    }
}
