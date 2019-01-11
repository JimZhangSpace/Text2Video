package com.example.advanceDemo.aeDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.lansoeditor.advanceDemo.R;

public class AEListActivity extends Activity implements OnClickListener {

    private static final String TAG = "AEListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ae_activity_main);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.id_aemain_aobama:
                    showActivity(AEInputActivity.class, AEInputActivity.AE_DEMO_AOBAMA);
                    break;
                case R.id.id_aemain_xiaohuangya:
                    showActivity(AEInputActivity.class, AEInputActivity.AE_DEMO_XIAOHUANGYA);
                    break;
                case R.id.id_aemain_zhemehaokan:
                    showActivity(AEInputActivity.class, AEInputActivity.AE_DEMO_HAOKAN);
                    break;
                case R.id.id_aemain_xianzi:
                    showActivity(AEInputActivity.class, AEInputActivity.AE_DEMO_XIANZI);
                    break;
                case R.id.id_aemain_kuaishan:
                    showActivity(AEInputActivity.class, AEInputActivity.AE_DEMO_KUAISHAN);
                    break;
                case R.id.id_aemain_videobtimap:
                    showActivity(AEInputActivity.class, AEInputActivity.AE_DEMO_VIDEOBITMAP);
                    break;
                default:
                    break;
            }
    }
    // -----------------------------
    private void showActivity(Class<?> cls,int type){
        Intent intent = new Intent(AEListActivity.this, cls);
        intent.putExtra("AEType", type);
        startActivity(intent);
    }
    private void initView() {
        findViewById(R.id.id_aemain_aobama).setOnClickListener(this);
        findViewById(R.id.id_aemain_xiaohuangya).setOnClickListener(this);
        findViewById(R.id.id_aemain_zhemehaokan).setOnClickListener(this);

        findViewById(R.id.id_aemain_xianzi).setOnClickListener(this);
        findViewById(R.id.id_aemain_kuaishan).setOnClickListener(this);
        findViewById(R.id.id_aemain_videobtimap).setOnClickListener(this);
    }
}
