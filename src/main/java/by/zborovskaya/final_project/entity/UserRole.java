package by.zborovskaya.final_project.entity;

public enum UserRole {
    CLIENT("client"),
    ADMINISTRATOR("administrator"),
    OVERVIEW("");
    private String value;
    UserRole (String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
