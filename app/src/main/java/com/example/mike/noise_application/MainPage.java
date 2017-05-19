package com.example.mike.noise_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainPage extends AppCompatActivity {

    public ImageButton but1, but2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void dataentry() {
        but1 = (ImageButton) findViewById(R.id.data_entry);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Dataentry = new Intent(MainPage.this, Data_entry.class);

                startActivity(Dataentry);
            }
        });
    }
    public void datapage() {
        but2 =(ImageButton) findViewById(R.id.data);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Datapage = new Intent(MainPage.this,Data_page.class);

                startActivity(Datapage);
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
            dataentry();
            datapage();
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
                Intent homebutton = new Intent(MainPage.this, MainPage.class);
                startActivity(homebutton);
                break;
            case R.id.action_data:
                Intent Data = new Intent(MainPage.this,Data_page.class);
                startActivity(Data);
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}

