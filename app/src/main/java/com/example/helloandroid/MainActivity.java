package com.example.helloandroid;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helloandroid.lifecycle.ActivityA;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText et;
    TextView tv;
    Button button, yesPopupButton, noPopupButton, shareButton, searchButton, navActivities;
    PopupWindow popupWindow;
    LinearLayout linearLayoutMain;
    Toast toast;
    Context appContext;
    Spinner colorSpinner;
    ArrayAdapter<CharSequence> spinnerAdapter;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        et = (EditText) findViewById(R.id.editText);
        tv = (TextView) findViewById(R.id.textView);

        linearLayoutMain = (LinearLayout) findViewById(R.id.linearLayoutMain);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //change textView in activity main
                tv.setText("Hello, " + et.getText());

                //set layout for the popup
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup,null);

                //change text for the popup
                ((TextView)customView.findViewById(R.id.popupText)).setText("Is your name " + et.getText() + "?");
                //get instance to popup button
                yesPopupButton = (Button) customView.findViewById(R.id.yesButton);
                noPopupButton = (Button) customView.findViewById(R.id.noButton);

                //create popup window
                popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.showAtLocation(linearLayoutMain, Gravity.CENTER, 0, 0);

                appContext = getApplicationContext();

                yesPopupButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        toast = Toast.makeText(appContext, "Hello, " + et.getText(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

                noPopupButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        toast = Toast.makeText(appContext, "Enter name again", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        });

        colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
        colorSpinner.setOnItemSelectedListener(this);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_colors, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(spinnerAdapter);

        shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, et.getText());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri searchLink = Uri.parse("https://www.google.com/search?q=" + et.getText());
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, searchLink);
                startActivity(sendIntent);
            }
        });

        navActivities = (Button) findViewById(R.id.startNav);
        navActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Intent.ACTION_MAIN);
                //intent.setComponent(new ComponentName("com.example.helloandroid", "com.example.helloandroid.ActivityA"));
                Intent intent = new Intent(MainActivity.this, ActivityA.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        switch (selectedItem){
            case "Red":
                button.setTextColor(getResources().getColor(R.color.red));
                break;
            case "Blue":
                button.setTextColor(getResources().getColor(R.color.blue));
                break;
            case "Green":
                button.setTextColor(getResources().getColor(R.color.green));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}