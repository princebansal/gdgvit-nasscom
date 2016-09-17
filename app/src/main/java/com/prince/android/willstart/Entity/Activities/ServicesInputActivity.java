package com.prince.android.willstart.Entity.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prince.android.willstart.Entity.Instances.InputView;
import com.prince.android.willstart.R;

import java.util.ArrayList;
import java.util.List;

public class ServicesInputActivity extends AppCompatActivity {

    private static final String TAG = SearchResultsActivity.class.getSimpleName();
    private LinearLayout inputContainer;
    private LinearLayout.LayoutParams params;
    private List<InputView> inputViewList;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_bullets);
        init();
        setInit();
    }


    private void init() {
        inputContainer=(LinearLayout)findViewById(R.id.innerLinearLayout);
        inputViewList=new ArrayList<>();
        toolbar=(Toolbar)findViewById(R.id.toolbar);

    }

    private void setInit() {
        addInputView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addInputView() {
        final InputView inputView=new InputView();
        final View v=getLayoutInflater().inflate(R.layout.service_input_item,inputContainer,false);
        inputView.setInputField((EditText) v.findViewById(R.id.phrase));
        inputView.setInputCheck((CheckBox) v.findViewById(R.id.check));
        inputView.getInputField().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_NEXT){
                    Log.i(TAG, "onEditorAction: ");
                    addInputView();
                    inputView.getInputCheck().setChecked(true);
                }
                return false;
            }
        });
        inputView.getInputCheck().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Log.i(TAG, "onClick: ");

                if(!((CheckBox)v2).isChecked())
                {
                    Log.i(TAG, "onClick: on unchecked");
                    inputContainer.removeView(v);
                }
            }
        });
        inputContainer.addView(v);
    }
}
