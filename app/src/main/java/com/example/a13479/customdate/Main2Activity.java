package com.example.a13479.customdate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.customdatelibrary.VerticalZhxDate;

public class Main2Activity extends AppCompatActivity {

    private VerticalZhxDate verticalzhxdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();
    }

    private void initView() {
        verticalzhxdate = (VerticalZhxDate) findViewById(R.id.verticalzhxdate);

    }
}
