package by.zborovskaya.final_project.dao;

import by.zborovskaya.final_project.entity.User;

public interface UserDao {
    User authorization (String login, String password) throws DaoException;
    boolean	registration(User user) throws DaoException;
    User getUserID(int userID) throws DaoException;
    boolean changePassword(String oldPassword, String newPassword,
                           int userID) throws DaoException;
}
