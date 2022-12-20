package com.example.viviappis.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.viviappis.R;
import com.example.viviappis.control.loginAndRegister.LoginActivity;
import com.example.viviappis.data.model.Utente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;


public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    protected FirebaseAuth au;
    private FirebaseFirestore db;
    private Query user;


    public ProfileViewModel() {

        this.au = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        user = db.collection("users").whereEqualTo("email",au.getCurrentUser().getEmail());



        this.mText =new MutableLiveData<>();

        mText.setValue("");
    }
    public LiveData<String> getText() {
        return mText;
    }
}
