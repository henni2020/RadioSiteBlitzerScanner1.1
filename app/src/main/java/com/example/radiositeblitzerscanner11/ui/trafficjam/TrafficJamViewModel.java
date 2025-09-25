package com.example.radiositeblitzerscanner11.ui.trafficjam;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrafficJamViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TrafficJamViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}