package by.zborovskaya.final_project.dao;


import by.zborovskaya.final_project.entity.ClientAdv;

import java.util.List;

public interface ClientDao {
    boolean	addClientData(ClientAdv client, int userID) throws DaoException;
    boolean	updateClientData(int userID, ClientAdv newClient) throws DaoException;
    List<ClientAdv> findAll() throws DaoException;
    int findClientById(int idClient) throws DaoException;
}
