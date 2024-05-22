package com.example.erp_tuyen.loginsap;

public class DsChiTietDonDatHang {
    String mavt;
    String tenvt;
    String km;
    String ctkm;
    String giatricode;
    String soluong;
    String gia;
    String thanhtien;

    public DsChiTietDonDatHang(String mavt, String tenvt, String km, String ctkm, String giatricode, String soluong, String gia, String thanhtien) {
        this.mavt = mavt;
        this.tenvt = tenvt;
        this.km = km;
        this.ctkm = ctkm;
        this.giatricode = giatricode;
        this.soluong = soluong;
        this.gia = gia;
        this.thanhtien = thanhtien;
    }

    public String getMavt() {
        return mavt;
    }

    public void setMavt(String mavt) {
        this.mavt = mavt;
    }

    public String getTenvt() {
        return tenvt;
    }

    public void setTenvt(String tenvt) {
        this.tenvt = tenvt;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getCtkm() {
        return ctkm;
    }

    public void setCtkm(String ctkm) {
        this.ctkm = ctkm;
    }

    public String getGiatricode() {
        return giatricode;
    }

    public void setGiatricode(String giatricode) {
        this.giatricode = giatricode;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(String thanhtien) {
        this.thanhtien = thanhtien;
    }
}
