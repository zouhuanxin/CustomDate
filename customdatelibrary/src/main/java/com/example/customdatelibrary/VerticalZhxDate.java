package com.example.customdatelibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Message;
import android.support.annotation.UiThread;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customdatelibrary.adapter.DayAdapter;
import com.example.customdatelibrary.adapter.MonthAdapter;
import com.example.customdatelibrary.bean.Customdatebean;
import com.example.customdatelibrary.bean.Notebean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 个性化日历
 * 1.单选
 * 2.多选
 * 3.点击起始日期与终点日期中间自动选择
 * 4.
 * <p>
 * 0表示正常
 * 每种模式可以指定选中日期 1  支持单选模式与多选模式
 * 每种模式可以选择指定日期禁用 2  支持单选模式与多选模式
 * 每种模式可以禁用所有日期 3  支持单选模式与多选模式
 * 每种模式可以指定预选择日期 4  支持多选模式
 */
public class VerticalZhxDate extends LinearLayout {
    private Context context;
    private View view;
    private int year = -1;
    private int month = -1;
    private int day = -1;
    private List<String> monthlist = new ArrayList<>();
    private List<List<String>> daylist = new ArrayList<>();
    private  List<View> tlist = new ArrayList<>(); //view集合

    private LayoutInflater inflater;
    private int type = 1;

    private MonthAdapter ma;
    private List<DayAdapter> dayAdapters = new ArrayList<>(); //保存生成的所有日期适配器对象
    //一次生成 12*9 九年数据 12*4=48  49-60 61-108
    private int nowpager = 0;
    private int bs = 2; //倍数 只能是双数 2 4 6 8 ....
    //保持年份数据
    private List<Integer> years = new ArrayList<>();
    private int minyear = 0;
    private int maxyear = 3000;

    //模式状态
    private int sta = 0;
    //指定选中日期集合
    //指定禁用日期集合
    //禁用所有日期
    //预选中日期集合
    private List<String> stalist = new ArrayList<>();

    //自定义日期数据源
    //默认里面只有今天的数据
    //数据类型为 Customdatebean
    private List<Customdatebean> customdates = new ArrayList<>();

    //日期下面备注下标信息
    private List<List<Notebean>> notebeans = new ArrayList<>();

    private LinearLayout verticalzhxdateGroup;

    private OnDateSingleClick onDateSingleClick;

    public void setOnDateSingleClick(OnDateSingleClick onDateSingleClick) {
        this.onDateSingleClick = onDateSingleClick;
        resh();
    }

    public void setStalist(int sta, List<String> stalist) {
        this.sta = sta;
        this.stalist = stalist;
        resh();
    }

    public void setType(int type) {
        this.type = type;
        resh();
    }

    public void setBs(int bs) {
        this.bs = bs;
        resh();
    }

    //设置当前选中哪个月份
    //你只需要选中月份 当前年份不需要管 月份的选择与年份无关
    public void setNatigationMonth(int i) {
        ma.setIndex(i);
    }

    //程序代码选择日期
    //支持多选 连续俩种模式
    public void setDayReslist(List<String> list) {
        if (type == 3) {
            if (list.size() != 2) return;
        }
        for (int i = 0; i < dayAdapters.size(); i++) {
            dayAdapters.get(i).setDxDate(list);
        }
    }

