package com.example.advanceDemo.aeDemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lansoeditor.advanceDemo.R;

import java.io.File;
import java.util.List;

public class AEExampleAdapter extends RecyclerView.Adapter<AEExampleAdapter.ExampleViewHolder> {
    private static final String TAG = "AEExampleAdapter";
    private List<AEExample> aeExamples;

    public AEExampleAdapter(List<AEExample> data, Context context) {
        aeExamples=data;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_aeexample_item, parent, false);
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {

        AEExample example = aeExamples.get(position);
        if(example.coverPath!=null){
            File file=new File(example.coverPath);

            if(file.exists()){
                Bitmap bmp= BitmapFactory.decodeFile(example.coverPath);
                holder.beautyImage.setImageBitmap(bmp);
                holder.nameTv.setText(example.dirName);
            }else{
                holder.nameTv.setText("文件不存在");
            }
        }


    }

    @Override
    public int getItemCount() {
        return aeExamples.size();
    }

    static class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView beautyImage;
        TextView nameTv;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            beautyImage = itemView.findViewById(R.id.image_item);
            nameTv = itemView.findViewById(R.id.name_item);
        }
    }
}