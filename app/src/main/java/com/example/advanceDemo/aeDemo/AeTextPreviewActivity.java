package com.example.advanceDemo.aeDemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.advanceDemo.VideoPlayerActivity;
import com.example.advanceDemo.utils.DemoUtil;
import com.example.advanceDemo.utils.LSOProgressDialog;
import com.lansoeditor.advanceDemo.R;
import com.lansosdk.LanSongAe.LSOAEImageLayerInfo;
import com.lansosdk.LanSongAe.LSOAeDrawable;
import com.lansosdk.LanSongAe.LSOLoadAeJsons;
import com.lansosdk.LanSongAe.OnLSOAeJsonLoadedListener;
import com.lansosdk.box.DrawPad;
import com.lansosdk.box.LSLog;
import com.lansosdk.box.LSOOneLineText;
import com.lansosdk.box.onDrawPadCompletedListener;
import com.lansosdk.box.onDrawPadErrorListener;
import com.lansosdk.box.onDrawPadProgressListener;
import com.lansosdk.videoeditor.LSOAETextPreview;
import com.lansosdk.videoeditor.LanSongFileUtil;

import java.util.ArrayList;

import static com.lansosdk.videoeditor.CopyFileFromAssets.copyAssets;

public class AeTextPreviewActivity extends Activity {


    LSOAeDrawable drawable1;
    private static final String TAG = "AEInputActivity";
    String jsonPath;
    String addAudioPath;

    LSOAETextPreview aePreview;
    String exportVideoPath;

    LSOProgressDialog progressDialog;
    ArrayList<LSOAEImageLayerInfo> imageLayerInfos;
    ArrayList<LSOOneLineText> oneLineTextArray = new ArrayList<>();

