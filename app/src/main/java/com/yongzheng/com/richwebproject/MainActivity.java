package com.yongzheng.com.richwebproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by yongzheng on 2018/8/24.
 */
public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_main);
        //跳转都url
        findViewById(R.id.btn_url).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,RichUrlActivity.class));
            }
        });
        //跳转到显示html内容
        findViewById(R.id.btn_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,RichDataActivity.class));
            }
        });
    }

}
