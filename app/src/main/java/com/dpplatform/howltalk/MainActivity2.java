package com.dpplatform.howltalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fragment.PeopleFragment;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity2_framelayout, new PeopleFragment()).commit();
    }
}