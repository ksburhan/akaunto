package de.othr.sw.ksburhan.akaunto.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long authorID;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long date;

    public Post() {}

    public Post(Long id, Long authorID, String content) {
        this.id = id;
        this.authorID = authorID;
        this.content = content;
        this.date = System.currentTimeMillis();
    }

    // Getter/Setter equals/hashCode toString compareTo

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", authorID='" + authorID + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(Long authorID) {
        this.authorID = authorID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
    public void setCurrentDate() {
        this.date = System.currentTimeMillis();
    }
}
