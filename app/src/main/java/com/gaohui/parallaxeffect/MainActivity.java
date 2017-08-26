package com.gaohui.parallaxeffect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.gaohui.parallaxeffect.utils.Cheeses;
import com.gaohui.parallaxeffect.view.MyListView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyListView mListView = (MyListView) findViewById(R.id.lv);

        final View mHeaderView = View.inflate(MainActivity.this, R.layout.view_header, null);
        final ImageView mImage = (ImageView) mHeaderView.findViewById(R.id.iv);

        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                // 当布局填充结束之后, 此方法会被调用
                mListView.setParallaxImage(mImage);

                mHeaderView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        mListView.addHeaderView(mHeaderView);


        mListView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, Cheeses.NAMES));
    }
}