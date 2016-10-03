package com.sunghyun.andriod.lambdabasic;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.layout1);
        RelativeLayout layout2 = (RelativeLayout) findViewById(R.id.layout2);

        layout.setVisibility(View.GONE);
        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.VISIBLE);


        Button btn = (Button) findViewById(R.id.button);



    }


}
