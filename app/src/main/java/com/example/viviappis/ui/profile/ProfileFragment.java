package com.example.viviappis.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Utente;
import com.example.viviappis.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.List;
import java.util.Locale;
import java.util.Map;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth au;
    private FirebaseFirestore db;

    public Utente getUser() {
        return user;
    }

    protected Utente user;


    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        au = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // it's used for taking the data of the current user from Firestore to the app
        db.collection(getResources().getString(R.string.db_rac_users)).get().addOnCompleteListener((t)->{

            user = new Utente(filterListByID(t.getResult().getDocuments(),au.getCurrentUser().getEmail()));
            final TextView textView = binding.textProfile;
            textView.setText(user.getName());
        });



        return root;
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