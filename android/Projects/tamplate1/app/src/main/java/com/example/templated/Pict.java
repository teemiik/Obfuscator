package com.example.templated;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.ybq.parallaxviewpager.ParallaxViewPager;

import java.io.IOException;
import java.util.ArrayList;

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

import static com.example.templated.BannerManager.getInstanceBannerManager;

public class Pict extends AppCompatActivity {

    private ParallaxViewPager #parallaxViewPager#;

    private ImageView #buttonCancel#;
    private CardView #bannerLayout#;
    private TextView #bannerText#;

    private ArrayList<Integer> #arrayPict#;
    int #places# = 0;
    private  Banner #oldBanner#;

    private int #timer#;
    private int #defaultTimer#;
    private int #autoTimer#;
    private int #delayTimer#;

    private boolean #startTimer# = false;
    private Handler #timeHandler# = new Handler();
    private Handler #handlerDelay# = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pict);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        parallaxViewPager = findViewById(R.id.viewpager);
        buttonCancel = findViewById(R.id.button_cancel);
        bannerLayout = findViewById(R.id.banner);
        bannerText = findViewById(R.id.banner_text);

        oldBanner = getInstanceBannerManager().getBanner();

        arrayPict = new ArrayList<>();

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

        if (savedInstanceState != null) places = savedInstanceState.getInt("pos");
        else if (getIntent() != null) {
            places = getIntent().getExtras().getInt("pos");
        }

        setTitle((places + 1) + " слайд");

        loadImages();
        initViewPager();

        parallaxViewPager.setCurrentItem(places);
        parallaxViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                places = position;
                setTitle((places + 1) + " слайд");
                invalidateOptionsMenu();
                if (oldBanner != null && (bannerLayout.getVisibility()==View.INVISIBLE||bannerLayout.getVisibility()==View.GONE)) {
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
                            .with(Pict.this)
                            .load(oldBanner.getImg())
                            .asBitmap()
                            .into(target);
                    bannerLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(oldBanner.getUrl()));
                            next();
                            startActivity(i);
                        }
                    });
                } else {
                    handlerDelay.removeCallbacksAndMessages(null);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

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

    private void #bannerShow#() {
        timer = getResources().getInteger(R.integer.banner_close);
        defaultTimer = getResources().getInteger(R.integer.banner_close);
        autoTimer = getResources().getInteger(R.integer.banner_autoclose);
        delayTimer = getResources().getInteger(R.integer.banner_delay);
        handlerDelay.postDelayed(runnableDelay, delayTimer * 1000);
    }

    //funct2

    private void #next#() {
        bannerLayout.setVisibility(View.GONE);
        timer = getResources().getInteger(R.integer.banner_close);
        defaultTimer = getResources().getInteger(R.integer.banner_close);
        autoTimer = getResources().getInteger(R.integer.banner_autoclose);
        delayTimer = getResources().getInteger(R.integer.banner_delay);
        timeHandler.removeCallbacksAndMessages(null);
        handlerDelay.removeCallbacksAndMessages(null);

    }

    //funct3

    private Runnable runnableDelay = new Runnable() {
        @Override
        public void run() {
                bannerLayout.setVisibility(View.VISIBLE);
                if (timer > 0) {
                    bannerText.setVisibility(View.VISIBLE);
                    bannerText.setText(String.valueOf(timer));
                    startTimer = true;
                    timeHandler.postDelayed(runnable, 1000);
                } else {
                    buttonCancel.setVisibility(View.VISIBLE);
                }
            //}
        }

    };

    //funct4

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

    //funct5

    @Override
    protected void onStart() {
        super.onStart();
        if (startTimer)
            timeHandler.postDelayed(runnable, 1000);
    }

    //funct6

    @Override
    protected void onStop() {
        super.onStop();
        timeHandler.removeCallbacksAndMessages(null);
    }

    //funct7

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("places", places);
    }

    //funct8

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.tab_images:
                WallpaperManager wm = WallpaperManager.getInstance(this);
                try {
                    wm.setResource(arrayPict.get(places));
                } catch (IOException e) {
                    Toast.makeText(this, "Фон не установился!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //funct9

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //funct0

    private void #loadImages#() {
        arrayPict.add(R.drawable.img1);
        arrayPict.add(R.drawable.img2);
        arrayPict.add(R.drawable.img3);
        arrayPict.add(R.drawable.img4);
        arrayPict.add(R.drawable.img5);
        arrayPict.add(R.drawable.img6);
        arrayPict.add(R.drawable.img7);
        arrayPict.add(R.drawable.img8);
        arrayPict.add(R.drawable.img9);
        arrayPict.add(R.drawable.img10);
        arrayPict.add(R.drawable.img11);
        arrayPict.add(R.drawable.img12);
        arrayPict.add(R.drawable.img13);
        arrayPict.add(R.drawable.img14);
        arrayPict.add(R.drawable.img15);
        arrayPict.add(R.drawable.img16);
        arrayPict.add(R.drawable.img17);
        arrayPict.add(R.drawable.img18);
        arrayPict.add(R.drawable.img19);
        arrayPict.add(R.drawable.img20);
        arrayPict.add(R.drawable.img21);
        arrayPict.add(R.drawable.img22);
        arrayPict.add(R.drawable.img23);
        arrayPict.add(R.drawable.img24);
        arrayPict.add(R.drawable.img25);
    }

    private void #initViewPager#() {
        PagerAdapter adapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object obj) {
                container.removeView((View) obj);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View view = View.inflate(container.getContext(), R.layout.item_pager, null);
                final ImageView imageView = view.findViewById(R.id.item_img);
                final ImageView arrowLeft = view.findViewById(R.id.arrow_left);
                final ImageView arrowRight = view.findViewById(R.id.arrow_right);

               switch (position) {
                    case 0:
                        arrowLeft.setVisibility(View.INVISIBLE);
                        arrowRight.setVisibility(View.VISIBLE);
                        break;
                    case 9:
                        arrowLeft.setVisibility(View.VISIBLE);
                        arrowRight.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        arrowLeft.setVisibility(View.VISIBLE);
                        arrowRight.setVisibility(View.VISIBLE);
                        break;
                }
                SimpleTarget target = new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        imageView.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                    }
                };
                Glide
                        .with(Pict.this)
                        .load(arrayPict.get(position))
                        .asBitmap()
                        .into(target);
                container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                view.setTag(position);
                return view;
            }

            @Override
            public int getCount() {
                return arrayPict.size();
            }
        };
        parallaxViewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
