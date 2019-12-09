package com.example.pknu_news;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {


    private GoogleMap mMap;
    private ArrayList<Double> list;
    private ArrayList<MainNewsData> arrayList;
    private ArrayList<LatLng> pos;
    private ArrayList<Intent> intentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onResume(){
        super.onResume();

        if(mMap != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            //mMap.clear();
            list.clear();
            //LatLng position = new LatLng(0,0);
            // Add a marker in Sydney and move the camera
            for(int i=0, j=0; i<(list.size()/2);i=i+2,j++){
                mMap.addMarker(new MarkerOptions().position(new LatLng(list.get(i), list.get(i+1))).title(String.valueOf(arrayList.get(j).getTitle())));
                Log.e("DDDDDDD", String.valueOf(list.get(i)));
            }

        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(35.133613,129.104275)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        // Add a marker in Sydney and move the camera

        arrayList = new ArrayList<>();
        list = new ArrayList<>();
        pos = new ArrayList<>();
        intentArrayList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //초기 데이터 세팅
        DatabaseReference databaseReference = database.getReference("mainnews");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MainNewsData mainnewsData = snapshot.getValue(MainNewsData.class);
                    arrayList.add(mainnewsData);//Log.d("aaaaa","dddddd");
                    list.add(snapshot.getValue(MainNewsData.class).getXdata());
                    list.add(snapshot.getValue(MainNewsData.class).getYdata());

                }
                if (mMap != null) { //prevent crashing if the map doesn't exist yet (eg. on starting activity)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(35.133613,129.104275)));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
                    for (int i = 0, j = 0; j < (list.size() / 2); i = i + 2, j++) {
                        pos.add(new LatLng(list.get(i),list.get(i+1)));
                        mMap.addMarker(new MarkerOptions().position(pos.get(j)).title(String.valueOf(arrayList.get(j).getTitle())));
                        Log.e("KKKKKKKKKK",String.valueOf(arrayList.get(j).getTitle()));
                        if(arrayList.get(j).getProfile() == null){
                            intentArrayList.add(new Intent(MapsActivity.this,NewsArticleActivity.class));
                            intentArrayList.get(j).putExtra("title",String.valueOf(arrayList.get(j).getTitle()));
                            intentArrayList.get(j).putExtra("reporter",String.valueOf(arrayList.get(j).getReporter()));
                            intentArrayList.get(j).putExtra("content",String.valueOf(arrayList.get(j).getArticle()));
                            //intentArrayList.get(j).putExtra("num",String.valueOf(arrayList.get(j).getNum()));
                            Log.e("HHHHHHH", "AAAAAAAAAAAAAAAA");
                        }else{
                            intentArrayList.add(new Intent(MapsActivity.this,NewsArticleImageActivity.class));
                            intentArrayList.get(j).putExtra("title",String.valueOf(arrayList.get(j).getTitle()));
                            intentArrayList.get(j).putExtra("reporter",String.valueOf(arrayList.get(j).getReporter()));
                            intentArrayList.get(j).putExtra("content",String.valueOf(arrayList.get(j).getArticle()));
                            intentArrayList.get(j).putExtra("image",String.valueOf(arrayList.get(j).getProfile()));
                            //intentArrayList.get(j).putExtra("num",String.valueOf(arrayList.get(j).getNum()));
                            Log.e("HHHHHHH", String.valueOf(arrayList.get(j).getProfile()));
                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("SubNewsActvity", String.valueOf(databaseError.toException()));
            }
        });
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String str = marker.getId();
        String[] part = str.split("m");
        int num = Integer.parseInt(part[1]);
        startActivity(intentArrayList.get(num));

    }
}
