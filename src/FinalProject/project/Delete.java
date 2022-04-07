package FinalProject.project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import FinalProject.Util.User;
import FinalProject.config.DBConnection;

public class Delete extends JFrame implements ActionListener, MouseListener{
    Vector<User> user;
	DBConnection dbConnection;

	JPanel topPanel, midPanel, botPanel, leftMidPanel, rightMidPanel,
			codeLblPanel, nameLblPanel, priceLblPanel, stockLblPanel,
			codeFieldPanel, nameFieldPanel, priceFieldPanel, stockFieldPanel;
	JLabel title, codeLbl, nameLbl, priceLbl, stockLbl;
	JTextField codeField, nameField, priceField, stockField;
	JTable userTable;
	JScrollPane tableScrollPane;
	JButton deleteBtn, backBtn, clearBtn;

    public Delete(Vector<User> user, DBConnection dbConnection) {
        //populate vector
		this.user = user;
		this.dbConnection = dbConnection;

		Dimension dimensionSize = new Dimension(130, 30);


		/*top panel*/
		topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(20, 0, 30, 0));

		title = new JLabel("Update Menu");
		title.setFont(new Font("", Font.BOLD, 18));
		
		topPanel.add(title);


		/*mid panel*/
		midPanel = new JPanel(new GridLayout(1, 2));
	
		/*mid left panel for update*/
		leftMidPanel = new JPanel(new GridLayout(4, 2));

		//code
		codeLblPanel = new JPanel();
		codeFieldPanel = new JPanel();
		
		codeLbl = new JLabel("Code");
	
		codeField = new JTextField();
		codeField.setPreferredSize(dimensionSize);
		codeField.setEnabled(false);

		codeLblPanel.add(codeLbl);
		codeFieldPanel.add(codeField);

		//name
		nameLblPanel = new JPanel();
		nameFieldPanel = new JPanel();
		
		
		nameLbl = new JLabel("Name");

		nameField = new JTextField();
		nameField.setPreferredSize(dimensionSize);
		nameField.setEnabled(false);

		nameLblPanel.add(nameLbl);
		nameFieldPanel.add(nameField);

		//price
		priceLblPanel = new JPanel();
		priceFieldPanel = new JPanel();
		
		priceLbl = new JLabel("Price");

		priceField = new JTextField();
		priceField.setPreferredSize(dimensionSize);
        priceField.setEnabled(false);

		priceLblPanel.add(priceLbl);
		priceFieldPanel.add(priceField);

		//stock
		stockLblPanel = new JPanel();
		stockFieldPanel = new JPanel();
		
		stockLbl = new JLabel("Stock");

		stockField = new JTextField();
		stockField.setPreferredSize(dimensionSize);
        stockField.setEnabled(false);

		stockLblPanel.add(stockLbl);
		stockFieldPanel.add(stockField);


		//add all to midpanel
		leftMidPanel.add(codeLblPanel);
		leftMidPanel.add(codeFieldPanel);
		leftMidPanel.add(nameLblPanel);
		leftMidPanel.add(nameFieldPanel);
		leftMidPanel.add(priceLblPanel);
		leftMidPanel.add(priceFieldPanel);
		leftMidPanel.add(stockLblPanel);
		leftMidPanel.add(stockFieldPanel);

		
		/*mid right panel for table*/
		rightMidPanel = new JPanel();

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
		userTable.addMouseListener(this);

		//give scrollbar to jtable
		tableScrollPane = new JScrollPane(userTable);
		tableScrollPane.setPreferredSize(new Dimension(330, 300));
		rightMidPanel.add(tableScrollPane);

		//add all to midpanel
		midPanel.add(leftMidPanel);
		midPanel.add(rightMidPanel);


		/*bot panel*/
		botPanel = new JPanel();
		deleteBtn = new JButton("Delete");
		deleteBtn.setPreferredSize(new Dimension(100, 30));
		deleteBtn.addActionListener(this);

		backBtn = new JButton("Back");
		backBtn.setPreferredSize(new Dimension(100, 30));
		backBtn.addActionListener(this);
		
		clearBtn = new JButton("Clear");
		clearBtn.setPreferredSize(new Dimension(100, 30));
		clearBtn.addActionListener(this);

		botPanel.add(clearBtn);
		botPanel.add(deleteBtn);
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
		setSize(800, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("PT Pudding Database");
	}

    void clearTextField() {
		codeField.setText("");
		nameField.setText("");
		priceField.setText("");
		stockField.setText("");
	}

    boolean validateInput(String code) {
		if(code.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please click item from table to update", "Update Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == deleteBtn) {
            //delete vector
            String code = codeField.getText();
            
            if(validateInput(code)) {
                for(int x=0;x<user.size();x++) {
                    if(user.get(x).getCode().equals(code)) {
                        user.remove(x);
                        break;
                    }
                }
    
                //delete from db
				dbConnection.deleteUser(code);
    
                //give an information that data has been changed
                JOptionPane.showMessageDialog(this, "Successfully delete data with code " + code + "!", "Delete Success", JOptionPane.INFORMATION_MESSAGE);
                
                //go to home
                this.dispose();
                new Home(user, dbConnection);
            }
        }
        else if(e.getSource() == backBtn) {
            //dispose this frame
            this.dispose();

            //go to home
            new Home(user, dbConnection);
        }
        else if(e.getSource() == clearBtn) clearTextField();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == userTable) {
			//get clicked row
			int row = userTable.getSelectedRow();

			//set textfiel to clicked data
			codeField.setText(String.valueOf(userTable.getValueAt(row, 0)));
			nameField.setText(String.valueOf(userTable.getValueAt(row, 1)));
			priceField.setText(String.valueOf(userTable.getValueAt(row, 2)));
			stockField.setText(String.valueOf(userTable.getValueAt(row, 3)));
		}
    }

    @Override
    public void mousePressed(MouseEvent e) {  
    }

    @Override
    public void mouseReleased(MouseEvent e) {  
    }

    @Override
    public void mouseEntered(MouseEvent e) {  
    }

    @Override
    public void mouseExited(MouseEvent e) {  
    }

    
    
}
