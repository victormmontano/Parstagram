package com.example.parstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    RecyclerView rvPosts;
    List<Post> postList;
    PostsAdapter postsAdapter;
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvPosts = findViewById(R.id.rvPosts);
        postList = new ArrayList<>();
        postsAdapter = new PostsAdapter(this, postList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(layoutManager);
        rvPosts.setAdapter(postsAdapter);

        bnv = findViewById(R.id.bnv);

        bnv.setSelectedItemId(R.id.home);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.post:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
            }
        });

        queryPosts();

    }

    private void queryPosts() {
        postList.clear();
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "failure", e);
                    return;
                }
                Log.i(TAG, ParseUser.getCurrentUser().getUsername());

              for(Post post: posts) {
                    if (post.getUser().getUsername().equals(ParseUser.getCurrentUser().getUsername())) {
                       // Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                        postList.add(post);
                        postsAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }


}