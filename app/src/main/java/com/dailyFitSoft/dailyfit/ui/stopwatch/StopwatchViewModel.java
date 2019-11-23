package com.dailyFitSoft.dailyfit.ui.stopwatch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StopwatchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StopwatchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is stopwatch fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}