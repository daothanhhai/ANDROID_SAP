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

public class SanPhamDieuKinhAdapter extends BaseAdapter {

    private activity_sanphamdieukinh context;
    private activity_giohang context1;
    private int layout;
    private List<SanPhamDieuKinh> sanphamdieukinhList;
    private float gia=0;

    public SanPhamDieuKinhAdapter(activity_sanphamdieukinh context, int layout, List<SanPhamDieuKinh> sanphamdieukinhList) {
        this.context = context;
        this.layout = layout;
        this.sanphamdieukinhList = sanphamdieukinhList;

    }

    @Override
    public int getCount() {
        return sanphamdieukinhList.size();
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
        Button buttoncspdk,buttontruspdk,buttondhspdk,buttongiohang;
        TextView txtsoluongspdk;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        final Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txttensp=(TextView) view.findViewById(R.id.textviewtenspdk);
            holder.txtmasp=(TextView) view.findViewById(R.id.textViewmaspdk);
            holder.txtgiasp=(TextView) view.findViewById(R.id.textgiaspdk);
            holder.imagesp=(ImageView) view.findViewById(R.id.imageviewspdk);

            holder.buttoncspdk=(Button) view.findViewById(R.id.buttoncongspdk);
            holder.buttontruspdk=(Button) view.findViewById(R.id.buttontruspdk);
            holder.buttondhspdk=(Button) view.findViewById(R.id.buttondhspdk);
            holder.txtsoluongspdk=(TextView) view.findViewById(R.id.textViewslspdk);


            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final SanPhamDieuKinh sanphamdieukinh=sanphamdieukinhList.get(i);

        holder.txttensp.setText(sanphamdieukinh.getTensp());
        holder.txtmasp.setText(sanphamdieukinh.getMasp());

        holder.txtgiasp.setText(String.format("%,.0f", Float.valueOf(sanphamdieukinh.getGiasp()) ));

        holder.imagesp.setImageBitmap(sanphamdieukinh.getHinhsp());


        holder.buttoncspdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                i= Integer.parseInt(holder.txtsoluongspdk.getText().toString());
                i=i+1;
                holder.txtsoluongspdk.setText(String.valueOf(i));
            }
        });

        holder.buttontruspdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                i= Integer.parseInt(holder.txtsoluongspdk.getText().toString());
                i=i-1;
                holder.txtsoluongspdk.setText(String.valueOf(i));
            }
        });

        holder.buttondhspdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String masp=holder.txtmasp.getText().toString();
                String tensp=holder.txttensp.getText().toString();
                int soluong= Integer.parseInt(holder.txtsoluongspdk.getText().toString());

                holder.txtgiasp.setText(sanphamdieukinh.getGiasp()); // làm mất dấy phẩy chỗ giá
                gia = Float.parseFloat(holder.txtgiasp.getText().toString()); // lấy giá ko có dấu phẩy
                holder.txtgiasp.setText(String.format("%,.0f", Float.valueOf(sanphamdieukinh.getGiasp()) )); // để lại dấy phẩy cho giá khi hiển thị trên layout
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
