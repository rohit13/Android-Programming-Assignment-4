package com.example.sharm_000.homework4;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CoverPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CoverPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoverPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private OnButtonClickedListener bListener;
    private OnTask1ClickedListener t1Listener;
    Button aboutMe;
    Button task1;
    public CoverPageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CoverPageFragment newInstance() {
        CoverPageFragment fragment = new CoverPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            /*mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
        try{
            bListener = (OnButtonClickedListener) getContext();
            t1Listener= (OnTask1ClickedListener) getContext();
        }catch(ClassCastException ex){
            throw new ClassCastException("forget to implement interface");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cover_page, container, false);
        aboutMe = (Button) view.findViewById(R.id.aboutme);
        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bListener.onButtonClicked(savedInstanceState);
            }
        });
        task1 = (Button) view.findViewById(R.id.task1);
        task1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                t1Listener.onTask1ButtonClicked(savedInstanceState);
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bListener = null;
        t1Listener = null;
    }

    public interface OnButtonClickedListener{
        public void onButtonClicked(Bundle savedInstanceState);
    }

    public interface OnTask1ClickedListener{
        public void onTask1ButtonClicked(Bundle savedInstanceState);
    }
}
