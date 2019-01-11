package com.example.advanceDemo.aeDemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.advanceDemo.utils.DemoUtil;
import com.example.advanceDemo.utils.LSOProgressDialog;
import com.lansoeditor.advanceDemo.R;
import com.lansosdk.LanSongAe.LSOAeDrawable;
import com.lansosdk.LanSongAe.LSOLoadAeJsons;
import com.lansosdk.LanSongAe.LSOTextDelegate;
import com.lansosdk.LanSongAe.OnLSOAeJsonLoadedListener;
import com.lansosdk.box.AEJsonLayer;
import com.lansosdk.videoeditor.DrawPadAEPreview;

import java.io.IOException;

import static com.lansosdk.videoeditor.CopyFileFromAssets.copyAssets;

public class AEPreviewActivity extends Activity {


    AEJsonLayer aeJsonLayer;
    LSOAeDrawable drawable1;
    LSOAeDrawable drawable2;
    private static final String TAG = "AEInputActivity";
    private String dstPath = null;
    String bgVideo;
    String mvColorPath;
    String mvMaskPath;
    String jsonPath;
    String addAudioPath;

    String jsonPath2;

    Bitmap bmpImage0;
    Bitmap bmpImage1;
    Bitmap bmpImage2;
    Bitmap bmpImage3;
    Bitmap bmpImage4;
    Bitmap bmpImage5;
    Bitmap bmpImage6;
    Bitmap bmpImage7;

    String videoImage0;
    LSOTextDelegate textDelegate;

    String jsonUsedFontPath;

    DrawPadAEPreview aePreview;

