/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck_17_18;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author STIREN
 */
public class CK_17_18 extends JFrame{

    /**
     * @param args the command line arguments
     */
    public CK_17_18() {

        JPanel pnFlow = new JPanel();
        pnFlow.setLayout(new FlowLayout());
        
        JButton btn1 = new JButton("a");
        JButton btn2 = new JButton("b");
        JButton btn3 = new JButton("c");
        JButton btn4 = new JButton("d");
        JButton btn5 = new JButton("e");

        pnFlow.add(btn1);
        pnFlow.add(btn2);
        pnFlow.add(btn3);
        pnFlow.add(btn4);
        pnFlow.add(btn5);
        
        Container con = getContentPane();
        con.add(pnFlow);
        
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                a a = new a();               
                //a.setVisible(true);
            }
        });
        
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                b b = new b();
                b.setVisible(true);
            }
        });
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                c c = new c();
                c.setVisible(true);
            }
        });
        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                d d = new d();
                d.setVisible(true);
            }
        });
        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent x) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                e e = new e();
                e.setVisible(true);
            }
        });
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        CK_17_18 fMain = new CK_17_18();
        fMain.setSize(600, 100);
        fMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fMain.setLocationRelativeTo(null);
        fMain.setVisible(true);
        
        
    }
    
}
