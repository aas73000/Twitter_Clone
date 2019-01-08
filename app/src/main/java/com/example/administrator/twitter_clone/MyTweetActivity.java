package com.example.administrator.twitter_clone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class MyTweetActivity extends AppCompatActivity implements View.OnClickListener {
    private ExtendedEditText tweetText;
    private Button sendingTweetButton;
    private Button gettingTweetsButton;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tweet);
        tweetText = findViewById(R.id.mytweetTweet);
        sendingTweetButton = findViewById(R.id.mytweetButton);
        gettingTweetsButton = findViewById(R.id.mytweetGetTweetButton);
        listView = findViewById(R.id.mytweetListView);
        sendingTweetButton.setOnClickListener(MyTweetActivity.this);
        gettingTweetsButton.setOnClickListener(MyTweetActivity.this);
    }

    private void sendingTweetsToServer() {
        ParseObject MyTweet = new ParseObject("MyTweet");
        MyTweet.put("user_name", ParseUser.getCurrentUser().getUsername());
        MyTweet.put("tweet", tweetText.getText().toString());
        MyTweet.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    FancyToast.makeText(MyTweetActivity.this, "Tweeted Successfully", FancyToast.LENGTH_LONG
                            , FancyToast.SUCCESS, true).show();
                } else {
                    FancyToast.makeText(MyTweetActivity.this, "Error" + e.getMessage(), FancyToast.LENGTH_LONG
                            , FancyToast.ERROR, true).show();
                }
            }
        });
    }

    private void gettingTweetsFromServer(){
        final ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
        final SimpleAdapter simpleAdapter = new SimpleAdapter(MyTweetActivity.this,arrayList,android.R.layout.simple_list_item_2,
                new String[]{"tweetUserName","tweetValue"},new int []{android.R.id.text1,android.R.id.text2});
        try {
            ParseQuery<ParseObject> userParseQuery = ParseQuery.getQuery("MyTweet");
            userParseQuery.whereContainedIn("user_name",ParseUser.getCurrentUser().getList("fanOf"));
            userParseQuery.findInBackground(new FindCallback<ParseObject>() {

                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(objects != null && objects.size()>0 && e == null){
                        for(ParseObject usersTwitData : objects ){
                            HashMap<String,String> hashUserTwits = new HashMap<>() ;
                            //Log.i("error",usersTwitData.get("user_name")+" "+usersTwitData.get("tweet"));
                            try {
                                hashUserTwits.put("tweetUserName",usersTwitData.getString("user_name"));
                                hashUserTwits.put("tweetValue",usersTwitData.getString("tweet"));
                                arrayList.add(hashUserTwits);
                             //   Log.i("error",arrayList.toString());
                            } catch (Exception e1) {
                                FancyToast.makeText(MyTweetActivity.this, "Error" + e1.getMessage(), FancyToast.LENGTH_LONG
                                        , FancyToast.ERROR, true).show();
                            }
                        }
                    }else{
                        try {
                            FancyToast.makeText(MyTweetActivity.this, "Error" + e.getMessage(), FancyToast.LENGTH_LONG
                                    , FancyToast.ERROR, true).show();
                        } catch (Exception e1) {
                            FancyToast.makeText(MyTweetActivity.this, "Error" + e1.getMessage(), FancyToast.LENGTH_LONG
                                    , FancyToast.ERROR, true).show();
                        }
                    }
                    listView.setAdapter(simpleAdapter);
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(MyTweetActivity.this, "No tweets found " , FancyToast.LENGTH_LONG
                    , FancyToast.ERROR, true).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mytweetButton:
                sendingTweetsToServer();
                break;
            case R.id.mytweetGetTweetButton:
                gettingTweetsFromServer();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyTweetActivity.this,TwitterUsers.class);
        startActivity(intent);
        finish();
    }
}
