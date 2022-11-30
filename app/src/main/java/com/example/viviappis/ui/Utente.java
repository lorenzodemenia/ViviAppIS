package com.example.viviappis.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Utente {
    private final String name;
    private final String surname;
    private final String birth;
    private final String email;
    private String password;
    private Collection<Utente> friends;
    //private Collection<Evento> events;
    private int score;


    public Utente(String name, String surname, String birth, String email, String password){
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.email = email;
        this.password = password;
        this.friends = new ArrayList<>();
        //this.events = new ArrayList<>();
        this.score = 0;
    }

    public String getName(){
        return this.name;
    }

    public int getScore() {
        return score;
    }

    public Collection<Utente> getFriends() {
        return friends;
    }

    public String getBirth() {
        return birth;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public void setFriends(Collection<Utente> friends) {
        this.friends = friends;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static Map<String, Object> userMap(Utente u){
        Map<String, Object> user = new HashMap<>();
        user.put("name", u.getName());
        user.put("surname", u.getSurname());
        user.put("birthday", u.getBirth());
        user.put("email", u.getEmail());
        user.put("password", u.getPassword());
        user.put("friends", u.getFriends());
        user.put("score", u.getScore());
        return user;
    }
}
