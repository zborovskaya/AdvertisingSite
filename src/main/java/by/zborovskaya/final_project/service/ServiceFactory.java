package by.zborovskaya.final_project.service;

import by.zborovskaya.final_project.service.impl.AdvertisementServiceImpl;
import by.zborovskaya.final_project.service.impl.ClientServiceImpl;
import by.zborovskaya.final_project.service.impl.MessageServiceImpl;
import by.zborovskaya.final_project.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final AdvertisementService advertisementService = new AdvertisementServiceImpl();
    private final ClientService clientService=new ClientServiceImpl();
    private final MessageService messageService=new MessageServiceImpl();
    private final UserService userService= new UserServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public AdvertisementService getAdvertisementService() {
        return advertisementService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public UserService getUserService() {
        return userService;
    }
}
