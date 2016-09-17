package com.prince.android.willstart.Entity.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.prince.android.willstart.R;

public class FormBullets extends AppCompatActivity {

    private EditText editText;
    private CheckBox cb;
    private LinearLayout ll;
    private LinearLayout innerll;
    private LinearLayout.LayoutParams params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_bullets);
        ll=(LinearLayout)findViewById(R.id.activity_form_bullets);
        innerll=(LinearLayout)findViewById(R.id.innerLinearLayout);
        editText=new EditText(this);
        params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        cb = new CheckBox(this);
        cb.setChecked(true);



        editText.setLayoutParams(params);
        editText.setText("");
        innerll.addView(cb);
        innerll.addView(editText);


        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
