package com.example.viviappis.ui.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.viviappis.R;
import com.example.viviappis.databinding.FragmentDashboardBinding;
import com.example.viviappis.databinding.FragmentEventPageBinding;


public class EventPageFragment extends Fragment
{
    private FragmentEventPageBinding binding;

    private TextView inpNm, inpDs, inpDate, InpProp;
    private RecyclerView inpPart;
    private Button bIscr, bCanc, bStart;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentEventPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void hide(Boolean a)
    {
        if(a)binding.pageEvent.setVisibility(View.INVISIBLE);
        else binding.pageEvent.setVisibility(View.VISIBLE);
    }

    private void setUpUIViews()
    {
        inpNm = binding.pageEventNm;
        inpDs = binding.pageEventDesc;
        inpDate = binding.pageEventData;
        InpProp = binding.pageEventProp;
        inpPart = binding.pageEventPart;
        bIscr = binding.pageEventIscr;
        bCanc = binding.pageEventCanc;
        bStart = binding.pageEventStart;
    }


    /**
     * Questa funzione va ad inserire i Listener ai vari componenti nella pagina
     */
    private void addActionListener()
    {

    }
}