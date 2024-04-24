package com.example.csit228_f1_v2;

public class Tweet {
    public int tweetid;
    public int uid;

    public String body;
    public int like;

    public Tweet(int tweetid, int uid, String body, int like) {
        this.tweetid = tweetid;
        this.uid = uid;
        this.body = body;
        this.like = like;
    }

}
