package by.zborovskaya.final_project.control.command.impl;

import by.zborovskaya.final_project.control.command.Command;
import by.zborovskaya.final_project.entity.User;
import by.zborovskaya.final_project.entity.UserRole;
import by.zborovskaya.final_project.service.AdvertisementService;
import by.zborovskaya.final_project.service.ServiceException;
import by.zborovskaya.final_project.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GoToIndexPage implements Command {
    private final static String INDEX_PAGE_REDIRECT = "Controller?command=gotoindexpage";
    private final static String ATTRIBUTE_USER = "user";
    private final static String MAIN_PAGE = "/WEB-INF/jsp/main.jsp";
    private final static String ATTRIBUTE_CATEGORIES = "categories";
    private final static String ATTRIBUTE_MESSAGE = "message";
    private final static String ERROR_MESSAGE = "ERROR";
    private final static String ADMINISTRATOR_PAGE = "/WEB-INF/jsp/administrator.jsp";
    private final static String ATTRIBUTE_LOCAL = "local";
    private final static String LOCAL_EN = "en";
    private final static String ATTRIBUTE_URL = "url";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        String redirectPath = null;


        if(null == session.getAttribute(ATTRIBUTE_USER) ||
                ((User)session.getAttribute(ATTRIBUTE_USER)).getUserRole() == UserRole.CLIENT){
            redirectPath = MAIN_PAGE;
        } else {
            redirectPath = ADMINISTRATOR_PAGE;
        }

        ServiceFactory factory = ServiceFactory.getInstance();
        AdvertisementService advertisementService=factory.getAdvertisementService();
        try{
            List<String> categories = advertisementService.getAllCategories();

            request.setAttribute(ATTRIBUTE_CATEGORIES, categories);
        } catch(ServiceException e){
            session.setAttribute(ATTRIBUTE_MESSAGE, ERROR_MESSAGE);
            response.sendRedirect(INDEX_PAGE_REDIRECT);
            return;
        }

        if(null == session.getAttribute(ATTRIBUTE_LOCAL)){
            session.setAttribute(ATTRIBUTE_LOCAL, LOCAL_EN);
        }

        session.setAttribute(ATTRIBUTE_URL, INDEX_PAGE_REDIRECT);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(redirectPath);
        requestDispatcher.forward(request, response);
    }

}
