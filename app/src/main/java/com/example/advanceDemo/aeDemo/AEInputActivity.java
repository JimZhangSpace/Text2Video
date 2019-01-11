package com.example.advanceDemo.aeDemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lansosdk.LanSongAe.LSOAeDrawable;
import com.lansosdk.LanSongAe.LSOLoadAeJsons;
import com.lansosdk.LanSongAe.OnLSOAeJsonLoadedListener;
import com.lansosdk.LanSongAe.LSOTextDelegate;
import com.lansosdk.LanSongAe.LSOAeText;
import com.example.advanceDemo.VideoPlayerActivity;
import com.example.advanceDemo.utils.DemoUtil;
import com.example.advanceDemo.utils.LSOProgressDialog;
import com.lansoeditor.advanceDemo.R;
import com.lansosdk.box.BitmapLayer;
import com.lansosdk.box.DrawPad;
import com.lansosdk.box.onDrawPadCompletedListener;
import com.lansosdk.box.onDrawPadErrorListener;
import com.lansosdk.box.onDrawPadProgressListener;
import com.lansosdk.videoeditor.AudioPadExecute;
import com.lansosdk.videoeditor.DrawPadAEExecute;
import com.lansosdk.videoeditor.LanSongFileUtil;
import com.lansosdk.videoeditor.MediaInfo;

import java.util.List;
import java.util.Map;

import static com.lansosdk.videoeditor.CopyFileFromAssets.copyAssets;

public class AEInputActivity extends Activity {
    EditText et1;
    EditText et2;
    EditText et3;
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


    DrawPadAEExecute execute;
    int inputType = 0;
    LSOProgressDialog progressDialog;
    LinearLayout layout3Line;
    LinearLayout imageLayout;
    ImageView imageView;
    Button btnSelectVideo;
    TextView tvWordHint;

    private final static int SELECT_FILE_REQUEST_CODE = 200;


    public static final int AE_DEMO_AOBAMA = 101;

    public static final int AE_DEMO_XIAOHUANGYA = 102;

    public static final int AE_DEMO_HAOKAN = 103;

    public static final int AE_DEMO_XIANZI = 104;

    public static final int AE_DEMO_KUAISHAN = 106;

    public static final int AE_DEMO_VIDEOBITMAP = 107;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ae_input_three_line_layout);
        et1 = (EditText) findViewById(R.id.id_aeinput_et1);
        et2 = (EditText) findViewById(R.id.id_aeinput_et2);
        et3 = (EditText) findViewById(R.id.id_aeinput_et3);

        layout3Line = (LinearLayout) findViewById(R.id.id_aeinput_textlayout);
        imageLayout = (LinearLayout) findViewById(R.id.id_aeinput_imagelayout);

        imageView = (ImageView) findViewById(R.id.id_aeinput_imageview);
        btnSelectVideo = (Button) findViewById(R.id.id_aeinput_btnselect);
        tvWordHint = (TextView) findViewById(R.id.id_aeinput_tvWordHint);


        inputType = getIntent().getIntExtra("AEType", AE_DEMO_AOBAMA);
        Log.i(TAG, "inputType" + inputType);

        if (inputType == AE_DEMO_XIANZI) {

            showImage("zixiaxianzi_img0.png");

        } else if (inputType == AE_DEMO_KUAISHAN) {  //快闪
            tvWordHint.setVisibility(View.VISIBLE);
            btnSelectVideo.setVisibility(View.GONE);
            layout3Line.setVisibility(View.GONE);

        } else if (inputType == AE_DEMO_VIDEOBITMAP) {  //替换视频
            layout3Line.setVisibility(View.GONE);
            imageLayout.setVisibility(View.GONE);

            btnSelectVideo.setVisibility(View.VISIBLE);
        } else if (inputType == AE_DEMO_HAOKAN) {

            showImage("haokan_img_0.png");

        } else if (inputType == AE_DEMO_XIAOHUANGYA) {

            showImage("xiaohuangya_img_0.png");

        } else {

            imageLayout.setVisibility(View.GONE);
            btnSelectVideo.setVisibility(View.GONE);
            tvWordHint.setVisibility(View.GONE);

            layout3Line.setVisibility(View.VISIBLE);
        }


        findViewById(R.id.id_aeinput_btnselect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startSelectVideoActivity();
                DemoUtil.showToast(getApplication(), "为直观演示,暂时屏蔽,请直接点击开始");
            }
        });

        findViewById(R.id.id_aeinput_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAEDemo();
            }
        });
        testJson();
    }

    private void showImage(String imgName) {
        layout3Line.setVisibility(View.GONE);
        imageLayout.setVisibility(View.GONE);
        btnSelectVideo.setVisibility(View.GONE);
        imageLayout.setVisibility(View.VISIBLE);


        bmpImage0 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), imgName));
        imageView.setImageBitmap(bmpImage0);
    }

