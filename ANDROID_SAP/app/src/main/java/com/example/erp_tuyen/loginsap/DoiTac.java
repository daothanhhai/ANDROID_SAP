package com.example.erp_tuyen.loginsap;

public class DoiTac {
    private String makh;
    private String tenkh;
    private String madt;
    private String tendt;
    private String diachi;
    private String dienthoai;
    private String ngaysinh;

    public DoiTac(String makh, String tenkh, String madt, String tendt, String diachi, String dienthoai, String ngaysinh) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.madt = madt;
        this.tendt = tendt;
        this.diachi = diachi;
        this.dienthoai = dienthoai;
        this.ngaysinh = ngaysinh;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
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

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }
}
