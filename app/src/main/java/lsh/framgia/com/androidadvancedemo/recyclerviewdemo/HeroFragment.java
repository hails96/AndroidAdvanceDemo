package lsh.framgia.com.androidadvancedemo.recyclerviewdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lsh.framgia.com.androidadvancedemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeroFragment extends Fragment {

    public HeroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hero, container, false);
        setupRecyclerView(view);
        return view;
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerViewHero = view.findViewById(R.id.recycler_view_hero);
        recyclerViewHero.setHasFixedSize(true);
        recyclerViewHero.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewHero.setAdapter(new HeroAdapter(getContext()));
    }
}
