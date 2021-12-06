package by.zborovskaya.final_project.service.impl;

import by.zborovskaya.final_project.dao.ClientDao;
import by.zborovskaya.final_project.dao.DaoException;
import by.zborovskaya.final_project.dao.DaoFactory;
import by.zborovskaya.final_project.entity.ClientAdv;
import by.zborovskaya.final_project.service.ClientService;
import by.zborovskaya.final_project.service.ServiceException;
import by.zborovskaya.final_project.service.validation.ClientInfoValidator;
import by.zborovskaya.final_project.service.validation.exception.ValidatorException;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private final static String INVALID_NAME = "";
    private final static String INVALID_PHONE = "";

    @Override
    public boolean addClientData(ClientAdv client, int userID) throws ServiceException, ValidatorException {
        String firstName = client.getFirstName();
        String lastName = client.getLastName();
        int phone = client.getPhone();
        String email= client.getEmail();

        if(!ClientInfoValidator.isFirstNameValid(firstName)){
            throw new ValidatorException(INVALID_NAME);
        }

        if(!ClientInfoValidator.isLastNameValid(lastName)){
            throw new ValidatorException(INVALID_NAME);
        }

        if(!ClientInfoValidator.isPhoneValid(String.valueOf(phone))){
            throw new ValidatorException(INVALID_NAME);
        }
        if(!ClientInfoValidator.isEmailValid(email)){
            throw new ValidatorException(INVALID_NAME);
        }

        DaoFactory daoFactory=DaoFactory.getInstance();
        ClientDao clientDao = daoFactory.getClientDao();
        boolean isClientDataAdded = false;
        try {
            isClientDataAdded = clientDao.addClientData(client,userID);
        }catch(DaoException e) {
            throw new ServiceException(e);
        }

        return isClientDataAdded;
    }

    @Override
    public boolean updateClientData(int userID, ClientAdv newClient) throws ServiceException, ValidatorException {
        String firstName = newClient.getFirstName();
        String lastName = newClient.getLastName();
        int phone = newClient.getPhone();
        String email= newClient.getEmail();

        if(!ClientInfoValidator.isFirstNameValid(firstName)){
            throw new ValidatorException(INVALID_NAME);
        }

        if(!ClientInfoValidator.isLastNameValid(lastName)){
            throw new ValidatorException(INVALID_NAME);
        }

        if(!ClientInfoValidator.isPhoneValid(String.valueOf(phone))){
            throw new ValidatorException(INVALID_NAME);
        }
        if(!ClientInfoValidator.isEmailValid(email)){
            throw new ValidatorException(INVALID_NAME);
        }

        DaoFactory daoFactory=DaoFactory.getInstance();
        ClientDao clientDao = daoFactory.getClientDao();
        boolean isClientDataUpdated = false;
        try {
            isClientDataUpdated = clientDao.updateClientData(userID,newClient);
        }catch(DaoException e) {
            throw new ServiceException(e);
        }

        return isClientDataUpdated;
    }

    @Override
    public List<ClientAdv> findAll() throws ServiceException {
        return null;
    }

    @Override
    public int findClientById(int idClient) throws ServiceException {
        return 0;
    }
}
