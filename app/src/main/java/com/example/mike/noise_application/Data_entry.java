package com.example.mike.noise_application;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Data_entry extends AppCompatActivity {

    private ImageButton butcam, recbtn, butnote;
    private ImageView imageview;
    private final int requestCode = 20;

    DatabaseHelper myDB;
    EditText editName,editTextID;
    Button btnAddData;
    Button btnView;
    Button btnUpdate;
    Button btnDelete;



    Button b_time;
    Button b_date;
    int day_x,month_x,year_x,hour_x,minute_x;
    static final int DLG_time=1;
    static final int DLG_date=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDB=new DatabaseHelper(this);

        editName= (EditText)findViewById(R.id.editText_name);

        editTextID= (EditText)findViewById(R.id.editTextID);
        btnAddData= (Button) findViewById(R.id.button_add);
        btnView= (Button) findViewById(R.id.button_view);
        btnUpdate=(Button)findViewById(R.id.button_update);
        btnDelete=(Button)findViewById(R.id.button_delete);

        b_time=(Button)findViewById(R.id.b_time);
        b_date=(Button)findViewById(R.id.b_date);

        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);
        hour_x=cal.get(Calendar.HOUR_OF_DAY);
        minute_x=cal.get(Calendar.MINUTE);



        AddData();
        viewAll();
        updateData();
        deleteDate();
        DateDialogDate();
        DateDialogTime();

    }
    public void DateDialogDate()
    {
        b_date=(Button)findViewById(R.id.b_date);
        b_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DLG_date);
            }
        });
    }

    public void DateDialogTime()
    {
        b_time=(Button)findViewById(R.id.b_time);
        b_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DLG_time);
            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch(id) {
            case DLG_date:
                return new DatePickerDialog(Data_entry.this,dpickerListner,year_x,month_x,day_x);

            case DLG_time:
                return new TimePickerDialog(Data_entry.this,tpickerListner,hour_x,minute_x,false);

            default:
                return null;
        }
    }


    private DatePickerDialog.OnDateSetListener dpickerListner= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x=year;
            month_x=month;
            day_x=dayOfMonth;


        }
    };
    private TimePickerDialog.OnTimeSetListener tpickerListner= new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x=hourOfDay;
            minute_x=minute;

        }


    };
    public void deleteDate()
    {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows=myDB.deleteData(editTextID.getText().toString());
                        if(deletedRows>0)
                        {
                            Toast.makeText(Data_entry.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(Data_entry.this,"Data is not Deleted",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void updateData()
    {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String temp_date= String.valueOf(year_x)+'/'+ String.valueOf(month_x)+'/'+String.valueOf(day_x);
                        String temp_time= String.valueOf(hour_x)+':'+String.valueOf(minute_x);
                        boolean isUpdate=myDB.updateData(editTextID.getText().toString(),editName.getText().toString(),temp_date,temp_time);
                        if(isUpdate==true)
                        {
                            Toast.makeText(Data_entry.this,"Data Updated",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(Data_entry.this,"Data is not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void AddData()
    {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String temp_date= String.valueOf(year_x)+'/'+ String.valueOf(month_x+1)+'/'+String.valueOf(day_x);
                        String temp_time= String.valueOf(hour_x)+':'+String.valueOf(minute_x);
                        boolean isInserted= myDB.insertData(editName.getText().toString(),temp_date,temp_time);

                        editName.setText("");
                        if(isInserted==true) {
                            Toast.makeText(Data_entry.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Data_entry.this, MainPage.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(Data_entry.this,"Data is not Inserted",Toast.LENGTH_LONG).show();


                    }
                }
        );
    }
    public void viewAll()
    {
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res= myDB.getAllData();
                        if(res.getCount()==0)
                        {
                            showMessage("Error","Nothing found");
                            return;
                        }
                        StringBuffer buffer= new StringBuffer();

                        while(res.moveToNext())
                        {
                            buffer.append("Id :"+res.getString(0)+"\n");
                            buffer.append("Name :"+res.getString(1)+"\n");
                            buffer.append("Date :"+res.getString(2)+"\n");
                            buffer.append("Time :"+res.getString(3)+"\n\n");


                        }
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title, String message)
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }


    public void camaraaction() {
        butcam =(ImageButton) findViewById(R.id.btncam);
        imageview = (ImageView) findViewById(R.id.imageview);
//        imageview.setRotation(imageview.getRotation()+ 90);
        butcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent picture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(picture,requestCode);
            }
        });
    }

    public void recording() {
        recbtn =(ImageButton) findViewById(R.id.recbtn);
        recbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Recording = new Intent(Data_entry.this,Recording.class);

                startActivity(Recording);
            }
        });
    }
    public void note() {
        butnote =(ImageButton) findViewById(R.id.notebtn);
        butnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addnote = new Intent(Data_entry.this,addnote.class);

                startActivity(addnote);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageview.setImageBitmap(bitmap);*/
        if(this.requestCode == requestCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");

            String partFilename = currentDateFormat();
            storeCameraPhotoInSDCard(bitmap, partFilename);

            // display the image from SD Card to ImageView Control
            String storeFilename = "photo_" + partFilename + ".jpg";
            Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
            imageview.setImageBitmap(mBitmap);
            imageview.setImageBitmap(bitmap);
        }

    }
    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate){
        File outputFile = new File(Environment.getExternalStorageDirectory(), "photo" + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Bitmap getImageFileFromSDCard(String filename){
        Bitmap bitmap = null;
        File imageFile = new File(Environment.getExternalStorageDirectory() + filename);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
            camaraaction();
            recording();
            note();

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
                Intent homebutton = new Intent(Data_entry.this, MainPage.class);
                startActivity(homebutton);
                break;
            case R.id.action_data:
                Intent Data = new Intent(Data_entry.this,Data_page.class);
                startActivity(Data);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}

