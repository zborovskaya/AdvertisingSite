package by.zborovskaya.final_project.control.command.impl;

import by.zborovskaya.final_project.control.command.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Registration implements Command {

    private final static String ATTRIBUTE_URL = "url";
    private final static String ATTRIBUTE_LOCAL = "local";
    private final static String LOCAL_EN = "en";
    private final static String REGISTRATION_REDIRECT = "Controller?command=registration";
    private final static String REGISTRATION = "/WEB-INF/jsp/registration.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        session.setAttribute(ATTRIBUTE_URL, REGISTRATION_REDIRECT);

        if(null == session.getAttribute(ATTRIBUTE_LOCAL)){
            session.setAttribute(ATTRIBUTE_LOCAL, LOCAL_EN);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(REGISTRATION);
        requestDispatcher.forward(request, response);

    }

}
