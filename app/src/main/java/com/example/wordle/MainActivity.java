package com.example.wordle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private TextView scoreTextView;
    private ArrayList <EditText> editTexts = new ArrayList<>();
    //private TableLayout table;
    String word;
    String score;
    String original_word;
    BufferedReader bufferedReader;
    Button submit, skip, hint;

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
        scoreTextView.setText("0");
        score = scoreTextView.getText().toString();

        submit = findViewById(R.id.btn_submit);
        submit.setEnabled(false);
        hint = findViewById(R.id.btn_hint);
        skip = findViewById(R.id.btn_skip);


        for (EditText editText : editTexts) {
            word += editText.getText().toString();
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    submit.setEnabled(false);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    nextTextView().requestFocus();
                    //checkWord();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    for (int i = 4; i < editTexts.size() ; i+=5) {
                        if (editTexts.get(i) == editText) {
                            submit.setEnabled(true);
                            submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getApplicationContext(), "Not Implemented!! You clicked SUBMIT!!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }

                public TextView nextTextView() {
                    int i;
                    for (i = 0; i < editTexts.size() - 1; i++) {
                        if (editTexts.get(i) == editText)
                            return editTexts.get(i + 1);
                    }
                    return editTexts.get(i);
                }
            });
        }

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
                editTexts.add(editText);
            }
        }
    }

    //public String getWord() {}

}