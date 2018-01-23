package com.example.webapp.webapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

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

public class WebBanner extends Activity {

    private WebView #mWebView#;
    private ImageButton #bImageClose#;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_banner);

        bImageClose = findViewById(R.id.b_image_close);
        bImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebBanner.this.finish();
            }
        });

        mWebView = findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebClient());
        mWebView.loadUrl(getInstanceBannerManager().getUrl());

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

    //funct3

    //funct4

    //funct5

    //funct6

    //funct7

    //funct8

    //funct9

    //funct0
}
