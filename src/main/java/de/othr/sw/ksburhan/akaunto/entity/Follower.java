package de.othr.sw.ksburhan.akaunto.entity;

import javax.persistence.*;

@Entity(name = "Followers")
public class Follower {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="from_account_fk")
    private Account from;

    @ManyToOne
    @JoinColumn(name="to_account_fk")
    private Account to;

    public Follower() {};

    public Follower(Account from, Account to) {
        this.from = from;
        this.to = to;
    }

}
