package com.example.uehara1414.jankenapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {
    final int JANKEN_GU = 0;
    final int JANKEN_CHOKI = 1;
    final int JANKEN_PA = 2;

    final int RESULT_DRAW = 0;
    final int RESULT_WIN = 1;
    final int RESULT_LOSE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resetData();

        int my_hand = 0;
        Intent intent = getIntent();
        int id = intent.getIntExtra("MY_HAND", 0);

        ImageView myHandImageView = (ImageView)findViewById(R.id.my_hand);
        switch (id){
            case R.id.goo:
                myHandImageView.setImageResource(R.drawable.goo);
                my_hand = JANKEN_GU;
                break;
            case R.id.choki:
                myHandImageView.setImageResource(R.drawable.choki);
                my_hand = JANKEN_CHOKI;
                break;
            case R.id.pa:
                myHandImageView.setImageResource(R.drawable.pa);
                my_hand = JANKEN_PA;
                break;
            default:
                my_hand = JANKEN_GU;
                break;
        }

        int comHand = (int)(Math.random() * 3);
        ImageView comHandImageView = (ImageView)findViewById(R.id.computer_hand);

        switch (comHand){
            case JANKEN_GU:
                comHandImageView.setImageResource(R.drawable.goo);
                break;
            case JANKEN_CHOKI:
                comHandImageView.setImageResource(R.drawable.choki);
                break;
            case JANKEN_PA:
                comHandImageView.setImageResource(R.drawable.pa);
                break;
        }

        TextView resultLabel = (TextView)findViewById(R.id.result);
        int gameResult = getResult(my_hand, comHand);

        switch (gameResult){
            case RESULT_DRAW:
                resultLabel.setText(R.string.result_draw);
                break;
            case RESULT_WIN:
                resultLabel.setText(R.string.result_win);
                break;
            case RESULT_LOSE:
                resultLabel.setText(R.string.result_lose);
                break;
        }
    }

    public void onBackButtonTapped(View view){
        finish();
    }

    private int getResult(int myHand, int comHand){
        return (comHand - myHand + 3) % 3;
    }

    private void resetData(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    private void saveData(int myHand, int comHand){
        int gameResult = getResult(myHand, comHand);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();

        int gameCount = pref.getInt("GAME_COUNT", 0);
        int winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0);
        int lastComHand = pref.getInt("LAST_COM_HAND", 0);
        int lastGameResult = pref.getInt("LAST_GAME_RESULT", -1);

        editor.putInt("GAME_COUNT", gameCount + 1);
        if (lastGameResult == RESULT_WIN && gameResult == RESULT_WIN){
            editor.putInt("WINNING_STREAK_COUNT", winningStreakCount + 1);
        } else {
            editor.putInt("WINNING_STREAK_COUNT", 0);
        }
        editor.putInt("LAST_MY_HAND", myHand);
        editor.putInt("LAST_COM_HAND", comHand);
        editor.putInt("BEFORE_LAST_COM_HAND", lastComHand);
        editor.putInt("GAME_RESULT", gameResult);

        editor.commit();
    }
}
