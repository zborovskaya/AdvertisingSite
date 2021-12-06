package by.zborovskaya.final_project.control;

import by.zborovskaya.final_project.control.command.Command;
import by.zborovskaya.final_project.control.command.CommandProvider;
import by.zborovskaya.final_project.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/Controller")
public class Controller extends HttpServlet {

    private static final String PARAM_COMMAND = "command";
//    private final static String ERROR_PAGE = "/WEB-INF/error.jsp";

    private final CommandProvider provider = new CommandProvider();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        PrintWriter writer = resp.getWriter();
//        writer.println("<h2>" +req.getParameter("command") + "</h2>");
        executeTask(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ShowAllAdvertisement com=new ShowAllAdvertisement();
//        PrintWriter writer = resp.getWriter();
//        writer.println("<h2>" +req.getParameter("command") + "</h2>");
        executeTask(req,resp);
    }

    public void executeTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String name;
        Command command;

        name = request.getParameter(PARAM_COMMAND);
        command = provider.getCommand(name);

        try {
            command.execute(request,response);
        } catch (ServiceException e) {
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher(ERROR_PAGE);
//            requestDispatcher.forward(request, response);
        }
    }

}
