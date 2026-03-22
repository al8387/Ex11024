package com.example.ex11024;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionList = new ArrayList<>();
        loadRawQuestions();
    }

    private void loadRawQuestions() {
        try {
            InputStream is = getResources().openRawResource(R.raw.questions);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                questionList.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
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
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_add_question) {
            Intent intent = new Intent(MainActivity.this, addQuestion.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_credits) {
            Intent intent = new Intent(MainActivity.this, credits.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_game) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}