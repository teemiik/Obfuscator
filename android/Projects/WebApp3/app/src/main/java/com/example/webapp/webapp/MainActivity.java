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
    private FinestWebView.Builder #fwvBuilder#;
    private Timer #wBVTimer#;

    private int #wBVSec# = 0;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebClient());
        mWebView.loadUrl(getString(R.string.url_banner));

        fwvBuilder = new FinestWebView.Builder(this).theme(R.style.FinestWebViewTheme)
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
                .showIconMenu(true)
                .menuSelector(R.drawable.selector_light_theme)
                .menuTextGravity(Gravity.CENTER)
                .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                .dividerHeight(0)
                .gradientDivider(false)
                .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down);

        getwBVTimer();

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
        public boolean shouldOverrideUrlLoading (WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }

    //funct2

    private void getwBVTimer() {

        wBVTimer = new Timer();
        wBVTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (wBVSec != getResources().getInteger(R.integer.banner_close)) {
                    wBVSec++;
                } else {
                    startActivity(new Intent(MainActivity.this, WebSite.class));
                    wBVTimer.cancel();
                }
            }
        }, 0, 1000);

    }

    //funct3

    //funct4

    //funct5

    //funct6

    //funct7

    //funct8

    //funct9

    //funct0
}
