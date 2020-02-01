package com.example.customdatelibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customdatelibrary.MethodUtil;
import com.example.customdatelibrary.R;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {
    private Context context;
    private List<String> list;
    private int type = 1;
    private int sta = 0;
    private List<String> jys;
    private List<ViewHolder> dgljs = new ArrayList<>(); //所有viewholder集合

    public DayAdapter(List<String> list, int type, int sta, List<String> jys) {
        this.list = list;
        this.type = type;
        this.sta = sta;
        this.jys = jys;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null) context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.day_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (sta == 0) {

        } else if (sta == 1 || sta == 2) {
            //每种模式可以指定选中日期 1
            //每种模式可以选择指定日期禁用 2
            if (type == 1 || type == 2){
                for (String s : jys) {
                    if (s.equals(list.get(i))) {
                        viewHolder.dayItemL1.setEnabled(sta == 1 ? true : false);
                        if (sta == 2) viewHolder.dayItemT1.setTextColor(Color.parseColor("#cccccc"));
                    } else {
                        viewHolder.dayItemL1.setEnabled(sta == 1 ? false : true);
                        if (sta == 1) viewHolder.dayItemT1.setTextColor(Color.parseColor("#cccccc"));
                    }
                }
            }
        } else if (sta == 3) {
            if (type == 1 || type == 2){
                viewHolder.dayItemL1.setEnabled(false);
                viewHolder.dayItemT1.setTextColor(Color.parseColor("#cccccc"));
            }
        } else if (sta == 4) {
            for (int p = 0; p < jys.size(); p++) {
                if (jys.get(p).equals(list.get(i))) {
                    if (type == 2) {
                        viewHolder.dayItemT1.setBackgroundResource(R.drawable.button_radius_bule);
                        viewHolder.dayItemT1.setTextColor(Color.parseColor("#ffffff"));
                        viewHolder.dayItemL1.setTag(true);
                    }
                }
            }
        }
        viewHolder.dayItemT1.setText(list.get(i).indexOf("月") != -1 ? String.valueOf(list.get(i).split("月")[1]) : list.get(i));
        viewHolder.dayItemT1.setHint(list.get(i));
        if (i < 7) {
            viewHolder.dayItemT1.setTextColor(Color.parseColor("#000000"));
        }
        dgljs.add(viewHolder);
        switch (type) {
            case 1:
                dxpre(viewHolder, i);
                break;
            case 2:
                dgpre(viewHolder, i);
                break;
            case 3:
                dgljpre(viewHolder, i);
                break;
            case 4:

                break;
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        //设置textview高度
        int w = MethodUtil.getScreenWidth(context);
        ViewGroup.LayoutParams params = holder.dayItemL1.getLayoutParams();
        params.height = w / 7;
        holder.dayItemL1.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout dayItemL1;
        private TextView dayItemT1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayItemL1 = (LinearLayout) itemView.findViewById(R.id.day_item_l1);
            dayItemT1 = (TextView) itemView.findViewById(R.id.day_item_t1);
        }
    }

    //1 单选
    private int index = -1;

    private void dxpre(final ViewHolder viewHolder, final int i) {
        if (i < 7 || TextUtils.isEmpty(viewHolder.dayItemT1.getText())) {
            return;
        }
        if (index == i) {
            viewHolder.dayItemL1.setTag(true);
            viewHolder.dayItemT1.setBackgroundResource(R.drawable.button_radius_bule);
            viewHolder.dayItemT1.setTextColor(Color.parseColor("#ffffff"));
        }
        viewHolder.dayItemL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == index) {
                    index = -1;
                } else {
                    index = i;
                }
                clearcolor();
                dgljs.clear();
                notifyDataSetChanged();
            }
        });
    }

    //2 多选
    private void dgpre(final ViewHolder viewHolder, final int i) {
        viewHolder.dayItemL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i < 7 || TextUtils.isEmpty(viewHolder.dayItemT1.getText())) {
                    return;
                }
                if (viewHolder.dayItemL1.getTag() == null || (boolean) viewHolder.dayItemL1.getTag() == false) {
                    //没有选中
                    viewHolder.dayItemT1.setBackgroundResource(R.drawable.button_radius_bule);
                    viewHolder.dayItemT1.setTextColor(Color.parseColor("#ffffff"));
                    viewHolder.dayItemL1.setTag(true);
                } else {
                    viewHolder.dayItemT1.setBackgroundResource(R.drawable.button_radius_white);
                    viewHolder.dayItemT1.setTextColor(Color.parseColor("#8B8989"));
                    viewHolder.dayItemL1.setTag(false);
                }
            }
        });
    }

    //3 点击起始日期与终点日期中间自动选择
    private int a = -1, b = -1;

    private void dgljpre(final ViewHolder viewHolder, final int i) {
        viewHolder.dayItemL1.setPadding(0, 10, 0, 0);
        if (i < 7 || TextUtils.isEmpty(viewHolder.dayItemT1.getText())) {
            return;
        }
        dgljs.get(i).dayItemL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dgljs.get(i).dayItemL1.getTag() == null || (boolean) dgljs.get(i).dayItemL1.getTag() == false) {
                    //没有选中
                    dgljs.get(i).dayItemT1.setBackgroundResource(R.drawable.button_radius_bule);
                    dgljs.get(i).dayItemT1.setTextColor(Color.parseColor("#ffffff"));
                    dgljs.get(i).dayItemL1.setTag(true);
                    dgljcolor2(i);
                } else {
                    clearcolor();
                }
            }
        });
    }

    private void dgljcolor() {
        for (int i = 0; i < dgljs.size(); i++) {
            if (i == a) {
                dgljs.get(a).dayItemL1.setTag(true);
                dgljs.get(a).dayItemT1.setBackgroundResource(R.drawable.button_radius_bule_left);
                dgljs.get(a).dayItemT1.setTextColor(Color.parseColor("#ffffff"));
            } else if (i == b) {
                dgljs.get(b).dayItemL1.setTag(true);
                dgljs.get(b).dayItemT1.setBackgroundResource(R.drawable.button_radius_bule_right);
                dgljs.get(b).dayItemT1.setTextColor(Color.parseColor("#ffffff"));
            } else if (i > a && i < b) {
                dgljs.get(i).dayItemT1.setBackgroundResource(R.drawable.button_bule);
                dgljs.get(i).dayItemT1.setTextColor(Color.parseColor("#ffffff"));
            } else {
                dgljs.get(i).dayItemT1.setBackgroundResource(R.drawable.button_white);
                dgljs.get(i).dayItemT1.setTextColor(Color.parseColor("#8B8989"));
            }
        }
    }

    private void dgljcolor2(int i) {
        //检测是否有俩个选中状态
        for (int n = 0; n < dgljs.size(); n++) {
            if (dgljs.get(n).dayItemL1.getTag() != null && (boolean) dgljs.get(n).dayItemL1.getTag() == true) {
                if (a == -1) {
                    a = n;
                    return;
                } else if (b == -1 && n != a) {
                    if (n < a) {
                        b = a;
                        a = n;
                    } else {
                        b = n;
                    }
                    dgljcolor();
                    return;
                } else if (a != -1 && b != -1) {
                    //已经有俩个选中状态了
                    if (n != a && n != b) {
                        if (n < a) {
                            dgljs.get(a).dayItemL1.setTag(false);
                            a = n;
                        } else if (n > b) {
                            dgljs.get(b).dayItemL1.setTag(false);
                            b = n;
                        } else if (n > a && n < b) {
                            dgljs.get(b).dayItemL1.setTag(false);
                            b = n;
                        }
                        dgljcolor();
                        return;
                    }
                }
            }
        }
    }

    private void clearcolor() {
        a = -1;
        b = -1;
        for (int i = 0; i < dgljs.size(); i++) {
            dgljs.get(i).dayItemL1.setTag(false);
            dgljs.get(i).dayItemT1.setBackgroundResource(R.drawable.button_radius_white);
            dgljs.get(i).dayItemT1.setTextColor(Color.parseColor("#8B8989"));
        }
    }

    //4 随x y变化而点击
    public int firstIndex = 7;

    public void DownFourpre(int x, int y) {
        clearcolor();
        for (int i = 7; i < dgljs.size(); i++) {
            if (!TextUtils.isEmpty(dgljs.get(i).dayItemT1.getText())) {
                if (isTouchPointInView2(dgljs.get(i).dayItemT1, x, y)) {
                    firstIndex = i;
                    dgljs.get(i).dayItemT1.setBackgroundResource(R.drawable.button_radius_bule);
                    dgljs.get(i).dayItemT1.setTextColor(Color.parseColor("#ffffff"));
                    dgljs.get(i).dayItemL1.setTag(true);
                }
            }
        }
    }

    public void Fourpre(int x, int y) {
        for (int i = firstIndex; i < dgljs.size(); i++) {
            if (!TextUtils.isEmpty(dgljs.get(i).dayItemT1.getText())) {
                if (isTouchPointInView(dgljs.get(i).dayItemT1, x, y)) {
                    dgljs.get(i).dayItemT1.setBackgroundResource(R.drawable.button_radius_bule);
                    dgljs.get(i).dayItemT1.setTextColor(Color.parseColor("#ffffff"));
                    dgljs.get(i).dayItemL1.setTag(true);
                    setCowColor(i);
                } else {
                    dgljs.get(i).dayItemT1.setBackgroundResource(R.drawable.button_radius_white);
                    dgljs.get(i).dayItemT1.setTextColor(Color.parseColor("#8B8989"));
                    dgljs.get(i).dayItemL1.setTag(false);
                }
            }
        }
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
        if (y >= top && x >= left) {
            return true;
        }
        return false;
    }

    private boolean isTouchPointInView2(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }

    private void setCowColor(int i) {
        int max = 0;
        if (i > 13 && i <= 20) {
            max = 13 + 1;
        } else if (i > 20 && i <= 27) {
            max = 20 + 1;
        } else if (i > 27 && i <= 34) {
            max = 27 + 1;
        } else if (i > 34 && i <= 41) {
            max = 34 + 1;
        }
        for (int m = firstIndex; m < max; m++) {
            if (!TextUtils.isEmpty(dgljs.get(m).dayItemT1.getText())) {
                dgljs.get(m).dayItemL1.setTag(true);
                dgljs.get(m).dayItemT1.setBackgroundResource(R.drawable.button_radius_bule);
                dgljs.get(m).dayItemT1.setTextColor(Color.parseColor("#ffffff"));
            }
        }
    }

    //返回当前适配器中选中的所有数据集合
    //选中字体都是白色
    public List<String> getList() {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < dgljs.size(); i++) {
            if (dgljs.get(i).dayItemL1.getTag() != null && (boolean) dgljs.get(i).dayItemL1.getTag()) {
                res.add(dgljs.get(i).dayItemT1.getHint().toString());
            }
        }
        if (type == 3){
            if (res.size() == 2){
                int start = Integer.parseInt(res.get(0).split("月")[1]);
                int end = Integer.parseInt(res.get(1).split("月")[1]);
                res.clear();
                for (int i = start; i <= end; i++) {
                    res.add(dgljs.get(i).dayItemT1.getHint().toString());
                }
            }
        }
        return res;
    }
}