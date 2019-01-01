/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author STIREN
 */
public class CongViec {
    private String MaCV;
    private String TenCV;
    private int DonGia;

    public CongViec() {
        this.MaCV = "";
        this.TenCV = "";
        this.DonGia = 0;
    }

    public String getMaCV() {
        return MaCV;
    }

    public String getTenCV() {
        return TenCV;
    }

    public int getDonGia() {
        return DonGia;
    }

    public void setMaCV(String MaCV) {
        this.MaCV = MaCV;
    }

    public void setTenCV(String TenCV) {
        this.TenCV = TenCV;
    }

    public void setDonGia(int DonGia) {
        this.DonGia = DonGia;
    }   
}
