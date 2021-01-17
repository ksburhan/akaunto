package de.othr.sw.ksburhan.akaunto.entity;

import javax.persistence.*;

@Entity
@Table(name = "ads")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String pictureUrl;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private int clicks;

    public Advertisement(String description, String url, String pictureUrl, String provider) {
        this.description = description;
        this.url = url;
        this.pictureUrl = pictureUrl;
        this.provider = provider;
    }

    public Advertisement() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public void incrementClicks() {
        this.clicks++;
    }
}
