/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Process.ProcessDataInterface;
import Process.ProcessHelpers;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author fadwa
 */
public class GUI extends javax.swing.JFrame {

    private ProcessHelpers processHelpers = new ProcessHelpers();
    ArrayList<ProcessDataInterface> stubs = processHelpers.getStubs();
    int noOfProcesses = processHelpers.getNoOfProcesses();
    Timer timer = new Timer();
    TimerTask timerTask;

    public GUI() {
        initComponents();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                displayMailbox(stubs);
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 2000);
    }

    private void initComponents() {

        mailboxScrollPane = new javax.swing.JScrollPane();
        mailboxTextArea = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        raise = new javax.swing.JButton();
        kill = new javax.swing.JButton();
        selectProcess = new javax.swing.JComboBox<>();
        processesLabel = new javax.swing.JLabel();
        mailboxLabel = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        selectMailbox = new javax.swing.JLabel();

        mailboxTextArea.setColumns(20);
        mailboxTextArea.setRows(5);
        mailboxScrollPane.setViewportView(mailboxTextArea);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        raise.setText("Raise");
        raise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                raiseActionPerformed(evt);
            }
        });

        kill.setText("Kill");
        kill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                killActionPerformed(evt);
            }
        });

        selectProcess.setModel(new javax.swing.DefaultComboBoxModel<>());
        selectProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectProcessActionPerformed(evt);
            }
        });

        processesLabel.setText("Number of Processes:");

        mailboxLabel.setText("Mailbox:");

        title.setText("BULLY ALGORITHM");
        title.setAlignmentX(0.5F);

        selectMailbox.setText("select mailbox");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(mailboxScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(mailboxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(raise, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(processesLabel))
                                                .addGap(51, 51, 51)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(kill, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(selectMailbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(selectProcess, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(145, 145, 145)
                                                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(selectProcess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(processesLabel))
                                        .addComponent(selectMailbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(raise, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(kill, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(mailboxLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mailboxScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void raiseActionPerformed(java.awt.event.ActionEvent evt) {
        processHelpers.raiseProcess();
        noOfProcesses = processHelpers.getNoOfProcesses();
        processesLabel.setText("Number of processes alive: " + Integer.toString(noOfProcesses));
        setProcessesListField(processHelpers.getIds());
    }

    private void killActionPerformed(java.awt.event.ActionEvent evt) {
        processHelpers.killProcess();
        noOfProcesses = processHelpers.getNoOfProcesses();
        processesLabel.setText("Number of processes alive: " + Integer.toString(noOfProcesses));
        setProcessesListField(processHelpers.getIds());

    }

    private void selectProcessActionPerformed(java.awt.event.ActionEvent evt) {

        javax.swing.JComboBox<?> is = (javax.swing.JComboBox<?>) evt.getSource();
        displayMailbox(stubs);
    }

    public void displayMailbox(ArrayList<ProcessDataInterface> stubs) {
        try {

            if (selectProcess.getSelectedItem() != null) {
                int id = (Integer) selectProcess.getSelectedItem();
                String mailbox = processHelpers.getStubs().get(id).getMailboxToString();
                mailboxTextArea.setText(mailbox);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    void setProcessesListField(int[] ids) {
        selectProcess.removeAllItems();
        for (int i = 0; i < ids.length; i++) {
            selectProcess.addItem(ids[i]);
        }
    }
    // Variables declaration - do not modify                     
    private javax.swing.JButton raise;
    private javax.swing.JButton kill;
    private javax.swing.JComboBox<Integer> selectProcess;
    private javax.swing.JLabel processesLabel;
    private javax.swing.JLabel mailboxLabel;
    private javax.swing.JLabel title;
    private javax.swing.JLabel selectMailbox;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane mailboxScrollPane;
    private javax.swing.JTextArea mailboxTextArea;
    // End of variables declaration  

}
