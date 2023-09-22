package com.example.mvprebuild3;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class StateMaintainer {
    private final String TAG = getClass().getSimpleName();
    private final WeakReference<FragmentManager> mFragmentManager;
    private String mStateMaintainerTag;
    private StateMaintainerFragment mStateMaintainerFragment;

    public StateMaintainer(FragmentManager fragmentManager, String stateMaintainerTag) {
        mFragmentManager = new WeakReference<>(fragmentManager);
        mStateMaintainerTag = stateMaintainerTag;
    }

    //Создает Фрагмент, ответственный за хранение объектов
    public boolean firstTimeIn() {
        try{
            mStateMaintainerFragment = (StateMaintainerFragment)
                    mFragmentManager.get().findFragmentByTag(mStateMaintainerTag);

            if(mStateMaintainerFragment == null){
                Log.d(TAG, "Создаем новый Фрагмент");
                mStateMaintainerFragment = new StateMaintainerFragment();
                mFragmentManager.get().beginTransaction()
                        .add(mStateMaintainerFragment, mStateMaintainerTag).commit();
                return true;
            } else{
                Log.d(TAG, "Ранее созданный Фрагмент " + mStateMaintainerTag);
                return false;
            }
        } catch (NullPointerException e){
            Log.d(TAG, "NPE error while firstTimeIn()");
            return false;
        }
    }

    public void put(Object object) {
        mStateMaintainerFragment.put(object.getClass().getName(), object);
    }

    public <T> T get(String key) {
        return mStateMaintainerFragment.get(key);
    }


    private class StateMaintainerFragment extends Fragment{
        private HashMap<String, Object> mData;

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            // САМОЕ ГЛАВНОЕ, почему мы вообще используем здесь фрагмент?

            // Control whether a fragment instance is retained across Activity re-creation
            // (such as from a configuration change).
            //
            // If set, the fragment lifecycle will be slightly different
            // when an activity is recreated:
            //
            // onDestroy() will not be called (but onDetach() still will be,
            // because the fragment is being detached from its current activity).
            //
            // onCreate(Bundle) will not be called since the fragment is not being re-created.
            //onAttach(Activity) and onActivityCreated(Bundle) will still be called.

            setRetainInstance(true);
            // Почему Deprecated?

            //Instead of retaining the Fragment itself,
            // use a non-retained Fragment and keep retained state in a ViewModel
            // attached to that Fragment.
            //
            // The ViewModel's constructor and its onCleared() callback
            // provide the signal for initial creation and final destruction of the retained state.

        }

        public <T> T get(String key){
            return (T) mData.get(key);
        }

        public void put(String key, Object object) {
            mData.put(key, object);
        }
    }
}
