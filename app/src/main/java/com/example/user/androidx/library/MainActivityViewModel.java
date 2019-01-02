package com.example.user.androidx.library;

import android.util.Log;
import java.util.Random;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private final String TAG = this.getClass().getSimpleName();
    private MutableLiveData<String> myRandomNumber;

    public MutableLiveData<String> getNumber()
    {
        Log.i(TAG,"Get Number");
        if(myRandomNumber == null)
        {
            myRandomNumber = new MutableLiveData<>();
            createNumber();
        }
        return myRandomNumber;
    }

    public void createNumber() {

        Log.i(TAG,"createNumber");
        Random random = new Random();
        myRandomNumber.setValue("Number :"+(random.nextInt(10-1)));

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG,"viewmodel onCleared called");
    }
}