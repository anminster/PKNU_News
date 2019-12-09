package com.example.pknu_news;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubNewsActivity extends Activity {

    private RecyclerView.Adapter adapter;
    private ArrayList<NewsData> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subnews);

        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        TextView tv_number = findViewById(R.id.text_number);
        ImageView image = findViewById(R.id.image_title);
        TextView tv_title = findViewById(R.id.text_title);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // use a linear layout manager
        arrayList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //초기 데이터 세팅
        DatabaseReference databaseReference = database.getReference("subnews");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    NewsData newsData = snapshot.getValue(NewsData.class);
                    arrayList.add(newsData);
                    //Log.d("aaaaa","dddddd");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("SubNewsActvity", String.valueOf(databaseError.toException()));
            }
        });
        adapter = new CustomAdapter(arrayList,this);
        recyclerView.setAdapter(adapter);
    }


}
