package com.tetris.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tetris.R;
import com.tetris.utils.UserSettings;

public class GraphicActivity extends AppCompatActivity {

    private RadioGroup myRadioGroup;
    private Button confirm;
    private int optionDefault;
    private RadioButton highButton;
    private RadioButton mediumButton;
    private RadioButton lowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        highButton = (RadioButton) findViewById(R.id.high_radioButton);
        mediumButton = (RadioButton) findViewById(R.id.medium_radioButton);
        lowButton = (RadioButton) findViewById(R.id.low_radioButton);
        settingDefault();

        myRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.high_radioButton:UserSettings.setGraphicsResolution(1);
                        break;
                    case R.id.medium_radioButton:UserSettings.setGraphicsResolution(2);
                        break;
                    case R.id.low_radioButton:UserSettings.setGraphicsResolution(3);
                        break;
                }
            }
        });

        confirm = findViewById(R.id.ok_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSettings.changeResolution();
                openMainActivity();
            }
        });
    }

    private void settingDefault() {
        optionDefault = UserSettings.getGraphicsResolution();
        switch (optionDefault){
            case 1:
                highButton.setChecked(true);
                break;
            case 2:mediumButton.setChecked(true);
                break;
            case 3:lowButton.setChecked(true);
                break;
        }
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}