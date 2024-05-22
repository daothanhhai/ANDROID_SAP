package com.example.erp_tuyen.loginsap;

public class DoanhThuTheoDoiTuong {
    private String madt,tendt,doanhthu,tyle;

    public DoanhThuTheoDoiTuong(String madt, String tendt, String doanhthu, String tyle) {
        this.madt = madt;
        this.tendt = tendt;
        this.doanhthu = doanhthu;
        this.tyle = tyle;
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

    public String getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(String doanhthu) {
        this.doanhthu = doanhthu;
    }

    public String getTyle() {
        return tyle;
    }

    public void setTyle(String tyle) {
        this.tyle = tyle;
    }
}
