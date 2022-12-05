package com.example.viviappis.data.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Evento
{
    private String name;
    private String description;
    private final Utente creator;
    private String date;
    private final String password;
    private Collection<Utente> partecipants;
    private boolean isPublic;


    public Evento (String name, String description, Utente creator, String date, String password, boolean isPublic){
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.password = password;
        this.date = date;
        this.partecipants = new ArrayList<>();
        this.isPublic = isPublic;
    }

    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getDate() {
        return date;
    }
    public Collection<Utente> getPartecipants() {
        return partecipants;
    }
    public String getDescription() {
        return description;
    }
    public Utente getCreator() {
        return creator;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void addPartecipants(Utente u) {
        this.partecipants.add(u);
    }

    public boolean isPublic() {
        return isPublic;
    }


    public static Map<String, Object> eventMap(Evento e){
        Map<String, Object> event = new HashMap<>();
        event.put("name", e.getName());
        event.put("description", e.getDescription());
        event.put("date", e.getDate());
        event.put("creator", e.getCreator());
        event.put("password", e.getPassword());
        event.put("partecipants", e.getPartecipants());
        event.put("public", e.isPublic());
        return event;
    }
}
