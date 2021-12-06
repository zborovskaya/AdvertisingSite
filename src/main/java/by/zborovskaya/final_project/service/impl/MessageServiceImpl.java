package by.zborovskaya.final_project.service.impl;

import by.zborovskaya.final_project.entity.Message;
import by.zborovskaya.final_project.service.MessageService;
import by.zborovskaya.final_project.service.ServiceException;

import java.util.List;

public class MessageServiceImpl implements MessageService {
    @Override
    public List<Message> getMessages(int userIdFrom, int userIdTo) throws ServiceException {
        return null;
    }

    @Override
    public List<Integer> getUsersIdFrom(int userIdTo) throws ServiceException {
        return null;
    }

    @Override
    public boolean addMessage(Message message) throws ServiceException {
        return false;
    }

    @Override
    public Message messageByID(int id) throws ServiceException {
        return null;
    }
}
