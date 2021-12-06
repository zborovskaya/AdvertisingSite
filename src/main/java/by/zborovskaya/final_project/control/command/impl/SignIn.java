package by.zborovskaya.final_project.control.command.impl;

import by.zborovskaya.final_project.control.command.Command;
import by.zborovskaya.final_project.service.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignIn implements Command {

    private final static String ATTRIBUTE_URL = "url";
    private final static String ATTRIBUTE_LOCAL = "local";
    private final static String LOCAL_EN = "en";
    private final static String MAIN_INDEX = "/WEB-INF/jsp/main_index.jsp";
    private final static String SIGN_IN_REDIRECT = "Controller?command=signin";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ServiceException {
        HttpSession session = request.getSession(true);

        session.setAttribute(ATTRIBUTE_URL, SIGN_IN_REDIRECT);

        if(null == session.getAttribute(ATTRIBUTE_LOCAL)){
            session.setAttribute(ATTRIBUTE_LOCAL, LOCAL_EN);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(MAIN_INDEX);
        requestDispatcher.forward(request, response);
    }
}
