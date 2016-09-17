package com.prince.android.willstart.Entity.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.prince.android.willstart.R;

/**
 * Created by Prince Bansal Local on 9/17/2016.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private SearchManager searchManager;
    private SearchView searchView;
    private String keyword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setInit();
    }

    private void init() {
        toolbar=(Toolbar)findViewById(R.id.toolbar);

    }

    private void setInit() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Will Start");
        if(getIntent().hasExtra("keyword")){
            keyword=getIntent().getStringExtra("keyword");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        if(searchView!=null&&keyword!=null){
            searchView.setQuery(keyword,false);
        }
        return true;
    }
}
