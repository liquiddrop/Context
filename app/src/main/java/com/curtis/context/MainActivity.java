package com.curtis.context;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    ImageButton world_button;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "We are in on Create of Main activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        world_button = (ImageButton) findViewById(R.id.world_button);
        final Context context = this;
        world_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, World_History.class);
                startActivity(intent);
            }
        });
        world_text_button = findViewById(R.id.world_text);
        final Context context = this;
        world_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, World_History.class);
                startActivity(intent);
            }
        });
        */
        Log.i(TAG, "We are in on Create of Main activity2");
        if (savedInstanceState == null) {
            /*
            Log.i(TAG, "savedInstanceState is null");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment = new RecyclerViewFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
            */
        }
    }

    public void goToWorldHistory(View v)
    {
        final Context context = this;
        Intent intent = new Intent(context, World_History.class);
        startActivity(intent);
    }

    public void goToCountry(View v)
    {
        final Context context = this;
        EditText inputText = (EditText)findViewById(R.id.editText);
        String content = inputText.getText().toString(); //gets you the contents of edit text
        Log.i(TAG, "Clicked Go for country " + content);
        Intent intent = new Intent(context, Single_List_Display.class);
        intent.putExtra("Country", content);
        startActivity(intent);
    }

    public void goToCity(View v)
    {
        final Context context = this;
        EditText inputText = (EditText)findViewById(R.id.editText2);
        String content = inputText.getText().toString(); //gets you the contents of edit text
        Log.i(TAG, "Clicked Go for city " + content);
        Intent intent = new Intent(context, Single_List_Display.class);
        intent.putExtra("City", content);
        startActivity(intent);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
