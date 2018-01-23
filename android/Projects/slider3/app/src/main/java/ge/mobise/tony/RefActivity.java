package ge.mobise.tony;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Artem Koshkin
 * Telegram: @artemkoshkin
 */

public class RefActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this,PicsActivity.class);
        switch (view.getId()) {
            case R.id.btn1:
                intent.putExtra("pos",0);
                break;
            case R.id.btn2:
                intent.putExtra("pos",1);
                break;
            case R.id.btn3:
                intent.putExtra("pos",2);
                break;
            case R.id.btn4:
                intent.putExtra("pos",3);
                break;
            case R.id.btn5:
                intent.putExtra("pos",4);
                break;
            case R.id.btn6:
                intent.putExtra("pos",5);
                break;
            case R.id.btn7:
                intent.putExtra("pos",6);
                break;
            case R.id.btn8:
                intent.putExtra("pos",7);
                break;
            case R.id.btn9:
                intent.putExtra("pos",8);
                break;
            case R.id.btn10:
                intent.putExtra("pos",9);
                break;
        }
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
