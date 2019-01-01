/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck_17_18;

import Connect.DataAccessHelper;
import Entity.BaoDuong;
import Entity.CongViec;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author STIREN
 */
public class d extends javax.swing.JFrame {

    /**
     * Creates new form d
     */
    DataAccessHelper d = new DataAccessHelper();
    
    //Lấy số xe trong ngày
    public ArrayList<BaoDuong> LayDSSoXeTrongNgay()
    {
        String SQL = "SELECT DISTINCT SOXE FROM BAODUONG WHERE DAY(NGAYGIONHAN) = DAY(GETDATE()) AND MONTH(NGAYGIONHAN) = MONTH(GETDATE()) AND YEAR(NGAYGIONHAN) = YEAR(GETDATE())";

        ArrayList<BaoDuong> temp = new ArrayList<BaoDuong>();
        try{
            d.getConnect();
            PreparedStatement ps = d.getConn().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            if(rs!=null)
                while(rs.next()){
                    BaoDuong BD = new BaoDuong();
                    BD.setSoXe(rs.getString("SOXE"));
                    temp.add(BD);
                }
            d.getClose();
        }catch(Exception e){
            e.printStackTrace();
        }
        return temp;
    }
    //Lấy tên công việc     
    public ArrayList<CongViec> LayDSTenCV()
    {
        String SQL = "SELECT TENCV FROM CONGVIEC";
        ArrayList<CongViec> temp = new ArrayList<CongViec>();
        try{
            d.getConnect();
            PreparedStatement ps = d.getConn().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            if(rs!=null)
                while(rs.next()){
                    CongViec CV = new CongViec();
                    CV.setTenCV(rs.getString("TENCV"));
                    temp.add(CV);
                }
            d.getClose();
        }catch(Exception e){
            e.printStackTrace();
        }
        return temp;
    }
    
    //Lấy giá theo tên công việc
    public int LayGiaTheoTenCV(String TenCV)
    {
        String SQL = "SELECT DONGIA FROM CONGVIEC WHERE TENCV = N'" + TenCV + "'";
        try {
            d.getConnect();
            Statement st = d.getConn().createStatement();
            ResultSet rs =st.executeQuery(SQL);
            if(rs!=null && rs.next() )
                return rs.getInt("DONGIA");
            d.getClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    //Lấy mã bảo dưỡng theo số xe
    public String LayMaBDTheoSoXe(String SoXe)
    {
        String SQL = "SELECT MABD FROM BAODUONG WHERE SOXE = '" + SoXe + "' AND DAY(NGAYGIONHAN) = DAY(GETDATE()) AND MONTH(NGAYGIONHAN) = MONTH(GETDATE()) AND YEAR(NGAYGIONHAN) = YEAR(GETDATE())";
        try {
            d.getConnect();
            Statement st = d.getConn().createStatement();
            ResultSet rs =st.executeQuery(SQL);
            if(rs!=null && rs.next() )
                return rs.getString("MABD");
            d.getClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    //lấy mã công việc theo tên công việc
    public String LayMaCVTheoTenCV(String TenCV)
    {
        String SQL = "SELECT MACV FROM CONGVIEC WHERE TENCV = N'" + TenCV + "'";
        try {
            d.getConnect();
            Statement st = d.getConn().createStatement();
            ResultSet rs =st.executeQuery(SQL);
            if(rs!=null && rs.next() )
                return rs.getString("MACV");
            d.getClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    //Thêm chi tiết bảo dưỡng
    public boolean ThemCTBD(String MaBD, String MaCV)
    {
        String SQL = "INSERT INTO CT_BD VALUES ('" + MaBD + "', '" + MaCV + "')";
        try {
            d.getConnect();
            Statement st = d.getConn().createStatement();
            int rs = st.executeUpdate(SQL);
            if(rs>0)
                return true;
            d.getClose();
        } catch (Exception e) {
        }
        return false;
    }
    
    public d() {
        initComponents();
        cbTenCV.removeAllItems();
        cbSoXe.removeAllItems();
        for(CongViec CV : LayDSTenCV())
        {
            cbTenCV.addItem(CV.getTenCV());
        }
        for(BaoDuong BD : LayDSSoXeTrongNgay())
            cbSoXe.addItem(BD.getSoXe());
        
        cbTenCV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                String TenCV = cbTenCV.getSelectedItem().toString();
                
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.addRow(new Object[]{TenCV, LayGiaTheoTenCV(TenCV)});
            }
        });
        
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                int row = jTable1.getRowCount();
                int column = jTable1.getColumnCount();
                
                for(int i=0; i<row; i++)
                {
                    String MaBD = LayMaBDTheoSoXe(cbSoXe.getSelectedItem().toString());
                    String MaCV = LayMaCVTheoTenCV(jTable1.getValueAt(i, 0).toString());
                    if(ThemCTBD(MaBD, MaCV) == false)
                    {
                        JOptionPane.showMessageDialog(rootPane, "Thêm thất bại");
                        break;
                    }
                }
                //JOptionPane.showMessageDialog(rootPane, "Thêm thành công");
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbSoXe = new javax.swing.JComboBox<>();
        cbTenCV = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Số xe");

        jLabel2.setText("Tên công việc");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên công việc", "Đơn giá"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnThem.setText("THÊM");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(cbSoXe, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(109, 109, 109)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cbTenCV, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(149, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(cbSoXe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTenCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(btnThem)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(d.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(d.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(d.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(d.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new d().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThem;
    private javax.swing.JComboBox<String> cbSoXe;
    private javax.swing.JComboBox<String> cbTenCV;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
