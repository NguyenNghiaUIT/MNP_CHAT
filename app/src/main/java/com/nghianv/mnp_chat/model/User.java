package com.nghianv.mnp_chat.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NguyenNghia on 4/3/2016.
 */
public class User {
    private String id;
    private String name;
    private int avatar;

    private int countFriends;
    private String dayOfBirth;
    private String about;

    private List<User> friends = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();

    public User(){

    }

    public User(String id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setAvatar(int avatar){
        this.avatar = avatar;
    }

    public int getAvatar(){
        return this.avatar;
    }

    public void setDayOfBirth(String dayOfBirth){
        this.dayOfBirth = dayOfBirth;
    }

    public String getDayOfBirth(){
        return this.dayOfBirth;
    }

    public void setAbout(String about){
        this.about = about;
    }

    public void addPost(Post post){
        posts.add(post);
    }

    public void addFriend(User friend){
        friends.add(friend);
    }

    public List<Post> getPosts(){
        return this.posts;
    }

    public List<User> getFriends(){
        return this.friends;
    }

    public int getCountFriends(){
        return this.friends.size();
    }
}
