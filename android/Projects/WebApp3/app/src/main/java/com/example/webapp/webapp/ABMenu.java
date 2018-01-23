package com.example.webapp.webapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.webapp.webapp.BannerManager.getInstanceBannerManager;

//pactf1

//pactf2

//pactf3

//pactf4

//pactf5

//pactf6

//pactf7

//pactf8

//pactf9

//pactf0


public class ABMenu extends Activity {

    private Button bStart;
    private ApiBanner #apiBanner#;
    private ImageView #buttonCancel#;
    private CardView #bannerLayout#;
    private TextView #bannerText#;

    private int #timer#;
    private int #defaultTimer#;
    private int #autoTimer#;
    private int #delayTimer#;

    private boolean #startTimer# = false;
    private Handler #timeHandler# = new Handler();
    private Handler #handlerDelay# = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (getInstanceBannerManager().getVisibleLoadActivity() == 0) {
            if (getResources().getInteger(R.integer.load_activity) != 0) {
                startActivity(new Intent(this, LoadActivity.class));
                getInstanceBannerManager().setVisibleLoadActivity(1);
            }
        }

        bStart = findViewById(R.id.b_start);

        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ABMenu.this, MainActivity.class));
            }
        });

        timer = getResources().getInteger(R.integer.banner_close);
        defaultTimer = getResources().getInteger(R.integer.banner_close);
        autoTimer = getResources().getInteger(R.integer.banner_autoclose);
        delayTimer = getResources().getInteger(R.integer.banner_delay);

        buttonCancel = findViewById(R.id.button_cancel);
        bannerLayout = findViewById(R.id.banner);
        bannerText = findViewById(R.id.banner_text);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiBanner = retrofit.create(ApiBanner.class);
        getBannerApi();

        //initfunc1

        //initfunc2

        //initfunc3

        //initfunc4

        //initfunc5

        //initfunc6

        //initfunc7

        //initfunc8

        //initfunc9

        //initfunc0
    }

    //funct1

    //funct2

    private void #getBannerApi#() {
        Call<Banner> getBanner = apiBanner.getBanner(getString(R.string.api_url));
        getBanner.enqueue(new Callback<Banner>() {
            @Override
            public void onResponse(Call<Banner> call, Response<Banner> response) {
                final Banner banner = response.body();
                getInstanceBannerManager().setBanner(banner);
                getInstanceBannerManager().setUrl(banner.getUrl());
                if (response.isSuccessful() && banner != null && banner.getVisible()) {
                    SimpleTarget target = new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                            bannerLayout.setBackground(drawable);
                            generate();
                        }
                    };
                    Glide
                            .with(ABMenu.this)
                            .load(banner.getImg())
                            .asBitmap()
                            .into(target);
                    bannerLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (getResources().getInteger(R.integer.browser_or_webView) == 0) {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(banner.getUrl()));
                                next();
                                startActivity(i);
                            } else {
                                startActivity(new Intent(ABMenu.this, WebBanner.class));
                                bannerLayout.setVisibility(View.GONE);
                            }
                        }
                    });

                } else next();
            }

            @Override
            public void onFailure(Call<Banner> call, Throwable t) {
                next();
            }
        });
    }

    //funct3

    private void #next#() {
        bannerLayout.setVisibility(View.GONE);
        timer = getResources().getInteger(R.integer.banner_close);
        defaultTimer = getResources().getInteger(R.integer.banner_close);
        autoTimer = getResources().getInteger(R.integer.banner_autoclose);
        delayTimer = getResources().getInteger(R.integer.banner_delay);
        timeHandler.removeCallbacksAndMessages(null);
        handlerDelay.removeCallbacksAndMessages(null);

    }

    //funct4

    private Runnable runnableDelay = new Runnable() {
        @Override
        public void run() {
            bannerLayout.setVisibility(View.VISIBLE);
            if (timer != 0) {
                bannerText.setVisibility(View.INVISIBLE);
                bannerText.setText(String.valueOf(timer));
                startTimer = true;
                timeHandler.postDelayed(runnable, 1000);
            } else {
                buttonCancel.setVisibility(View.VISIBLE);
            }
        }

    };

    //funct5

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d("DEV", autoTimer + timer + "");
            timer--;
            if (timer > 0) {
                bannerText.setText(String.valueOf(timer));
                timeHandler.postDelayed(this, 1000);
            }
            if (timer == 0) {
                buttonCancel.setVisibility(View.VISIBLE);
                bannerText.setVisibility(View.GONE);
                timeHandler.postDelayed(this, 1000);
            }
            if (autoTimer != 0 && timer == (defaultTimer - autoTimer)) next();
        }
    };

    //funct6

    private void #generate#() {
        handlerDelay.postDelayed(runnableDelay,delayTimer*1000);
    }

    //funct7

    @Override
    protected void onStart() {
        super.onStart();
        /*if (startTimer)
            timeHandler.postDelayed(runnable, 1000);*/
    }

    //funct8

    @Override
    protected void onStop() {
        super.onStop();
        timeHandler.removeCallbacksAndMessages(null);
    }

    //funct9

    //funct0
}
