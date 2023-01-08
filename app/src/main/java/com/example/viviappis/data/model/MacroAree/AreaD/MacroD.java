package com.example.viviappis.data.model.MacroAree.AreaD;

import com.example.viviappis.Pair;
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

public class MacroD extends Evento {
    private Pair<String, Collection<Utente>> squadra1;
    private Pair<String, Collection<Utente>> squadra2;
    private Map<Utente, Integer> ranklist;
    private final int points;
    private Pair<String, Collection<Utente>> winners;

    /*
    * ********** elim
    * ********** inserire tutte le classi del tipo palla prigioniera ... dentro array.xml
    * **********
     */

    /**
     * Constructor of object MacroD
     * @param name name of the event
     * @param description description of the event
     * @param creator user creator of the event
     * @param date time of the event
     * @param password password to log into the event
     * @param isPublic define if the event is public or not
     * @param minPart define the minimum number of partecipants
     * @param maxPart define the maximum number of partecipants
     * @param nameSquadra1 name of the team 1
     * @param nameSquadra2 name of the team 2
     * @param points points gained for the victory
     */
    public MacroD(String name, String description, String creator, String date, String password, boolean isPublic, int minPart, int maxPart, String nameSquadra1, String nameSquadra2, int points, String luogo) {
        super(name, description, creator, date, password, isPublic, minPart, maxPart, luogo);
        this.points = points;
        this.squadra1 = new Pair<>(nameSquadra1, new ArrayList<>());
        this.squadra2 = new Pair<>(nameSquadra2, new ArrayList<>());
        this.ranklist = new HashMap<>();
        this.winners = new Pair<>("", new ArrayList<>());
    }

    /**
     * Add one user to team 1
     * @param u
     */
    public void addToSquadra1(Utente u){
        this.squadra1.getY().add(u);
    }

    /**
     * Add one user to team 2
     * @param u
     */
    public void addToSquadra2(Utente u){
        this.squadra2.getY().add(u);
    }

    /**
     * Return the name of team 1
     * @return
     */
    public String getNameSquadra1(){
        return this.squadra1.getX();
    }

    /**
     * Return a collection of users that belong to team 1
     * @return
     */
    public Collection<Utente> getSquadra1(){
        return this.squadra1.getY();
    }

    /**
     * Return the name of team 2
     * @return
     */
    public String getNameSquadra2(){
        return this.squadra2.getX();
    }

    /**
     * Set team 1's name
     * @param nameSquadra1
     */
    public void setNameSquadra1(String nameSquadra1){
        this.squadra1.setX(nameSquadra1);
    }

    /**
     * Set team 2's name
     * @param nameSquadra2
     */
    public void setNameSquadra2(String nameSquadra2){
        this.squadra2.setX(nameSquadra2);
    }

    /**
     * Return a collection of users that belong to team 1
     * @return
     */
    public Collection<Utente> getSquadra2(){
        return this.squadra2.getY();
    }

    /**
     * Return the ranklist of the event as a map holding users' names as keys and relatives points as values
     * @return
     */
    public Map<Utente, Integer> getRanklist() {
        return ranklist;
    }

    /**
     * Give points to all users in winner team
     */
    public void addPoints(){
        for(Utente u: this.winners.getY()){
            u.setScore(u.getScore() + this.points);
        }
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
        //TODO: da rivere perchè probabilmente è ordinata in ordine crescente invece che decrescente KEKW. Meme del dev che piange per la sua incompetenza :')
        //TODO UPDATE: ok forse è risolto ma lo scopriremo a tempo debito
    }*/

    /**
     * Add winner users to winners map field
     * @param winners
     */
    public void addWinners(Pair<String, Collection<Utente>> winners){
        this.winners.setX(winners.getX());
        for(Utente u: winners.getY()) {
            this.winners.getY().add(u);
        }
    }

    public void setWinners(Pair<String, Collection<Utente>> winners) {
        this.winners = winners;
    }

    /**
     * Set values of squadra1 with squadra1 field's values
     * @param squadra1
     */
    public void setSquadra1(Pair<String, Collection<Utente>> squadra1) {
        this.squadra1 = squadra1;
    }

    /**
     * Set values of squadra2 with squadra2 field's values
     * @param squadra2
     */
    public void setSquadra2(Pair<String, Collection<Utente>> squadra2) {
        this.squadra2 = squadra2;
    }

    /**
     * Set ranklist values with ranklist field's values
     * @param ranklist
     */
    public void setRanklist(Map<Utente, Integer> ranklist) {
        this.ranklist = ranklist;
    }

    /**
     * Return the winner team
     * @return
     */
    public Pair<String, Collection<Utente>> getWinners(){
        return this.winners;
    }

    /**
     * Create a Map of an event using Strings as keys and the fields of the event as values.
     * We use it in order to communicate the values of the event to the database.
     * @return return a map having fields' name as keys and event fields' values as value
     */
    public Map<String, Object> macroDEventMap(){
        Map<String, Object> mdeMap = new HashMap<>();
        mdeMap.put("name", this.getName());
        mdeMap.put("description", this.getDescription());
        mdeMap.put("date", this.getDate());
        mdeMap.put("creator", this.getCreator());
        mdeMap.put("password", this.getPassword());
        mdeMap.put("partecipants", this.getPartecipants());
        mdeMap.put("public", this.isPublic());
        mdeMap.put("team 1", this.getSquadra1());
        mdeMap.put("team 2", this.getSquadra2());
        mdeMap.put("winners", this.getWinners());
        mdeMap.put("ranklist", this.getRanklist());
        return mdeMap;
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
        this.setSquadra1((Pair<String, Collection<Utente>>) map.get("team 1"));
        this.setSquadra2((Pair<String, Collection<Utente>>) map.get("team 2"));
        this.setWinners((Pair<String, Collection<Utente>>) map.get("winners"));
        this.setRanklist((Map<Utente, Integer>) map.get("ranklist"));
    }

}
