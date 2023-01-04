package com.example.viviappis.ui.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.viviappis.R;
import com.example.viviappis.databinding.FragmentDashboardBinding;
import com.example.viviappis.databinding.FragmentEventPageBinding;


public class EventPageFragment extends Fragment
{
    private FragmentEventPageBinding binding;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_page, container, false);
    }
}