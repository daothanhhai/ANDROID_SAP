package com.example.erp_tuyen.loginsap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SanPhamMoiAdapter extends BaseAdapter {
    private activity_sanphammoi context;
    private activity_giohang context1;
    private int layout;
    private List<SanPhamMoi> sanphammoiList;
    private float gia=0;

    public SanPhamMoiAdapter(activity_sanphammoi context, int layout, List<SanPhamMoi> sanphammoiList) {
        this.context = context;
        this.layout = layout;
        this.sanphammoiList = sanphammoiList;
    }

    @Override
    public int getCount() {
        return sanphammoiList.size();
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
        TextView txtmasp,txttensp;
        TextView txtgiasp;
        ImageView imagesp;
        Button buttoncspm,buttontruspm,buttondhspm,buttongiohang;
        TextView txtsoluongspm;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        final Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txttensp=(TextView) view.findViewById(R.id.textviewtenspm);
            holder.txtmasp=(TextView) view.findViewById(R.id.textViewmaspm);
            holder.txtgiasp=(TextView) view.findViewById(R.id.textgiaspm);
            holder.imagesp=(ImageView) view.findViewById(R.id.imageviewspm);

            holder.buttoncspm=(Button) view.findViewById(R.id.buttoncongspm);
            holder.buttontruspm=(Button) view.findViewById(R.id.buttontruspm);
            holder.buttondhspm=(Button) view.findViewById(R.id.buttondhspm);
            holder.txtsoluongspm=(TextView) view.findViewById(R.id.textViewslspm);



            //holder.buttongiohang=(Button) view.findViewById(R.id.buttongiohang);

            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final SanPhamMoi sanphammoi=sanphammoiList.get(i);

        holder.txttensp.setText(sanphammoi.getTensp());
        holder.txtmasp.setText(sanphammoi.getMasp());
        holder.txtgiasp.setText(String.format("%,.0f", Float.valueOf(sanphammoi.getGiasp()) ));

        holder.imagesp.setImageBitmap(sanphammoi.getHinhsp());


        holder.buttoncspm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                i= Integer.parseInt(holder.txtsoluongspm.getText().toString());
                i=i+1;
                holder.txtsoluongspm.setText(String.valueOf(i));
            }
        });

        holder.buttontruspm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                i= Integer.parseInt(holder.txtsoluongspm.getText().toString());
                i=i-1;
                holder.txtsoluongspm.setText(String.valueOf(i));
            }
        });

        holder.buttondhspm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String masp=holder.txtmasp.getText().toString();
                    String tensp=holder.txttensp.getText().toString();
                    int soluong= Integer.parseInt(holder.txtsoluongspm.getText().toString());

                    holder.txtgiasp.setText(sanphammoi.getGiasp());
                    gia = Float.parseFloat(holder.txtgiasp.getText().toString());
                    holder.txtgiasp.setText(String.format("%,.0f", Float.valueOf(sanphammoi.getGiasp()) ));

                     float giagoc=gia;

                     float giamoi=soluong * giagoc;


                BitmapDrawable bitmapDrawable=(BitmapDrawable) holder.imagesp.getDrawable();
                Bitmap bitmap= bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);

                    if (activity_giohang.arrayGioHang !=null)
                    {
                        boolean exists=false;

                        for(int i=0; i <activity_giohang.arrayGioHang.size();i++)
                        {
                            if(activity_giohang.arrayGioHang.get(i).getMasp()==masp){
                                activity_giohang.arrayGioHang.get(i).setSoluong(activity_giohang.arrayGioHang.get(i).getSoluong() + soluong);
                                activity_giohang.arrayGioHang.get(i).setGiasp(activity_giohang.arrayGioHang.get(i).getGiasp() + giamoi);
                                exists=true;
                            }

                        }
                        if(exists==false)
                        {
                            activity_giohang.arrayGioHang.add(new GioHang(masp, tensp, giamoi, soluong, bitmap));
                        }
                    }
                    else {

                        activity_giohang.arrayGioHang = new ArrayList<>();
                        activity_giohang.arrayGioHang.add(new GioHang(masp, tensp, giamoi, soluong, bitmap));
                    }
                Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

            }
        });

        return view;


    }


}
