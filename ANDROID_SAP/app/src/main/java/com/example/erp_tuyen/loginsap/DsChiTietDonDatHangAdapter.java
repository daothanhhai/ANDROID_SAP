package com.example.erp_tuyen.loginsap;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DsChiTietDonDatHangAdapter extends BaseAdapter {
    private activity_dsdondathang context;

    private int layout;
    private List<DsChiTietDonDatHang> dsctdondathangList;

    public DsChiTietDonDatHangAdapter(activity_dsdondathang context, int layout, List<DsChiTietDonDatHang> dsctdondathangList) {
        this.context = context;
        this.layout = layout;
        this.dsctdondathangList = dsctdondathangList;
    }

    @Override
    public int getCount() {
        return dsctdondathangList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
    private class Viewholder{
        TextView txtmavt;
        TextView txttenvt;
        TextView txtkm;
        TextView txtctkm;
        TextView txtgtc;
        TextView txtsoluong;

        TextView txtgia;
        TextView txttt;



    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {


        final Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);


            holder.txttenvt=(TextView) view.findViewById(R.id.tvtenvtctdhtdv);
            holder.txtkm=(TextView) view.findViewById(R.id.tvkhuyenmaictdhtdv);
            holder.txtctkm=(TextView) view.findViewById(R.id.tvctkmctdhtdv);
            holder.txtgtc=(TextView) view.findViewById(R.id.tvgtcctdhtdv);
            holder.txtsoluong=(TextView) view.findViewById(R.id.tvsoluongctdhtdv);

            holder.txtgia=(TextView) view.findViewById(R.id.tvdongiactdhtdv);
            holder.txttt=(TextView) view.findViewById(R.id.tvthanhtienctdhtdv);


            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final DsChiTietDonDatHang dsctdonDatHangtdv=dsctdondathangList.get(i);

        holder.txttenvt.setText(dsctdonDatHangtdv.getTenvt());
        holder.txttenvt.setWidth(180);
        holder.txttenvt.setHeight(100);
        holder.txttenvt.setGravity(Gravity.CENTER);



//        holder.txtkm.setText(dsctdonDatHangtdv.getKm());
//        holder.txtkm.setWidth(10);
//        holder.txtkm.setHeight(80);
//        holder.txtkm.setGravity(Gravity.CENTER);
//
//
//        holder.txtctkm.setText(dsctdonDatHangtdv.getCtkm());
//        holder.txtctkm.setWidth(60);
//        holder.txtctkm.setHeight(80);
//        holder.txtctkm.setGravity(Gravity.CENTER);


//        String b=dsctdonDatHangtdv.getGia();

        holder.txtgtc.setText(String.format("%,.0f", Float.valueOf(dsctdonDatHangtdv.getGiatricode()) ));
        //holder.txtgtc.setText(dsctdonDatHangtdv.getGiatricode());
        holder.txtgtc.setWidth(160);
        holder.txtgtc.setHeight(100);
        holder.txtgtc.setGravity(Gravity.CENTER);


        //holder.edsl.setText(donDatHangtdv.getSoluong());
        holder.txtsoluong.setText(String.format("%,.0f", Float.valueOf(dsctdonDatHangtdv.getSoluong()) ));
        holder.txtsoluong.setWidth(50);
        holder.txtsoluong.setHeight(100);

        holder.txtgia.setText(String.format("%,.0f", Float.valueOf(dsctdonDatHangtdv.getGia())));
        holder.txtgia.setWidth(120);
        holder.txtgia.setHeight(100);


        holder.txttt.setText(String.format("%,.0f", Float.valueOf(dsctdonDatHangtdv.getThanhtien()) ));
        holder.txttt.setWidth(120);
        holder.txttt.setHeight(100);


        return view;
    }
}
