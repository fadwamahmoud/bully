/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Process.ProcessHelpers;
import Process.ProcessData;
import java.util.ArrayList;
/**
 *
 * @author fadwa
 */
public class GUI{
    private JLabel label;
    private JPanel panel;
    private JButton button;
    private JButton startProcess;
    private JButton killProcess;
    private JFrame frame;
    private ProcessHelpers processHelpers = new ProcessHelpers(); 
    int noOfProcesses = processHelpers.getNoOfProcesses();
        public GUI(){
            
           System.out.println(System.getProperty("user.dir"));


            frame = new JFrame();
            
           
            
            panel = new JPanel();
            
            panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
            // rows and cols
            panel.setLayout(new GridLayout(0 ,1));
//            panel.add(button);
            
            
            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("BULLY");
            frame.pack();
            frame.setVisible(true);
            
            
            
            //no of processes
            
            label = new JLabel("no of processes alive: " + noOfProcesses );
            panel.add(label);
            
            
            // start processes
            ActionListener actionListener = new ActionListener(){
              @Override
               public void actionPerformed(ActionEvent arg0) {
                   processHelpers.raiseProcess();
                   noOfProcesses = processHelpers.getNoOfProcesses();
                   label.setText(Integer.toString(noOfProcesses));
              }
            };
            startProcess = new JButton("start");
            panel.add(startProcess);
            startProcess.addActionListener(actionListener);
            
          
            
            
             // kill processes
            killProcess = new JButton("kill");
            panel.add(killProcess);
            killProcess.addActionListener(new ActionListener(){
                @Override
                 public void actionPerformed(ActionEvent arg0) {
                     processHelpers.killProcess();
                     noOfProcesses = processHelpers.getNoOfProcesses();
                     label.setText(Integer.toString(noOfProcesses));
                }
            });
            
            
            //current coordinator
            
            //logs
           
            
            
        }
        public static void main(String[] args){
            new GUI();
            
        }
        
    
}
