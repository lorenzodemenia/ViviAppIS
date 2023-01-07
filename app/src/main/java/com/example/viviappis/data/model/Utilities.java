package com.example.viviappis.data.model;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.viviappis.R;

import java.lang.annotation.Retention;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Utilities
{
    /**
     * Questa funzione permmete di controllare se la stringa passata rappresenta una data accettabile
     * @param c Data da controllare se accettabile
     * @param check valore che rappresenta se la data deve essere prima o dopo la data odierna (1 dopo, -1 prima)
     * @return
     */
    public static String cntrDate(String c, Context con, int check)
    {
        c = c.replace("-", "/").replace(" ", "");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);

        try
        {
            Date a =  formatter.parse(c);
            if(check==-1 &&  a.after(Calendar.getInstance().getTime()))return con.getString(R.string.ut_err_after_now);
            else if(check==1 &&  a.before(Calendar.getInstance().getTime()))return con.getString(R.string.ut_err_before_now);

            return c;
        }
        catch (Exception e ) {return con.getString(R.string.ut_err_no_data);}
    }

    /**
     * Questa funziona crea un on click listener che gestisce inserimento di una data tramite data piker controllando che la data sia in un formato accettabile
     * e che sia precedente alla data odierna
     * @param inp campo dove andare a inserire il valore della data
     * @param con contenitore
     * @param out campo dove andare a mostrare errore dato dalla scelta sbaglita della data
     * @param check valore che rappresenta se la data deve essere prima o dopo la data odierna (1 dopo, -1 prima)
     * @return ascoltatore creato
     */
    public static View.OnClickListener createDataInp(TextView inp, Context con, TextView out, int check)
    {
        return (View view) ->
        {
            Calendar c = Calendar.getInstance();
            DatePickerDialog a = new DatePickerDialog(con, (datePicker, y, m, g) ->
            {
                String s = g + "/" + (m + 1) + "/" + y;
                String cnt = cntrDate(s, con, check);
                if(cnt.equals(s))
                {
                    inp.setText(s);
                    out.setText("");
                }
                else
                {
                    if(cnt.equals(con.getString(R.string.ut_err_no_data)))out.setText(con.getString(R.string.ut_err_no_data_out));
                    else if(cnt.equals(con.getString(R.string.ut_err_after_now)))out.setText(con.getString(R.string.ut_err_after_now_out));
                    else if (cnt.equals(con.getString(R.string.ut_err_before_now)))out.setText(con.getString(R.string.ut_err_before_now_out));
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            a.show();
        };
    }

    /**
     * Serve a creare una password di una dimensione passata
     * @param passwordSize Dimensione delle password da creare
     * @return la password creata
     */
    public static String getNewPassword(int passwordSize)
    {
        String alphabet = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz?!<>-*[]{}/";
        String password = "";

        for (int i = 0; i < passwordSize; i++) password += alphabet.charAt((int)(Math.random()*alphabet.length()));

        return password;
    }

}
