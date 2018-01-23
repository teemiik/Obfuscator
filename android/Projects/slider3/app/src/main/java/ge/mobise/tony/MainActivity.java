package ge.mobise.tony;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.apache.http.params.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Artem Koshkin
 * Telegram: @artemkoshkin
 */

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    Api api;
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
        setContentView(R.layout.activity_main);
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
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
        getBanner();
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            default:
                intent = new Intent(this, PicsActivity.class);
                intent.putExtra("pos", 0);
                break;
            case R.id.btn2:
                intent = new Intent(this, RefActivity.class);
                break;
            case R.id.btn3:
                intent = new Intent(this, AboutActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    private void getBanner() {
        Call<Banner> getBanner = api.getBanner(getString(R.string.api_url));
        getBanner.enqueue(new Callback<Banner>() {
            @Override
            public void onResponse(Call<Banner> call, Response<Banner> response) {
                final Banner banner = response.body();
                PicsActivity.banner = banner;
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
                            .with(MainActivity.this)
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

                } else next();
            }

            @Override
            public void onFailure(Call<Banner> call, Throwable t) {
                next();
            }
        });
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
            bannerLayout.setVisibility(View.VISIBLE);
            if (timer != 0) {
                bannerText.setVisibility(View.INVISIBLE);
                bannerText.setText(String.valueOf(timer));
                startTimer = true;
                handler.postDelayed(runnable, 1000);
            } else {
                buttonCancel.setVisibility(View.VISIBLE);
            }
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
        handlerDelay.postDelayed(runnableDelay,delay*1000);

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
}
