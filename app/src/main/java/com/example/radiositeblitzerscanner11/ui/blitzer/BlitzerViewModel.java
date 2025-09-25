package com.example.radiositeblitzerscanner11.ui.blitzer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BlitzerViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BlitzerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}