/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ck_17_18;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author STIREN
 */
public class ButtonLocationDemo extends JFrame{
    public ButtonLocationDemo(){
      JPanel p = new JPanel();
      JButton button = new JButton("Button");
      p.setLayout(null);
      button.setBounds(40,100,100,60);
      p.add(button);

      getContentPane().add(p);
      //setLayout(null);
      setDefaultCloseOperation(3);
      setSize(800,500);
      setVisible(true);
    }
    
}
