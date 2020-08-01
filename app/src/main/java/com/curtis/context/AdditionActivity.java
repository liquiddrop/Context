package com.curtis.context;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AdditionActivity extends AppCompatActivity {

    public static final String TAG = "AdditionActivity";
    Bundle extras;
    String Key;
    String Value;
    private String[] countryArray;
    private String[] cityArray;
    protected HistoricalEventViewModel viewModel;
    private Context context = this;

    //function to sort by most common occurance and get rid of duplicates in a list of strings
    public String[] sortMostCommon(List<String> input)
    {
        HashMap<String, Integer> countMap
                = new HashMap<String, Integer>();

        for (int i = 0; i < input.size(); i++) {
            if (countMap.containsKey(input.get(i))) {

                // If char is present in charCountMap,
                // incrementing it's count by 1
                countMap.put(input.get(i), countMap.get(input.get(i)) + 1);
            }
            else {

                // If char is not present in charCountMap,
                // putting this char to charCountMap with 1 as it's value
                countMap.put(input.get(i), 1);
            }
        }
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(countMap.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        String output[] = new String[list.size()];
        int j=list.size()-1;
        for (Map.Entry<String, Integer> aa : list) {
            Log.i(TAG, "Key " + aa.getKey() + " NumOccur: " + aa.getValue());
            output[j] = aa.getKey();
            j--;
            //temp.put(aa.getKey(), aa.getValue());
        }
        return output;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "We are in on Create of Main activity");
        context = this;
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
            Log.i(TAG, "No extras so probably from world history");
        }

        viewModel = ViewModelProviders.of(this).get(HistoricalEventViewModel.class);

        viewModel.getAllCountries().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> countries) {
                // Update the cached copy of the words in the adapter.
                Log.i(TAG, "In get events with importance");
                if(countries.isEmpty())
                {
                    Log.i(TAG, "Countries is empty!");
                }
                else
                {
                    for (int i = 0; i < countries.size(); i++) {
                        Log.i(TAG, "Country " + countries.get(i));
                    }
                    countryArray = new String[countries.size()];
                    countryArray = sortMostCommon(countries);
                    //countries.toArray(countryArray);
                    ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(context,
                            android.R.layout.simple_dropdown_item_1line, countryArray);
                    final AutoCompleteTextView countryView = (AutoCompleteTextView)
                            findViewById(R.id.countryEditText);
                    countryView.setAdapter(countryAdapter);
                    countryView.setDropDownHeight(500);
                    countryView.setThreshold(1);
                    countryView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            countryView.showDropDown();
                        }
                    });
                }
            }
        });

        viewModel.getAllCities().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable final List<String> cities) {
                // Update the cached copy of the words in the adapter.
                Log.i(TAG, "In get events with importance");
                if(cities.isEmpty())
                {
                    Log.i(TAG, "Cities is empty!");
                }
                else
                {
                    for (int i = 0; i < cities.size(); i++) {
                        Log.i(TAG, "City " + cities.get(i));
                    }
                    cityArray = new String[cities.size()];
                    cityArray = sortMostCommon(cities);
                    //cities.toArray(cityArray);
                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(context,
                            android.R.layout.simple_dropdown_item_1line, cityArray);
                    final AutoCompleteTextView cityView = (AutoCompleteTextView)
                            findViewById(R.id.cityEditText);
                    cityView.setAdapter(cityAdapter);
                    cityView.setDropDownHeight(320);
                    cityView.setThreshold(1);
                    cityView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cityView.showDropDown();
                        }
                    });
                }
            }
        });
    }

    public void goToWorldHistory(View v)
    {
        context = this;
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
        context = this;
        EditText inputText = (EditText)findViewById(R.id.countryEditText);
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
        context = this;
        EditText inputText = (EditText)findViewById(R.id.cityEditText);
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
