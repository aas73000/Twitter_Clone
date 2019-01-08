package com.example.administrator.twitter_clone;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class TwitterUsers extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList arrayList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_users);
        listView = findViewById(R.id.twitteruserListView);
        progressBar = findViewById(R.id.twitteractivityProgressBar);
        arrayList = new ArrayList();
        try {
            ParseQuery<ParseUser> parseUserParseQuery = ParseUser.getQuery();
            if (ParseUser.getCurrentUser() != null) {
                parseUserParseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            }
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, arrayList);
            listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            listView.setOnItemClickListener(this);
            parseUserParseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (objects != null && objects.size() > 0 && e == null) {
                        for (ParseUser twitterUser : objects) {
                            arrayList.add(twitterUser.get("username"));
                        }
                        listView.setAdapter(arrayAdapter);
                        progressBar.setAlpha(0);
                        for (Object tuser : arrayList) {
                            if (ParseUser.getCurrentUser().getList("fanOf") != null && ParseUser.getCurrentUser().
                                    getList("fanOf").contains(tuser)) {
                                listView.setItemChecked(arrayList.indexOf(tuser), true);
                            }
                        }

                    }

                }
            });
        } catch (Exception e) {
            FancyToast.makeText(this, "error getting data:" + e.getMessage(), FancyToast.LENGTH_LONG
                    , FancyToast.ERROR, true).show();
            Log.i("error", e.getMessage());
            progressBar.setAlpha(0);
            ParseUser.logOut();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogOut:
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Intent intent = new Intent(TwitterUsers.this, SignActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
                break;
            case R.id.menuTweet:
                Intent intent = new Intent(this,MyTweetActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckedTextView checkedTextView = (CheckedTextView) view;
        if (checkedTextView.isChecked()) {
            FancyToast.makeText(this, arrayList.get(position) + " now followed!", FancyToast.LENGTH_LONG,
                    FancyToast.INFO, true).show();
            ParseUser.getCurrentUser().add("fanOf", arrayList.get(position));
        } else {
            FancyToast.makeText(this, arrayList.get(position) + " now unfollowed!", FancyToast.LENGTH_LONG,
                    FancyToast.INFO, true).show();
            ParseUser.getCurrentUser().getList("fanOf").remove(arrayList.get(position));
            List currentUserfanOf = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().put("fanOf", currentUserfanOf);
        }
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    FancyToast.makeText(TwitterUsers.this, "saved changes", FancyToast.LENGTH_LONG
                            , FancyToast.INFO, true).show();
                } else {
                    FancyToast.makeText(TwitterUsers.this, "error:" + e.getMessage(), FancyToast.LENGTH_LONG
                            , FancyToast.INFO, true).show();
                }
            }
        });
    }
}
