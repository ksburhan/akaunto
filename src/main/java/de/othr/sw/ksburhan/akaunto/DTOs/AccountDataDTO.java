package de.othr.sw.ksburhan.akaunto.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.othr.sw.ksburhan.akaunto.entity.Account;

import javax.persistence.Column;

public class AccountDataDTO {

    private Account account;
    private String favouriteSport;
    private String hometown;

    public AccountDataDTO() {
    }

    public AccountDataDTO(@JsonProperty String favouriteSport, @JsonProperty String hometown) {
        this.favouriteSport = favouriteSport;
        this.hometown = hometown;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getFavouriteSport() {
        return favouriteSport;
    }

    public void setFavouriteSport(String favouriteSport) {
        this.favouriteSport = favouriteSport;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }
}
