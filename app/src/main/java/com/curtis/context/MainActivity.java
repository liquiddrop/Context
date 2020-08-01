package com.curtis.context;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    ImageButton world_button;
    private Context context = this;

    private static String[] countrySuggestions = { "United States","Italy","Greece","Germany","France","China","England","Egypt","Russia" };
    private static String[] citySuggestions = { "Washington","Rome","Berlin","New York","Paris","Athens","Jerusalem" };
    private String[] countryArray;
    private String[] cityArray;


    protected HistoricalEventViewModel viewModel;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

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

        context.deleteDatabase("historical_events_database.db");
        Log.i(TAG, "Entering onCreate");
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
        context = this;
        Intent intent = new Intent(context, World_History.class);
        startActivity(intent);
    }

    public void goToCountry(View v)
    {
        context = this;
        EditText inputText = (EditText)findViewById(R.id.countryEditText);
        String content = inputText.getText().toString(); //gets you the contents of edit text
        Log.i(TAG, "Clicked Go for country " + content);
        Intent intent = new Intent(context, Single_List_Display.class);
        intent.putExtra("Country", content);
        startActivity(intent);
    }

    public void goToCity(View v)
    {
        context = this;
        EditText inputText = (EditText)findViewById(R.id.cityEditText);
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
