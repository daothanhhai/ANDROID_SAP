package com.example.erp_tuyen.loginsap;

import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DoanhThuAdapter extends BaseAdapter {
    private activity_doanhthu context;

    private int layout;
    private List<DoanhThu> doanhThuList;

    public DoanhThuAdapter(activity_doanhthu context, int layout, List<DoanhThu> doanhThuList) {
        this.context = context;
        this.layout = layout;
        this.doanhThuList = doanhThuList;
    }

    @Override
    public int getCount() {
        return doanhThuList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class Viewholder{
        TextView txtsoct;
        TextView txtngayct;
        TextView txtmadt;
        TextView txttendt;
        TextView txtdoanhthu;
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {

        Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txttendt=(TextView) view.findViewById(R.id.textviewtendt);
            holder.txttendt.setBackground(ContextCompat.getDrawable(context,R.drawable.border));
            holder.txttendt.setWidth(160);
            //holder.txttendt.setHeight(80);

            holder.txtdoanhthu=(TextView) view.findViewById(R.id.textviewdoanhthu);
            holder.txtdoanhthu.setBackground(ContextCompat.getDrawable(context,R.drawable.border));
            holder.txtdoanhthu.setWidth(160);
            //holder.txtdoanhthu.setHeight(80);

            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final DoanhThu doanhThu=doanhThuList.get(i);

        holder.txttendt.setText(doanhThu.getTendt());


        holder.txtdoanhthu.setText(String.format("%,.0f", Float.valueOf(doanhThu.getDoanhthu()) ));

        return view;
    }
}
