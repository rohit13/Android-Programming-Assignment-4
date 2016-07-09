package com.example.sharm_000.homework4;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;

public class RecyclerViewActivity extends AppCompatActivity implements RecyclerViewFragment.OnCardItemClickedListener{

    Fragment recyclerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        if(savedInstanceState!=null){
            if(getSupportFragmentManager().getFragment(savedInstanceState,"recyclerFrag") != null){
                recyclerFragment = getSupportFragmentManager().getFragment(savedInstanceState,"recyclerFrag");
            }else
                recyclerFragment = RecyclerViewFragment.newInstance();
        }else
            recyclerFragment = RecyclerViewFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.recyclerViewAct, recyclerFragment).commit();
    }

    @Override
    public void onCardItemClicked(HashMap<String,?> movie) {
        getSupportFragmentManager().beginTransaction().replace(R.id.recyclerViewAct,MovieFragment.newInstance(movie)).addToBackStack(null).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(recyclerFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "recyclerFrag", recyclerFragment);
        }
    }
}
