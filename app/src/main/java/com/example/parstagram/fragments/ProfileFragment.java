package com.example.parstagram.fragments;

import android.util.Log;
import android.widget.Toast;

import com.example.parstagram.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends PostsFragment {
    @Override
    protected void queryPosts() {
        super.queryPosts();
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.include(Post.KEY_CREATEDAT);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATEDAT);

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
                         Log.i("ARRAY", "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                        //postList.add(post);
                        //postsAdapter.notifyDataSetChanged();
                    }
                }

                postList.clear();
                postList.addAll(posts);
                postsAdapter.notifyDataSetChanged();

            }
        });
    }
}
