package com.example.advanceDemo.layerDemo;

import android.content.Context;
import android.graphics.PointF;

import com.lansosdk.box.BitmapLoader;
import com.lansosdk.box.LSLog;

import java.util.ArrayList;

import com.lansosdk.LanSongFilter.LanSong3x3ConvolutionFilter;
import com.lansosdk.LanSongFilter.LanSongAddBlendFilter;
import com.lansosdk.LanSongFilter.LanSongAlphaBlendFilter;
import com.lansosdk.LanSongFilter.LanSongBrightnessFilter;
import com.lansosdk.LanSongFilter.LanSongCGAColorspaceFilter;
import com.lansosdk.LanSongFilter.LanSongChromaKeyBlendFilter;
import com.lansosdk.LanSongFilter.LanSongColorBalanceFilter;
import com.lansosdk.LanSongFilter.LanSongColorBlendFilter;
import com.lansosdk.LanSongFilter.LanSongColorBurnBlendFilter;
import com.lansosdk.LanSongFilter.LanSongColorDodgeBlendFilter;
import com.lansosdk.LanSongFilter.LanSongColorInvertFilter;
import com.lansosdk.LanSongFilter.LanSongContrastFilter;
import com.lansosdk.LanSongFilter.LanSongCrosshatchFilter;
import com.lansosdk.LanSongFilter.LanSongDarkenBlendFilter;
import com.lansosdk.LanSongFilter.LanSongDifferenceBlendFilter;
import com.lansosdk.LanSongFilter.LanSongDissolveBlendFilter;
import com.lansosdk.LanSongFilter.LanSongDivideBlendFilter;
import com.lansosdk.LanSongFilter.LanSongEmbossFilter;
import com.lansosdk.LanSongFilter.LanSongExclusionBlendFilter;
import com.lansosdk.LanSongFilter.LanSongExposureFilter;
import com.lansosdk.LanSongFilter.LanSongFalseColorFilter;
import com.lansosdk.LanSongFilter.LanSongFilter;
import com.lansosdk.LanSongFilter.LanSongGammaFilter;
import com.lansosdk.LanSongFilter.LanSongGlassSphereFilter;
import com.lansosdk.LanSongFilter.LanSongGrayscaleFilter;
import com.lansosdk.LanSongFilter.LanSongHalftoneFilter;
import com.lansosdk.LanSongFilter.LanSongHardLightBlendFilter;
import com.lansosdk.LanSongFilter.LanSongHazeFilter;
import com.lansosdk.LanSongFilter.LanSongHighlightShadowFilter;
import com.lansosdk.LanSongFilter.LanSongHueBlendFilter;
import com.lansosdk.LanSongFilter.LanSongHueFilter;
import com.lansosdk.LanSongFilter.LanSongKuwaharaFilter;
import com.lansosdk.LanSongFilter.LanSongLaplacianFilter;
import com.lansosdk.LanSongFilter.LanSongLevelsFilter;
import com.lansosdk.LanSongFilter.LanSongLightenBlendFilter;
import com.lansosdk.LanSongFilter.LanSongLinearBurnBlendFilter;
import com.lansosdk.LanSongFilter.LanSongLookupFilter;
import com.lansosdk.LanSongFilter.LanSongLuminosityBlendFilter;
import com.lansosdk.LanSongFilter.LanSongMonochromeFilter;
import com.lansosdk.LanSongFilter.LanSongMultiplyBlendFilter;
import com.lansosdk.LanSongFilter.LanSongNormalBlendFilter;
import com.lansosdk.LanSongFilter.LanSongOverlayBlendFilter;
import com.lansosdk.LanSongFilter.LanSongPixelationFilter;
import com.lansosdk.LanSongFilter.LanSongPosterizeFilter;
import com.lansosdk.LanSongFilter.LanSongRGBFilter;
import com.lansosdk.LanSongFilter.LanSongSaturationBlendFilter;
import com.lansosdk.LanSongFilter.LanSongSaturationFilter;
import com.lansosdk.LanSongFilter.LanSongScreenBlendFilter;
import com.lansosdk.LanSongFilter.LanSongSepiaFilter;
import com.lansosdk.LanSongFilter.LanSongSoftLightBlendFilter;
import com.lansosdk.LanSongFilter.LanSongSourceOverBlendFilter;
import com.lansosdk.LanSongFilter.LanSongSphereRefractionFilter;
import com.lansosdk.LanSongFilter.LanSongSubtractBlendFilter;
import com.lansosdk.LanSongFilter.LanSongSwirlFilter;
import com.lansosdk.LanSongFilter.LanSongToonFilter;
import com.lansosdk.LanSongFilter.LanSongTwoInputFilter;
import com.lansosdk.LanSongFilter.LanSongVignetteFilter;
import com.lansosdk.LanSongFilter.LanSongWhiteBalanceFilter;
import com.lansosdk.LanSongFilter.LanSongIF1977Filter;
import com.lansosdk.LanSongFilter.LanSongIFAmaroFilter;
import com.lansosdk.LanSongFilter.LanSongIFBrannanFilter;
import com.lansosdk.LanSongFilter.LanSongIFEarlybirdFilter;
import com.lansosdk.LanSongFilter.LanSongIFHefeFilter;
import com.lansosdk.LanSongFilter.LanSongIFHudsonFilter;
import com.lansosdk.LanSongFilter.LanSongIFInkwellFilter;
import com.lansosdk.LanSongFilter.LanSongIFLomofiFilter;
import com.lansosdk.LanSongFilter.LanSongIFLordKelvinFilter;
import com.lansosdk.LanSongFilter.LanSongIFNashvilleFilter;
import com.lansosdk.LanSongFilter.LanSongIFRiseFilter;
import com.lansosdk.LanSongFilter.LanSongIFSierraFilter;
import com.lansosdk.LanSongFilter.LanSongIFSutroFilter;
import com.lansosdk.LanSongFilter.LanSongIFToasterFilter;
import com.lansosdk.LanSongFilter.LanSongIFValenciaFilter;
import com.lansosdk.LanSongFilter.LanSongIFWaldenFilter;
import com.lansosdk.LanSongFilter.LanSongIFXproIIFilter;
import com.lansosdk.LanSongFilter.LanSongBeautyAdvanceFilter;
import com.lansosdk.LanSongFilter.LanSongBlackMaskBlendFilter;
import com.lansosdk.LanSongFilter.LanSongBlurFilter;
import com.lansosdk.LanSongFilter.LanSongBulgeDistortionFilter;
import com.lansosdk.LanSongFilter.LanSongDistortionPinchFilter;
import com.lansosdk.LanSongFilter.LanSongDistortionStretchFilter;
import com.lansosdk.LanSongFilter.LanSongMaskBlendFilter;

