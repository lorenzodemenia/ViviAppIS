package com.example.viviappis.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Evento implements Serializable
{
    private String name;
    private String description;
    private String creator;
    private String date;
    private String password;
    private List<Utente> partecipants;
    private boolean isPublic;
    private int minPart;
    private int maxPart;


    /**
     * Genera un oggetto di tipo evento
     * @param name nome dell'evento
     * @param description descrizione dell'evento
     * @param creator creatore dell'evento
     * @param date data dell'evento
     * @param password password dell'evento
     * @param isPublic rappresenta se l'evento è publico
     * @param minPart numero minimo di partecipanti
     * @param maxPart numero massimo di partecipanti
     */
    public Evento(String name, String description, String creator, String date, String password, boolean isPublic, int minPart, int maxPart)
    {
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.password = password;
        this.date = date;
        this.partecipants = new ArrayList<>();
        this.isPublic = isPublic;
        this.minPart=minPart;
        this.maxPart=maxPart;

    }
    /**
     * Genera un oggetto di tipo evento
     * @param name nome dell'evento
     * @param description descrizione dell'evento
     * @param creator creatore dell'evento
     * @param date data dell'evento
     * @param password password dell'evento
     * @param partecipants array che rappresenta i partecipanti all'evento
     * @param isPublic rappresenta se l'evento è publico
     * @param minPart numero minimo di partecipanti
     * @param maxPart numero massimo di partecipanti
     */
    public Evento(String name, String description, String creator, String date, String password, boolean isPublic, int minPart, int maxPart, List<Utente> partecipants)
    {
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.password = password;
        this.date = date;
        this.partecipants = partecipants;
        this.isPublic = isPublic;
        this.minPart=minPart;
        this.maxPart=maxPart;
    }
    /**
     * Genera un oggetto di tipo evento
     * @param a oggetto di tipo GenericEvent per creare un nuovo oggetto
     */
    public Evento(Evento a) {this(a.getName(), a.getDescription(), a.getCreator(), a.getDate(), a.getPassword(),a.isPublic(), a.minPart, a.maxPart, a.partecipants);}

    /**
     * crea un oggetto a partire da una stringa creata con toStringData()
     * @param a stringa creata con to string data (deve avere la forma name/description/creator/date/password/minPart/maxPart/isPublic;
     */
    public Evento(String a)
    {
        this("","","","","",false,0,0);

        String[] b = a.split("/");
        if(b.length==8)
        {
            this.name = b[0];
            this.creator = b[1];
            this.description = b[2];
            this.password = b[3];
            this.date = b[4];
            this.partecipants = new ArrayList<>();
            this.isPublic = Boolean.parseBoolean(b[7]);
            this.minPart=Integer.parseInt(b[5]);
            this.maxPart=Integer.parseInt(b[6]);
        }
    }

    /**
     * ritorna il valore minimo dei partecipanti
     * @return ritorna il valore minimo dei partecipanti
     */
    public int getMinPart() {return minPart;}
    /**
     * ritorna il valore massimo dei partecipanti
     * @return ritorna il valore massimo dei partecipanti
     */
    public int getMaxPart() {return maxPart;}

    /**
     * Return the name of the event.
     * @return name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * Return the password oth the event.
     * @return password of the event
     */
    public String getPassword() {
        return password;
    }

    /**
     * Return the time of the event.
     * @return time of the event
     */
    public String getDate() {
        return date;
    }

    /**
     * Return the users that partecipate to the event.
     * @return partecipants in the event
     */
    public Collection<Utente> getPartecipants() {
        return partecipants;
    }

    /**
     * Return the description of the event
     * @return description of the event
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return the users the created the event.
     * @return creator of the event
     */
    public String getCreator() {
        return creator;
    }

    /**
     * set the date of the instance to the value of the param date.
     * @param date time to set as time of the event
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * set the description of the instance to the value of the param description.
     * @param description to set as description of the event
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * set the name of the instance to the value of the param name.
     * @param name to set as name of the event
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set the isPublic of the instance to the value of the param isPublic.
     * @param isPublic value to set defining if the event is public or not
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * add the user u to the partecipants list.
     * @param u user to add to partecipants list
     */
    public void addPartecipants(Utente u) {
        this.partecipants.add(u);
    }

    /**
     * Return if the event is public or not.
     * @return if the event is public or not
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * Create a Map of an event using Strings as keys and the fields of the event as values.
     * We use it in order to communicate the values of the event to the database.
     * @return return a map having fields' name as keys and event fields' values as value
     */
    public Map<String, Object> toMap()
    {
        Map<String, Object> event = new HashMap<>();
        event.put("name", this.getName());
        event.put("description", this.getDescription());
        event.put("date", this.getDate());
        event.put("creator", this.getCreator());
        event.put("password", this.getPassword());
        event.put("partecipants", this.getPartecipants());
        event.put("public", this.isPublic());
        event.put("maxPart", this.maxPart);
        event.put("minPart", this.minPart);
        return event;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setMaxPart(int maxPart) {
        this.maxPart = maxPart;
    }

    public void setMinPart(int minPart) {
        this.minPart = minPart;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPartecipants(List<Utente> partecipants) {
        this.partecipants.clear();
        this.partecipants.addAll(partecipants);
    }

    public String toStringData()
    {
        String part = "";
        for (Utente i : partecipants);
        return name+'/'+description+'/'+creator+'/'+date+'/'+password+'/'+minPart+'/'+maxPart+'/'+isPublic;
    }

    /**
     * Genera la stringa che rappresenta l'oggetto
     * @return stringa che rappresenta l'oggetto
     */
    @Override
    public String toString()
    {
        return "Evento{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creator='" + creator + '\'' +
                ", date='" + date + '\'' +
                ", password='" + password + '\'' +
                ", partecipants=" + partecipants +
                ", isPublic=" + isPublic +
                '}';
    }
}

