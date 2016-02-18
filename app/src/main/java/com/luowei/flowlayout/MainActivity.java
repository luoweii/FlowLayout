package com.luowei.flowlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private FlowLayout flContent;
    private String[] data = {"半壶纱","大梦想家","夏洛特烦恼","因为遇见你","恭喜发财","你不来我不老",
            "青春修炼手册","可念不可说","星星泪", "给我一点温度","燕归巢","Rain","秘密",
            "找一个伤心的理由","我的大学","世间始终你好","独角戏","容易烧伤的女人","美人吟","哭不回的爱, 哭不回的你"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this,VDHActivity.class));
            }
        });

        LayoutInflater inflater = getLayoutInflater();
        flContent = (FlowLayout) findViewById(R.id.flContent);
        for (String s : data) {
            TextView tv = (TextView) inflater.inflate(R.layout.item_text, flContent,false);
            tv.setText(s);
            flContent.addView(tv);
        }
    }

    public void btnOnClick(View view) {
        view.setVisibility(View.GONE);
    }
}
