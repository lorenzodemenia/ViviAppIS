package com.example.viviappis;

public class GenericEvent extends Evento{
    int minPart;
    int maxPart;
    public GenericEvent(String name, String description, Utente creator, String date, String password, boolean isPublic, int minPart, int maxPart) {
        super(name, description, creator, date, password, isPublic);
        this.minPart=minPart;
        this.maxPart=maxPart;
    }
}
