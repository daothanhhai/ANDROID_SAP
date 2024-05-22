package com.example.erp_tuyen.loginsap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SanPhamChinhAdapter extends BaseAdapter {
    private activity_sanphamchinh context;

    private int layout;
    private List<SanPhamChinh> sanphamchinhList;

    public SanPhamChinhAdapter(activity_sanphamchinh context, int layout, List<SanPhamChinh> sanphamchinhList) {
        this.context = context;
        this.layout = layout;
        this.sanphamchinhList = sanphamchinhList;
    }

    @Override
    public int getCount() {
        return sanphamchinhList.size();
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
        TextView txttensp;
        TextView txtgiasp;
        ImageView imagesp;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txttensp=(TextView) view.findViewById(R.id.textViewtensp);
            holder.txtgiasp=(TextView) view.findViewById(R.id.textViewgiasp);
            holder.imagesp=(ImageView) view.findViewById(R.id.imageviewhinh);
            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final SanPhamChinh sanphamchinh=sanphamchinhList.get(i);

        holder.txttensp.setText(sanphamchinh.getTensp());

        holder.txtgiasp.setText(String.format("%,.0f", Float.valueOf(sanphamchinh.getGiasp()) ));

        holder.imagesp.setImageBitmap(sanphamchinh.getHinhsp());



        return view;
    }


}
