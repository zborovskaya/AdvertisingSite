package by.zborovskaya.final_project.service;

import by.zborovskaya.final_project.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessages(int userIdFrom, int userIdTo) throws ServiceException;
    List<Integer> getUsersIdFrom(int userIdTo) throws ServiceException;
    boolean addMessage(Message message) throws ServiceException;
    Message messageByID(int id) throws ServiceException;
}
