package com.example.webapp.webapp;

import android.annotation.SuppressLint;
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
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.webapp.webapp.BannerManager.getInstanceBannerManager;
import static com.example.webapp.webapp.Singleton.getInstance;

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

public class MainActivity extends Activity {

    private WebView #mWebView#;
    private Singleton #singleton#;
    private Student #student#;

    private Timer bannerTimer;
    private int bannerSec = 0;

    private ImageView #buttonCancel#;
    private CardView #bannerLayout#;
    private TextView #bannerText#;

    private  Banner #oldBanner#;

    private int #timer#;
    private int #defaultTimer#;
    private int #autoTimer#;
    private int #delayTimer#;

    private boolean #startTimer# = false;
    private Handler #timeHandler# = new Handler();
    private Handler #handlerDelay# = new Handler();

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        singleton = getInstance();
        student = new Student("1", "Петров", "452585");


        mWebView = findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebClient());
        mWebView.loadUrl(getString(R.string.url_banner));

        new FinestWebView.Builder(this).theme(R.style.FinestWebViewTheme)
                .titleDefault("Browser")
                .showUrl(true)
                .statusBarColorRes(R.color.bluePrimaryDark)
                .toolbarColorRes(R.color.bluePrimary)
                .titleColorRes(R.color.finestWhite)
                .urlColorRes(R.color.bluePrimaryLight)
                .iconDefaultColorRes(R.color.finestWhite)
                .showProgressBar(true)
                .progressBarColorRes(R.color.finestWhite)
                .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                .showSwipeRefreshLayout(true)
                .swipeRefreshColorRes(R.color.bluePrimaryDark)
                .showIconMenu(false)
                .menuSelector(R.drawable.selector_light_theme)
                .menuTextGravity(Gravity.CENTER)
                .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                .dividerHeight(0)
                .gradientDivider(false)
                .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down);

        buttonCancel = findViewById(R.id.button_cancel);
        bannerLayout = findViewById(R.id.banner);
        bannerText = findViewById(R.id.banner_text);

        timer = getResources().getInteger(R.integer.banner_close);
        defaultTimer = getResources().getInteger(R.integer.banner_close);
        autoTimer = getResources().getInteger(R.integer.banner_autoclose);
        delayTimer = getResources().getInteger(R.integer.banner_delay);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        oldBanner = getInstanceBannerManager().getBanner();

        getBannerTimer();

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

    private class #WebClient# extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }

    private void getBannerTimer() {

        bannerTimer = new Timer();
        bannerTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (bannerSec != getResources().getInteger(R.integer.banner_period_close)) {
                    bannerSec++;
                } else {
                    runOnUiThread(new Runnable(){
                        public void run() {
                            showBanner();
                        }
                    });
                    bannerSec = 0;
                }
            }
        }, 0, 1000);

    }

    private void showBanner() {

        if (oldBanner != null && (bannerLayout.getVisibility()== View.INVISIBLE||bannerLayout.getVisibility()==View.GONE)) {
            Log.d("DEV", "Сейчас покажем баннер!");
            SimpleTarget target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                    Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                    bannerLayout.setBackground(drawable);
                    bannerShow();
                }
            };
            Glide
                    .with(MainActivity.this)
                    .load(oldBanner.getImg())
                    .asBitmap()
                    .into(target);
            bannerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getResources().getInteger(R.integer.browser_or_webView) == 0) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(oldBanner.getUrl()));
                        next();
                        startActivity(i);
                    } else {
                        startActivity(new Intent(MainActivity.this, WebBanner.class));
                        bannerLayout.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            handlerDelay.removeCallbacksAndMessages(null);
        }
    }

    private void bannerShow() {
        timer = getResources().getInteger(R.integer.banner_close);
        defaultTimer = getResources().getInteger(R.integer.banner_close);
        autoTimer = getResources().getInteger(R.integer.banner_autoclose);
        delayTimer = getResources().getInteger(R.integer.banner_delay);
        handlerDelay.postDelayed(runnableDelay, delayTimer * 1000);
    }

    private void next() {
        bannerLayout.setVisibility(View.GONE);
        timer = getResources().getInteger(R.integer.banner_close);
        defaultTimer = getResources().getInteger(R.integer.banner_close);
        autoTimer = getResources().getInteger(R.integer.banner_autoclose);
        delayTimer = getResources().getInteger(R.integer.banner_delay);
        timeHandler.removeCallbacksAndMessages(null);
        handlerDelay.removeCallbacksAndMessages(null);

    }

    private Runnable runnableDelay = new Runnable() {
        @Override
        public void run() {
            bannerLayout.setVisibility(View.VISIBLE);
            if (timer > 0) {
                bannerText.setVisibility(View.INVISIBLE);
                bannerText.setText(String.valueOf(timer));
                startTimer = true;
                timeHandler.postDelayed(runnable, 1000);
            } else {
                buttonCancel.setVisibility(View.VISIBLE);
            }
            //}
        }

    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
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

    //funct2

    @Override
    public void onLocalVoiceInteractionStarted() {
        super.onLocalVoiceInteractionStarted();
    }

    //funct3

    @Override
    protected void onResume() {
        super.onResume();
    }

    //funct4

    @Override
    protected void onStart() {
        super.onStart();
        if (startTimer)
            timeHandler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timeHandler.removeCallbacksAndMessages(null);
    }

    //funct5

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //funct6

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    //funct7

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //funct8

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerTimer.cancel();
    }

    //funct9

    //funct0

}
