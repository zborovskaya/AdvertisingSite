package by.zborovskaya.final_project.entity;

import java.util.ArrayList;
import java.util.List;

public class ClientAdv extends Entity {
    private User user;
    private String email;
    private String firstName;
    private String lastName;
    private int phone;
    private String city;
    private List<Advertisement> advertisements = new ArrayList<>();
    private List<Message> userMessage = new ArrayList<>();

    public ClientAdv(String email, String firstName, String lastName, int phone, String city) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.city=city;
    }

    public ClientAdv(User user, String email, String firstName, String lastName,
                     int phone, String city) {
        this.user = user;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.city = city;
        this.advertisements = advertisements;
        this.userMessage = userMessage;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Client{" +
                ", email='" + email + '\'' +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", phone=" + phone +
                ", city='" + city + '\'' +
                ", advertisements=" + advertisements +
                '}';
    }
}
