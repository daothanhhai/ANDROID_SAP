package com.example.erp_tuyen.loginsap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TopSanPhamAdapter extends BaseAdapter {
    private activity_topsanpham context;
    private int layout;
    private List<TopSanPham> topSanPhamsList;

    public TopSanPhamAdapter(activity_topsanpham context, int layout, List<TopSanPham> topSanPhamsList) {
        this.context = context;
        this.layout = layout;
        this.topSanPhamsList = topSanPhamsList;
    }

    @Override
    public int getCount() {
        return topSanPhamsList.size();
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

        TextView txttenvt;
        TextView txttopsp;
        TextView txttylesp;
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txttenvt=(TextView) view.findViewById(R.id.textViewtenvt);
            holder.txttopsp=(TextView) view.findViewById(R.id.textViewtopsanpham);
            holder.txttylesp=(TextView) view.findViewById(R.id.tvtltopsp);
            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final TopSanPham topSanPham=topSanPhamsList.get(i);

        holder.txttenvt.setText(topSanPham.getTenVt());
        holder.txttopsp.setText(String.format("%,.0f", Float.valueOf(topSanPham.getTopSp()) ));
        holder.txttylesp.setText(String.format("%,.0f", Float.valueOf(topSanPham.getTyle()) )+""+"%");



        return view;
    }
}
