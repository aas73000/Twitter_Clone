package com.example.administrator.twitter_clone;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class TwitterUsers extends AppCompatActivity {

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
            parseUserParseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
            parseUserParseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                        if (objects != null && objects.size() > 0 && e == null) {
                            for (ParseUser twitterUser : objects) {
                                arrayList.add(twitterUser.get("username"));
                            }

                            listView.setAdapter(arrayAdapter);
                            progressBar.setAlpha(0);
                        }

                }
            });
        } catch (Exception e) {
            FancyToast.makeText(this, "error getting data", FancyToast.LENGTH_LONG
                    , FancyToast.ERROR, true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ParseUser.logOut();
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
