package com.example.wordle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private TextView scoreTextView;
    //private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("W!");
        toolbar.inflateMenu(R.menu.main_menu);
        initToolbar();
        setTexToNull();

        scoreTextView = findViewById(R.id.scoreTextView);
        String score = scoreTextView.getText().toString();

    }

    public void initToolbar() {

        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.Howto) {
                Toast.makeText(getApplicationContext(), "Not Implemented!! You clicked HowTo!!", Toast.LENGTH_LONG).show();
                //startActivity(new Intent(getApplicationContext(), Folder.class));
                return true;
            }
            else  if (id == R.id.Analytics) {
                Toast.makeText(getApplicationContext(), "Not Implemented !!", Toast.LENGTH_LONG).show();
                //startActivity(new Intent(getApplicationContext(), Folder.class));
                return true;
            }
            else  if (id == R.id.Settings) {
                Toast.makeText(getApplicationContext(), "Not Implemented !!", Toast.LENGTH_LONG).show();
                //startActivity(new Intent(getApplicationContext(), Folder.class));
                return true;
            }
            else  if (id == R.id.Swap) {
                Toast.makeText(getApplicationContext(), "Not Implemented !!", Toast.LENGTH_LONG).show();
                //startActivity(new Intent(getApplicationContext(), Folder.class));
                return true;
            }
            else {  return false; }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setTexToNull() {
        TableLayout table = findViewById(R.id.GameBoard);
        for(int i = 0; i < table.getChildCount(); i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for(int j = 0; j < row.getChildCount(); j++) {
                EditText editText = (EditText) row.getChildAt(j);
                //Do what you need to do.
                editText.setText("");
                editText.setCursorVisible(false);
            }
        }

    }

}