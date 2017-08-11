package com.example.uehara1414.jankenapp;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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
        int gameResult = (comHand - my_hand + 3) % 3;
        switch (gameResult){
            case 0:
                // あいこ
                resultLabel.setText(R.string.result_draw);
                break;
            case 1:
                // 勝った
                resultLabel.setText(R.string.result_win);
                break;
            case 2:
                // 負けた
                resultLabel.setText(R.string.result_lose);
                break;
        }
    }

    public void onBackButtonTapped(View view){
        finish();
    }
}