    public void setNotebeans(List<Notebean> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < notebeans.size(); j++) {
                for (int m = 0; m < notebeans.get(j).size(); m++) {
                    if (list.get(i).getDate().equals(notebeans.get(j).get(m).getDate())) {
                        notebeans.get(j).get(m).setNote(list.get(i).getNote());
                    }
                }
            }
        }
        resh();
    }

    //自定义数据源
    //会全部刷新
    public void setCustomdates(List<Customdatebean> list) {
        customdates = list;
        monthlist.clear();
        daylist.clear();
        years.clear();
        initData();
        resh();
    }

    //更新自定义数据源
    //局部刷新
    public void uploadCustomdates(Customdatebean customdatebean) {
        customdatesAdd(customdatebean);
        for (int i = 0; i < dayAdapters.size(); i++) {
            dayAdapters.get(i).uploadCustomDateaResh(customdatebean);
        }
    }

    private void resh() {
        // initData();
        synchronized (this) {
            initView(view);
            initday();
        }
    }

    public VerticalZhxDate(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (this.context == null) this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DateLayout);
        Constant.MONTH_XHX = a.getString(R.styleable.DateLayout_month_xhxcolor) == null ? "#4678ff" : a.getString(R.styleable.DateLayout_month_xhxcolor);
        Constant.DAYITEM = a.getString(R.styleable.DateLayout_dayitemcolor) == null ? "#4678ff" : a.getString(R.styleable.DateLayout_dayitemcolor);
        bs = a.getInteger(R.styleable.DateLayout_bs, 2);
        Constant.YEAR_FONTSIZE = a.getInteger(R.styleable.DateLayout_year_fontsize, 16);
        Constant.MOUTH_FONTSIZE = a.getInteger(R.styleable.DateLayout_month_fontsize, 15);
        Constant.DAY_FONTSIZE = a.getInteger(R.styleable.DateLayout_day_fontsize, 14);
        Constant.DAYNOTE_FONTSIZE = a.getInteger(R.styleable.DateLayout_daynote_fontsize, 8);
        Constant.TITLE_FONTSIZE = a.getInteger(R.styleable.DateLayout_title_fontsize, 15);
        Constant.TITLE_bACKCOLOR = a.getString(R.styleable.DateLayout_title_backcolor) == null ? "#f1f1f1" : a.getString(R.styleable.DateLayout_title_backcolor);
        Constant.TITLE_FONTCOLOR = a.getString(R.styleable.DateLayout_title_fontcolor) == null ? "#000000" : a.getString(R.styleable.DateLayout_title_fontcolor);
        Constant.TITLE_LOCAL = a.getString(R.styleable.DateLayout_title_local) == null ? "center" : a.getString(R.styleable.DateLayout_title_local);
        monthlist.clear();
        customdates.clear();
        daylist.clear();
        years.clear();
        notebeans.clear();
        //单独放在这里进行初始化为了方便重新设置数据源刷新问题
        customdatesAdd(new Customdatebean(Integer.parseInt(MethodUtil.getSystemTime().split("年")[0]) + "年" +
                Integer.parseInt(MethodUtil.getSystemTime().split("年")[1].split("月")[0]) + "月" +
                Integer.valueOf(MethodUtil.getSystemTime().split("月")[1].split("日")[0]), "今天"));
        initData();
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.verticalzhxdate, this, true);
        initView(view);
        initday();
    }

    private void initData() {
        year = Integer.parseInt(MethodUtil.getSystemTime().split("年")[0]);
        month = Integer.parseInt(MethodUtil.getSystemTime().split("年")[1].split("月")[0]);
        day = Integer.valueOf(MethodUtil.getSystemTime().split("月")[1].split("日")[0]);
        nowpager = bs / 2 * 12 + 1 + month - 1;
        minyear = year - bs / 2;
        maxyear = year + bs / 2;

        createMonthDay(year);
    }

    private void customdatesAdd(Customdatebean customdatebean) {
        for (Customdatebean cb : customdates) {
            if (cb.getOlddate().equals(customdatebean.getOlddate())) {
                Collections.replaceAll(customdates, cb, customdatebean);
                return;
            }
        }
        customdates.add(customdatebean);
    }

    //生成当前年份的十二个月信息
    private void createMonthDay(int year) {
        int c = bs / 2;
        int y = year - c;
        for (int m = 0; m < bs + 1; m++) {
            for (int i = 1; i < 13; i++) {
                List<String> tl = new ArrayList<>();
                List<Notebean> tnotelist = new ArrayList<>();
                tl.add("日");
                tl.add("一");
                tl.add("二");
                tl.add("三");
                tl.add("四");
                tl.add("五");
                tl.add("六");
                for (int s = 0; s < 7; s++) {
                    tnotelist.add(new Notebean("", ""));
                }
                int t = MethodUtil.getWeekdayOfMonth(y + m, i, 1);
                if (t != 7) {
                    for (int j = 0; j < t; j++) {
                        tl.add("");
                        tnotelist.add(new Notebean("", ""));
                    }
                }
                int a = MethodUtil.getMonthOfDay(y + m, i);
                loadDay(tl, a, y, m, i, tnotelist);
                years.add(y + m);
                daylist.add(tl);
                notebeans.add(tnotelist);
            }
        }
    }

    //装载日期数据
    private void loadDay(List<String> tl, int a, int y, int m, int i, List<Notebean> tnotelist) {
        for (int n = 1; n <= a; n++) {
            String s = y + m + "年" + i + "月" + String.valueOf(n);
            String s2 = y + m + "年" + (i < 10 ? "0" + i : i) + "月" + String.valueOf(n < 10 ? "0" + n : n);
            tl.add(s);
            tnotelist.add(new Notebean(s, ""));
        }
        for (int p = 0; p < customdates.size(); p++) {
            for (int u = 0; u < tl.size(); u++) {
                if (customdates.get(p).getOlddate().equals(tl.get(u))) {
                    Collections.replaceAll(tl, tl.get(u), y + m + "年" + i + "月" + customdates.get(p).getNewdata());
                }
            }
        }
    }

    //初始化日期 默认1月
    private void initday() {
        dayAdapters.clear();
        tlist.clear();
        for (int i = 0; i < 12 * (bs + 1); i++) {
            View dayview = inflater.inflate(R.layout.monthfragment, null);
            final RecyclerView dayview_R = dayview.findViewById(R.id.monthfragment_r1);
            final TextView titleview_R = dayview.findViewById(R.id.monthfragment_title);
            int y = Integer.parseInt(MethodUtil.getSystemTime().split("年")[0]);
            int b = y - bs / 2;
            titleview_R.setText(b + MethodUtil.getNowYear(i) + "年" + (i - 12 * MethodUtil.getNowYear(i) + 1) + "月");
            titleview_R.setTextColor(Color.parseColor(Constant.TITLE_FONTCOLOR));
            titleview_R.setBackgroundColor(Color.parseColor(Constant.TITLE_bACKCOLOR));
            titleview_R.setTextSize(Constant.TITLE_FONTSIZE);
            titleview_R.setGravity(Constant.TITLE_LOCAL.equals("center")?Gravity.CENTER:Constant.TITLE_LOCAL.equals("left")?
                    Gravity.CENTER|Gravity.LEFT:Constant.TITLE_LOCAL.equals("right")?Gravity.CENTER|Gravity.RIGHT:Gravity.CENTER);
            titleview_R.setVisibility(VISIBLE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);
            final DayAdapter dayAdapter = new DayAdapter(daylist.get(i), notebeans.get(i), type, sta, stalist);
            dayAdapter.setOnDateSingleClick(onDateSingleClick);
            dayAdapters.add(dayAdapter);
            dayview_R.setLayoutManager(gridLayoutManager);
            dayview_R.setAdapter(dayAdapter);
            tlist.add(dayview);
            if (type == 4) {
                dayview_R.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                dayAdapter.DownFourpre((int) event.getRawX(), (int) event.getRawY());
                                break;
                            case MotionEvent.ACTION_MOVE:
                                dayAdapter.Fourpre((int) event.getRawX(), (int) event.getRawY());
                                break;
                            case MotionEvent.ACTION_UP:
                                dayAdapter.firstIndex = 7;
                                break;
                        }
                        return true;
                    }
                });
            }
        }
        int b = bs/2;
        initVerticalLayoutView(tlist,b*12+month -1,b*12+month+1);
    }

    //默认超出之前范围隐藏
    //可以覆盖重写
    public void setHidescrollView(int s,int e){
        initVerticalLayoutView(tlist,s,e);
    }

    @UiThread
    private void initVerticalLayoutView(List<View> tlist,int s,int e) {
        verticalzhxdateGroup.removeAllViews();
        for (int i=0;i<tlist.size();i++){
            tlist.get(i).setVisibility(GONE);
            if (i>=s && i<= e){
                tlist.get(i).setVisibility(VISIBLE);
            }
            verticalzhxdateGroup.addView(tlist.get(i));
        }
    }

    private void initView(View view) {
        verticalzhxdateGroup = (LinearLayout) view.findViewById(R.id.verticalzhxdate_group);
    }


    //返回所有选中数据集合
    public List<String> getList() {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < dayAdapters.size(); i++) {
            for (String s : dayAdapters.get(i).getList()) {
                res.add(s);
            }
        }
        return res;
    }

}
