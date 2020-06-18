package com.curtis.context;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class Single_List_Display extends AppCompatActivity {

    public static final String TAG = "Single_List_Display";

    protected RecyclerViewFragment fragment;
    protected Bundle extras;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        // Find search item
        final MenuItem item = menu.findItem(R.id.action_search);
        // Get the SearchView and set the searchable configuration
        //final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) item.getActionView();

        searchView.setLayoutParams(new ActionBar.LayoutParams(Gravity.RIGHT));
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
        setContentView(R.layout.activity_single_list);
        Intent intent = getIntent();
        extras = intent.getExtras();
        String message;
        if(extras != null){
            if(extras.containsKey("Country")){
                message = intent.getStringExtra("Country");
            }
            else if(extras.containsKey("City")){
                message = intent.getStringExtra("City");
            }
            else {
                message = "There was no input!";
            }
            Log.i(TAG, "Clicked Go for " + message);
            setTitle("History of " + message);
        }
        else{
            setTitle("No Input!!");
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = new RecyclerViewFragment();
        transaction.replace(R.id.content_fragment_single, fragment);
        fragment.setArguments(extras);
        transaction.commit();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdditionActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

}
