package com.example.erp_tuyen.loginsap;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DonDatHangTDVAdapter extends BaseAdapter {

    private activity_dondathangtdv context;

    private int layout;
    private List<DonDatHangTDV> dondathangtdvList;

    public DonDatHangTDVAdapter(activity_dondathangtdv context, int layout, List<DonDatHangTDV> dondathangtdvList) {
        this.context = context;
        this.layout = layout;
        this.dondathangtdvList = dondathangtdvList;
    }

    @Override
    public int getCount() {
        return dondathangtdvList.size();
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
        TextView txtmavt;
        TextView txttenvt;
        TextView txtkm;
        TextView txtctkm;
        TextView txtgtc;
        EditText edsl;

        TextView txtgia;
        TextView txtgiagoc;

        TextView txttt;

        Button btsua;
        Button btxoa;

    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {

        final Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);


            holder.txttenvt=(TextView) view.findViewById(R.id.tvtvtddhtdv);
            holder.txtkm=(TextView) view.findViewById(R.id.tvkmddhtdv);
            holder.txtctkm=(TextView) view.findViewById(R.id.tvctkmddhtdv);
            holder.txtgtc=(TextView) view.findViewById(R.id.tvgtcddhtdv);
            holder.edsl=(EditText) view.findViewById(R.id.edslddhtdv);

            holder.txtgia=(TextView) view.findViewById(R.id.tvdgddhtdv);



            holder.txttt=(TextView) view.findViewById(R.id.tvttddhtdv);
            holder.btxoa=(Button) view.findViewById(R.id.btxoadhtdv);
            holder.btsua=(Button) view.findViewById(R.id.btsuadhtdv);

            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final DonDatHangTDV donDatHangtdv=dondathangtdvList.get(i);

        holder.txttenvt.setText(donDatHangtdv.getTenvt());
        holder.txttenvt.setWidth(220);
        holder.txttenvt.setHeight(80);



        holder.txtkm.setText(donDatHangtdv.getKm());
        holder.txtkm.setWidth(10);
        holder.txtkm.setHeight(80);
        holder.txtkm.setGravity(Gravity.CENTER);


        holder.txtctkm.setText(donDatHangtdv.getCtkm());
        holder.txtctkm.setWidth(20);
        holder.txtctkm.setHeight(80);
        holder.txtctkm.setGravity(Gravity.CENTER);


        holder.txtgtc.setText(donDatHangtdv.getGiatricode());
        holder.txtgtc.setWidth(70);
        holder.txtgtc.setHeight(80);
        holder.txtgtc.setGravity(Gravity.CENTER);


        //holder.edsl.setText(donDatHangtdv.getSoluong());
        holder.edsl.setText(String.format("%,.0f", Float.valueOf(donDatHangtdv.getSoluong()) ));
        holder.edsl.setWidth(50);
        holder.edsl.setHeight(60);

        holder.txtgia.setText(donDatHangtdv.getGia());
        holder.txtgia.setWidth(70);
        holder.txtgia.setHeight(80);


        holder.txttt.setText(donDatHangtdv.getThanhtien());
        holder.txttt.setWidth(70);
        holder.txttt.setHeight(80);


        holder.btxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int vitri=i;
                Toast.makeText(context, "vị trí" + vitri, Toast.LENGTH_SHORT).show();

                String mavt=holder.txttenvt.getText().toString();
                context.DialogxoaDDH(vitri,mavt);
            }
        });

        holder.btsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri=i;
                String mavt=holder.txttenvt.getText().toString();
                String km=holder.txtkm.getText().toString();
                String ctkm=holder.txtctkm.getText().toString();
                String gtc=holder.txtgtc.getText().toString();
                String soluong=holder.edsl.getText().toString();
                String gia=holder.txtgia.getText().toString();

                String tt=holder.txttt.getText().toString();
                context.DialogsuaDDH(vitri,mavt,km,ctkm,gtc,soluong,gia,tt);


            }
        });



        return view;
    }
}
