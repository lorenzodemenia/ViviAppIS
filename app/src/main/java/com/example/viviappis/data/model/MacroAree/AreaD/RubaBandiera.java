package com.example.viviappis.data.model.MacroAree.AreaD;

import com.example.viviappis.data.model.Utente;

public class RubaBandiera extends MacroD{
    public RubaBandiera(String name, String creator, String date, String password, boolean isPublic,String nameSquadra1, String nameSquadra2, int points) {
        super(name, "l rubabandiera è un gioco tradizionale che si fa all’aperto. Si gioca in due squadre con lo stesso numero di giocatori, più un’ulteriore persona, che assume il ruolo del porta-bandiera.\n" +
                "Giocatori: da 7 bambini in su (ma sempre in numero dispari) \n" +
                "Come si gioca: si tracciano una linea retta immaginaria al centro del campo da gioco e altre due linee equidistanti e parallele che delimiteranno la “casa” delle due squadre. I giocatori di una squadra si allineano lungo la linea della propria “casa”, gli uni di fronte agli altri. Ad ogni giocatore viene assegnato un numero in ordine progressivo partendo da un estremo della fila (il primo giocatore sarà il numero 1, il giocatore di fianco a lui il numero 2 e così via). Il porta-bandiera si dispone ad un estremo della linea di mezzeria e tiene in mano, con il braccio alto e teso in avanti, la “bandiera”, che può essere un fazzoletto o qualcosa di simile. A questo punto il porta-bandiera chiama ad alta voce un numero qualsiasi. I giocatori delle due squadre corrispondenti al numero chiamato devono correre verso il porta-bandiera per “rubare la bandiera” e portarla nella propria “casa”, evitando di essere toccato dall’avversario. Alla squadra del giocatore che “ruba la bandiera” e riesce a portarla nella propria “casa” spetta il punto. \n" +
                "Obiettivo: vince la squadra che accumula più punti.", creator, date, password, isPublic, 7, 51, nameSquadra1, nameSquadra2, points);
    }



}
