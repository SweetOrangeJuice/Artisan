package com.sweetorangejuice.artisan.model;

/**
 * Created by as on 2017/5/14.
 */

public class Comment {
    private String objectId;
    private String account;
    private String text;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
