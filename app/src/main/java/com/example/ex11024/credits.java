package com.example.ex11024;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for displaying application credits.
 *
 * @author adam
 * @version 1.0
 * @since 20.3.2026
 */
public class credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
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
        else if (id == R.id.menu_add_question) startActivity(new Intent(this, addQuestion.class));
        else if (id == R.id.menu_credits) return true;
        else if (id == R.id.menu_game) startActivity(new Intent(this, MainActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
