package com.prince.android.willstart.Entity.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.prince.android.willstart.Entity.Instances.Company;
import com.prince.android.willstart.Entity.Instances.SearchResult;
import com.prince.android.willstart.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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

    private List<Company> searchResultList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setInit();
        setData();
    }

    private void init() {
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        fab=(FloatingActionButton)findViewById(R.id.fab);
    }

    private void setInit() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Will Start");
        if(getIntent().hasExtra("keyword")){
            keyword=getIntent().getStringExtra("keyword");
        }
        handleIntent(getIntent());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setData() {
        List<String> imageUrls=new ArrayList<>();
        imageUrls.add("http://www.mobygames.com/images/i/22/36/1121436.png");
        imageUrls.add("http://www.logospike.com/wp-content/uploads/2015/11/Company_Logos_01.jpg");
        imageUrls.add("https://www.seeklogo.net/wp-content/uploads/2014/12/twitter-logo-vector-download.jpg");
        imageUrls.add("http://www.mobygames.com/images/i/26/09/650109.png");
        imageUrls.add("http://www.jamesgood.co.uk/sites/default/files/Logo-Blog_58.png");
        List<String> names=new ArrayList<>();
        names.add("NASA");
        names.add("Dell");
        names.add("Twitter");
        names.add("EA Sports");
        names.add("Starbucks");
        List<Company> list=new ArrayList<>();
        for(int i=0;i<5;i++){
            Company c=new Company();
            c.setPicUrl(imageUrls.get(i));
            c.setName(names.get(i));
            list.add(c);
        }
        searchResultList=list;
        recyclerView.setAdapter(new SearchResultsAdapter(this));

        Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.scale);
        hyperspaceJump.setStartTime(500);
        fab.startAnimation(hyperspaceJump);    }

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


        private final Context context;

        public SearchResultsAdapter(Context context) {
            this.context=context;
        }

        @Override
        public SearchResultsAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=getLayoutInflater().inflate(R.layout.row_item,parent,false);
            SearchViewHolder searchViewHolder=new SearchViewHolder(view);
            return searchViewHolder;
        }

        @Override
        public void onBindViewHolder(SearchViewHolder holder, int position) {
            holder.companyName.setText(searchResultList.get(position).getName());
            Glide.with(context).load(searchResultList.get(position).getPicUrl()).asBitmap().into(holder.companyLogo);
        }

        @Override
        public int getItemCount() {
            return searchResultList.size();
        }

        public class SearchViewHolder extends RecyclerView.ViewHolder{

            private CircleImageView companyLogo;
            private TextView companyName;
            private RecyclerView servicesRecycler;

            public SearchViewHolder(View itemView) {
                super(itemView);
                companyLogo=(CircleImageView)itemView.findViewById(R.id.companyLogo);
                companyName=(TextView)itemView.findViewById(R.id.companyName);
                servicesRecycler=(RecyclerView)itemView.findViewById(R.id.recViewFeatures);
            }
        }
    }
}
