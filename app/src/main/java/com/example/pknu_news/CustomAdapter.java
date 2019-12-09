package com.example.pknu_news;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<NewsData> arrayList;
    private Context context;

    public CustomAdapter(ArrayList<NewsData> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_title;
        TextView tv_number;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        if(arrayList.get(pos).getProfile() == null){
                            Intent intent = new Intent(context,NewsArticleActivity.class);
                            intent.putExtra("title",String.valueOf(arrayList.get(pos).getTitle()));
                            intent.putExtra("reporter",String.valueOf(arrayList.get(pos).getReporter()));
                            intent.putExtra("content",String.valueOf(arrayList.get(pos).getArticle()));
                            Log.e("HHHHHHH", "AAAAAAAAAAAAAAAA");
                            context.startActivity(intent);
                        }else{
                            Intent intent = new Intent(context,NewsArticleImageActivity.class);
                            intent.putExtra("title",String.valueOf(arrayList.get(pos).getTitle()));
                            intent.putExtra("reporter",String.valueOf(arrayList.get(pos).getReporter()));
                            intent.putExtra("content",String.valueOf(arrayList.get(pos).getArticle()));
                            intent.putExtra("image",String.valueOf(arrayList.get(pos).getProfile()));
                            Log.e("HHHHHHH", String.valueOf(arrayList.get(pos).getProfile()));
                            context.startActivity(intent);
                        }


                        //Log.e("HHHHHHH", String.valueOf(pos));

                        //Log.e("HHHHHHHHHHH", String.valueOf(arrayList.get(pos).getTitle()));
                        //notifyItemChanged(pos) ;
                    }
                }
            });
            this.iv_profile = itemView.findViewById(R.id.image_title);
            this.tv_title = itemView.findViewById(R.id.text_title);
            this.tv_number = itemView.findViewById(R.id.text_number);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                        .into(holder.iv_profile);
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_number.setText(String.valueOf(arrayList.get(position).getNum()));
//        holder.tv_Reporter.setText(arrayList.get(position).getReporter());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null? arrayList.size() : 0);
    }


}
