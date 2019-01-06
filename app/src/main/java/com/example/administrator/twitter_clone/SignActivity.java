package com.example.administrator.twitter_clone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.parse.ParseInstallation;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class SignActivity extends AppCompatActivity {
//    private Toolbar signinactivityToolbar;
    private Button signinActivitySignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        signinActivitySignUpButton = findViewById( R.id.signinactivitySignUpButton);
//        signinactivityToolbar = findViewById(R.id.signinactivityToolbar);
//        setSupportActionBar(signinactivityToolbar);
//        signinactivityToolbar.setTitle(R.string.app_name);
//        signinactivityToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        ParseInstallation.getCurrentInstallation().saveInBackground();
        onButtonClicked();
    }
   private void onButtonClicked(){
           signinActivitySignUpButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(SignActivity.this,SignUpActivity.class);
                   startActivity(intent);
               }
           });
   }
}
