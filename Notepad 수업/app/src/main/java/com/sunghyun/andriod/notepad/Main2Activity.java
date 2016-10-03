package com.sunghyun.andriod.notepad;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {

    RecyclerCardAdapter adapter;
    RelativeLayout textLayout;
    RelativeLayout inputLayout;
    RecyclerView recylcerView;
    EditText mEditText;
    boolean keyboard=false;
    public static ArrayList<Note> datas = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        datas = new ArrayList<>();

        inputLayout = (RelativeLayout) findViewById(R.id.inputLayout);
        textLayout = (RelativeLayout) findViewById(R.id.textlayout);

        inputLayout.setVisibility(View.INVISIBLE);
        textLayout.setVisibility(View.INVISIBLE);

        mEditText = (EditText) findViewById(R.id.editText);

        Button inputButton = (Button) findViewById(R.id.input_button);
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = mEditText.getText().toString(); // 에딧텍스트에서 스트링가져온다
                String firstLine;
                String content;

                if (text.indexOf("\n")!= -1){
                    firstLine= text.substring(0,text.indexOf("\n"));
                    content = text.substring(text.indexOf("\n")+1,text.length());
                } else {
                    firstLine = text;
                    content = "";
                }

                Note data = new Note();
                data.title = "제목: "+ firstLine;
                data.content = "내용: " + content;

                datas.add(data);
                adapter.notifyDataSetChanged();
                inputLayout.setVisibility(View.GONE);
                textLayout.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(Main2Activity.this, android.R.anim.slide_in_left);
                animation.setDuration(1000);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //keyBoardOff();
                textLayout.setVisibility(View.GONE);
                inputLayout.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(Main2Activity.this, android.R.anim.fade_in);
                animation.setDuration(1000);
                inputLayout.startAnimation(animation);

            }
        });




        recylcerView = (RecyclerView) findViewById(R.id.recyclerCardView);
        adapter = new RecyclerCardAdapter(this,datas,R.layout.recycler_card_item);
        recylcerView.setAdapter(adapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recylcerView.setLayoutManager(manager);

    }

    @Override
    public void onBackPressed() {

        adapter.notifyDataSetChanged();
        //recylcerView.invalidate();
        inputLayout.setVisibility(View.GONE);
        textLayout.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(Main2Activity.this, android.R.anim.slide_in_left);
        animation.setDuration(3000);




    }

    public void keyBoardOff() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
}
