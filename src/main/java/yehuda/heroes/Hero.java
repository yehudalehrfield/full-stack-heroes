package yehuda.heroes;

import jakarta.persistence.*;

@Entity
@Table(name = "heroes")
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "isAlive")
    private boolean isAlive;

    public Hero() {

    }

    public Hero(String title, String description, boolean isAlive) {
        this.title = title;
        this.description = description;
        this.isAlive = isAlive;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setLiveness() {
        setLiveness(false);
    }

    public void setLiveness(boolean isAlive) {
        this.isAlive = isAlive;
    }

    @Override
    public String toString() {
        return "Hero [id=%d, title=%s, desc=%s, isAlive=%s]".formatted(id, title, description, isAlive);
    }

}
