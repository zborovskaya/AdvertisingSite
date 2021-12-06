package by.zborovskaya.final_project.dao;

import by.zborovskaya.final_project.dao.impl.AdvertisementDaoImpl;
import by.zborovskaya.final_project.dao.impl.ClientDaoImpl;
import by.zborovskaya.final_project.dao.impl.MessageDaoImpl;
import by.zborovskaya.final_project.dao.impl.UserDaoImpl;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();

    private final UserDao userDao = new UserDaoImpl();
    private final ClientDao clientDao = new ClientDaoImpl();
    private final AdvertisementDao advertisementDao=new AdvertisementDaoImpl();
    private final MessageDao messageDao=new MessageDaoImpl();

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    public AdvertisementDao getAdvertisementDao() {
        return advertisementDao;
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }
}
