package com.example.wordle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private TextView scoreTextView;
    private ArrayList <EditText> editTexts = new ArrayList<>();
    private ArrayList <EditText> rowOfEditText = new ArrayList<>();
    //private TableLayout table;
    private String word = "";
    private String score;
    private String original_word;
    private BufferedReader bufferedReader;
    private Button submit, skip, hint;
    private ArrayList <String> word_list = new ArrayList<>();
    int count = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("W!");
        toolbar.inflateMenu(R.menu.main_menu);
        initToolbar();
        setTexToNull();
        scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("0");
        score = scoreTextView.getText().toString();


            try {
                getWord();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        Random rand = new Random();
        int n = rand.nextInt(2500);
        original_word = word_list.get(n);
        String [] arr = new String[5];
        arr[0] = Character.toString(original_word.charAt(0));
        arr[1] = Character.toString(original_word.charAt(1));
        arr[2] = Character.toString(original_word.charAt(2));
        arr[3] = Character.toString(original_word.charAt(3));
        arr[4] = Character.toString(original_word.charAt(4));
        Log.d("The original word is: ", original_word);

        //Set cursor to the first Edittext
        editTexts.get(0).requestFocus();

        submit = findViewById(R.id.btn_submit);
        submit.setEnabled(false);
        hint = findViewById(R.id.btn_hint);
        skip = findViewById(R.id.btn_skip);

        for (EditText editText : editTexts) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    submit.setEnabled(false);
                    submit.setBackgroundColor(Color.parseColor("#7899B3"));
                    submit.setText(R.string.submit);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    word += editText.getText().toString();
                    rowOfEditText.add(editText);
                    nextTextView().requestFocus();
                    //checkWord();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    for (int i = 4; i < editTexts.size(); i+=5) {
                        if (editTexts.get(i) == editText) {
                            submit.setEnabled(true);
                            submit.setBackgroundColor(Color.parseColor("#0D5995"));
                            submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getApplicationContext(), "Not Implemented!! You clicked SUBMIT!!", Toast.LENGTH_LONG).show();
                                    Log.i("The complete word is: ", word);
                                    //checkWord(); STARTS
                                    for (int i = 0; i < rowOfEditText.size(); i++) {
                                        String letter = rowOfEditText.get(i).getText().toString();
                                        if (original_word.contains(letter)) {
                                            if (arr[i].equals(letter)) {
                                                rowOfEditText.get(i).setBackgroundColor(Color.parseColor("#578C59"));
                                                rowOfEditText.get(i).setTextColor(Color.WHITE);
                                            } else {
                                                rowOfEditText.get(i).setBackgroundColor(Color.YELLOW);
                                                rowOfEditText.get(i).setTextColor(Color.WHITE);
                                            }

                                        } else {
                                            rowOfEditText.get(i).setBackgroundColor(Color.GRAY);
                                            rowOfEditText.get(i).setTextColor(Color.WHITE);
                                        }
                                        count--;
                                    }
                                    word = "";
                                    rowOfEditText.clear();
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
                editText.setClickable(false);
                //
                // editText.setFocusableInTouchMode(false);
                editTexts.add(editText);
            }
        }
    }

    public void getWord() throws IOException {

        String line = "";
        bufferedReader = new BufferedReader(
                new InputStreamReader(this.getAssets().open("words.txt")));
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            word_list.add(line.toUpperCase());
        }
        bufferedReader.close();
    }


}