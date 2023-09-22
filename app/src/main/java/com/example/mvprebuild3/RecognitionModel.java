package com.example.mvprebuild3;

import com.example.mvprebuild3.data.DAO;
import com.example.mvprebuild3.models.Routine;

import java.util.ArrayList;

public class RecognitionModel implements RecognitionMVP.RecognitionModelOps {

    private ArrayList<Routine> mRoutines;
    private DAO mDAO;
    private RecognitionMVP.RequiredPresenterOps mPresenter;

    public RecognitionModel(RecognitionMVP.RequiredPresenterOps presenter) {

    }

    @Override
    public boolean loadAllData() {
        mRoutines = mDAO.getAllroutines();
        return mRoutines != null;
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if(!isChangingConfiguration){
            mPresenter = null;
            mDAO = null;
            //mNotes = null;
            mRoutines = null;
        }
    }
}
