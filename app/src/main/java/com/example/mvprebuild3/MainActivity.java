package com.example.mvprebuild3;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.speech.RecognitionListener;
import android.view.View;
import android.widget.ProgressBar;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.mvprebuild3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements RecognitionMVP.RecognitionViewOps {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private StateMaintainer mStateMaintainer = new StateMaintainer(getSupportFragmentManager(),
            this.getClass().getSimpleName());
    private RecognitionMVP.ProvidedPresenterOps mPresenter;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
        setupMVP();
    }

    private void setupMVP() {
    // При первом запуске сессии приложения
        if(mStateMaintainer.firstTimeIn()) {
            // шаг 1
            // Активити создает экз През и Модели
            // Сохраняет ссылку на PresenterOps в StateMaintainer
            // Вставляет в Презентер ссылку на View

            //create Presenters
            RecognitionPresenter recognitionPresenter = new RecognitionPresenter(this);
            //new Model
            RecognitionModel recognitionModel = new RecognitionModel(recognitionPresenter);
            //set Model to Presenter
            recognitionPresenter.setModel(recognitionModel);
            //Add P & M to StateMaitainer
            mStateMaintainer.put(recognitionPresenter);
            mStateMaintainer.put(recognitionModel);
            //Make Presenter as an interface
            //to limit communications
            mPresenter = recognitionPresenter;
        //if not first time
        }else{
            //get Presenter
            mPresenter = mStateMaintainer.get(RecognitionPresenter.class.getName());
            //update View in Presenter
            mPresenter.setView(this);
        }
    }

    private void setupViews() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = binding.progressBar;



        //setSupportActionBar(binding.toolbar);
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // icon "MIC is OFF"
        binding.fab.setBackgroundDrawable(getDrawable(R.drawable.ic_baseline_mic_none_24));



        // состояние кнопки определяют 2 флага
        // первый - текст или микрофон, в зависимости от состояния полей новой записи
        // если есть пустые поля, мы можем записать аудио
        // если Рутина в аудио противоречит Рутине в текстовом поле, предлагается выбор,
        // остальное - в комментарий
        //

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if()
            }
        });
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }
}