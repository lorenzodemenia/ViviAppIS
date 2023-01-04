package com.example.viviappis.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.viviappis.data.model.Utente;
import com.example.viviappis.ui.profile.ProfileFragment;


public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<String> mText;



    public ProfileViewModel() {
        this.mText =new MutableLiveData<>();



    }
    public LiveData<String> getText() {
        return mText;
    }


}
