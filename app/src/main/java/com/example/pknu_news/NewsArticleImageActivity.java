package com.example.pknu_news;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsArticleImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_article_image);

        Intent intent=new Intent(this.getIntent());
        String title=intent.getStringExtra("title");
        String reporter=intent.getStringExtra("reporter");
        String content=intent.getStringExtra("content");
        String image=intent.getStringExtra("image");
        TextView tv_title= findViewById(R.id.title);
        TextView tv_reporter= findViewById(R.id.reporter);
        TextView tv_content= findViewById(R.id.content);
        ImageView iv_image= findViewById(R.id.image);
        tv_title.setText(title);
        tv_reporter.setText(reporter);
        tv_content.setText(content);
        Uri uri = Uri.parse(image);
        Log.e("TTTTTt", String.valueOf(uri));
        Glide.with(this).load(uri).into(iv_image);




        tv_content.setMovementMethod(new ScrollingMovementMethod());



//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


}
