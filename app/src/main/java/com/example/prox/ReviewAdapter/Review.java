package com.example.prox.ReviewAdapter;

public class Review {
    String username = "";
    String rating = "";
    String feedback = "";

    public Review(String name, String store, String price) {
        this.username = name;
        this.rating = store;
        this.feedback = price;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

