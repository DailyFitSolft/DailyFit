package com.dailyFitSoft.dailyfit.ui.training_history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrainingHistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TrainingHistoryViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}