package com.example.erp_tuyen.loginsap;

public class DsBKNopTienGH {
    private String stt,ngaybk,nguoilap,tongtien;

    public DsBKNopTienGH(String stt, String ngaybk, String nguoilap, String tongtien) {
        this.stt = stt;
        this.ngaybk = ngaybk;
        this.nguoilap = nguoilap;
        this.tongtien = tongtien;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getNgaybk() {
        return ngaybk;
    }

    public void setNgaybk(String ngaybk) {
        this.ngaybk = ngaybk;
    }

    public String getNguoilap() {
        return nguoilap;
    }

    public void setNguoilap(String nguoilap) {
        this.nguoilap = nguoilap;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }
}
