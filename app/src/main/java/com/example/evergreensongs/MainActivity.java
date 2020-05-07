package com.example.evergreensongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    ImageView logo_icon;
    RelativeLayout layout;
    Animation top_anim,fade_anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        top_anim=AnimationUtils.loadAnimation(this,R.anim.top_anim);
        fade_anim=AnimationUtils.loadAnimation(this,R.anim.fade_in);

        logo_icon=(ImageView) findViewById(R.id.logo_image);
        layout=(RelativeLayout) findViewById(R.id.logo_text);


        logo_icon.setAnimation(fade_anim);
        layout.setAnimation(top_anim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        },2000);
    }

    @Override
    public void onBackPressed() {

        return;
    }
}
