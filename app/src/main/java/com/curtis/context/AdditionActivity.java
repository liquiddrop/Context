package com.curtis.context;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class AdditionActivity extends AppCompatActivity {

    public static final String TAG = "AdditionActivity";
    Bundle extras;
    String Key;
    String Value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "We are in on Create of Main activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Add Timeline");

        Log.i(TAG, "We are in on Create of Addition activity");
        Intent intent = getIntent();
        extras = intent.getExtras();
        if(extras != null){
            if(extras.containsKey("Country")){
                Value = intent.getStringExtra("Country");
                Key = "Country";
            }
            else if(extras.containsKey("City")){
                Value = intent.getStringExtra("City");
                Key = "City";
            }
            else {
                Value = "There was no input!";
            }
            Log.i(TAG, "Clicked Go for " + Value);
        }
        else{
            setTitle("No Input!!");
        }
    }

    public void goToWorldHistory(View v)
    {
        final Context context = this;
        Intent intent;
        if(extras != null) {
            intent = new Intent(context, Dual_List_Display.class);
            intent.putExtras(extras);
        }
        else {
            intent = new Intent(context, World_History.class);
        }
        startActivity(intent);
    }

    public void goToCountry(View v)
    {
        final Context context = this;
        EditText inputText = (EditText)findViewById(R.id.editText);
        String content = inputText.getText().toString(); //gets you the contents of edit text
        Log.i(TAG, "Clicked Go for country " + content);
        String[] multi_message = new String[4];
        Intent intent;
        if(extras != null) {
            intent = new Intent(context, Dual_List_Display.class);
            multi_message[0] = Key;
            multi_message[1] = Value;
            multi_message[2] = "Country";
            multi_message[3] = content;
            intent.putExtra("Multi", multi_message);
        }
        else {
            intent = new Intent(context, Dual_List_Display.class);
            intent.putExtra("Country", content);
        }
        startActivity(intent);
    }

    public void goToCity(View v)
    {
        final Context context = this;
        EditText inputText = (EditText)findViewById(R.id.editText2);
        String content = inputText.getText().toString(); //gets you the contents of edit text
        Log.i(TAG, "Clicked Go for city " + content);
        String[] multi_message = new String[4];
        Intent intent;
        if(extras != null) {
            intent = new Intent(context, Dual_List_Display.class);
            multi_message[0] = Key;
            multi_message[1] = Value;
            multi_message[2] = "City";
            multi_message[3] = content;
            intent.putExtra("Multi", multi_message);
        }
        else {
            intent = new Intent(context, Dual_List_Display.class);
            intent.putExtra("City", content);
        }
        startActivity(intent);
    }
}