//    private void startSelectVideoActivity() {
////        Intent i = new Intent(this, FileExplorerActivity.class);
////        startActivityForResult(i, SELECT_FILE_REQUEST_CODE);
////    }
////
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        switch (resultCode) {
////            case RESULT_OK:
////                if (requestCode == SELECT_FILE_REQUEST_CODE) {
////                    Bundle b = data.getExtras();
////                    videoImage0 = b.getString("SELECT_VIDEO");
////                }
////                break;
////            default:
////                break;
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (execute != null) {
            execute.release();
            execute = null;
        }
//        LanSongFileUtil.deleteFile(drawpadDstPath);
    }

    private void startAEDemo() {
        bmpImage0 = null;
        bmpImage1 = null;
        bmpImage2 = null;
        bmpImage3 = null;
        mvColorPath = null;
        mvMaskPath = null;
        bgVideo = null;


        if (inputType == AE_DEMO_AOBAMA) {
            bgVideo = copyAssets(getApplicationContext(), "aobamaEx.mp4");
            jsonPath = copyAssets(getApplicationContext(), "aobama.json");
            mvColorPath = copyAssets(getApplicationContext(), "ao_color.mp4");
            mvMaskPath = copyAssets(getApplicationContext(), "ao_mask.mp4");

            bmpImage0 = textToBitmap3Line(255, 185);
            startBgvideoJsonMV();

        } else if (inputType == AE_DEMO_HAOKAN) {

            jsonPath = copyAssets(getApplicationContext(), "haokan.json");
            mvColorPath = copyAssets(getApplicationContext(), "haokan_mvColor.mp4");
            mvMaskPath = copyAssets(getApplicationContext(), "haokan_mvMask.mp4");
            bmpImage0 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "haokan_img_0.png"));
            bmpImage1 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "haokan_img_1.png"));
            bmpImage2 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "haokan_img_2.png"));
            bmpImage3 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "haokan_img_3.png"));
            bmpImage4 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "haokan_img_4.png"));
            startJsonMV();

        } else if (inputType == AE_DEMO_XIAOHUANGYA) {


            jsonPath = copyAssets(getApplicationContext(), "xiaohuangya.json");
            mvColorPath = copyAssets(getApplicationContext(), "xiaohuangya_mvColor.mp4");
            mvMaskPath = copyAssets(getApplicationContext(), "xiaohuangya_mvMask.mp4");

            bmpImage0 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "xiaohuangya_img_0.png"));
            bmpImage1 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "xiaohuangya_img_1.png"));
            bmpImage2 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "xiaohuangya_img_2.png"));
            startJsonMV();

        } else if (inputType == AE_DEMO_XIANZI) {
            jsonPath = copyAssets(getApplicationContext(), "zixiaxianzi.json");
            mvColorPath = copyAssets(getApplicationContext(), "zixiaxianzi_mvColor.mp4");
            mvMaskPath = copyAssets(getApplicationContext(), "zixiaxianzi_mvMask.mp4");
            bmpImage0 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "zixiaxianzi_img0.png"));
            bmpImage1 = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), "zixiaxianzi_img1.png"));
            startJsonMV();

        } else if (inputType == AE_DEMO_KUAISHAN) {  //文字快闪;
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

            startJsonMV();
        } else if (inputType == AE_DEMO_VIDEOBITMAP) {  //早安替换视频;
            jsonPath = copyAssets(getApplicationContext(), "zaoan.json");
            mvColorPath = copyAssets(getApplicationContext(), "zaoan_mvColor.mp4");
            mvMaskPath = copyAssets(getApplicationContext(), "zaoan_mvMask.mp4");
            videoImage0 = copyAssets(getApplicationContext(), "zaoan_replace.mp4");

            bmpImage0 = null;

            startJsonMV();

        }
    }

    /**
     * AE模板类型: 先背景视频 ,  中间json层, 最外层是mv;
     */
    private void startBgvideoJsonMV() {

        if (jsonPath != null && bgVideo != null) {  //有json
            LSOLoadAeJsons.loadAsync(getApplicationContext(), new String[]{jsonPath}, new OnLSOAeJsonLoadedListener() {
                @Override
                public void onCompositionsLoaded(@Nullable LSOAeDrawable[] drawables) {
                    if (drawables != null && drawables.length > 0) {

                        drawable1 = drawables[0];
                        progressDialog = new LSOProgressDialog();
                        progressDialog.show(AEInputActivity.this);

                        drawable1.setFontFilePath(jsonUsedFontPath);

                        List<LSOAeText>  list= drawable1.getJsonTexts();
                        if(list!=null && !list.isEmpty()){
                            for (LSOAeText text : list){
                                Log.e("LSTODO", "---text is : "+text.text);
                            }
                        }else{
                            Log.e("LSTODO", "----text is null: ");
                        }


                        //json的图片/文字/视频替换;
                        drawable1.updateBitmap("image_0", bmpImage0);
                        drawable1.updateBitmap("image_1", bmpImage1);
                        drawable1.updateBitmap("image_2", bmpImage2);
                        drawable1.updateBitmap("image_3", bmpImage3);


                        if (videoImage0 != null) {
                            drawable1.updateVideoBitmap("image_0", videoImage0);
                        }


                        if (textDelegate != null) {
                            drawable1.setTextDelegate(textDelegate);
                        }

                        //------------增加图层;
                        dstPath = LanSongFileUtil.createMp4FileInBox();
                        execute = new DrawPadAEExecute(getApplicationContext(), bgVideo, dstPath);

                        execute.addAeLayer(drawable1);//增加ae图层

                        if (mvColorPath != null && mvMaskPath != null) {
                            execute.addMVLayer(mvColorPath, mvMaskPath);//增加mv图层;
                        }
                        startAE();
                    }
                }
            });
        } else {
            DemoUtil.showHintDialog(AEInputActivity.this, "背景视频或jsonPath出错:jsonPath :"+jsonPath +" bgVideo"+ bgVideo);
        }
    }

    /**
     * AE模板类型, 先json层, 后mv层;
     */
    private void startJsonMV() {

        if (jsonPath != null) {  //有json
            LSOLoadAeJsons.loadAsync(getApplicationContext(), new String[]{jsonPath}, new OnLSOAeJsonLoadedListener() {
                @Override
                public void onCompositionsLoaded(@Nullable LSOAeDrawable[] drawables) {
                    if (drawables != null && drawables.length > 0) {
                        drawable1 = drawables[0];


                        setJsonFontPath();

                        List<LSOAeText> texts = drawable1.getJsonTexts();
                        for (LSOAeText text : texts) {
                            Log.e("LSTODO", "LSOAeText: is :" + text.text);
                        }
                        progressDialog = new LSOProgressDialog();
                        progressDialog.show(AEInputActivity.this);


                        drawable1.updateBitmap("image_0", bmpImage0);
                        drawable1.updateBitmap("image_1", bmpImage1);
                        drawable1.updateBitmap("image_2", bmpImage2);
                        drawable1.updateBitmap("image_3", bmpImage3);
                        drawable1.updateBitmap("image_4", bmpImage4);
                        drawable1.updateBitmap("image_5", bmpImage5);

                        if (videoImage0 != null) {
                            drawable1.updateVideoBitmap("image_0", videoImage0);
                        }

                        if (textDelegate != null) {
                            drawable1.setTextDelegate(textDelegate);
                        }

                        //------------增加图层;
                        dstPath = LanSongFileUtil.createMp4FileInBox();
                        execute = new DrawPadAEExecute(getApplicationContext(), dstPath);

                        execute.addAeLayer(drawable1);//增加ae图层

                        if (mvColorPath != null && mvMaskPath != null) {
                            execute.addMVLayer(mvColorPath, mvMaskPath);//增加mv图层;
                        }
                        startAE();
                    }
                }
            });
        } else {
            DemoUtil.showHintDialog(AEInputActivity.this, "没有json文件!");
        }
    }


    //设置字体;
    private void setJsonFontPath() {

        /**
         * 设置字体有两种情况;
         * 1, json中所有的文字都是一种字体; 则可以直接用:
         * drawable1.setFontFilePath(jsonUsedFontPath); //比如/sdcard/lansongBox/STHeiti.ttf
         * 2, json中的不同文字,用不同字体, 则用setFontAssetDelegate
         */

        // 设置字体文件的绝对路径 比如/sdcard/lansongBox/STHeiti.ttf
//        drawable1.setFontFilePath(jsonUsedFontPath);

//        fontPath=copyAssets(getApplicationContext(),"STHeiti.ttf");
//        drawable1.setFontAssetListener( new LSOFontAssetListener(){
//            public String getFontPath(String fontFamily) {
//
//                Log.e("LSTODO", "------getfont path: " + fontFamily);
//
//                return fontPath;
//            }
//        });
    }

    /**
     * 模板中要导出两个 json文件的情况;
     */
    private void startTwoJson() {

        if (jsonPath != null && jsonPath2 != null) {  //有json

            //json的路径顺序,和加载完毕后导出的AeDrawable的顺序一致;
            String[] paths = {jsonPath, jsonPath2};

            LSOLoadAeJsons.loadAsync(getApplicationContext(), paths, new OnLSOAeJsonLoadedListener() {
                @Override
                public void onCompositionsLoaded(@Nullable LSOAeDrawable[] drawables) {

                    for (int i = 0; i < drawables.length; i++) {
                        LSOAeDrawable drawable = drawables[i];

                        if (drawable.jsonPath.equals(jsonPath)) {  //第一个;
                            drawable.updateBitmap("image_0", bmpImage0);
                            drawable1 = drawable;
                        } else if (drawable.jsonPath.equals(jsonPath2)) {  //第二个;

                            Map<String, String> maps = drawable.getImageIdName();
                            for (String key : maps.keySet()) {
                                Bitmap bmp = BitmapFactory.decodeFile(copyAssets(getApplicationContext(), maps.get(key)));
                                drawable.updateBitmap(key, bmp);//-------直接更新.....
                            }
                            drawable2 = drawable;
                        }
                    }
                    //------------增加图层;
                    dstPath = LanSongFileUtil.createMp4FileInBox();
                    execute = new DrawPadAEExecute(getApplicationContext(), dstPath);

                    execute.addAeLayer(drawable2);//增加Ae图层

                    if (mvColorPath != null && mvMaskPath != null) {
                        execute.addMVLayer(mvColorPath, mvMaskPath);//增加mv图层;
                    }
                    execute.addAeLayer(drawable1);//增加Ae图层
                    startAE();
                }
            });
        } else {
            DemoUtil.showHintDialog(AEInputActivity.this, "没有json文件!");
        }
    }

    private void startAE() {
        /**
         * 进度/完成/错误回调, 开始
         */
        execute.setDrawPadProgressListener(new onDrawPadProgressListener() {
            @Override
            public void onProgress(DrawPad v, long currentTimeUs) {
                showProgress(currentTimeUs);
            }
        });

        execute.setDrawPadCompletedListener(new onDrawPadCompletedListener() {
            @Override
            public void onCompleted(DrawPad v) {
                execute.release();
                startPreview();
            }
        });
        execute.setDrawPadErrorListener(new onDrawPadErrorListener() {
            @Override
            public void onError(DrawPad d, int what) {
                hideProgressDialog();
                execute.cancel();
                DemoUtil.showHintDialog(AEInputActivity.this, "AE合成错误,请联系我们!");
            }
        });

        if (execute.start() == false) {
            hideProgressDialog();
            DemoUtil.showHintDialog(AEInputActivity.this, "AE合成错误,请联系我们!");
        }
    }

    BitmapLayer bitmapLayer;
    boolean bitmapFull;

    /**
     * 演示在增加图层后, 对图层调节;
     */
    private void startBitmapMV(Bitmap bmp, final String mvColor, String mvMask) {
        MediaInfo info;
        info = new MediaInfo(mvColor);
        if (info.prepare()) {
            progressDialog = new LSOProgressDialog();
            progressDialog.show(AEInputActivity.this);

            dstPath = LanSongFileUtil.createMp4FileInBox();

            execute = new DrawPadAEExecute(getApplicationContext(), dstPath);
            execute.setEncodeBitrate(2048 * 1024);
            execute.setDrawPadCompletedListener(new onDrawPadCompletedListener() {
                @Override
                public void onCompleted(DrawPad v) {
                    startPreview();
                }
            });
            execute.setDrawPadProgressListener(new onDrawPadProgressListener() {
                @Override
                public void onProgress(DrawPad v, long currentTimeUs) {
                    showProgress(currentTimeUs);
                }
            });
            execute.setDrawPadErrorListener(new onDrawPadErrorListener() {
                @Override
                public void onError(DrawPad d, int what) {
                    hideProgressDialog();
                    DemoUtil.showHintDialog(AEInputActivity.this, "AE合成错误,请联系我们!");
                }
            });

            //增加图片图层
            if (bmp != null) {
                bitmapLayer = execute.addBitmapLayer(bmp);
            }

            //增加mv图层;
            if (mvColor != null && mvMask != null) {
                execute.addMVLayer(mvColor, mvMask);
            }
            /**
             * 开始
             */
            if (execute.start()) {
                if (bitmapLayer != null && bitmapFull) {   //对图片图层进行调节,让其图片满屏;
                    bitmapLayer.setScaledValue(bitmapLayer.getPadWidth(), bitmapLayer.getPadHeight());
                }
            } else {
                hideProgressDialog();
                DemoUtil.showHintDialog(AEInputActivity.this, "AE合成错误,请联系我们!");
            }
        }
    }

    private void showProgress(long currentTimeUs) {
        if (progressDialog != null && execute != null) {
            float time = (float) currentTimeUs / (float) execute.getDuration();
            int b = Math.round(time * 100);
            if (b < 100) {
                progressDialog.setProgress(b);
            }
        }
    }

    private void startPreview() {


        if (addAudioPath != null) {
            addAudio(dstPath);
        } else {
            hideProgressDialog();
            Intent intent = new Intent(AEInputActivity.this, VideoPlayerActivity.class);
            intent.putExtra("videopath", dstPath);
            startActivity(intent);
        }


    }

    private void startPreview2(String path) {
        hideProgressDialog();
        Intent intent = new Intent(AEInputActivity.this, VideoPlayerActivity.class);
        intent.putExtra("videopath", path);
        startActivity(intent);
    }

    private void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.release();
            progressDialog = null;
        }
    }

    private AudioPadExecute audioExecute;

    /**
     * 增加音频;
     *
     * @param srcpath
     */
    private void addAudio(final String srcpath) {
        audioExecute = new AudioPadExecute(getApplication(), srcpath);
        audioExecute.addSubAudio(addAudioPath, true, 1.0f);
        audioExecute.setOnAudioPadCompletedListener(new AudioPadExecute.onAudioPadExecuteCompletedListener() {
            @Override
            public void onCompleted(String path) {

                audioExecute.release();
                audioExecute = null;

                hideProgressDialog();
                Intent intent = new Intent(AEInputActivity.this, VideoPlayerActivity.class);
                intent.putExtra("videopath", path);
                startActivity(intent);
                LanSongFileUtil.deleteFile(srcpath);
            }
        });
        audioExecute.start();
    }

    private Bitmap textToBitmap3Line(int width, int height) {

        String str1 = et1.getText().toString();
        String str2 = et2.getText().toString();
        String str3 = et3.getText().toString();

        int fontSize = 30;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        if (inputType == AE_DEMO_AOBAMA) {
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

    private void resetAeResource() {
        mvMaskPath = null;
        mvColorPath = null;
        jsonPath = null;
        bmpImage0 = null;
        bmpImage1 = null;
        bmpImage2 = null;
        bmpImage3 = null;
        bmpImage4 = null;
        bmpImage5 = null;

        bgVideo = null;

        videoImage0 = null;
        textDelegate = null;
        jsonUsedFontPath = null;
    }

    public void testJson() {
        resetAeResource();

        drawable1.getAeTextLayerInfos();
//        bgVideo = copyAssets(getApplicationContext(), "nobg_mvBg.mp4");
//        jsonPath = copyAssets(getApplicationContext(), "nobg_data.json");
//        mvColorPath = copyAssets(getApplicationContext(), "nobg_mvColor.mp4");
//        mvMaskPath = copyAssets(getApplicationContext(), "nobg_mvMask.mp4");
//
//        startBgvideoJsonMV();

    }
}
