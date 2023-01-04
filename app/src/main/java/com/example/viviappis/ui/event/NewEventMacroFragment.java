package com.example.viviappis.ui.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Evento;


public class NewEventMacroFragment extends Fragment
{
    private Evento e=null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        e = new Evento(getArguments().getString(getResources().getString(R.string.event_send_ev)));
        System.out.println(e);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        System.out.println(savedInstanceState);
        return inflater.inflate(R.layout.fragment_new_event_macro, container, false);
    }
}