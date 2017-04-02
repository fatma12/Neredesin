package com.ucinbir.xplugin.neredesin;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by xplugin on 31.03.2017.
 */
public class GirisActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris);
        anaEkranaGec();

    }
    private void anaEkranaGec(){
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.animasyon);
        ImageView girisLogo=(ImageView) findViewById(R.id.girisLogoImageView);
        anim.reset();
        girisLogo.clearAnimation();
        girisLogo.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(GirisActivity.this, MainActivity.class);
                startActivity(intent);
                GirisActivity.this.finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}