public class TestAllFilter {

    private ArrayList<LanSongFilter> filters=new ArrayList<>();


    private static TestAllFilter mInstance=null;

    public static  TestAllFilter getInstance(Context ctx){
        if(mInstance==null){
            mInstance=new TestAllFilter(ctx);
        }
        return mInstance;
    }


    public TestAllFilter(Context ctx){
        addFilter(ctx);
    }


    public  LanSongFilter getFilter(int index){
        if(index>=0 && index<filters.size()){
            LSLog.i("获取 NO."+ index);
            return filters.get(index);
        }else{
            LSLog.e("TestAllFilter  error .return null.index :"+index + "  filters.size:"+filters.size());
            return null;
        }
    }
    public int getFilterSize(){
        return filters.size();
    }
    private void addFilter(Context context)
    {

        //共82个
        filters.add(new LanSongFilter());
        filters.add(new LanSongBeautyAdvanceFilter());
        filters.add(new LanSongContrastFilter(2.0f));
        filters.add(new LanSongGammaFilter(2.0f));
        filters.add(new LanSongColorInvertFilter());
        filters.add(new LanSongPixelationFilter());
        filters.add(new LanSongHueFilter(90.0f));
        filters.add(new LanSongBrightnessFilter(0.5f));
        filters.add(new LanSongGrayscaleFilter());
        filters.add(new LanSongSepiaFilter());
        filters.add(new LanSongPosterizeFilter());
        filters.add(new LanSongSaturationFilter(1.0f));
        filters.add(new LanSongExposureFilter(0.0f));
        filters.add(new LanSongHighlightShadowFilter(0.0f, 1.0f));
        filters.add(new LanSongMonochromeFilter(1.0f, new float[]{0.6f, 0.45f, 0.3f, 1.0f}));
        filters.add(new LanSongRGBFilter(1.0f, 1.0f, 1.0f));
        filters.add(new LanSongWhiteBalanceFilter(5000.0f, 0.0f));
        filters.add(new LanSongBlurFilter());
        filters.add(new LanSongVignetteFilter( new PointF(0.5f,0.5f), new float[]{0.0f,0.0f, 0.0f}, 0.3f, 0.75f));
        filters.add(createBlendFilter(context, LanSongBlackMaskBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongMaskBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongDifferenceBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongSourceOverBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongColorBurnBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongColorDodgeBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongDarkenBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongDissolveBlendFilter.class));
        filters.add(createBlendFilter(context,LanSongExclusionBlendFilter.class));
        filters.add(createBlendFilter(context,LanSongHardLightBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongLightenBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongAddBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongDivideBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongMultiplyBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongOverlayBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongScreenBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongAlphaBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongColorBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongHueBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongSaturationBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongLuminosityBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongLinearBurnBlendFilter.class));
        filters.add(createBlendFilter(context,LanSongSoftLightBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongSubtractBlendFilter.class));
        filters.add(createBlendFilter(context,LanSongChromaKeyBlendFilter.class));
        filters.add(createBlendFilter(context, LanSongNormalBlendFilter.class));
        filters.add(new LanSongCrosshatchFilter());
        filters.add(new LanSongCGAColorspaceFilter());
        filters.add(new LanSongKuwaharaFilter());
        filters.add(new LanSongBulgeDistortionFilter());
        filters.add(new LanSongDistortionPinchFilter());
        filters.add(new LanSongDistortionStretchFilter());
        filters.add(new LanSongGlassSphereFilter());
        filters.add(new LanSongHazeFilter());
        filters.add(new LanSongSphereRefractionFilter());
        filters.add(new LanSongSwirlFilter());
        filters.add(new LanSongFalseColorFilter());
        filters.add(new LanSongColorBalanceFilter());
        filters.add(new LanSongHalftoneFilter());
        filters.add(new LanSongIFAmaroFilter(context));
        filters.add(new LanSongIFRiseFilter(context));
        filters.add(new LanSongIFHudsonFilter(context));
        filters.add(new LanSongIFXproIIFilter(context));
        filters.add(new LanSongIFSierraFilter(context));
        filters.add(new LanSongIFLomofiFilter(context));
        filters.add(new LanSongIFEarlybirdFilter(context));
        filters.add(new LanSongIFSutroFilter(context));
        filters.add(new LanSongIFToasterFilter(context));
        filters.add(new LanSongIFToasterFilter(context));
        filters.add(new LanSongIFBrannanFilter(context));
        filters.add(new LanSongIFInkwellFilter(context));
        filters.add(new LanSongIFWaldenFilter(context));
        filters.add(new LanSongIFHefeFilter(context));
        filters.add(new LanSongIFValenciaFilter(context));
        filters.add(new LanSongIFNashvilleFilter(context));
        filters.add(new LanSongIFLordKelvinFilter(context));
        filters.add(new LanSongIF1977Filter(context));

        filters.add(new LanSongEmbossFilter());
        filters.add(new LanSongLaplacianFilter());
        filters.add(new LanSongToonFilter());




        LanSongLookupFilter amatorka = new LanSongLookupFilter();
        String var3 = "assets://LSResource/lookup_amatorka.png";
        amatorka.setBitmap(BitmapLoader.load(context, var3, 0, 0));
        filters.add(amatorka);

        LanSongLevelsFilter levelsFilter = new LanSongLevelsFilter();
        levelsFilter.setMin(0.0f, 3.0f, 1.0f);
        filters.add(levelsFilter);

        LanSong3x3ConvolutionFilter convolution = new LanSong3x3ConvolutionFilter();
        convolution.setConvolutionKernel(new float[]{-1.0f, 0.0f, 1.0f,
                -2.0f, 0.0f, 2.0f, -1.0f, 0.0f, 1.0f});

        filters.add(convolution);
    }

    private static LanSongFilter createBlendFilter(Context context,
                                                    Class<? extends LanSongTwoInputFilter> filterClass) {
        try {
            LanSongTwoInputFilter filter = filterClass.newInstance();
            String var3 = "assets://LSResource/blend_demo.png"; //这里只是为了方便,用默认图片;
            filter.setBitmap(BitmapLoader.load(context, var3, 0, 0));
            return filter;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
