package com.example.erp_tuyen.loginsap;

import android.graphics.Bitmap;

public class GioHang {
    private String masp;
    private String tensp;
    private Float giasp;
    private int soluong;
    private Bitmap hinhsp;

    public GioHang(String masp, String tensp, Float giasp, int soluong, Bitmap hinhsp) {
        this.masp = masp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.soluong = soluong;
        this.hinhsp = hinhsp;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Float getGiasp() {
        return giasp;
    }

    public void setGiasp(Float giasp) {
        this.giasp = giasp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public Bitmap getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(Bitmap hinhsp) {
        this.hinhsp = hinhsp;
    }
}
