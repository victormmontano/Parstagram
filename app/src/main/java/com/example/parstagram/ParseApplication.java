package com.example.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("IkTVx1EL5YCq0m2CqhaWXQqLxLRQJ6X01XB3lvxO")
                .clientKey("aNFsVxAtBPoDgM8mqRk0TAArH93FZvwn25WP1pWy")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
