package com.example.ex11024;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.io.FileOutputStream;

/**
 * Activity for adding custom questions to the quiz.
 *
 * @author adam
 * @version 1.0
 * @since 20.3.2026
 */
public class addQuestion extends AppCompatActivity {

    private EditText etQuestion, etAnswer1, etAnswer2, etAnswer3, etAnswer4;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_question);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etQuestion = findViewById(R.id.etQuestion);
        etAnswer1 = findViewById(R.id.etAnswer1);
        etAnswer2 = findViewById(R.id.etAnswer2);
        etAnswer3 = findViewById(R.id.etAnswer3);
        etAnswer4 = findViewById(R.id.etAnswer4);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qText = etQuestion.getText().toString();
                String a1 = etAnswer1.getText().toString();
                String a2 = etAnswer2.getText().toString();
                String a3 = etAnswer3.getText().toString();
                String a4 = etAnswer4.getText().toString();

                if (!qText.isEmpty() && !a1.isEmpty() && !a2.isEmpty() && !a3.isEmpty() && !a4.isEmpty()) {
                    String formattedQuestion = qText + "|" + a1 + "|" + a2 + "|" + a3 + "|" + a4 + "\n";
                    saveQuestionToFile(formattedQuestion);
                } else {
                    Toast.makeText(addQuestion.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Saves the formatted question string to the internal storage file.
     *
     * @param questionData The pipe-separated question and answers string.
     */
    private void saveQuestionToFile(String questionData) {
        try {
            FileOutputStream fos = openFileOutput("user_questions.txt", Context.MODE_APPEND);
            fos.write(questionData.getBytes());
            fos.close();
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            etQuestion.setText("");
            etAnswer1.setText("");
            etAnswer2.setText("");
            etAnswer3.setText("");
            etAnswer4.setText("");
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
        if (id == R.id.menu_settings) startActivity(new Intent(this, Settings.class));
        else if (id == R.id.menu_add_question) return true;
        else if (id == R.id.menu_credits) startActivity(new Intent(this, credits.class));
        else if (id == R.id.menu_game) startActivity(new Intent(this, MainActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
