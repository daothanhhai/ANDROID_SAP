package com.example.erp_tuyen.loginsap;

public class TopSanPham {
    private String TenVt,TopSp,Tyle;

    public TopSanPham(String tenVt, String topSp,String tyle) {
        TenVt = tenVt;
        TopSp = topSp;
        Tyle = tyle;
    }

    public String getTenVt() {
        return TenVt;
    }

    public void setTenVt(String tenVt) {
        TenVt = tenVt;
    }

    public String getTopSp() {
        return TopSp;
    }

    public void setTopSp(String topSp) {
        TopSp = topSp;
    }

    public String getTyle() {
        return Tyle;
    }

    public void setTyle(String tyle) {
        Tyle = tyle;
    }
}
