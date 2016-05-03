package com.nghianv.mnp_chat.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NguyenNghia on 4/3/2016.
 */
public class Post {
    private String id;
    private String content;
    private int like;
    private String publishDate;
    private List<String> comments;

    public Post(){
        comments = new ArrayList<>();
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }

    public void setLike(int like){
        this.like = like;
    }

    public int getLike(){
        return this.like;
    }

    public void setPublishDate(String publishDate){
        this.publishDate = publishDate;
    }

    public String getPublishDate(){
        return this.publishDate;
    }
}
