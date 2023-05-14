package com.example.mario.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;

import com.example.mario.Adapters.ListViewScoreAdapter;
import com.example.mario.Model.Scores;
import com.example.mario.Model.User;
import com.example.mario.R;
import com.example.mario.databinding.FragmentScoreBinding;

import java.util.ArrayList;

public class ScoreFragment extends Fragment {

    private static ScoreFragment instance;
    private Scores scores;

    private FragmentScoreBinding binding;

    public ScoreFragment() {
        scores = new Scores();
    }

    public static ScoreFragment getInstance() {
        if (instance == null) {
            instance = new ScoreFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentScoreBinding.inflate(inflater, container, false);

        showTop10List();

        binding.scoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MapDialogFragment mapDialogFragment = new MapDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", scores.getAllScore().get(position));
                mapDialogFragment.setArguments(bundle);
                mapDialogFragment.show(getParentFragmentManager(), "map_dialog");

            }
        });

        binding.scoreList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                scores.removeUser(scores.getAllScore().get(position));
                showTop10List();
                return false;
            }
        });



        return binding.getRoot();
    }

    public void showTop10List(){
        ArrayList<User> top10 = new ArrayList<>(scores.getAllScore().subList(0, Math.min(scores.getAllScore().size(), 10)));
        ListViewScoreAdapter adapter = new ListViewScoreAdapter(getContext(), R.layout.score_list_item, top10);
        binding.scoreList.setAdapter(adapter);
    }

    public void addScore(User user) {
        this.scores.addScore(user);

    }

    public Scores getScores() {
        return scores;
    }

    public ScoreFragment setScores(Scores scores) {
        this.scores = scores;
        return this;
    }
}