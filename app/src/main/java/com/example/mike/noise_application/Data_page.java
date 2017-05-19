package com.example.mike.noise_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Data_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_page, menu);

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
            case R.id.action_Daily_Overview:
                Intent daily = new Intent(Data_page.this,Data_page.class);
                startActivity(daily);
                break;

            case R.id.action_Monthly_Overview:
                Intent Monthly = new Intent(Data_page.this, monthoverview.class);
                startActivity(Monthly);
                break;

            case R.id.action_Yearly_Overview:

                Intent Yearly = new Intent(Data_page.this, yearoverview.class);
                startActivity(Yearly);
                break;

            case R.id.action_homepage:
                Intent homebutton = new Intent(Data_page.this, MainPage.class);
                startActivity(homebutton);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

}
