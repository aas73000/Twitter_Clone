package com.example.administrator.twitter_clone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class SignActivity extends AppCompatActivity implements View.OnClickListener {
//    private Toolbar signinactivityToolbar;
    private Button loginButton,signupButton;
    private ExtendedEditText signinUsername,signinPassword;
    private ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initailizeAllViews();
//        signinactivityToolbar = findViewById(R.id.signinactivityToolbar);
//        setSupportActionBar(signinactivityToolbar);
//        signinactivityToolbar.setTitle(R.string.app_name);
//        signinactivityToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        ParseInstallation.getCurrentInstallation().saveInBackground();
        loginButton.setOnClickListener(SignActivity.this);
        signupButton.setOnClickListener(SignActivity.this);
    }

   private void initailizeAllViews(){
       loginButton = findViewById( R.id.signinactivityLoginButton);
       signinUsername = findViewById(R.id.signinactivityUserName);
       signinPassword = findViewById(R.id.signinactivityPassword);
       signupButton = findViewById(R.id.signinactivitySignUpButton);
       progressBar = findViewById(R.id.signinactivityProgressBar);
   }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signinactivityLoginButton:
                progressBar.setAlpha(1);
                ParseUser.logInInBackground(signinUsername.getText().toString(), signinPassword.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                               if(user != null && e== null){
                                   FancyToast.makeText(SignActivity.this,user.getUsername()+" Succesfully log in",
                                           FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                               }else{
                                   FancyToast.makeText(SignActivity.this,signinUsername.getText()+" not registered:"+e.getMessage(),
                                           FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                                   Log.i("error","login");
                               }
                                progressBar.setAlpha(0);
                            }
                        });
                Intent intentTwitter = new Intent(SignActivity.this,TwitterUsers.class);
                startActivity(intentTwitter);
                break;
            case R.id.signinactivitySignUpButton:
                Intent intent = new Intent(SignActivity.this,SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
