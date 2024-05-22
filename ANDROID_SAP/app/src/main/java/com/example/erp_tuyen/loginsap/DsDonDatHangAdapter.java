package com.example.erp_tuyen.loginsap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DsDonDatHangAdapter extends BaseAdapter {
    private activity_dsdondathang context;


    private int layout;
    private List<DsDonDatHang> dsdondathangList;

    public DsDonDatHangAdapter(activity_dsdondathang context, int layout, List<DsDonDatHang> dsdondathangList) {
        this.context = context;
        this.layout = layout;
        this.dsdondathangList = dsdondathangList;
    }

    @Override
    public int getCount() {
        return dsdondathangList.size();
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
        TextView txtngayct;
        TextView txtdoituong;
        TextView txttongtien;
        TextView txttinhtrang;
        ImageView imgxoaddh;
        Button btxemctddhtdv;
        String stt;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {
        final Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txtngayct=(TextView) view.findViewById(R.id.textViewngayct);

            holder.txtdoituong=(TextView) view.findViewById(R.id.textViewdoituong);
            holder.txttongtien=(TextView) view.findViewById(R.id.textViewtongtien);
            holder.txttinhtrang=(TextView) view.findViewById(R.id.textViewtinhtrang);
            holder.imgxoaddh=(ImageView) view.findViewById(R.id.imagedeleteDDH);
            holder.btxemctddhtdv=(Button) view.findViewById(R.id.buttonxemddh);

            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final DsDonDatHang dsdondathang=dsdondathangList.get(i);

        holder.stt=dsdondathang.getStt();


        holder.txtdoituong.setText(dsdondathang.getTendt());

        holder.txtdoituong.setWidth(170);
        holder.txtdoituong.setHeight(70);


        holder.txtngayct.setText(dsdondathang.getNgayct());
        holder.txtngayct.setWidth(100);
        holder.txtngayct.setHeight(70);

        holder.txttongtien.setText(String.format("%,.0f", Float.valueOf(dsdondathang.getTongtien()) ));
        holder.txttongtien.setWidth(80);
        holder.txttongtien.setHeight(70);

        holder.txttinhtrang.setText(dsdondathang.getTinhtrang());
        holder.txttinhtrang.setWidth(80);
        holder.txttinhtrang.setHeight(50);


        holder.imgxoaddh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogxoaDDH(holder.txtdoituong.getText().toString(),holder.stt);
            }
        });

        holder.btxemctddhtdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri=i;

                context.Dialogxemctddhtdv(holder.stt);
            }
        });

        return view;
    }
}
