package com.prince.android.willstart.Entity.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.prince.android.willstart.R;

public class FirstActivity extends AppCompatActivity implements ImageButton.OnClickListener{


    private EditText et;
    private ImageButton ib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        et=(EditText)findViewById(R.id.keyword);
        ib=(ImageButton)findViewById(R.id.imageButton);
        ib.setOnClickListener(this);





    }

    @Override
    public void onClick(View view) {




    }
}
