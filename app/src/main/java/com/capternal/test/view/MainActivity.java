package com.capternal.test.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.capternal.test.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText objEditTextInput = null;
    private TextView objTextViewSample = null;
    private Button objButtonChangeText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        objEditTextInput = (EditText) findViewById(R.id.editTextInput);
        objTextViewSample = (TextView) findViewById(R.id.textView_sample);
        objButtonChangeText = (Button) findViewById(R.id.buttonChangeText);
        objButtonChangeText.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        objEditTextInput = null;
        objTextViewSample = null;
        objButtonChangeText = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonChangeText:
                objTextViewSample.setText(objEditTextInput.getText().toString());
                break;
        }
    }
}
