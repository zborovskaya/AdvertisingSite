package by.zborovskaya.final_project.entity;

public class Message extends Entity{
    private int idMessage;
    private User userFrom;
    private User userTo;
    private String message;
    private String time;

    public Message(int idMessage, User userFrom, User userTo, String message, String time) {
        this.idMessage = idMessage;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.message = message;
        this.time = time;
    }

    public Message(User userFrom, User userTo, String message, String time) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.message = message;
        this.time = time;
    }
    public Message(int idMessage) {
        this.idMessage = idMessage;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "idMessage=" + idMessage +
                ", userFrom=" + userFrom.toString() +
                ", userTo=" + userTo.toString() +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
