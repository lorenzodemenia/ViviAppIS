package com.example.viviappis.data.model.MacroAree.AreaD;


import com.example.viviappis.data.model.Utente;

public class PallaPrigioniera extends MacroD {
    public PallaPrigioniera(String name, String creator, String date, String password, boolean isPublic,String nameSquadra1, String nameSquadra2, int points)  {
        super(name, "Il gioco si svolge a squadre. Esse devono essere composte da un minimo di cinque giocatori fino ad un massimo di venti. Per giocare a palla prigioniera è necessaria una palla e un campo da gioco diviso in due, sia esso posto all'aperto o in una palestra, di misura rettangolare grande pressappoco 15x8 metri. Il campo dovrà essere delimitato sul fondo da una striscia orizzontale che delimiterà l'aerea dove si collocheranno i prigionieri catturati durante il gioco. Le due squadre prenderanno posto sulle due parti del campo. A questo punto il gioco può cominciare. Colui che è in possesso della palla dovrà cercare di colpire gli avversari nell'altro campo tramite il lancio della palla. Se la palla prima di colpire l'avversario tocca terra o viene deviata la presa non è valida, al contrario se il giocatore viene colpito direttamente dovrà correre nell'area dei prigionieri posta sul fondo del campo degli avversari. Li dovrà aspettare che qualche suo compagno intercetti un tiro fermando la palla al volo liberandolo così dalla sua prigionia. Vince il gioco chi riesce a colpire tutti gli avversari.",
                creator, date, password, isPublic, 10, 30, nameSquadra1, nameSquadra2, points);
    }
}
