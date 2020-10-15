package com.curtis.context;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class World_History extends AppCompatActivity {

    public static final String TAG = "World_History_activity";

    protected RecyclerViewFragment fragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        // Find search item
        final MenuItem item = menu.findItem(R.id.action_search);
        // Get the SearchView and set the searchable configuration
        //final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) item.getActionView();

        searchView.setLayoutParams(new ActionBar.LayoutParams(Gravity.END));
        // Assumes current activity is the searchable activity
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(true);
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                //fragment.setListPosition(fragment.searchForClosestEvent(Integer.parseInt(query)));
                fragment.setListPosition(fragment.searchForClosestEvent(Integer.parseInt(query)));
                searchView.setQuery("",false);
                searchView.clearFocus();
                //searchView.setIconified(true);
                searchView.onActionViewCollapsed();
                item.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                //fragment.setListPosition(fragment.searchForClosestEvent(Integer.parseInt(query)));
                return true;
            }
        });


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world__history);
        setTitle("World History");
        Log.i(TAG, "In world history on create");

        //setup ads this only does something in the free version
        AdUtil ads = new AdUtil();
        ads.setUpAdView(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = new RecyclerViewFragment();
        transaction.replace(R.id.content_fragment_single, fragment);
        transaction.commit();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdditionActivity.class);
                startActivity(intent);
            }
        });
    }

}
