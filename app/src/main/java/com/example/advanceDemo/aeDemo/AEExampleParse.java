package com.example.advanceDemo.aeDemo;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class AEExampleParse {

    private static final String TAG = "ExampleParse";
    private static String[]  exampleDirNames={"aobama","liudehua","linzhiling","zaoan",
            "haoyixie2018","kongzhisixiang","xiaohongsan",""};


    private static final String EXAMPLE_DIR_PATH="/sdcard/LanSongAEExample/";


    public static ArrayList<AEExample> parse(){

         ArrayList<AEExample> aeExampleList=new ArrayList<>();

        for(String dirName: exampleDirNames){
            String str= EXAMPLE_DIR_PATH+dirName;


            File files=new File(str);
            if(files.exists() && files.isDirectory() && files.listFiles().length>0){

                File[] files1=files.listFiles();

                for (File item : files1){
                    AEExample example=new AEExample();

                    example.dirName=dirName;

                    String path=item.getAbsolutePath();
                    if(path.contains("cover")){

                        example.coverPath=path;
                    }

                    if(path.contains("preview")){
                        example.previewVideoPath=path;
                    }

                    if(path.contains("bg.mp4")){
                        example.videoBgPath=path;
                    }

                    if(path.contains("mvColor")){
                        example.mvColorPath=path;
                    }

                    if(path.contains("mvMask")){
                        example.mvMaskPath=path;
                    }

                    if(path.contains(".json")){
                        example.jsonPath=path;
                    }

                    if(path.contains("images")){
                        example.imagesDirPath=path;
                    }
                    aeExampleList.add(example);
                }

            }else{
                Log.e(TAG, dirName+ "不是文件夹..ERROR..");
            }
        }
        return aeExampleList;
    }



}
