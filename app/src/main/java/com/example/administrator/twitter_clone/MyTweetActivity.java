package com.example.administrator.twitter_clone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class MyTweetActivity extends AppCompatActivity implements View.OnClickListener {
    private ExtendedEditText tweetText;
    private Button sendingTweetButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tweet);
        tweetText = findViewById(R.id.mytweetTweet);
        sendingTweetButton = findViewById(R.id.mytweetButton);
        sendingTweetButton.setOnClickListener(MyTweetActivity.this);
    }
    private void sendingTweetsToServer(){
        ParseObject MyTweet = new ParseObject("MyTweet");
        MyTweet.put("user_name",ParseUser.getCurrentUser().getUsername());
        MyTweet.put("tweet",tweetText.getText().toString());
        MyTweet.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    FancyToast.makeText(MyTweetActivity.this,"Tweeted Successfully",FancyToast.LENGTH_LONG
                            ,FancyToast.SUCCESS,true).show();
                }else {
                    FancyToast.makeText(MyTweetActivity.this,"Error"+e.getMessage(),FancyToast.LENGTH_LONG
                            ,FancyToast.ERROR,true).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        sendingTweetsToServer();
    }
}
