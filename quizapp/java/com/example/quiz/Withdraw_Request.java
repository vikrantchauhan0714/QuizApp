package com.example.quiz;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Withdraw_Request {
    private String userId;
    private String emailAddress;
    private String requestedBy;
    private long coins ;

    @ServerTimestamp
    private Date createdDate;


    public Withdraw_Request() {
    }
    public Withdraw_Request(String userId, String emailAddress, String requestedBy,long coins) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.requestedBy = requestedBy;
        this.coins=coins;



    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }
}
