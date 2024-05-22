package com.example.erp_tuyen.loginsap;

public class DsDonDatHang {
    private String stt,ngayct,madt,tendt,tongtien,tinhtrang;

    public DsDonDatHang(String stt,String ngayct, String tendt, String tongtien,String tinhtrang) {
        this.stt=stt;
        this.ngayct = ngayct;
        this.madt = madt;
        this.tendt = tendt;
        this.tongtien = tongtien;
        this.tinhtrang=tinhtrang;
    }


    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getNgayct() {
        return ngayct;
    }

    public void setNgayct(String ngayct) {
        this.ngayct = ngayct;
    }

    public String getMadt() {
        return madt;
    }

    public void setMadt(String madt) {
        this.madt = madt;
    }

    public String getTendt() {
        return tendt;
    }

    public void setTendt(String tendt) {
        this.tendt = tendt;
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
