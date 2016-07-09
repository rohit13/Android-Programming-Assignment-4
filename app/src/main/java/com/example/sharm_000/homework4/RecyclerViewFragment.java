package com.example.sharm_000.homework4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.CheckBox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.OvershootInRightAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInRightAnimator;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewFragment extends Fragment{

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    LinearLayoutManager layoutManager;
    MovieData movieData = new MovieData();;
    Button selectAll;
    Button clearAll;
    Button delete;
    private OnCardItemClickedListener onCardItemClickedListener;

    public RecyclerViewFragment() {
        // Required empty public constructor
        //movieData = new MovieData();;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        onCardItemClickedListener = (OnCardItemClickedListener) getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);
        //layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //specify adapter
        /*if(savedInstanceState!=null){
            if(savedInstanceState.getSerializable("movieData")!=null) {
                movieData = (MovieData) savedInstanceState.getSerializable("movieData");
            }
            else
                movieData=new MovieData();
        }else
            movieData=new MovieData();*/

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), movieData.getMoviesList());
        recyclerView.setAdapter(recyclerViewAdapter);
        adapterAnimation();
        itemAnimator();

        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                onCardItemClickedListener.onCardItemClicked(movieData.getItem(position));
            }

            @Override
            public void onItemLongClick(View view, int position) {
                int newPos = position + 1;
                //if (newPos >= 1 && newPos <= movieData.getSize()) {
                //HashMap<String, ?> dupMovie = movieData.getItem(position);
                movieData.addItem(newPos, (HashMap<String, ?>) movieData.getItem(position).clone());
                recyclerViewAdapter.notifyItemInserted(newPos);
                //}
            }

            @Override
            public void onItemCheckBoxSelect(int position, boolean isChecked) {
                //if (position >= 0 && position < movieData.getSize()) {
                if (isChecked) {
                    movieData.getItem(position).put("isSelected", true);
                }
                else
                    movieData.getItem(position).put("isSelected", false);
                //recyclerViewAdapter.notifyItemChanged(position);
                //}
            }
        });


        selectAll = (Button) rootView.findViewById(R.id.selectAll);
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    View listItem = recyclerView.getChildAt(i);
                    CheckBox cb = (CheckBox) listItem.findViewById(R.id.chechBox);
                    if (!cb.isChecked()) {
                        toggle(cb);
                    }
                }*/
                for(int i=0;i<movieData.getSize();i++){
                    movieData.getItem(i).put("isSelected", true);
                    recyclerViewAdapter.notifyItemChanged(i);
                }
                //recyclerViewAdapter.notifyItemRangeChanged(0,movieData.getSize());
            }
        });

        clearAll = (Button) rootView.findViewById(R.id.clearAll);
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*for(int i=0; i < recyclerView.getChildCount();i++){
                    View listItem = recyclerView.getChildAt(i);
                    CheckBox cb = (CheckBox) listItem.findViewById(R.id.chechBox);
                    if(cb.isChecked()){
                        toggle(cb);
                    }
                }*/
                for(int i=0;i<movieData.getSize();i++){
                    movieData.getItem(i).put("isSelected", false);
                    recyclerViewAdapter.notifyItemChanged(i);
                }
                //recyclerViewAdapter.notifyItemRangeChanged(0,movieData.getSize());
            }
        });

        delete = (Button) rootView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = movieData.getSize();
                for (int i = 0; i < size; i++) {
                    boolean selectFlag = (Boolean) movieData.getItem(i).get("isSelected");
                    if (selectFlag) {
                        movieData.removeItem(i);
                        recyclerViewAdapter.notifyItemRemoved(i);
                        i--;
                        size--;
                    }
                }
                if(size<=5)
                    recyclerViewAdapter.notifyDataSetChanged();
                /*for(int i=0; i<recyclerView.getChildCount();i++){
                    View listItem = recyclerView.getChildAt(i);
                    CheckBox cb = (CheckBox) listItem.findViewById(R.id.chechBox);
                    if(cb.isChecked()){
                        movieData.removeItem(i);
                        recyclerViewAdapter.notifyItemRemoved(i);
                    }
                }*/
                /*ArrayList<Boolean> arr = new ArrayList<Boolean>(Arrays.asList(new Boolean[movieData.getSize()]));
                Collections.fill(arr, Boolean.FALSE);
                int size = movieData.getSize();
                int countRemoved=0;
                for (int i = 0; i < size; i++) {
                    boolean selectFlag = (Boolean) movieData.getItem(i).get("isSelected");
                    if (selectFlag) {
                        movieData.setItem(i, null);
                        arr.set(i, Boolean.TRUE);
                    }
                }
                movieData.removeAllItem();
                for (Boolean b:arr) {
                    if(b.booleanValue()){
                        countRemoved++;
                    }
                }*/
                //if(countRemoved==size)
                //recyclerViewAdapter.notifyDataSetChanged();
                /*else {
                    for (int j = 0; j < size; j++) {
                        if (arr.get(j))
                            recyclerViewAdapter.notifyItemRemoved(j);
                    }
                }*/

            }
        });


        return rootView;
    }

    private void itemAnimator() {
        SlideInRightAnimator animator = new SlideInRightAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        animator.setAddDuration(1000);
        animator.setRemoveDuration(500);
        recyclerView.setItemAnimator(animator);

        //LandingAnimator landingAnime= new LandingAnimator();
        //recyclerView.setItemAnimator(landingAnime);
        //recyclerView.getItemAnimator().setAddDuration(1000);
        //recyclerView.getItemAnimator().setRemoveDuration(100);
    }

    private void adapterAnimation() {
        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(recyclerViewAdapter);
        alphaAdapter.setDuration(500);
        recyclerView.setAdapter(alphaAdapter);
    }

  /*  public void toggle(CheckBox v)
    {
        if (v.isChecked())
            v.setChecked(false);
        else
            v.setChecked(true);
    }*/

    public static RecyclerViewFragment newInstance() {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnCardItemClickedListener{
        public void onCardItemClicked(HashMap<String,?> movie);
    }

   /* private void defaultAnimation(){
        DefaultItemAnimator anime = new DefaultItemAnimator();
        anime.setAddDuration(5000);
        anime.setRemoveDuration(200);
        recyclerView.setItemAnimator(anime);
    }
*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putSerializable("movieData", movieData);
    }
}
