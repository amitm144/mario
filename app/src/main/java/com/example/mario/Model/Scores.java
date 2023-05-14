package com.example.mario.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Scores {
    private ArrayList<User> allScore;

    public Scores() {
        this.allScore = new ArrayList<>();
    }

    public Scores(ArrayList<User> allScore) {
        this.allScore = allScore;
    }

    public void addScore(User user){
        allScore.add(user);
    }
    public void removeUser(User user) { allScore.remove(user);}

    public ArrayList<User> getAllScore() {
        sort();
        return allScore;
    }

    private void sort(){
        Collections.sort(allScore, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                // Compare the score field of User objects
                return Integer.compare(user2.getScore(), user1.getScore()); // Descending order
            }
        });
    }
}
