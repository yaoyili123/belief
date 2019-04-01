package com.example.belief.ui;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import com.example.belief.R;
import com.example.belief.utils.BNVEffect;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.buttom_nav_view)
    public BottomNavigationView buttomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BNVEffect.disableShiftMode(buttomNav);
    }

}
