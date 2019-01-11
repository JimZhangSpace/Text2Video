package com.lansosdk.videoeditor;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;

import com.lansosdk.box.LSLog;
import com.lansosdk.box.onDrawPadSizeChangedListener;


/**
 * 最简单的自动布局View; ALL版本 勿删!!!!!
 */
public class TESTMiniDrawPadView extends FrameLayout {

    static final int AR_ASPECT_FIT_PARENT = 0; // without clip
    private static final String TAG = LSLog.TAG;
    private TextureRenderView mTextureRenderView;
    private SurfaceTexture mSurfaceTexture = null;
    private int drawPadWidth, drawPadHeight;
    private onViewAvailable mViewAvailable = null;

    public TESTMiniDrawPadView(Context context) {
        super(context);
        initVideoView(context);
    }

    public TESTMiniDrawPadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVideoView(context);
    }

    public TESTMiniDrawPadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TESTMiniDrawPadView(Context context, AttributeSet attrs, int defStyleAttr,
                               int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initVideoView(context);
    }

    private void initVideoView(Context context) {
        setTextureView();
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    private void setTextureView() {
        mTextureRenderView = new TextureRenderView(getContext());
        mTextureRenderView.setSurfaceTextureListener(new SurfaceCallback());

        mTextureRenderView.setDispalyRatio(AR_ASPECT_FIT_PARENT);

        View renderUIView = mTextureRenderView.getView();
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        renderUIView.setLayoutParams(lp);
        addView(renderUIView);
        mTextureRenderView.setVideoRotation(0);
    }
    public void setOnViewAvailable(onViewAvailable listener) {
        mViewAvailable = listener;
        if (mSurfaceTexture != null) {
            mViewAvailable.viewAvailable(this);
        }
    }
    public interface onViewAvailable {
        void viewAvailable(TESTMiniDrawPadView v);
    }

    private class SurfaceCallback implements TextureView.SurfaceTextureListener {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface,
                                              int width, int height) {
            mSurfaceTexture = surface;
            drawPadHeight = height;
            drawPadWidth = width;
            Log.e("LSTODO", "onSurfaceTextureAvailable--------:wh "+ drawPadWidth+ drawPadHeight);
            if (mViewAvailable != null) {
                mViewAvailable.viewAvailable(null);
            }
            checkLayoutSize();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface,
                                                int width, int height) {
            mSurfaceTexture = surface;
            drawPadHeight = height;
            drawPadWidth = width;
            Log.e("LSTODO", "onSurfaceTextureSizeChanged--------: ");
            checkLayoutSize();
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            mSurfaceTexture = null;
            Log.e("LSTODO", "onSurfaceTextureDestroyed--------: ");
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    }
    boolean isDrawPadSizeChanged=false;

    private int desireWidth;
    private int desireHeight;

    private onDrawPadSizeChangedListener mSizeChangedCB = null;
    public void setDrawPadSize(int width, int height,
                               onDrawPadSizeChangedListener cb) {

        isDrawPadSizeChanged=true;
        if (width != 0 && height != 0 && cb != null) {

            if(drawPadWidth==0 || drawPadHeight==0){  //直接重新布局UI
                mTextureRenderView.setVideoSize(width, height);
                desireWidth=width;
                desireHeight=height;
                mTextureRenderView.setVideoSampleAspectRatio(1, 1);
                mSizeChangedCB = cb;
                requestLayout();
            }else{
                float setAcpect = (float) width / (float) height;
                float setViewacpect = (float) drawPadWidth / (float) drawPadHeight;

                if (setAcpect == setViewacpect) { // 如果比例已经相等,不需要再调整,则直接显示.
                    if (cb != null) {
                        cb.onSizeChanged(width, height);
                    }
                } else if (Math.abs(setAcpect - setViewacpect) * 1000 < 16.0f) {
                    if (cb != null) {
                        cb.onSizeChanged(width, height);
                    }
                } else if (mTextureRenderView != null) {
                    mTextureRenderView.setVideoSize(width, height);
                    mTextureRenderView.setVideoSampleAspectRatio(1, 1);
                    mSizeChangedCB = cb;
                }
                requestLayout();
            }
        }
    }

    /**
     * 检查得到的大小, 如果和view成比例,则直接回调; 如果不成比例,则重新布局;
     */
    private void checkLayoutSize(){
        if(mSizeChangedCB!=null){
            float setAcpect = (float) desireWidth / (float) desireHeight;
            float setViewacpect = (float) drawPadWidth / (float) drawPadHeight;

            if (setAcpect == setViewacpect) { // 如果比例已经相等,不需要再调整,则直接显示.
                if (mSizeChangedCB != null) {
                    mSizeChangedCB.onSizeChanged(drawPadWidth, drawPadHeight);
                    mSizeChangedCB=null;
                }
            } else if (Math.abs(setAcpect - setViewacpect) * 1000 < 16.0f) {
                if (mSizeChangedCB != null) {
                    mSizeChangedCB.onSizeChanged(drawPadWidth, drawPadHeight);
                    mSizeChangedCB=null;
                }
            }else{
                mTextureRenderView.setVideoSize(desireWidth, desireHeight);
                mTextureRenderView.setVideoSampleAspectRatio(1, 1);
                Log.d(TAG,"再次 layout...");
                requestLayout();
            }
        }else{
            Log.e("LSTODO", "-----------recallJustLayout error: mSizeChangedCB ==null");
        }

    }
}
