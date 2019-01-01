/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck_17_18;

import Connect.DataAccessHelper;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jdk.nashorn.internal.codegen.CompilerConstants;


/**
 *
 * @author STIREN
 */
public class a extends DataAccessHelper{
    //DataAccessHelper d = new DataAccessHelper();
//    public boolean ThemKhachHang(String MaKH, String HotenKH, String DiaChi, String DienThoai)
//    {
//        String SQL = "INSERT INTO KHACHHANG VALUES ('" + MaKH + "', N'" + HotenKH + "', N'" + DiaChi + "', '" + DienThoai + "')";
//        try {
//            getConnect();
//            Statement st = conn.createStatement();
//            int rs = st.executeUpdate(SQL);
//            if(rs>0)
//                return true;
//            getClose();
//        } catch (Exception e) {
//        }
//        return false;
//    }
    
    public a() {
        JFrame f = new JFrame();
        JPanel p = new JPanel();
        p.setLayout(null);
        f.getContentPane().add(p);
        
        JButton btnThem = new JButton("Thêm");       
        JLabel lbMaKH = new JLabel("Mã khách hàng");
        JLabel lbHotenKH = new JLabel("Họ tên khách hàng");
        JLabel lbDC = new JLabel("Địa chỉ");
        JLabel lbDT = new JLabel("Điện thoại");
        JTextField txfMaKH = new JTextField();
        JTextField txfHotenKH = new JTextField();
        JTextField txfDC = new JTextField();
        JTextField txfDT = new JTextField();
        
        // đặt vị trí và kích thước
        btnThem.setBounds(200,200,100,50);
        lbMaKH.setBounds(20, 10, 200, 50);
        lbHotenKH.setBounds(20, 50, 200, 50);
        lbDC.setBounds(20, 90, 200, 50);
        lbDT.setBounds(20, 130, 200, 50);        
        txfMaKH.setBounds(150, 10, 200, 30);
        txfHotenKH.setBounds(150, 50, 200, 30);
        txfDC.setBounds(150, 90, 200, 30);
        txfDT.setBounds(150, 130, 200, 30);

        p.add(btnThem);
        p.add(lbMaKH);
        p.add(lbHotenKH);
        p.add(lbDC);
        p.add(lbDT);
        p.add(txfMaKH);
        p.add(txfHotenKH);
        p.add(txfDC);
        p.add(txfDT);
                
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                
                String MaKH = txfMaKH.getText();
                String HotenKH = txfHotenKH.getText();
                String DiaChi = txfDC.getText();
                String DienThoai = txfDT.getText();
                
                
                String SQL = "INSERT INTO KHACHHANG VALUES ('" + MaKH + "', N'" + HotenKH + "', N'" + DiaChi + "', '" + DienThoai + "')";                
                
                try {  
                    getConnect();
                    int st = conn.createStatement().executeUpdate(SQL);
                } catch (SQLException ex) {
                    Logger.getLogger(a.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        
        f.setSize(800,500);
        f.setVisible(true);
    }
    public static void main(String...args){
       new a();
       
       }
}
