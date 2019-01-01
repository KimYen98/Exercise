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
public class KhachHang {
    private String MaKH;
    private String HotenKH;
    private String DiaChi;
    private String DienThoai;

    public KhachHang() {
        this.MaKH = "";
        this.HotenKH = "";
        this.DiaChi = "";
        this.DienThoai = "";
    }

    public String getMaKH() {
        return MaKH;
    }

    public String getHotenKH() {
        return HotenKH;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public void setHotenKH(String HotenKH) {
        this.HotenKH = HotenKH;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public void setDienThoai(String DienThoai) {
        this.DienThoai = DienThoai;
    }
    public String toString()
    {
        return this.HotenKH;
    }
}
