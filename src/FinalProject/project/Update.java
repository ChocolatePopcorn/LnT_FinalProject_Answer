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

public class Update extends JFrame implements ActionListener, MouseListener {
	Vector<User> user;
	DBConnection dbConnection;
	
	JPanel topPanel, midPanel, botPanel, leftMidPanel, rightMidPanel,
			codeLblPanel, nameLblPanel, priceLblPanel, stockLblPanel,
			codeFieldPanel, nameFieldPanel, priceFieldPanel, stockFieldPanel;
	JLabel title, codeLbl, nameLbl, priceLbl, stockLbl;
	JTextField codeField, nameField, priceField, stockField;
	JTable userTable;
	JScrollPane tableScrollPane;
	JButton updateBtn, backBtn, clearBtn;

	public Update(Vector<User> user, DBConnection dbConnection) {
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

		priceLblPanel.add(priceLbl);
		priceFieldPanel.add(priceField);

		//stock
		stockLblPanel = new JPanel();
		stockFieldPanel = new JPanel();
		
		stockLbl = new JLabel("Stock");

		stockField = new JTextField();
		stockField.setPreferredSize(dimensionSize);

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
		updateBtn = new JButton("Update");
		updateBtn.setPreferredSize(new Dimension(100, 30));
		updateBtn.addActionListener(this);

		backBtn = new JButton("Back");
		backBtn.setPreferredSize(new Dimension(100, 30));
		backBtn.addActionListener(this);
		
		clearBtn = new JButton("Clear");
		clearBtn.setPreferredSize(new Dimension(100, 30));
		clearBtn.addActionListener(this);

		botPanel.add(clearBtn);
		botPanel.add(updateBtn);
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

	boolean validateInput(String code, String name, String price, String stock) {
		if(code.isEmpty() || name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please click item from table to update", "Update Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(price.equals("0") || price.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Price must be filled or can't be zero", "Insert Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(stock.equals("0") || stock.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Stock can't be filled! or can't be zero", "Insert Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == updateBtn) {

			//get input from textfield
			String code = codeField.getText();
			String name = nameField.getText();
			String priceStr = priceField.getText();
			String stockStr = stockField.getText();
			

			if(validateInput(code, name, priceStr, stockStr)) {
				//update vector
				for(User x : user) {
					if(x.getCode().equals(code)) {
						x.setPrice(Integer.valueOf(priceStr));
						x.setStock(Integer.valueOf(stockStr));
						break;
					}
				}

				//update db
				dbConnection.updateUser(code, Integer.valueOf(priceStr), Integer.valueOf(stockStr));

				//give an information that data has been changed
				JOptionPane.showMessageDialog(this, "Successfully update data with code " + code + "!", "Update Success", JOptionPane.INFORMATION_MESSAGE);
				
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