    int usedImageIdCount;  //id有顺序,从image_15是第一个.
    long lastPushEndtimeUs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aetext_preview_layout);
        aePreview = (LSOAETextPreview) findViewById(R.id.id_ae_preview2);
        aePreview.setDrawPadSize(540, 960, null);

        jsonPath=copyAssets(getApplicationContext(),"textONLY3.json");
        addAudioPath=copyAssets(getApplicationContext(),"textVideoMusic.m4a");

        exportVideoPath=LanSongFileUtil.createMp4FileInBox();
        setListeners();

        findViewById(R.id.id_ae_preview_export).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(aePreview!=null){
                    aePreview.export(exportVideoPath);
                }
            }
        });
        progressDialog=new LSOProgressDialog();

        DemoUtil.showHintDialog(this,"这是一个测试demo, 有些细节还没有修改,请注意!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        aePreview.setOnViewAvailable(new LSOAETextPreview.onViewAvailable() {
            @Override
            public void viewAvailable(LSOAETextPreview v) {
                if(drawable1!=null){
                    if(aePreview.isValid()){
                        aePreview.start();
                    }
                }else if(jsonPath!=null){
                    LSOLoadAeJsons.loadAsync(getApplicationContext(), new String[]{jsonPath}, new OnLSOAeJsonLoadedListener() {
                        @Override
                        public void onCompositionsLoaded(@Nullable LSOAeDrawable[] drawables) {
                            drawable1 = drawables[0];
                            imageLayerInfos= drawable1.getImageLayerInfos();
                            usedImageIdCount=imageLayerInfos.size();


                            /**
                             * 这些文字和时间, 都是从语音识别SDK得到的.
                             * */
                            String allText="从一开始的一无所有，到人生的第一个30万真的很不容易，到后来慢慢发展到120万500万800万，甚至1200万，我已经对这些数字麻木了.";
                            pushText(allText,170*1000,12885*1000);

                            String allText2="不管手机像素的高低，为什么就拍不出我这该死又无处安放的魅力呢？";
                            pushText(allText2,13260*1000,19495*1000);


                            for (LSOOneLineText line: oneLineTextArray){
                                drawable1.updateBitmap(line.jsonImageID,line.textImage);
                            }

                            aePreview.addAudioPath(addAudioPath);
                            aePreview.setDrawable(drawable1, oneLineTextArray);//增加ae图层

                            if(aePreview.isValid()){
                                aePreview.start();
                            }
                        }
                    });
                }else{
                    DemoUtil.showHintDialog(AeTextPreviewActivity.this,"没有json文件,加载失败");
                }
            }
        });
    }

    private void setListeners()
    {
        aePreview.setDrawPadCompletedListener(new onDrawPadCompletedListener() {
            @Override
            public void onCompleted(DrawPad v) {
                if(aePreview.isExportMode()){  //导出完成, 开始预览.
                    progressDialog.release();
                    Intent intent = new Intent(AeTextPreviewActivity.this,VideoPlayerActivity.class);
                    intent.putExtra("videopath", exportVideoPath);
                    startActivity(intent);
                }
            }
        });
        aePreview.setDrawPadProgressListener(new onDrawPadProgressListener() {
            @Override
            public void onProgress(DrawPad v, long currentTimeUs) {
                if(aePreview.isExportMode()){
                    int percent=(int)(currentTimeUs*100/ aePreview.getDuration());
                    progressDialog.show(percent,AeTextPreviewActivity.this);
                }
            }
        });
        aePreview.setDrawPadErrorListener(new onDrawPadErrorListener() {
            @Override
            public void onError(DrawPad d, int what) {

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

    //暂时没有测试;
    private void pushText(String text, long startUs, long endUs)
    {
        /**
         时间计算方法是:
         1,根据开始时间和结束时间,得到这些文字的总时间.
         2, json分割句子后,得到N个image,  总时间除以N ,得到每个图片的显示时间.
         3, 暂时不关心语速的问题.因为文字显示有一个特性是: 一个整行显示出来的. 然后语音才慢慢的出来.
         4, 从而第一个image的时间是开始时间+平均时间*1;
         5,  第二个 是开始时间 +平均时间*2;
         */
        if(lastPushEndtimeUs>startUs){
            LSLog.e("ERROR: pushText: current start time < last push  end time.");
            return ;
        }
        if(startUs>=endUs){
            LSLog.e("ERROR: pushText:: start time >=endTime");
            return ;
        }

        lastPushEndtimeUs=endUs;

        int  imageId_startIndex;
        int  imageCount=0;  //总共多个image
        if(usedImageIdCount<0){
            LSLog.e("json id is end.");
            return ;
        }

        imageId_startIndex=(int) oneLineTextArray.size();

        LSOOneLineText oneText=null;
        while(text!=null){
            LSOAEImageLayerInfo oneLine=null;
            usedImageIdCount--;  //Ae模板中的图片id是从高到低, image_15是第一行;

            if(usedImageIdCount<0){
                break;
            }
            String imageKey="image_"+usedImageIdCount;

            for (LSOAEImageLayerInfo imageLayer : imageLayerInfos) {
                if(imageLayer.imgId.equals(imageKey)){
                    oneLine=imageLayer;
                }
            }
            //根据图片分割出字符串.
            if(oneLine!=null){
                oneText=new LSOOneLineText();

                oneText.jsonImageID=oneLine.imgId;
                oneText.jsonImageWidth=oneLine.width;
                oneText.jsonImageHeight=oneLine.height;
                oneText.startFrame=(int)oneLine.startFrame;


                getTextMaxFontSize(oneText.jsonImageHeight);

                text=splitText(text,oneText,oneText.jsonImageWidth);

                oneText.textImage=convertTextToImage(oneText.text,oneText.jsonImageWidth, oneText.jsonImageHeight);

                oneLineTextArray.add(oneText);
                imageCount++;
            }else{
                LSLog.w("json img is end.break;");
                break;
            }
        }
        if(oneText!=null){
            oneText.endTimeUs=endUs;
        }

        //增加时间戳.
        if(imageCount>0){
            long totalTimeUs=endUs - startUs;
            long averageTimeUs=totalTimeUs/imageCount;

            for (int i=0; i<imageCount; i++) {
                LSOOneLineText oneText2= oneLineTextArray.get(i+imageId_startIndex);
                oneText2.startTimeUs=startUs + averageTimeUs*i;
//                Log.e("LSTODO", "oneText2.startTimeUs: "+oneText2.startTimeUs);
            }
            totalTimeUs=endUs;
        }
    }





    final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 获得最大的字号.
     */
    public void getTextMaxFontSize(int imgHeight) {

        String text = "你";
        float fontSize=25;
        int height=1;

        Rect rect = new Rect();
//        fillPaint.setTypeface(typeface);  //设置字体
        while (true){
            fillPaint.setTextSize(fontSize);  //设置大小
            fillPaint.getTextBounds(text, 0, text.length(), rect);
            height = rect.height();
            if(height>imgHeight){
                break;
            }else{
                fontSize++;
            }
        }
        fillPaint.setTextSize(fontSize*0.75f-1);  //LSTODO
    }

    public String   splitText(String  allText,LSOOneLineText oneLineText,int imgWidth)
    {
        int index=1;
        //先分离出宽度
        while(true){
            float charWidth = fillPaint.measureText(allText, 0, index);
            if(charWidth<imgWidth){
                index++;
                if(index>allText.length()){
                    break;
                }
            }else{
                break;
            }
        }
        index--;
        String substring=allText.substring(0,index);

        oneLineText.text=substring;
        String endString=null;
        if(index<allText.length()-1){
            endString=allText.substring(index,allText.length());
        }
        return endString;
    }

    private Bitmap convertTextToImage(String text, int width, int height) {

        int y = 40;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        fillPaint.setColor(Color.WHITE);  //设置字体颜色
        canvas.drawText(text, 0, y, fillPaint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        return bitmap;
    }
}
