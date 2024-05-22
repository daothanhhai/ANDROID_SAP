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

public class SanPhamThanKinhCoAdapter extends BaseAdapter {
    private activity_sanphamthankinhco context;
    private activity_giohang context1;
    private int layout;
    private List<SanPhamThanKinhCo> sanphamthankinhcoList;
    private float gia=0;
    public SanPhamThanKinhCoAdapter(activity_sanphamthankinhco context, int layout, List<SanPhamThanKinhCo> sanphamthankinhcoList) {
        this.context = context;
        this.layout = layout;
        this.sanphamthankinhcoList = sanphamthankinhcoList;
    }
    @Override
    public int getCount() {
        return sanphamthankinhcoList.size();
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
        Button buttoncsptkc,buttontrusptkc,buttondhsptkc,buttongiohang;
        TextView txtsoluongsptkc;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        final Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txttensp=(TextView) view.findViewById(R.id.textviewtensptkc);
            holder.txtmasp=(TextView) view.findViewById(R.id.textViewmasptkc);
            holder.txtgiasp=(TextView) view.findViewById(R.id.textgiasptkc);
            holder.imagesp=(ImageView) view.findViewById(R.id.imageviewsptkc);

            holder.buttoncsptkc=(Button) view.findViewById(R.id.buttoncongsptkc);
            holder.buttontrusptkc=(Button) view.findViewById(R.id.buttontrusptkc);
            holder.buttondhsptkc=(Button) view.findViewById(R.id.buttondhsptkc);
            holder.txtsoluongsptkc=(TextView) view.findViewById(R.id.textViewslsptkc);


            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final SanPhamThanKinhCo sanphamthankinhco=sanphamthankinhcoList.get(i);

        holder.txttensp.setText(sanphamthankinhco.getTensp());
        holder.txtmasp.setText(sanphamthankinhco.getMasp());

        holder.txtgiasp.setText(String.format("%,.0f", Float.valueOf(sanphamthankinhco.getGiasp()) ));

        holder.imagesp.setImageBitmap(sanphamthankinhco.getHinhsp());


        holder.buttoncsptkc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                i= Integer.parseInt(holder.txtsoluongsptkc.getText().toString());
                i=i+1;
                holder.txtsoluongsptkc.setText(String.valueOf(i));
            }
        });

        holder.buttontrusptkc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                i= Integer.parseInt(holder.txtsoluongsptkc.getText().toString());
                i=i-1;
                holder.txtsoluongsptkc.setText(String.valueOf(i));
            }
        });

        holder.buttondhsptkc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String masp=holder.txtmasp.getText().toString();
                String tensp=holder.txttensp.getText().toString();
                int soluong= Integer.parseInt(holder.txtsoluongsptkc.getText().toString());

                holder.txtgiasp.setText(sanphamthankinhco.getGiasp()); // làm mất dấy phẩy chỗ giá
                gia = Float.parseFloat(holder.txtgiasp.getText().toString()); // lấy giá ko có dấu phẩy
                holder.txtgiasp.setText(String.format("%,.0f", Float.valueOf(sanphamthankinhco.getGiasp()) )); // để lại dấy phẩy cho giá khi hiển thị trên layout
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
