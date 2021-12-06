package by.zborovskaya.final_project.service;

import by.zborovskaya.final_project.entity.ClientAdv;
import by.zborovskaya.final_project.service.validation.exception.ValidatorException;

import java.util.List;

public interface ClientService {
    boolean	addClientData(ClientAdv client, int userID) throws ServiceException, ValidatorException;
    boolean	updateClientData(int userID, ClientAdv newClient) throws ServiceException, ValidatorException ;
    List<ClientAdv> findAll() throws ServiceException;
    int findClientById(int idClient) throws ServiceException;
}
