package by.zborovskaya.final_project.service;

import by.zborovskaya.final_project.entity.User;
import by.zborovskaya.final_project.service.validation.exception.ValidatorException;

public interface UserService {
    User authorization (String login, String password) throws ServiceException;
    boolean	registration(User user) throws ServiceException;
    User getUserID(int userID) throws ServiceException;
    boolean changePassword(String oldPassword, String newPassword,String confirmedPassword, int userID)
            throws ServiceException;
}
