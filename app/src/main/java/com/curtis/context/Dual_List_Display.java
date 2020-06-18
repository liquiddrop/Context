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

public class Dual_List_Display extends AppCompatActivity {

    public static final String TAG = "Dual_List_Display";

    protected RecyclerViewFragment fragment_left;
    protected RecyclerViewFragment fragment_right;

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
                fragment_left.setListPosition(fragment_left.searchForClosestEvent(Integer.parseInt(query)));
                fragment_right.setListPosition(fragment_right.searchForClosestEvent(Integer.parseInt(query)));
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
        setContentView(R.layout.activity_dual_list);
        setTitle("Compare History");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Bundle extras_left = intent.getExtras(), extras_right = intent.getExtras();
        String message;
        String[] multi_message;
        boolean has_multi = false;
        if(extras != null){
            if(extras.containsKey("Country")){
                message = intent.getStringExtra("Country");
            }
            else if(extras.containsKey("City")){
                message = intent.getStringExtra("City");
            }
            else if(extras.containsKey("Multi")){
                multi_message = intent.getStringArrayExtra("Multi");
                message = "Check multi";
                Log.i(TAG, "Clicked Go for " + multi_message[0] + " " + multi_message[1] + " " + multi_message[2] + " " + multi_message[3]);
                has_multi = true;
                extras_left.putString(multi_message[0], multi_message[1]);
                extras_right.putString(multi_message[2], multi_message[3]);
                setTitle("History of " + multi_message[1] + " vs " + multi_message[3]);
            }
            else {
                message = "There was no input!";
            }
            Log.i(TAG, "Clicked Go for " + message);
            if(!has_multi)
                setTitle("History of " + message + " vs World");
        }
        else{
            setTitle("No Input!!");
        }

        if(has_multi) {
            FragmentTransaction transaction_left = getSupportFragmentManager().beginTransaction();
            fragment_left = new RecyclerViewFragment();
            transaction_left.replace(R.id.content_fragment_dual_left, fragment_left);
            fragment_left.setArguments(extras_left);
            transaction_left.commit();
            FragmentTransaction transaction_right = getSupportFragmentManager().beginTransaction();
            fragment_right = new RecyclerViewFragment();
            transaction_right.replace(R.id.content_fragment_dual_right, fragment_right);
            fragment_right.setArguments(extras_right);
            transaction_right.commit();
        }
        else {
            FragmentTransaction transaction_left = getSupportFragmentManager().beginTransaction();
            fragment_left = new RecyclerViewFragment();
            transaction_left.replace(R.id.content_fragment_dual_left, fragment_left);
            fragment_left.setArguments(extras);
            transaction_left.commit();
            FragmentTransaction transaction_right = getSupportFragmentManager().beginTransaction();
            fragment_right = new RecyclerViewFragment();
            transaction_right.replace(R.id.content_fragment_dual_right, fragment_right);
            transaction_right.commit();
        }

        FloatingActionButton fab = findViewById(R.id.floatingActionHome);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
