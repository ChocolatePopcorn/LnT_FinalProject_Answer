package FinalProject.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import FinalProject.Util.User;
import FinalProject.config.DBConnection;

public class Home extends JFrame implements ActionListener {

    Vector<User> user;
    DBConnection dbConnection;

    JPanel topPanel, midPanel, botPanel,
            insertBtnPanel, viewBtnPanel, updateBtnPanel, deleteBtnPanel;
    JButton insertBtn, viewBtn, updateBtn, deleteBtn, exitBtn;
    JLabel title;
    

    public Home(Vector<User> user, DBConnection dbConnection) {
        
        //populate vector
        this.user = user;
        this.dbConnection = dbConnection;

        Dimension dimensionSize = new Dimension(130, 30);

        /*top panel*/
        topPanel = new JPanel();
        topPanel.setBorder(new EmptyBorder(20, 0, 30, 0));

        title = new JLabel("PT Pudding Menu");
        title.setFont(new Font("", Font.BOLD, 18));
        
        topPanel.add(title);


        /*mid panel*/
        midPanel = new JPanel(new GridLayout(4, 1));
        midPanel.setBorder(new EmptyBorder(40, 0, 40, 0));

        //insert
        insertBtnPanel = new JPanel();
        insertBtn = new JButton("Insert");
        insertBtn.setPreferredSize(dimensionSize);
        insertBtn.addActionListener(this);
        
        insertBtnPanel.add(insertBtn);
        
        //view
        viewBtnPanel = new JPanel();
        viewBtn = new JButton("View");
        viewBtn.setPreferredSize(dimensionSize);
        viewBtn.addActionListener(this);
        
        viewBtnPanel.add(viewBtn);
        
        //update
        updateBtnPanel = new JPanel();
        updateBtn = new JButton("Update");
        updateBtn.setPreferredSize(dimensionSize);
        updateBtn.addActionListener(this);
        
        updateBtnPanel.add(updateBtn);
        
        //delete
        deleteBtnPanel = new JPanel();
        deleteBtn = new JButton("Delete");
        deleteBtn.setPreferredSize(dimensionSize);
        deleteBtn.addActionListener(this);
        
        deleteBtnPanel.add(deleteBtn);

        //add all to mid panel
        midPanel.add(insertBtnPanel);
        midPanel.add(viewBtnPanel);
        midPanel.add(updateBtnPanel);
        midPanel.add(deleteBtnPanel);
        

        /*bot panel*/
		botPanel = new JPanel();
		exitBtn = new JButton("Exit");
		exitBtn.setPreferredSize(new Dimension(100, 30));
        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);
		exitBtn.addActionListener(this);

        botPanel.add(exitBtn);

        /*Add all to frame*/
        add(topPanel, BorderLayout.NORTH);
        add(midPanel, BorderLayout.CENTER);
        add(botPanel, BorderLayout.SOUTH);

        /*setup for frame*/
        setup();
    }
    
    void setup() {
        setVisible(true);
        setSize(350, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("PT Pudding Database");
    }

    void exitProgram() {
        this.dispose();
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*dispose this frame*/
        this.dispose();

        /*go to another frame*/
        if(e.getSource() == insertBtn) new Insert(user, dbConnection);
        else if(e.getSource() == viewBtn) new View(user, dbConnection);
        else if(e.getSource() == updateBtn) new Update(user, dbConnection);
        else if(e.getSource() == deleteBtn) new Delete(user, dbConnection);
        else if(e.getSource() == exitBtn) exitProgram();
    }
}
