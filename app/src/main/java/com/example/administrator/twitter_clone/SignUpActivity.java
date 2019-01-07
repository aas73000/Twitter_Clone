package com.example.administrator.twitter_clone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
//    private Toolbar signupactivityToolbar;
    private Button lognButton,signupButton;
    private ExtendedEditText signupEmail,signupUserName,signupPassword;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
       /* signupactivityToolbar = findViewById(R.id.signupactivityToolBar);
        setSupportActionBar(signupactivityToolbar);
        signupactivityToolbar.setTitle(R.string.app_name);
        signupactivityToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));*/
       initializeAllViews();
       lognButton.setOnClickListener(SignUpActivity.this);
       signupButton.setOnClickListener(SignUpActivity.this);
    }

    private void initializeAllViews(){
        lognButton = findViewById(R.id.signupactivityLogInButtton);
        signupButton = findViewById(R.id.signupactivitySignUpButton);
        signupEmail = findViewById(R.id.signupactivityEmail);
        signupUserName = findViewById(R.id.signupactivityUsername);
        signupPassword = findViewById(R.id.signupactivityPassword);
        progressBar = findViewById(R.id.signupactivityProgressBar);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signupactivityLogInButtton:
                Intent intent = new Intent(SignUpActivity.this,SignActivity.class);
                startActivity(intent);
                break;
            case R.id.signupactivitySignUpButton:
                progressBar.setAlpha(1);
                final ParseUser parseUser = new ParseUser();
                if(signupEmail.getText().equals("") || signupUserName.getText().equals("")
                        ||signupPassword.getText().equals("")){
                    FancyToast.makeText(SignUpActivity.this,"Fill all the entries",
                            FancyToast.LENGTH_LONG,FancyToast.INFO,true).show();
                    return;
                }
                parseUser.setEmail(signupEmail.getText().toString());
                parseUser.setUsername(signupUserName.getText().toString());
                parseUser.setPassword(signupPassword.getText().toString());
                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            FancyToast.makeText(SignUpActivity.this,"Successfully sign up with "+parseUser.getUsername(),
                                    FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                        }else{
                            FancyToast.makeText(SignUpActivity.this,e.getMessage(),
                                    FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();;
                        }
                        progressBar.setAlpha(0);
                    }
                });
                Intent twitterIntent = new Intent(SignUpActivity.this,TwitterUsers.class);
                startActivity(twitterIntent);
                break;
        }
    }
}
