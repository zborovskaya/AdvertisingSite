package by.zborovskaya.final_project.service.impl;

import by.zborovskaya.final_project.dao.AdvertisementDao;
import by.zborovskaya.final_project.dao.DaoException;
import by.zborovskaya.final_project.dao.DaoFactory;
import by.zborovskaya.final_project.entity.Advertisement;
import by.zborovskaya.final_project.service.AdvertisementService;
import by.zborovskaya.final_project.service.ServiceException;
import by.zborovskaya.final_project.service.validation.AdvertisementValidator;
import by.zborovskaya.final_project.service.validation.exception.ValidatorException;

import java.util.List;

public class AdvertisementServiceImpl implements AdvertisementService {
    private final static String INPUT_ALL_DATA = "To add new advertisement you must input category,title and text.";

    @Override
    public List<Advertisement> takeAll() throws ServiceException {
        DaoFactory daoFactory=DaoFactory.getInstance();
        AdvertisementDao advertisementDao = daoFactory.getAdvertisementDao();

        List<Advertisement> advertisements;
        try {
            advertisements =advertisementDao.all();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return advertisements;
    }

    @Override
    public List<String> getAllCategories() throws ServiceException {
        DaoFactory daoFactory=DaoFactory.getInstance();
        AdvertisementDao advertisementDao = daoFactory.getAdvertisementDao();
        List<String> categories;
        try{
            categories = advertisementDao.allCategory();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return categories;
    }

    @Override
    public List<Advertisement> foundByTitle(String title) throws ServiceException {
        DaoFactory daoFactory=DaoFactory.getInstance();
        AdvertisementDao advertisementDao = daoFactory.getAdvertisementDao();
        List<Advertisement> foundAdvertisements;
        try {
            foundAdvertisements = advertisementDao.foundByTitle(title);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return foundAdvertisements;
    }

    @Override
    public List<Advertisement> foundByCategory(String category) throws ServiceException {
        DaoFactory daoFactory=DaoFactory.getInstance();
        AdvertisementDao advertisementDao = daoFactory.getAdvertisementDao();
        List<Advertisement> foundAdvertisements;
        try {
            foundAdvertisements = advertisementDao.foundByTitle(category);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return foundAdvertisements;
    }

    @Override
    public List<Advertisement> foundByCategoryTitle(String category, String title) throws ServiceException {
        DaoFactory daoFactory=DaoFactory.getInstance();
        AdvertisementDao advertisementDao = daoFactory.getAdvertisementDao();
        List<Advertisement> foundAdvertisements;
        try {
            foundAdvertisements = advertisementDao.foundByCategoryTitle(category,title);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return foundAdvertisements;
    }

    @Override
    public Advertisement advertisementByID(int id) throws ServiceException {
        DaoFactory daoFactory=DaoFactory.getInstance();
        AdvertisementDao advertisementDao = daoFactory.getAdvertisementDao();
        Advertisement advertisementByID;
        try{
            advertisementByID = advertisementDao.advertisementByID(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return advertisementByID;
    }

    @Override
    public boolean addAdvertisement(Advertisement advertisement) throws ServiceException, ValidatorException {

        if(!AdvertisementValidator.isNotEmptyFields(advertisement)){
            throw new ValidatorException(INPUT_ALL_DATA);
        }

        DaoFactory daoFactory=DaoFactory.getInstance();
        AdvertisementDao advertisementDao = daoFactory.getAdvertisementDao();

        boolean isAdvertisementAdded = false;
        try{
            isAdvertisementAdded = advertisementDao.addAdvertisement(advertisement);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return  isAdvertisementAdded;
    }

    @Override
    public int getAllLike(int idAdvertisement) throws ServiceException {
        DaoFactory daoFactory=DaoFactory.getInstance();
        AdvertisementDao advertisementDao = daoFactory.getAdvertisementDao();
        int likeAmount = 0;
        try{
            likeAmount = advertisementDao.getAllLike(idAdvertisement);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return likeAmount;
    }

    @Override
    public boolean addLike(int idUser, int idAdvertisement) throws ServiceException {
        DaoFactory daoFactory=DaoFactory.getInstance();
        AdvertisementDao advertisementDao = daoFactory.getAdvertisementDao();

        boolean isLikeAdded = false;
        try{
            isLikeAdded = advertisementDao.addLike(idUser,idAdvertisement);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return  isLikeAdded;
    }

    @Override
    public boolean deleteLike(int idUser, int idAdvertisement) throws ServiceException {
        DaoFactory daoFactory=DaoFactory.getInstance();
        AdvertisementDao advertisementDao = daoFactory.getAdvertisementDao();
        boolean isLikeDeleted = false;

        try{
            isLikeDeleted = advertisementDao.deleteLike(idUser,idAdvertisement);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isLikeDeleted;
    }

    @Override
    public boolean updateAdvertisement(int idAdvertisement, Advertisement newAdvertisement)
            throws ServiceException, ValidatorException {
        if(!AdvertisementValidator.isNotEmptyFields(newAdvertisement)){
            throw new ValidatorException(INPUT_ALL_DATA);
        }

        DaoFactory daoFactory=DaoFactory.getInstance();
        AdvertisementDao advertisementDao = daoFactory.getAdvertisementDao();
        boolean isAdvertisementChanged = false;

        try{
            isAdvertisementChanged = advertisementDao.updateAdvertisement(idAdvertisement,newAdvertisement);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return  isAdvertisementChanged;
    }

    @Override
    public boolean deleteAdvertisement(int id) throws ServiceException {
        DaoFactory daoFactory=DaoFactory.getInstance();
        AdvertisementDao advertisementDao = daoFactory.getAdvertisementDao();
        boolean isAdvertisementDeleted = false;

        try{
            isAdvertisementDeleted = advertisementDao.deleteAdvertisement(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isAdvertisementDeleted;
    }

    @Override
    public int advertisementAmount() throws ServiceException {
        DaoFactory daoFactory=DaoFactory.getInstance();
        AdvertisementDao advertisementDao = daoFactory.getAdvertisementDao();
        int amount = 0;

        try{
            amount = advertisementDao.advertisementAmount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return amount;
    }
}
