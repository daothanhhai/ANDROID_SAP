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

public class SanPhamThanhNhietGiaiDocAdapter extends BaseAdapter {
    private activity_sanphamthanhnhietgiaidoc context;
    private activity_giohang context1;
    private int layout;
    private List<SanPhamThanhNhietGiaiDoc> sanphamthanhnhietgiaidocList;
    private float gia=0;

    public SanPhamThanhNhietGiaiDocAdapter(activity_sanphamthanhnhietgiaidoc context, int layout, List<SanPhamThanhNhietGiaiDoc> sanphamthanhnhietgiaidocList) {
        this.context = context;
        this.layout = layout;
        this.sanphamthanhnhietgiaidocList = sanphamthanhnhietgiaidocList;

    }

    @Override
    public int getCount() {
        return sanphamthanhnhietgiaidocList.size();
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
        Button buttoncsptngd,buttontrusptngd,buttondhsptngd,buttongiohang;
        TextView txtsoluongsptngd;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        final Viewholder holder;
        if(view==null)
        {
            holder=new Viewholder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view=inflater.inflate(layout,null);

            holder.txttensp=(TextView) view.findViewById(R.id.textviewtensptngd);
            holder.txtmasp=(TextView) view.findViewById(R.id.textViewmasptngd);
            holder.txtgiasp=(TextView) view.findViewById(R.id.textgiasptngd);
            holder.imagesp=(ImageView) view.findViewById(R.id.imageviewsptngd);

            holder.buttoncsptngd=(Button) view.findViewById(R.id.buttoncongsptngd);
            holder.buttontrusptngd=(Button) view.findViewById(R.id.buttontrusptngd);
            holder.buttondhsptngd=(Button) view.findViewById(R.id.buttondhsptngd);
            holder.txtsoluongsptngd=(TextView) view.findViewById(R.id.textViewslsptngd);


            view.setTag(holder);
        }
        else {
            holder=(Viewholder) view.getTag();

        }
        final SanPhamThanhNhietGiaiDoc sanphamthanhnhietgiaidoc=sanphamthanhnhietgiaidocList.get(i);

        holder.txttensp.setText(sanphamthanhnhietgiaidoc.getTensp());
        holder.txtmasp.setText(sanphamthanhnhietgiaidoc.getMasp());

        holder.txtgiasp.setText(String.format("%,.0f", Float.valueOf(sanphamthanhnhietgiaidoc.getGiasp()) ));

        holder.imagesp.setImageBitmap(sanphamthanhnhietgiaidoc.getHinhsp());


        holder.buttoncsptngd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                i= Integer.parseInt(holder.txtsoluongsptngd.getText().toString());
                i=i+1;
                holder.txtsoluongsptngd.setText(String.valueOf(i));
            }
        });

        holder.buttontrusptngd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                i= Integer.parseInt(holder.txtsoluongsptngd.getText().toString());
                i=i-1;
                holder.txtsoluongsptngd.setText(String.valueOf(i));
            }
        });

        holder.buttondhsptngd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String masp=holder.txtmasp.getText().toString();
                String tensp=holder.txttensp.getText().toString();
                int soluong= Integer.parseInt(holder.txtsoluongsptngd.getText().toString());

                holder.txtgiasp.setText(sanphamthanhnhietgiaidoc.getGiasp()); // làm mất dấy phẩy chỗ giá
                gia = Float.parseFloat(holder.txtgiasp.getText().toString()); // lấy giá ko có dấu phẩy
                holder.txtgiasp.setText(String.format("%,.0f", Float.valueOf(sanphamthanhnhietgiaidoc.getGiasp()) )); // để lại dấy phẩy cho giá khi hiển thị trên layout
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
