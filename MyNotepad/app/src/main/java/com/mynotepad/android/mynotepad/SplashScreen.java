package com.mynotepad.android.mynotepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by sachin on 2016-10-04.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

       final ImageView iv= (ImageView)findViewById(R.id.splashImageView);
        final Animation animation= AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotation);

        iv.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    finish();     //to end our splash screen activity
                Intent intent=new Intent(getBaseContext(), StartApp.class);
                startActivity(intent);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
