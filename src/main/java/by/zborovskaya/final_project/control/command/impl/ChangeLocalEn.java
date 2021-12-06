package by.zborovskaya.final_project.control.command.impl;

import by.zborovskaya.final_project.control.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeLocalEn implements Command {
    private final static String ATTRIBUTE_LOCAL = "local";
    private final static String ATTRIBUTE_URL = "url";
    private final static String LOCAL_EN = "en";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        session.setAttribute(ATTRIBUTE_LOCAL, LOCAL_EN);

        String requestPath = (String) session.getAttribute(ATTRIBUTE_URL);
        System.out.println("en "+requestPath);
        response.sendRedirect(requestPath);
    }
}