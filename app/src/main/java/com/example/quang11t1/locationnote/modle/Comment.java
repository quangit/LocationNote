package com.example.quang11t1.locationnote.modle;

import java.util.Date;

/**
 * Created by luongvien_binhson on 20-Nov-15.
 */
public class Comment {
    private int idComment;
    private String account;
    private String content;
    private Date time;

    public Comment() {

    }

    public Comment(int idComment, String account, String content, Date time) {
        this.idComment = idComment;
        this.account = account;
        this.content = content;
        this.time = time;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
