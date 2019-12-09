package com.example.pknu_news;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class MainActivity extends Activity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        //스레드 시작


        findViewById(R.id.mainnews).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Toast.makeText(MainActivity.this,"클릭_테스트",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, MapsActivity.class));
                    }
                });

        findViewById(R.id.subnews).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Toast.makeText(MainActivity.this,"클릭_테스트",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SubNewsActivity.class));
                    }
                });
        findViewById(R.id.submit1).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Toast.makeText(MainActivity.this,"설문조사가 잠시후에 뜹니다.",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, Submit1Activity.class));
                    }
                });
        findViewById(R.id.submit2).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Toast.makeText(MainActivity.this,"투고함이 잠시후에 뜹니다.",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, Submit2Activity.class));
                    }
                });
    }


    class ExceptionHandler implements Thread.UncaughtExceptionHandler {
        private Context mContext = null;
        ExceptionHandler(Context context) {
            mContext = context;
        }
        @Override
        public void uncaughtException(Thread arg0, Throwable arg1) {
            // TODO Auto-generated method stub
            StringWriter sw = new StringWriter();
            arg1.printStackTrace(new PrintWriter(sw));
            String msg = sw.toString();
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "anminster@naver.com" });
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "에러 보고서");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, msg);
            if(mContext != null) {
                PackageManager pm = getPackageManager();
                List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
                        best = info;
                    }
                }
                if (best != null) {
                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                    mContext.startActivity(intent);
                }
                else {
                    mContext.startActivity(Intent.createChooser(intent, "이메일 앱 선택"));
                }
            }
            System.exit(0);
        }
    }

}
