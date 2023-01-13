package com.example.viviappis.data.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Questa classe serve a gestire utente
 * @author jacopo
 * @version 1.0
 */
public class Utente
{
    private final String username;
    private final String name;
    private final String surname;
    private final String birth;
    private final String email;
    private String password;
    private int score;


    /**
     * Permette di costruire un oggetto di tipo Utente
     * @param name nome dell'utente
     * @param username username dell'utente
     * @param surname cognome dell'utente
     * @param birth data di nascita
     * @param email email dell'utente
     * @param password password dell'utente, salvata solo momentaneamente alla register
     * @param score rappresenta il valore di punteggio dell'utente
     */
    public Utente(String name, String surname, String username,  String birth, String email, String password, int score)
    {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.email = email;
        this.password = password;
        this.score = score;
    }
    /**
     * Permette di costruire un oggetto di tipo Utente
     * @param name nome dell'utente
     * @param surname cognome dell'utente
     * @param birth data di nascita
     * @param email email dell'utente
     * @param password password dell'utente, salvata solo momentaneamente alla register
     */
    public Utente(String name, String surname, String username, String birth, String email, String password){this(name, surname, username, birth,email,password, 0);}
    /**
     * Permette di costruire un oggetto di tipo Utente
     * @param a Rappresenta utente che serve per creare il nuovo utente
     */
    public Utente(Utente a){this(a.name, a.surname,a.username, a.birth, a.email, a.password, a.score);}
    /**
     * crea un utente a partire dalla mappa che rappresenta il documento nel database
     * @param user mappa del documento
     */
    public Utente(Map<String, Object> user)
    {
        this((String) user.get("name"), (String) user.get("surname"), (String)user.get("username"),
             (String) user.get("birthday"),(String) user.get("email"), "", ((Long) user.get("score")).intValue());
    }


    /**
     * Ritorna il valore del nome del utente
     * @return il valore del nome del utente
     */
    public String  getName() {return this.name;}

    /**
     * Ritorna il valore del Username del utente
     * @return il valore del Username del utente
     */
    public String  getUsername() {return this.username;}
    /**
     * Ritorna il valore del cognome del utente
     * @return il valore del cognome del utente
     */
    public String getSurname() {return surname;}
    /**
     * Ritorna il valore del punteggio del utente
     * @return il valore del punteggio del utente
     */
    public int getScore() {return score;}
    /**
     * Ritorna il valore della data di nascita del utente
     * @return il valore della data di nascita del utente
     */
    public String  getBirth() {return birth;}
    /**
     * Ritorna il valore della mail del utente
     * @return il valore della mail del utente
     */
    public String  getEmail(){return email;}
    /**
     * Ritorna il valore della password del utente
     * @return il valore della password del utente
     */
    public String getPassword() {return password;}

    /**
     * Permette di settare il valore dello score al valore passato
     * @param score valore da sostituire a score del utente
     */
    public void setScore(int score) {this.score = score;}


    /**
     * Permette di aggiungere al valore dello score il valore passato
     * @param score valore da aggiungere allo score dell'utente
     */
    public void addScore(int score) {this.score+= score;}
    /**
     * Permette di creare una mappa a partire dall'utente
     * @return La mappa che rappresenta l'utente
     */
    public Map<String, Object> toMap()
    {
        Map<String, Object> user = new HashMap<>();
        Field[] a = this.getClass().getDeclaredFields();


        user.put("name", this.getName());
        user.put("surname", this.getSurname());
        user.put("username", this.getUsername());
        user.put("birthday", this.getBirth());
        user.put("email", this.getEmail());
        user.put("score", this.getScore());

        return user;
    }

    /**
     * ritorna la stringa per stampare evento
     * @return stringa che rappresenta evento
     */
    @Override
    public String toString() {
        return "Utente{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birth='" + birth + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", score=" + score +
                '}';
    }
}
