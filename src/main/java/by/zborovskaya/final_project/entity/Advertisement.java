package by.zborovskaya.final_project.entity;

public class Advertisement extends Entity{
    private int idAdvertisement;
    private User user;
    private String category;
    private String title;
    private String text;
    private String advertisementDate;
    private String expire;
    private int numberOfLikes;
    private boolean isActive;

    public Advertisement(int idAdvertisement, User user, String category,
                         String title, String text, String advertisementDate, String expire,
                         int numberOfLikes,boolean isActive) {
        this.idAdvertisement = idAdvertisement;
        this.user = user;
        this.category = category;
        this.title = title;
        this.text = text;
        this.advertisementDate = advertisementDate;
        this.expire = expire;
        this.numberOfLikes = numberOfLikes;
        this.isActive=isActive;
    }

    public Advertisement( User user, String category,
                         String title, String text, String advertisementDate, String expire,
                         int numberOfLikes,boolean isActive) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.text = text;
        this.advertisementDate = advertisementDate;
        this.expire = expire;
        this.numberOfLikes = numberOfLikes;
        this.isActive=isActive;
    }

    public int getIdAdvertisement() {
        return idAdvertisement;
    }

    public User getUser() {
        return user;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getAdvertisementDate() {
        return advertisementDate;
    }

    public String getExpire() {
        return expire;
    }

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "idAdvertisement=" + idAdvertisement +
                ", user=" + user +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", advertisementDate='" + advertisementDate + '\'' +
                ",expire='" + expire + '\'' +
                ", numberOfLikes=" + numberOfLikes +
                ", isActive=" + isActive +
                '}';
    }
}
