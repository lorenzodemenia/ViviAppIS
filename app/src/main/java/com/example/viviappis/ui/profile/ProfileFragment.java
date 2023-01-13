package com.example.viviappis.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viviappis.R;
import com.example.viviappis.control.loginAndRegister.LoginActivity;
import com.example.viviappis.data.model.recicleView.ScorrimentoPartecipant;
import com.example.viviappis.data.model.Utente;
import com.example.viviappis.data.model.recicleView.ScorrimentoRanklist;
import com.example.viviappis.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth au;
    private FirebaseFirestore db;
    private Button bProfileLogout;
    private ProgressBar asp;
    private RecyclerView recyclerView;
    private TextView textName,textSurname,textDate,textNickname,textEmail;

    protected Utente user;


    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        au = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        setUpUiView();

        // it's used for taking the data of the current user from Firestore to the app
        db.collection(getResources().getString(R.string.db_rac_users)).get().addOnCompleteListener((t)->{

            user = new Utente(filterListByID(t.getResult().getDocuments(),au.getCurrentUser().getEmail()));

            textName.setText(user.getName());
            textSurname.setText(user.getSurname());
            textDate.setText(user.getBirth());
            textNickname.setText(user.getUsername());
            textEmail.setText(user.getEmail());

            createDashRanklist(t.getResult().getDocuments());

        });

        bProfileLogout.setOnClickListener((i)->{
                onClickLogout();});

        return root;
    }
    private void setUpUiView(){
        // Buttons setup
        bProfileLogout = binding.ButtonLogout;

        // Texts setup
        textName = binding.textName;
        textSurname = binding.textSurname;
        textDate = binding.textDate;
        textNickname = binding.textNickname;
        textEmail = binding.textEmail;

        recyclerView = binding.ProfileRecyclerView;
    }


    /**
     * crea la dash bord del fragmant per la visualizzazione degli eventi
     * @param l lista dei documenti presi dal server che rappresentano gli eventi
     */
    public void createDashRanklist(List<DocumentSnapshot> l)
    {
        ScorrimentoRanklist adapter = new ScorrimentoRanklist(this.getContext(), au.getCurrentUser().getEmail());

        for (DocumentSnapshot i : l)adapter.addPart(new Utente(i.getData()));

        adapter.sort();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    public void onClickLogout (){
        au.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));

    }

    public Map<String, Object> filterListByID(List<DocumentSnapshot> l, String id)
    {
        int i=0;

        while (i<l.size())
        {
            Map<String, Object> m = l.get(i).getData();
            String cnt = (String) m.get("email");

            if(!cnt.startsWith(id)) l.remove(i);
            else                      i++;
        }
        Map<String, Object> m = l.get(0).getData();
        return m;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}