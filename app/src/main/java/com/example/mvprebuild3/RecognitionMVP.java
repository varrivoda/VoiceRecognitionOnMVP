package com.example.mvprebuild3;

import android.widget.ProgressBar;

public interface RecognitionMVP {
    interface RecognitionViewOps{

        ProgressBar getProgressBar();
    }

    //FOR MODEL
    interface RequiredPresenterOps{

    }

    //FOR VIEW
    interface ProvidedPresenterOps {
        void onDestroy(boolean isChangingConfigurtion);
        void setView(RecognitionViewOps view);
    }

    interface RecognitionModelOps {
        boolean loadAllData();
        void onDestroy(boolean isChangingConfiguration);
    }
}
