package com.example.sharm_000.homework4;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CoverPageFragment.OnButtonClickedListener, CoverPageFragment.OnTask1ClickedListener{
    Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            if(getSupportFragmentManager().getFragment(savedInstanceState,"currentFrag") != null)
                currentFragment = getSupportFragmentManager().getFragment(savedInstanceState,"currentFrag");
            else
                currentFragment = CoverPageFragment.newInstance();
        }
        else
            currentFragment = CoverPageFragment.newInstance();

        getSupportFragmentManager().beginTransaction().
                replace(R.id.maincontainer, currentFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(currentFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"currentFrag",currentFragment);
        }
    }

    @Override
    public void onButtonClicked(Bundle savedInstanceState) {
        currentFragment=AboutMeFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,currentFragment).addToBackStack(null).commit();
    }

    @Override
    public void onTask1ButtonClicked(Bundle savedInstanceState) {
        Intent intent = new Intent(this, RecyclerViewActivity.class);
        startActivity(intent);
    }
}
