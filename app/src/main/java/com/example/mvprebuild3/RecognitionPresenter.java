package com.example.mvprebuild3;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.util.Log;

import java.lang.ref.WeakReference;

public class RecognitionPresenter
        implements RecognitionMVP.RequiredPresenterOps,
        RecognitionMVP.ProvidedPresenterOps, RecognitionListener {

    private static final String LOG_TAG = "TAG";
    WeakReference<RecognitionMVP.RecognitionViewOps> mView;
    private RecognitionMVP.RecognitionModelOps mModel;

    public RecognitionPresenter(RecognitionMVP.RecognitionViewOps view) {
        mView = new WeakReference<>(view);

    }

    @Override
    public void setView(RecognitionMVP.RecognitionViewOps view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void onDestroy(boolean isChangingConfigurtion) {
        mView = null;
        mModel.onDestroy(isChangingConfigurtion);
        if(!isChangingConfigurtion){
            mModel = null;
        }
    }

    public void setModel(RecognitionMVP.RecognitionModelOps model) {
        mModel = model;
    }

    // RecognitionListener methods
    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }


    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        //эксперимент
        // передаем элемент вью
        mView.get().getProgressBar().setIndeterminate(false);
        mView.get().getProgressBar().setMax(10);
    }
    
    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        mView.get().getProgressBar().setIndeterminate(true);
        //Аналог вот этого для
        // mView.get().getFab().setChecked(false);
        //Log.d(LOG_TAG, "toggleButon.setChecked(false)");
    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle bundle) {

    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}
