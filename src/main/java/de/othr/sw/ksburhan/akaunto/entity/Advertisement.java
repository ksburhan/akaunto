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
    private String provider;

    @Column(nullable = false)
    private int clicks;

    public Advertisement(Long id, String description, String url, String provider, int clicks) {
        this.id = id;
        this.description = description;
        this.url = url;
        this.provider = provider;
        this.clicks = clicks;
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
