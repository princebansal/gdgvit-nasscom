package com.prince.android.willstart.Entity.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.prince.android.willstart.Entity.Instances.SearchResult;
import com.prince.android.willstart.R;

import java.util.List;

/**
 * Created by Prince Bansal Local on 9/17/2016.
 */

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private SearchManager searchManager;
    private SearchView searchView;
    private String keyword;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private List<SearchResult> searchResultList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setInit();
    }

    private void init() {
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        fab=(FloatingActionButton)findViewById(R.id.fab);

    }

    private void setInit() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Will Start");
        if(getIntent().hasExtra("keyword")){
            keyword=getIntent().getStringExtra("keyword");
        }
        handleIntent(getIntent());

        Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.scale);
        hyperspaceJump.setStartTime(500);
        fab.startAnimation(hyperspaceJump);
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
        searchView.setOnQueryTextListener(this);
        if(searchView!=null&&keyword!=null){
            searchView.setQuery(keyword,true);
        }

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
        Log.i(TAG, "onNewIntent: ");
    }

    private void handleIntent(Intent intent) {
        Log.i(TAG, "handleIntent: ");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
          //  Toast.makeText(this,query,Toast.LENGTH_SHORT).show();
            Log.i(TAG, "handleIntent: "+query);
            //use the query to search
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        showMessage(query);
        return false;
    }

    private void showMessage(String query) {
       // Toast.makeText(this,query,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        showMessage(newText);
        return false;
    }

    public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchViewHolder>{


        @Override
        public SearchResultsAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;

        }

        @Override
        public void onBindViewHolder(SearchViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class SearchViewHolder extends RecyclerView.ViewHolder{

            public SearchViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
