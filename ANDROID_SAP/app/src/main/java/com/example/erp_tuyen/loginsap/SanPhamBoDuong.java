package com.example.erp_tuyen.loginsap;

import android.graphics.Bitmap;

public class SanPhamBoDuong {
    private String masp;
    private String tensp;
    private String giasp;

    private Bitmap hinhsp;

    public SanPhamBoDuong(String masp, String tensp, String giasp, Bitmap hinhsp) {
        this.masp = masp;
        this.tensp = tensp;
        this.giasp = giasp;
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

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public Bitmap getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(Bitmap hinhsp) {
        this.hinhsp = hinhsp;
    }
}
