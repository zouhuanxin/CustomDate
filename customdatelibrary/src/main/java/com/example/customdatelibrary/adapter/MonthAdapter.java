package com.example.customdatelibrary.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customdatelibrary.Constant;
import com.example.customdatelibrary.R;
import com.kproduce.roundcorners.RoundTextView;

import java.util.List;

//月份适配器
public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {
    private List<String> list;
    private int index = 0;
    private OnMonthAdapterClick onMonthAdapterClick;
    public interface OnMonthAdapterClick{
        void OnClick(int i);
    }

    public void setOnMonthAdapterClick(OnMonthAdapterClick onMonthAdapterClick){
        this.onMonthAdapterClick = onMonthAdapterClick;
    }

    public MonthAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.month_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.monthItemT1.setText(list.get(i));
        viewHolder.monthItemT1.setTextSize(Constant.MOUTH_FONTSIZE);
        viewHolder.monthItemT1.setTextColor(Color.parseColor("#cccccc"));
        viewHolder.monthItemT2.setBackgroundColor(Color.parseColor("#ffffff"));
        viewHolder.monthItemT1.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        if (i == index){
            viewHolder.monthItemT1.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
            viewHolder.monthItemT1.setTextColor(Color.parseColor("#000000"));
            viewHolder.monthItemT2.setBackgroundColor(Color.parseColor(Constant.MONTH_XHX));
        }
        viewHolder.monthItemT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = i;
                if (onMonthAdapterClick != null) onMonthAdapterClick.OnClick(index);
                notifyDataSetChanged();
            }
        });
    }

    public void setIndex(int i){
        this.index = i;
        notifyDataSetChanged();
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView monthItemT1;
        private RoundTextView monthItemT2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            monthItemT1 = (TextView) itemView.findViewById(R.id.month_item_t1);
            monthItemT2 = (RoundTextView) itemView.findViewById(R.id.month_item_t2);
        }
    }
}