package by.zborovskaya.final_project.service;

import by.zborovskaya.final_project.entity.Advertisement;
import by.zborovskaya.final_project.service.validation.exception.ValidatorException;

import java.util.List;

public interface AdvertisementService {
    List<Advertisement> takeAll()  throws ServiceException;
    List<String> getAllCategories()  throws ServiceException;
    List<Advertisement> foundByTitle(String title) throws ServiceException;
    List<Advertisement> foundByCategory(String category) throws ServiceException;
    List<Advertisement> foundByCategoryTitle(String category,String title) throws ServiceException;
    Advertisement advertisementByID(int id) throws ServiceException;
    boolean addAdvertisement(Advertisement advertisement) throws ServiceException, ValidatorException;
    int getAllLike (int idAdvertisement) throws ServiceException;
    boolean addLike(int idUser, int idAdvertisement) throws ServiceException;
    boolean deleteLike(int idUser, int idAdvertisement) throws ServiceException;
    boolean updateAdvertisement(int idAdvertisement, Advertisement newAdvertisement)
            throws ServiceException, ValidatorException;
    boolean deleteAdvertisement(int id) throws ServiceException;
    int advertisementAmount() throws ServiceException;
}
