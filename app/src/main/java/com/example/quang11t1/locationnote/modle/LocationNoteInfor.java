package com.example.quang11t1.locationnote.modle;

public class LocationNoteInfor {
    int idImage;
    String userName;
    String address;
    String comment;
    int imageLike;
    int imageComment;
    String timePost;
    String numberOfLike;
    String getNumberOfComment;

    public LocationNoteInfor() {
    }

    public String getNumberOfLike() {
        return numberOfLike;
    }

    public void setNumberOfLike(String numberOfLike) {
        this.numberOfLike = numberOfLike;
    }

    public String getGetNumberOfComment() {
        return getNumberOfComment;
    }

    public void setGetNumberOfComment(String getNumberOfComment) {
        this.getNumberOfComment = getNumberOfComment;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getTimePost() {
        return timePost;
    }

    public void setTimePost(String timePost) {
        this.timePost = timePost;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getImageLike() {
        return imageLike;
    }

    public void setImageLike(int imageLike) {
        this.imageLike = imageLike;
    }

    public int getImageComment() {
        return imageComment;
    }

    public void setImageComment(int imageComment) {
        this.imageComment = imageComment;
    }
}
