package by.zborovskaya.final_project.control.command.impl;

import by.zborovskaya.final_project.control.command.Command;
import by.zborovskaya.final_project.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class GoToClientAccount implements Command {
    private final static String ERROR_PAGE = "/WEB-INF/jsp/error.jsp";
    private final static String USER = "user";
    private final static String AUTHORIZATION = "auth";
    private final static String ATTRIBUTE_URL = "url";
    private final static String CLIENT_ACC_REDIRECT = "Controller?command=gotoclientaccount";
    private final static String CLIENT_ACC = "/WEB-INF/jsp/reader_account.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ServiceException {

                PrintWriter writer = response.getWriter();
        writer.println("<h2> Hello Client!</h2>");
    }
}

