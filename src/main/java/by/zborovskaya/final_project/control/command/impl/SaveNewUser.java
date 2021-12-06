package by.zborovskaya.final_project.control.command.impl;

import by.zborovskaya.final_project.control.command.Command;
import by.zborovskaya.final_project.entity.ClientAdv;
import by.zborovskaya.final_project.entity.User;
import by.zborovskaya.final_project.entity.UserRole;
import by.zborovskaya.final_project.service.ClientService;
import by.zborovskaya.final_project.service.ServiceException;
import by.zborovskaya.final_project.service.ServiceFactory;
import by.zborovskaya.final_project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SaveNewUser implements Command {

    private final static String LOCAL_BASE_NAME = "localisation/local";
    private final static String ATTRIBUTE_LOCAL = "local";
    private final static String REGISTRATION_REDIRECT = "Controller?command=registration";
    private final static String INDEX_PAGE_REDIRECT = "Controller?command=gotoindexpage";
    private final static String PARAM_LOGIN = "login";
    private final static String PARAM_PASSWORD = "password";
    private final static String PARAM_FIRST_NAME = "fName";
    private final static String PARAM_LAST_NAME = "lName";
    private final static String PARAM_EMAIL = "email";
    private final static String PARAM_CITY = "city";
    private final static String PARAM_PHONE = "phone";
    private final static String ATTRIBUTE_MESSAGE = "message";
    private final static String LOGIN_EXISTS = "local.loginExists";
    private final static String ERROR = "local.error";
    private final static String SUCCESSFUL_REG = "local.succRegistration";
    private final static String UNSUCC_REG = "local.unsuccReg";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        String clientLogin = request.getParameter(PARAM_LOGIN);
        String clientPassword = request.getParameter(PARAM_PASSWORD);
        String clientFirstName = request.getParameter(PARAM_FIRST_NAME);
        String clientLastName = request.getParameter(PARAM_LAST_NAME);
        String clientEmail = request.getParameter(PARAM_EMAIL);

        User user = new User("CLIENT",clientLogin,clientPassword,true);
        ClientAdv clientAdv = new ClientAdv( user,
                clientEmail,clientFirstName,clientLastName,0,null);

        ServiceFactory factory=ServiceFactory.getInstance();
        UserService userService= factory.getUserService();
        ClientService clientService= factory.getClientService();

        Locale locale = new Locale((String) session.getAttribute(ATTRIBUTE_LOCAL));
        ResourceBundle resourceBundle = ResourceBundle.getBundle(LOCAL_BASE_NAME, locale);

        try{
            boolean isUserRegistrated = userService.registration(user);

            if(!isUserRegistrated){
                session.setAttribute(ATTRIBUTE_MESSAGE, resourceBundle.getString(LOGIN_EXISTS));
                response.sendRedirect(REGISTRATION_REDIRECT);
                return;
            }

            session.setAttribute(ATTRIBUTE_MESSAGE, resourceBundle.getString(SUCCESSFUL_REG));
            response.sendRedirect(INDEX_PAGE_REDIRECT);
        } catch (ServiceException e) {
            session.setAttribute(ATTRIBUTE_MESSAGE, resourceBundle.getString(UNSUCC_REG));
            response.sendRedirect(REGISTRATION_REDIRECT);
            return;
        }
    }

}
