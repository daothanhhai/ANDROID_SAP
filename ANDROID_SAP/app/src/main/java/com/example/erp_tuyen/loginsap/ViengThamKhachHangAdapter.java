package com.example.erp_tuyen.loginsap;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViengThamKhachHangAdapter extends BaseAdapter {

    private activity_viengthamkhachhang context;
    private int layout;
    private List<VienThamKhachHang> viengthamkhachhangList;
    private List<VienThamKhachHang> viengthamkhachhangListAll;




    public ViengThamKhachHangAdapter(activity_viengthamkhachhang context, int layout, List<VienThamKhachHang> viengthamkhachhangList) {
        this.context = context;
        this.layout = layout;
        this.viengthamkhachhangList = viengthamkhachhangList;
        this.viengthamkhachhangListAll = viengthamkhachhangList;
    }

    @Override
    public int getCount() {
        return viengthamkhachhangList.size();
    }

    public Filter getFilter()
    {
        return filter;
    }

    Filter filter=new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filteredList=new FilterResults();
            if(charSequence==null || charSequence.length()==0){
                filteredList.values=viengthamkhachhangListAll;
                filteredList.count=viengthamkhachhangListAll.size();
            }else {

                ArrayList<VienThamKhachHang> khvt =new ArrayList<>();
                for(VienThamKhachHang vt:viengthamkhachhangList){

                    charSequence = VNCharacterUtils.removeAccent(charSequence.toString());
                    String q=VNCharacterUtils.removeAccent(vt.getTenkh().toLowerCase());

                    if(q.toLowerCase(Locale.getDefault()).contains(charSequence)){
                        khvt.add(vt);
                    }
                }
                filteredList.values=khvt;
                filteredList.count=viengthamkhachhangList.size();
            }

            return filteredList;

        }



        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if(filterResults.count==0){
                notifyDataSetInvalidated();
            }else {
                viengthamkhachhangList=(ArrayList<VienThamKhachHang>) filterResults.values;
                notifyDataSetChanged();
            }
        }
    };

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class Viewholder{
        TextView txtmakh,txttenkh;
        TextView txtdiachi,txtdienthoai;

        Button buttondhvt,buttonbcvt,buttoncheckin;
        Button buttonbando;
        ImageView imgltvtkh;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        final Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txtmakh=(TextView) view.findViewById(R.id.tvmkhvt);
            holder.txttenkh=(TextView) view.findViewById(R.id.tvtkhvt);
            holder.txtdiachi=(TextView) view.findViewById(R.id.tvdcvt);
            holder.txtdienthoai=(TextView) view.findViewById(R.id.tvdtvt);

            holder.buttondhvt=(Button) view.findViewById(R.id.buttondhvt);
            holder.buttonbcvt=(Button) view.findViewById(R.id.buttonbcvt);
            holder.buttoncheckin=(Button) view.findViewById(R.id.buttoncheckinvt);
            holder.buttonbando=(Button) view.findViewById(R.id.buttonbando);
            holder.imgltvtkh=(ImageView) view.findViewById(R.id.imglotrinhvtkh);


            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final VienThamKhachHang vienthamkhachhang=viengthamkhachhangList.get(i);

        holder.txtmakh.setText(vienthamkhachhang.getMakh());
        holder.txttenkh.setText(vienthamkhachhang.getTenkh());

        holder.txtdiachi.setText(vienthamkhachhang.getDiachi());
        holder.txtdienthoai.setText(vienthamkhachhang.getDienthoai());


        holder.buttoncheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String madt = holder.txtmakh.getText().toString();
                String tendt=holder.txttenkh.getText().toString();
                String diachi=holder.txtdiachi.getText().toString();
                String dienthoai=holder.txtdienthoai.getText().toString();
                String chietkhau=activity_viengthamkhachhang.chietkhau;
                String id=activity_viengthamkhachhang.id;
                String dvcs=activity_viengthamkhachhang.dvcs;
                String macbnv=activity_viengthamkhachhang.macbnv;
                String chudanh=activity_viengthamkhachhang.chucdanh;
                String username=activity_viengthamkhachhang.username;
                String hoten=activity_viengthamkhachhang.hoten;

                Intent intent=new Intent(context,activity_checkinviengthamkhachhang.class);
                intent.putExtra("MADT",madt);
                intent.putExtra("TENDT",tendt);
                intent.putExtra("DIACHI",diachi);
                intent.putExtra("DIENTHOAI",dienthoai);
                intent.putExtra("CHIETKHAU",chietkhau);
                intent.putExtra("ID",id);
                intent.putExtra("Ma_DvCs",dvcs);
                intent.putExtra("Ma_CbNv",macbnv);
                intent.putExtra("USERNAME",username);
                intent.putExtra("HOTEN",hoten);

                context.startActivity(intent);

            }
        });

        holder.buttonbcvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String madt = holder.txtmakh.getText().toString();

                Intent intent=new Intent(context,activity_donhangtheodoituongviengtham.class);
                intent.putExtra("MADT",madt);
                context.startActivity(intent);
            }
        });


        holder.buttonbando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,activity_googlemap.class);
                //intent.putExtra("MADT",madt);
                context.startActivity(intent);
            }
        });

        holder.imgltvtkh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,activity_lotrinhviengthamkhachhang.class);
                //intent.putExtra("MADT",madt);
                context.startActivity(intent);
            }
        });

        return view;



    }

}
