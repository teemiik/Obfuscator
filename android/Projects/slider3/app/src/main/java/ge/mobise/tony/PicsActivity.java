package ge.mobise.tony;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.ybq.parallaxviewpager.ParallaxViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 *
 */

public class PicsActivity extends AppCompatActivity {
    @BindView(R.id.viewpager)
    ParallaxViewPager viewpager;
    private int[] pic = new int[10];
    int pos = 0;
    boolean play = true;
    //MediaPlayer mPlayer;
    public static Banner banner;

    @BindView(R.id.button_cancel)
    ImageView buttonCancel;
    @BindView(R.id.banner)
    CardView bannerLayout;
    @BindView(R.id.banner_text)
    TextView bannerText;

    private int timer;
    private int defaultTimer;
    private int autotimer;
    private int delay;

    private boolean startTimer = false;
    private Handler handler = new Handler();
    private Handler handlerDelay = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pics);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        ButterKnife.bind(this);
        timer = getResources().getInteger(R.integer.banner_close_max);
        defaultTimer = getResources().getInteger(R.integer.banner_close_max);
        autotimer = getResources().getInteger(R.integer.banner_autoclose_max);
        delay = getResources().getInteger(R.integer.banner_delay);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
        if (savedInstanceState != null) pos = savedInstanceState.getInt("pos");
        else if (getIntent() != null) {
            pos = getIntent().getExtras().getInt("pos");
        }
        setTitle((pos + 1) + " слайд");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initImages();
        initViewPager();
        viewpager.setCurrentItem(pos);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
                setTitle((pos + 1) + " слайд");
                invalidateOptionsMenu();
                if (banner != null && (bannerLayout.getVisibility()==View.INVISIBLE||bannerLayout.getVisibility()==View.GONE)) {
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
                            .with(PicsActivity.this)
                            .load(banner.getImg())
                            .asBitmap()
                            .into(target);
                    bannerLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(banner.getUrl()));
                            next();
                            startActivity(i);
                        }
                    });
                } else {
                    handlerDelay.removeCallbacksAndMessages(null);
                    /*if(mPlayer!=null && mPlayer.isPlaying()) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer=null;
                        play = true;
                    }*/

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void bannerShow() {
        timer = getResources().getInteger(R.integer.banner_close_max);
        defaultTimer = getResources().getInteger(R.integer.banner_close_max);
        autotimer = getResources().getInteger(R.integer.banner_autoclose_max);
        delay = getResources().getInteger(R.integer.banner_delay);
        handlerDelay.postDelayed(runnableDelay, delay * 1000);
    }

    private void next() {
        bannerLayout.setVisibility(View.GONE);
        timer = getResources().getInteger(R.integer.banner_close_max);
        defaultTimer = getResources().getInteger(R.integer.banner_close_max);
        autotimer = getResources().getInteger(R.integer.banner_autoclose_max);
        delay = getResources().getInteger(R.integer.banner_delay);
        handler.removeCallbacksAndMessages(null);
        handlerDelay.removeCallbacksAndMessages(null);

    }

    private Runnable runnableDelay = new Runnable() {
        @Override
        public void run() {
            /*if (viewpager.getCurrentItem() != 2) {
                Log.d("DEV", "Ушли, баннер не покажем!");
                next();
            } else {*/
                Log.d("DEV", "Показываем баннер!");
                bannerLayout.setVisibility(View.VISIBLE);
                if (timer > 0) {
                    bannerText.setVisibility(View.VISIBLE);
                    bannerText.setText(String.valueOf(timer));
                    startTimer = true;
                    handler.postDelayed(runnable, 1000);
                } else {
                    buttonCancel.setVisibility(View.VISIBLE);
                }
            //}
        }

    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d("DEV", autotimer + timer + "");
            timer--;
            if (timer > 0) {
                bannerText.setText(String.valueOf(timer));
                handler.postDelayed(this, 1000);
            }
            if (timer == 0) {
                buttonCancel.setVisibility(View.VISIBLE);
                bannerText.setVisibility(View.GONE);
                handler.postDelayed(this, 1000);
            }
            if (autotimer != 0 && timer == (defaultTimer - autotimer)) next();
        }
    };

    private void generate() {
        handlerDelay.postDelayed(runnableDelay, delay * 1000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (startTimer)
            handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos", pos);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initImages() {
        pic[0] = R.drawable.img1;
        pic[1] = R.drawable.img2;
        pic[2] = R.drawable.img3;
        pic[3] = R.drawable.img4;
        pic[4] = R.drawable.img5;
        pic[5] = R.drawable.img6;
        pic[6] = R.drawable.img7;
        pic[7] = R.drawable.img8;
        pic[8] = R.drawable.img9;
        pic[9] = R.drawable.img10;
    }

    private void initViewPager() {
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
                final ImageView imageView = (ImageView) view.findViewById(R.id.item_img);
                final ImageView arrowLeft = (ImageView) view.findViewById(R.id.arrow_left);
                final ImageView arrowRight = (ImageView) view.findViewById(R.id.arrow_right);

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
                        .with(PicsActivity.this)
                        .load(pic[position])
                        .asBitmap()
                        .into(target);
                container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                view.setTag(position);
                return view;
            }

            @Override
            public int getCount() {
                return pic.length;
            }
        };
        viewpager.setAdapter(adapter);
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
