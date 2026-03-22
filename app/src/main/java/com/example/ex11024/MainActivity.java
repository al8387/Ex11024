package com.example.ex11024;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private TextView tvQuestion, tvScore, tvHighScore;
    private Button btn1, btn2, btn3, btn4, btnAddCustom;
    private ArrayList<String> allQuestions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int highScore = 0;
    private String correctAnswer = "";
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvQuestion = findViewById(R.id.tvQuestion);
        tvScore = findViewById(R.id.tvScore);
        tvHighScore = findViewById(R.id.tvBestScore);
        btn1 = findViewById(R.id.btnAnswer1);
        btn2 = findViewById(R.id.btnAnswer2);
        btn3 = findViewById(R.id.btnAnswer3);
        btn4 = findViewById(R.id.btnAnswer4);
        btnAddCustom = findViewById(R.id.btnAddCustom);

        if (btnAddCustom != null) {
            btnAddCustom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, addQuestion.class));
                }
            });
        }

        sp = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);
        highScore = sp.getInt("highscore", 0);

        if (tvHighScore != null) {
            tvHighScore.setText("High Score: " + highScore);
        }

        loadQuestions();

        if (!allQuestions.isEmpty()) {
            Collections.shuffle(allQuestions);
            displayQuestion();
        } else if (tvQuestion != null) {
            tvQuestion.setText("No questions available!");
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String selectedAnswer = b.getText().toString().trim();

                if (selectedAnswer.equals(correctAnswer)) {
                    score++;
                    if (tvScore != null) tvScore.setText("Score: " + score);
                    Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                }

                currentQuestionIndex++;
                displayQuestion();
            }
        };

        if (btn1 != null) btn1.setOnClickListener(listener);
        if (btn2 != null) btn2.setOnClickListener(listener);
        if (btn3 != null) btn3.setOnClickListener(listener);
        if (btn4 != null) btn4.setOnClickListener(listener);
    }

    private void loadQuestions() {
        allQuestions.clear();

        try {
            InputStream is = getResources().openRawResource(R.raw.questions);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().split("\\|").length == 5) {
                    allQuestions.add(line.trim());
                }
            }
            br.close();
        } catch (Exception e) {
        }

        try {
            FileInputStream fis = openFileInput("user_questions.txt");
            BufferedReader br2 = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br2.readLine()) != null) {
                if (line.trim().split("\\|").length == 5) {
                    allQuestions.add(line.trim());
                }
            }
            br2.close();
        } catch (Exception e) {
        }
    }

    private void displayQuestion() {
        if (allQuestions.isEmpty()) return;

        if (currentQuestionIndex < allQuestions.size()) {
            String[] parts = allQuestions.get(currentQuestionIndex).split("\\|");

            if (tvQuestion != null) tvQuestion.setText(parts[0].trim());
            correctAnswer = parts[1].trim();

            ArrayList<String> answers = new ArrayList<>();
            answers.add(parts[1].trim());
            answers.add(parts[2].trim());
            answers.add(parts[3].trim());
            answers.add(parts[4].trim());
            Collections.shuffle(answers);

            if (btn1 != null) btn1.setText(answers.get(0));
            if (btn2 != null) btn2.setText(answers.get(1));
            if (btn3 != null) btn3.setText(answers.get(2));
            if (btn4 != null) btn4.setText(answers.get(3));
        } else {
            if (score > highScore) {
                highScore = score;
                sp.edit().putInt("highscore", highScore).apply();
                if (tvHighScore != null) tvHighScore.setText("High Score: " + highScore);
            }
            Toast.makeText(this, "Game Over! Score: " + score, Toast.LENGTH_LONG).show();

            currentQuestionIndex = 0;
            score = 0;
            if (tvScore != null) tvScore.setText("Score: " + score);

            Collections.shuffle(allQuestions);
            displayQuestion();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_settings) {
            startActivity(new Intent(this, Settings.class));
            return true;
        } else if (id == R.id.menu_add_question) {
            startActivity(new Intent(this, addQuestion.class));
            return true;
        } else if (id == R.id.menu_credits) {
            startActivity(new Intent(this, credits.class));
            return true;
        } else if (id == R.id.menu_game) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}