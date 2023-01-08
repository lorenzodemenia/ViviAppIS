package com.example.viviappis.data.model.MacroAree.AreaA;


import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.Utente;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class MacroA extends Evento
{
    private List<Utente> Giocatori;
    private Map<Utente, Integer> ranklist;
    private final int points;
    private List<Utente> winners;

    /*
     * ********** elim
     * ********** inserire tutte le classi del tipo palla prigioniera ... dentro array.xml
     * **********
     */
    /**
     * Constructor of object MacroA
     * @param name name of the event
     * @param description description of the event
     * @param creator user creator of the event
     * @param date time of the event
     * @param password password to log into the event
     * @param isPublic define if the event is public or not
     * @param minPart define the minimum number of partecipants
     * @param maxPart define the maximum number of partecipants
     * @param points points gained for the victory
     */
    public MacroA(String name, String description, String creator, String date, String password, boolean isPublic, int minPart, int maxPart, int points, String luogo) {
        super(name, description, creator, date, password, isPublic, minPart, maxPart, luogo);
        this.points = points; //consigliato: mettere un numero >= 8
        this.Giocatori = new ArrayList<>();
        this.ranklist = new HashMap<>();
        this.winners = new ArrayList<>();
    }

    /**
     * Add one User to "Giocatori"
     * @param u
     * */
    public void addToGiocatori(Utente u){
        this.Giocatori.add(u);
    }

    /**
     * Add one User to "winners"
     * @param u
     * */
    public void addToWinners(Utente u){
        this.winners.add(u);
    }

    /**
     * Return the collection of all users that belong to "Giocatori"
     * @return
     */
    public List<Utente> getGiocatori() {
        return this.Giocatori;
    }

    /**
     * Return the collection of all users that belong to "winners"
     * @return
     */
    public List<Utente> getWinners() {
        return this.winners;
    }

    /**
     * Return the n-th Utente from "Giocatori"
     * @param n
     * @return
     */
    public Utente getGiocatore(int n) {
        return this.Giocatori.get(n);
    }

    /**
     * Return the n-th Utente from "winners"
     * @param n
     * @return
     */
    public Utente getWinner(int n) {
        return this.winners.get(n);
    }

    /**
     * Set values of Giocatori with Giocatori field's values
     * @param giocatori
     */
    public void setGiocatori(List<Utente> giocatori) {
        this.Giocatori = giocatori;
    }

    /**
     * Set values of Winners with Winners field's values
     * @param winners
     */
    public void setWinners(List<Utente> winners) {
        this.winners = winners;
    }

    /**
     * Remove one Utente from Giocatori
     * @param u
     */
    public void removeGiocatore(Utente u) { //utile ad esempio se un partecipante dell'evento non vuole più giocare o non vuole partecipare ai restanti round
        this.Giocatori.remove(u);
    }

    /**
     * Remove all users from Giocatori
     */
    public void removeGiocatori() { //da utilizzare alla fine del gioco
        this.Giocatori.removeAll(this.Giocatori);
    }

    /**
     * Remove one Utente from winners
     * @param u
     */
    public void removeWinner(Utente u) { //utile ad esempio se un partecipante dell'evento non vuole più giocare o non vuole partecipare ai restanti round
        this.winners.remove(u);
    }

    /**
     * Remove all users from winners
     */
    public void removeWinners() { //da utilizzare alla fine del gioco
        this.winners.removeAll(this.winners);
    }

    /**
     * Move one user from Winners to Giocatori
     * @param u
     */
    public void fromWinnersToGiocatori(Utente u) {
        removeGiocatore(u);
        addToWinners(u);
    }

    /**
     * Move one user from Giocatori to Winners
     * @param u
     */
    public void fromGiocatoriToWinners(Utente u) {
        removeWinner(u);
        addToGiocatori(u);
    }

    /**
     * Return a random Utente from "Giocatori"
     * @return
     */
    public Utente getRandomGiocatore() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, this.Giocatori.size() + 1); //+1 because nextInt is normally exclusive of the top value
        return getGiocatore(randomNum);
    }

    /**
     * Give points to all users in "Giocatori"
     */
    public void addPoints(){
        for(Utente u: this.Giocatori){
            u.setScore(u.getScore() + this.points);
        }
    }

    /**
     * Give points to all but one users in "Giocatori"
     */
    public void addPointsAllButOne(Utente u){ //utile ad esempio a nascondino, se l'ultimo giocatore fa 'tana libera tutti'
        u.setScore(u.getScore() + this.points);
    }

    /**
     * Give points to users in "Winner", the extra points will be commensurate about ranking
     */
    public void addWinnersPoints(){ //utile ad esempio per CorsaConSacchi dove in base all'arrivo vengono assegnati tot punti
        int extra_points = this.points;
        for(int i=0; i<this.winners.size(); ++i){
            Utente u = this.winners.get(i);
            u.setScore(u.getScore() + extra_points);
            extra_points/=2;
            if(extra_points < 1)
                extra_points=1;

        }
    }

    /**
     * Give extra points to the winner of this round
     */
    public void addExtraPoints() { //utile ad esempio in flipper
        this.winners.get(0).setScore(this.winners.get(0).getScore() + this.points*2);
    }

    /**
     * Return the ranklist of the event as a map holding users' names as keys and relatives points as values
     * @return
     */
    public Map<Utente, Integer> getRanklist() {
        return ranklist;
    }

    /**
     * Set ranklist values with ranklist field's values
     * @param ranklist
     */
    public void setRanklist(Map<Utente, Integer> ranklist) {
        this.ranklist = ranklist;
    }

    /**
     * Create the event's ranklist sorting for descending values
     */
   /* public void generateRanklistInOrder(){
        for(Utente u: this.getPartecipants()){
            this.ranklist.put(u, u.getScore());
        }
        List<Map.Entry<Utente, Integer>> list = new ArrayList<>(this.ranklist.entrySet());
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));
        this.ranklist.clear();
        for(Map.Entry<Utente, Integer> u: list){
            this.ranklist.put(u.getKey(), u.getValue());
        }
*/
    /**
     * Create a Map of an event using Strings as keys and the fields of the event as values.
     * We use it in order to communicate the values of the event to the database.
     * @return return a map having fields' name as keys and event fields' values as value
     */
    public Map<String, Object> macroAEventMap(){
        Map<String, Object> myMap = new HashMap<>();
        myMap.put("name", this.getName());
        myMap.put("description", this.getDescription());
        myMap.put("date", this.getDate());
        myMap.put("creator", this.getCreator());
        myMap.put("password", this.getPassword());
        myMap.put("partecipants", this.getPartecipants());
        myMap.put("public", this.isPublic());
        myMap.put("players", this.getGiocatori());
        myMap.put("winners", this.getWinners());
        myMap.put("ranklist", this.getRanklist());
        return myMap;
    }


    /**
     * Set all event fields' values with map param's values
     * @param map
     */
    public void setValuesFromMap(Map<String, Object> map){
        this.setDescription((String) map.get("description"));
        this.setDate((String) map.get("date"));
        this.setPublic((Boolean) map.get("public"));
        this.setPassword((String) map.get("password"));
        this.setCreator((String) map.get("creator"));
        this.setMaxPart((Integer) map.get("maxPart"));
        this.setMinPart((Integer) map.get("minPart"));
        this.setPartecipants((List<String>) map.get("partecipants"));
        this.setGiocatori((List<Utente>) map.get("players"));
        this.setWinners((List<Utente>) map.get("winners"));
        this.setRanklist((Map<Utente, Integer>) map.get("ranklist"));
    }
}