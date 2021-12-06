package by.zborovskaya.final_project.control.command.impl;

import by.zborovskaya.final_project.control.command.Command;
import by.zborovskaya.final_project.entity.Advertisement;
import by.zborovskaya.final_project.service.AdvertisementService;
import by.zborovskaya.final_project.service.ServiceException;
import by.zborovskaya.final_project.service.impl.AdvertisementServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ShowAllAdvertisement implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ServiceException {
        PrintWriter writer = response.getWriter();
        AdvertisementService advertisementService= new AdvertisementServiceImpl();
        List<Advertisement> advertisements=advertisementService.takeAll();
        for(Advertisement advertisement: advertisements) {
            writer.println("<h2>" + advertisement.getTitle() + "</h2>");
        }
    }
}
