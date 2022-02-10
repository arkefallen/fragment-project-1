package com.example.fragmentexample1updated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button openBtn;

    private Boolean isFragmentDisplayed = false;

    private static final String STATE_FRAGMENT = "state_of_fragment";

    private int mainActRadioButtonChoice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openBtn = findViewById(R.id.open_button);

        if (savedInstanceState!=null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed) {
                openBtn.setText(R.string.close);
            }
        }

        openBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFragmentDisplayed) {
                    showFragment();
                }
                else {
                    closeFragment();
                }
            }
        });
    }

    // Memunculkan fragmen dengan dibungkus dahulu
    private void showFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SimpleFragment simpleFragment = SimpleFragment.newInstance(mainActRadioButtonChoice);

        fragmentTransaction.add(R.id.fragment_container, simpleFragment).addToBackStack(null).commit();

        openBtn.setText(R.string.close);
        isFragmentDisplayed = true;
    }

    private void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);

        if(simpleFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }

        openBtn.setText(R.string.open);
        isFragmentDisplayed = false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // menyimpan state fragment saat ini agar tidak tereset jika adanya perubahan konfigurasi
        outState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        super.onSaveInstanceState(outState);
    }

//
//    @Override
//    public void OnRadioButtonChoiceChecked(int choice) {
//        // menyimpan pilihan radio button yang nantinya akan dimunculkan lagi di fragment
//        mainActRadioButtonChoice = choice;
//        Toast.makeText(this, "Choice is" + Integer.toString(choice), Toast.LENGTH_SHORT).show();
//    }
}