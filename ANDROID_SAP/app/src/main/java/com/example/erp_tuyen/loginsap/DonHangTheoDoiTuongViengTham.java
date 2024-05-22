package com.example.erp_tuyen.loginsap;

public class DonHangTheoDoiTuongViengTham {
    String soct;
    String ngayct;
    String tongtien;
    String tinhtrang;

    public DonHangTheoDoiTuongViengTham(String soct, String ngayct,String tongtien,String tinhtrang) {
        this.soct = soct;
        this.ngayct = ngayct;
        this.tinhtrang = tinhtrang;

        this.tongtien = tongtien;
    }

    public String getSoct() {
        return soct;
    }

    public void setSoct(String soct) {
        this.soct = soct;
    }

    public String getNgayct() {
        return ngayct;
    }

    public void setNgayct(String ngayct) {
        this.ngayct = ngayct;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }
}
