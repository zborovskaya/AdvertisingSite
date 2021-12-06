package by.zborovskaya.final_project.control.command.impl;

import by.zborovskaya.final_project.control.command.Command;
import by.zborovskaya.final_project.entity.User;
import by.zborovskaya.final_project.service.ServiceException;
import by.zborovskaya.final_project.service.ServiceFactory;
import by.zborovskaya.final_project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Runner implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException {
        User user = null;
        PrintWriter writer = response.getWriter();
        writer.println("<h2> NO ok</h2>");
        ServiceFactory factory=ServiceFactory.getInstance();
        UserService userService= factory.getUserService();
        try {
            user = userService.authorization("user1", "21232F297A57A5A7678");

        } catch (ServiceException e) {

        }

        writer.println("<h2> "+user.toString()+"</h2>");
    }
}
