package com.example.erp_tuyen.loginsap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends BaseAdapter {
    private activity_giohang context;
    private int layout;
    private List<GioHang> giohangList;

    public GioHangAdapter(activity_giohang context, int layout, List<GioHang> giohangList) {
        this.context = context;
        this.layout = layout;
        this.giohangList = giohangList;
    }

    @Override
    public int getCount() {
        return giohangList.size();
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
        TextView txttenspgh;
        TextView txtgiaspgh;
        TextView txtsoluonggh;
        ImageView imagespgh;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txttenspgh=(TextView) view.findViewById(R.id.textviewtenspgh);
            holder.txtgiaspgh=(TextView) view.findViewById(R.id.textgiaspgh);
            holder.txtsoluonggh=(TextView) view.findViewById(R.id.textViewslspgh);
            holder.imagespgh=(ImageView) view.findViewById(R.id.imageviewgiohang);



            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final GioHang giohang=giohangList.get(i);

        holder.txttenspgh.setText(giohang.getTensp());

        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");

        holder.txtgiaspgh.setText(decimalFormat.format(giohang.getGiasp()));

        holder.txtsoluonggh.setText(decimalFormat.format(giohang.getSoluong()) );

        holder.imagespgh.setImageBitmap(giohang.getHinhsp());




        return view;
    }
}
