package com.prince.android.willstart.Entity.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prince.android.willstart.R;

import java.util.ArrayList;

/**
 * Created by Shuvam Ghosh on 9/18/2016.
 */

public class ActivityAnalysis extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerViewAdapter adapter;
    private ArrayList<String> suggestions;
    private RecyclerView.LayoutManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        suggestions= new ArrayList<String>();
        init();

        suggestions.add("Hello");
        suggestions.add("How are you");
        suggestions.add("Focus on your goals");
        suggestions.add("Okay");
        suggestions.add("Now go!!");



    }

    private void init() {
        manager = new LinearLayoutManager(this);
        rv=(RecyclerView)findViewById(R.id.recView);
        rv.setLayoutManager(manager);
        adapter=new RecyclerViewAdapter(suggestions);
        rv.setAdapter(adapter);


    }


    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {

        ArrayList<String> list=new ArrayList<String>();

        public RecyclerViewAdapter(ArrayList<String> suggestions) {
            this.list=suggestions;
        }

        @Override
        public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.analysis_item,parent,false);
            MyView myView= new MyView(v);
            return myView;
        }

        @Override
        public void onBindViewHolder(MyView holder, int position) {

            holder.tv.setText(suggestions.get(position));

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class MyView extends RecyclerView.ViewHolder
        {
            private TextView tv;

            public MyView(View itemView) {
                super(itemView);
                tv=(TextView)itemView.findViewById(R.id.textView);


            }
        }
    }
}
