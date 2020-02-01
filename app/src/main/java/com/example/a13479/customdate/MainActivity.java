package com.example.a13479.customdate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout f1;
    private TextView t1;
    private FrameLayout fram;
    private ZhxDate zhxDate;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;
    private Button b10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        f1 = findViewById(R.id.fram);
//        t1 = findViewById(R.id.t1);

//        f1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        System.out.println("down");
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        System.out.println("move");
//                        System.out.println(isTouchPointInView(t1, (int) event.getRawX(), (int) event.getRawY()));
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        System.out.println("up");
//                        break;
//                }
//                return true;
//            }
//        });
        initView();
    }

    //(x,y)是否在view的区域内
    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        //view.isClickable() &&
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }

    private void initView() {
        zhxDate = (ZhxDate) findViewById(R.id.zhx);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        b5 = (Button) findViewById(R.id.b5);
        b6 = (Button) findViewById(R.id.b6);
        b7 = (Button) findViewById(R.id.b7);
        b8 = (Button) findViewById(R.id.b8);
        b9 = (Button) findViewById(R.id.b9);
        b10 = (Button) findViewById(R.id.b10);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b10.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        List<String> templist = new ArrayList<>();
        templist.add("2020年1月8");
        switch (v.getId()) {
            case R.id.b1:
                zhxDate.setType(1);
                break;
            case R.id.b2:
                zhxDate.setType(2);
                break;
            case R.id.b3:
                zhxDate.setType(3);
                break;
            case R.id.b4:
                zhxDate.setType(4);
                break;
            case R.id.b5:
                zhxDate.setStalist(1,templist);
                break;
            case R.id.b6:
                zhxDate.setStalist(2,templist);
                break;
            case R.id.b7:
                zhxDate.setStalist(3,null);
                break;
            case R.id.b8:
                zhxDate.setStalist(4,templist);
                break;
            case R.id.b9:
                zhxDate.setType(1);
                zhxDate.setStalist(0,null);
                break;
            case R.id.b10:
                Toast.makeText(MainActivity.this,String.valueOf(zhxDate.getList().size()),Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
