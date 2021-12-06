package by.zborovskaya.final_project.dao;

import by.zborovskaya.final_project.entity.Message;

import java.util.List;

public interface MessageDao {
    List<Message> getMessages(int userIdFrom, int userIdTo) throws DaoException;
    List<Integer> getUsersIdFrom(int userIdTo) throws DaoException;
    boolean addMessage(Message message) throws DaoException;
    Message messageByID(int id) throws DaoException;

}
