package by.zborovskaya.final_project.dao;

import by.zborovskaya.final_project.entity.Advertisement;


import java.util.List;

public interface AdvertisementDao {
    List<Advertisement> all()  throws DaoException;
    List<String> allCategory()  throws DaoException;
    List<Advertisement> foundByTitle(String title) throws DaoException;
    List<Advertisement> foundByCategory(String category) throws DaoException;
    List<Advertisement> foundByCategoryTitle(String category,String title) throws DaoException;
    Advertisement advertisementByID(int id) throws DaoException;
    boolean addAdvertisement(Advertisement advertisement) throws DaoException;
    int getAllLike (int idAdvertisement) throws DaoException;
    boolean addLike(int idUser, int idAdvertisement) throws DaoException;
    boolean deleteLike(int idUser, int idAdvertisement) throws DaoException;
    boolean updateAdvertisement(int idAdvertisement, Advertisement newAdvertisement) throws DaoException;
    boolean deleteAdvertisement(int id) throws DaoException;
    int advertisementAmount() throws DaoException;
}
