package by.zborovskaya.final_project.entity;

public class User extends Entity{
    private UserRole userRole;
    //TODO Integer
    private  int idUser;
    private String login;
    private String password;
    private boolean isActive;

    public User(int idUser){
        this.idUser = idUser;
    }

    public User(int idUser,String role,  String login, String password, boolean isActive) {
        this.idUser = idUser;
        this.userRole = UserRole.valueOf(role);
        this.login = login;
        this.password = password;
        this.isActive = isActive;
    }
    public User(String role,  String login, String password, boolean isActive) {
        this.userRole = UserRole.valueOf(role);
        this.login = login;
        this.password = password;
        this.isActive = isActive;
    }


    public String getLogin() {
        return login;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getIdUser() {
        return idUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "userRole=" + userRole +
                ", idUser=" + idUser +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
