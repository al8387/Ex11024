package com.example.ex11024;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    private EditText etUsername;
    private TextView tvBestScore;
    private Button btnSave, btnReset;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etUsername = findViewById(R.id.etUsername);
        tvBestScore = findViewById(R.id.tvBestScore);
        btnSave = findViewById(R.id.btnSaveSettings);
        btnReset = findViewById(R.id.btnResetScore);

        sp = getSharedPreferences("GameSettings", Context.MODE_PRIVATE);

        if (etUsername != null) {
            etUsername.setText(sp.getString("username", ""));
        }

        if (tvBestScore != null) {
            tvBestScore.setText("Your Best Score: " + sp.getInt("highscore", 0));
        }

        if (btnSave != null) {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etUsername != null) {
                        sp.edit().putString("username", etUsername.getText().toString()).apply();
                        Toast.makeText(Settings.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (btnReset != null) {
            btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sp.edit().putInt("highscore", 0).apply();
                    if (tvBestScore != null) {
                        tvBestScore.setText("Your Best Score: 0");
                    }
                }
            });
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
        if (id == R.id.menu_settings) return true;
        else if (id == R.id.menu_add_question) startActivity(new Intent(this, addQuestion.class));
        else if (id == R.id.menu_credits) startActivity(new Intent(this, credits.class));
        else if (id == R.id.menu_game) startActivity(new Intent(this, MainActivity.class));
        return super.onOptionsItemSelected(item);
    }
}