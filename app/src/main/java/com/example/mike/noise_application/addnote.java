package com.example.mike.noise_application;

import android.content.Intent;
import android.icu.util.Output;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.mike.noise_application.R.id.output;

public class addnote extends AppCompatActivity {

    String filename = "sample"+ ".txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button btnsave = (Button) this.findViewById(R.id.savebtn);
        final Button btnload = (Button) this.findViewById(R.id.loadbtn);

        final EditText editText = (EditText) this.findViewById(R.id.textinput);
        final TextView textView = (TextView) this.findViewById(output);
        final File file = new File(Environment.getExternalStorageDirectory(), filename);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fo = new FileOutputStream(file);

                    fo.write(editText.getText().toString().getBytes());
                    fo.close();
                    Toast.makeText(getApplicationContext(), "save successfull",Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        btnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int length = (int) file.length();
                    byte[] bytes = new byte[length];
                    FileInputStream fi = new FileInputStream(file);
                    fi.read(bytes);

                    String text = new String(bytes);
                    textView.setText(text);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);


        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {

            case R.id.action_settings:
                break;

            case R.id.action_info:
                break;

            case R.id.action_homepage:
                Intent homebutton = new Intent(addnote.this, MainPage.class);
                startActivity(homebutton);
                break;
            case R.id.action_data:
                Intent Data = new Intent(addnote.this,Data_page.class);
                startActivity(Data);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
