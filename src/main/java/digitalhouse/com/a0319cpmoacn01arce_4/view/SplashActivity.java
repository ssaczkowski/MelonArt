package digitalhouse.com.a0319cpmoacn01arce_4.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import digitalhouse.com.a0319cpmoacn01arce_4.R;
import digitalhouse.com.a0319cpmoacn01arce_4.util.ContextUtil;
import digitalhouse.com.a0319cpmoacn01arce_4.view.homePage.MainActivity;
import digitalhouse.com.a0319cpmoacn01arce_4.view.loginPage.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    private static int DELAY = 6000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation);

        ImageView logo = findViewById(R.id.logo);
        logo.startAnimation(animation);

       // TextView textView = findViewById(R.id.splash_activity_textView);

        //textView.startAnimation(animation);

        ContextUtil.startSound(this,R.raw.intro);

        if (AccessToken.getCurrentAccessToken() == null || FirebaseAuth.getInstance().getCurrentUser() == null) {

            if (LoginManager.getInstance()!=null){
             LoginManager.getInstance().logOut();
            }

        new Handler().postDelayed(() -> {
                Intent intentLoginActivity = new Intent(this, LoginActivity.class);
                startActivity(intentLoginActivity);
            ContextUtil.stopSound();
                finish();
        }, DELAY);
        } else {

            DELAY = 3000;

            new Handler().postDelayed(() -> {
            Intent intentMainActivity = new Intent(this, MainActivity.class);
            startActivity(intentMainActivity);
               ContextUtil.stopSound();
            finish();
            }, DELAY);
        }

    }
}
