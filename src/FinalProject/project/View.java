package FinalProject.project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import FinalProject.Util.User;
import FinalProject.config.DBConnection;

public class View extends JFrame implements ActionListener {
    
    Vector<User> user;
    DBConnection dbConnection;
    
    JPanel topPanel, midPanel, botPanel;
    JTable userTable;
    JScrollPane tableScrollPane;
    JLabel title;
    JButton backBtn;

    public View(Vector<User> user, DBConnection dbConnection) {
        
        //populate vector
        this.user = user;
        this.dbConnection = dbConnection;


        /*top panel*/
        topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(20, 0, 30, 0));

		title = new JLabel("View Menu");
		title.setFont(new Font("", Font.BOLD, 18));
		
		topPanel.add(title);


        /*mid panel*/
		midPanel = new JPanel();

		//take column
		Vector<Object> column = new Vector<>();
		column.add("Code");
		column.add("Name");
		column.add("Price");
		column.add("Stock");
	
		//take row
		Vector<Vector<Object>> row = new Vector<Vector<Object>>();
		for(User x : user) {
			Vector<Object> curr = new Vector<>();
			curr.add(x.getCode());
            curr.add(x.getName());
            curr.add(x.getPrice());
            curr.add(x.getStock());
        			
			row.add(curr);
		}
		
        /*create table to show all user*/
		userTable = new JTable(row, column);

        //give scrollbar to jtable
		tableScrollPane = new JScrollPane(userTable);
		tableScrollPane.setPreferredSize(new Dimension(330, 250));
		
        midPanel.add(tableScrollPane);

        /*bot panel*/
		botPanel = new JPanel();
		
		backBtn = new JButton("Back");
		backBtn.setPreferredSize(new Dimension(100, 30));
		backBtn.addActionListener(this);

		botPanel.add(backBtn);


        /*add all to frame*/
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
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backBtn) {
            //dispose this frame
			this.dispose();

			//go to home
			new Home(user, dbConnection);
        }   
    }
}
