package by.zborovskaya.final_project.control.command.impl;

import by.zborovskaya.final_project.control.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Logout  implements Command {

    private final static String USER = "user";
    private final static String AUTHORIZATION = "auth";
    private final static String LOCAL_BASE_NAME = "localisation/local";
    private final static String ATTRIBUTE_LOCAL = "local";
    private final static String INDEX_PAGE_REDIRECT = "Controller?command=gotoindexpage";
    private final static String ATTRIBUTE_URL = "url";
    private final static String ATTRIBUTE_MESSAGE = "message";
    private final static String SUCCESSFUL_LOGOUT = "local.successfulLogout";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if(session != null) {
            session.removeAttribute(AUTHORIZATION);
            session.removeAttribute(USER);
        }

        Locale locale = new Locale((String) session.getAttribute(ATTRIBUTE_LOCAL));
        ResourceBundle resourceBundle = ResourceBundle.getBundle(LOCAL_BASE_NAME, locale);

        session.setAttribute(ATTRIBUTE_URL, INDEX_PAGE_REDIRECT);

        session.setAttribute(ATTRIBUTE_MESSAGE, resourceBundle.getString(SUCCESSFUL_LOGOUT));
        response.sendRedirect(INDEX_PAGE_REDIRECT);

    }

}