    int inputType = 0;
    LSOProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ae_preview_layout);
        initView();

        inputType = getIntent().getIntExtra("AEType", AEInputActivity.AE_DEMO_AOBAMA);

        if (inputType == AEInputActivity.AE_DEMO_AOBAMA) {  //LSTODO临时使用;
            aePreview.setDrawPadSize(672, 378, null);
        } else if(inputType == AEInputActivity.AE_DEMO_KUAISHAN){
            aePreview.setDrawPadSize(1920, 1080, null);
        }else{
            aePreview.setDrawPadSize(540, 960, null);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        aePreview.setOnViewAvailable(new DrawPadAEPreview.onViewAvailable() {
            @Override
            public void viewAvailable(DrawPadAEPreview v) {
                startAEDemo();

                if(jsonPath!=null){
                    LSOLoadAeJsons.loadAsync(getApplicationContext(), new String[]{jsonPath}, new OnLSOAeJsonLoadedListener() {
                        @Override
                        public void onCompositionsLoaded(@Nullable LSOAeDrawable[] drawables) {
                            drawable1 = drawables[0];
                            addLayers();
                            aePreview.start();
                        }
                    });
                }else{
                    DemoUtil.showHintDialog(AEPreviewActivity.this,"没有json文件,加载失败");
                }

            }
        });
    }
    private void startAEDemo() {
        bmpImage0 = null;
        bmpImage1 = null;
        bmpImage2 = null;
        bmpImage3 = null;
        mvColorPath = null;
        mvMaskPath = null;
        bgVideo = null;
        jsonPath=null;

        if (inputType == AEInputActivity.AE_DEMO_AOBAMA) {
            bgVideo = copyAssets(getApplicationContext(), "aobamaEx.mp4");
            jsonPath = copyAssets(getApplicationContext(), "aobama.json");
            mvColorPath = copyAssets(getApplicationContext(), "ao_color.mp4");
            mvMaskPath = copyAssets(getApplicationContext(), "ao_mask.mp4");
            bmpImage0 = textToBitmap3Line(255, 185);
        } else if (inputType == AEInputActivity.AE_DEMO_HAOKAN) {
            jsonPath = copyAssets(getApplicationContext(), "haokan.json");
            mvColorPath = copyAssets(getApplicationContext(), "haokan_mvColor.mp4");
            mvMaskPath = copyAssets(getApplicationContext(), "haokan_mvMask.mp4");
            bmpImage0 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "haokan_img_0.png"));
            bmpImage1 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "haokan_img_1.png"));
            bmpImage2 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "haokan_img_2.png"));
            bmpImage3 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "haokan_img_3.png"));
            bmpImage4 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "haokan_img_4.png"));
        } else if (inputType == AEInputActivity.AE_DEMO_XIAOHUANGYA) {
            jsonPath = copyAssets(getApplicationContext(), "xiaoYa.json");
            mvColorPath = copyAssets(getApplicationContext(), "xiaoYa_mvColor.mp4");
            mvMaskPath = copyAssets(getApplicationContext(), "xiaoYa_mvMask.mp4");

            bmpImage0 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "xiaoYa_img_0.png"));
            bmpImage1 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "xiaoYa_img_1.png"));
            bmpImage2 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "xiaoYa_img_2.png"));

        } else if (inputType == AEInputActivity.AE_DEMO_XIANZI) {
            jsonPath = copyAssets(getApplicationContext(), "zixiaxianzi.json");
            mvColorPath = copyAssets(getApplicationContext(), "zixiaxianzi_mvColor.mp4");
            mvMaskPath = copyAssets(getApplicationContext(), "zixiaxianzi_mvMask.mp4");
            bmpImage0 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "zixiaxianzi_img0.png"));
            bmpImage1 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "zixiaxianzi_img1.png"));

        } else if (inputType == AEInputActivity.AE_DEMO_KUAISHAN) {  //文字快闪;
            jsonPath = copyAssets(getApplicationContext(), "kuaishan1.json");
            jsonUsedFontPath = copyAssets(getApplicationContext(), "STHeiti.ttf");

            bgVideo = null;
            mvColorPath = copyAssets(getApplicationContext(), "kuaishan_mvColor.mp4");
            mvMaskPath = copyAssets(getApplicationContext(), "kuaishan_mvMask.mp4");
            bmpImage0 = null;

            textDelegate = new LSOTextDelegate(drawable1);
            textDelegate.setText("蓝", "可");
            textDelegate.setText("松", "替");
            textDelegate.setText("短", "换");
            textDelegate.setText("视", "文");
            textDelegate.setText("频", "字");
        } else if (inputType == AEInputActivity.AE_DEMO_VIDEOBITMAP) {  //早安替换视频;
            jsonPath = copyAssets(getApplicationContext(), "zaoan.json");
            mvColorPath = copyAssets(getApplicationContext(), "zaoan_mvColor.mp4");
            mvMaskPath = copyAssets(getApplicationContext(), "zaoan_mvMask.mp4");
            videoImage0 = copyAssets(getApplicationContext(), "zaoan_replace.mp4");
            bmpImage0 = null;
        }else{

        }
    }

    private void addLayers() {
        if (bgVideo != null) {
            try {
                aePreview.addVideoLayer(bgVideo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        drawable1.updateBitmap("image_0", bmpImage0);
        drawable1.updateBitmap("image_1", bmpImage1);
        drawable1.updateBitmap("image_2", bmpImage2);
        drawable1.updateBitmap("image_3", bmpImage3);
        drawable1.updateBitmap("image_4", bmpImage4);

        if (videoImage0 != null) {
            drawable1.updateVideoBitmap("image_0", videoImage0);
        }

        if (textDelegate != null) {
            drawable1.setTextDelegate(textDelegate);
        }

        //------------增加图层;
        aeJsonLayer= aePreview.addAeLayer(drawable1);//增加ae图层

        if (mvColorPath != null && mvMaskPath != null) {
            aePreview.addMVLayer(mvColorPath, mvMaskPath);//增加mv图层;
        }
        if(aePreview.isValid()){
            aePreview.start();
        }
    }

    private Bitmap textToBitmap3Line(int width, int height) {

        String str1 = "第一行文字";
        String str2 = "第二行文字";
        String str3 = "第三行文字";

        int fontSize = 30;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        if (inputType == AEInputActivity.AE_DEMO_AOBAMA) {
            canvas.drawColor(Color.RED);
        }
        paint.setColor(Color.BLUE);

//        for(int i=0;i<tp.getHeigt();i++){
        int y = 40;
        int interval = 40;
        canvas.drawText(str1, 0, y, paint);
        y = y + interval;
        canvas.drawText(str2, 0, y, paint);
        y = y + interval;
        canvas.drawText(str3, 0, y, paint);

//            y=y+20;
//        }
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bitmap;
    }
    private void initView() {
        aePreview = (DrawPadAEPreview) findViewById(R.id.id_ae_preview2);
        findViewById(R.id.id_ae_preview_test1).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        aePreview.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
