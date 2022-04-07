package FinalProject.project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import FinalProject.Util.User;
import FinalProject.config.DBConnection;

public class Insert extends JFrame implements ActionListener {

	Vector<User> user;
	DBConnection dbConnection;

	JPanel topPanel, midPanel, botPanel,
			codeLblPanel, nameLblPanel, priceLblPanel, stockLblPanel,
			codeFieldPanel, nameFieldPanel, priceFieldPanel, stockFieldPanel;
	JLabel title, 
			codeLbl, nameLbl, priceLbl, stockLbl;
	JTextField codeField, nameField, priceField, stockField;
	
	JButton backBtn, insertBtn;
	

	public Insert(Vector<User> user, DBConnection dbConnection) {

		//populate vector
		this.user = user;
		this.dbConnection = dbConnection;

		Dimension dimensionSize = new Dimension(130, 30);
		
		
		/*top panel*/
		topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(20, 0, 30, 0));

		title = new JLabel("Insert Menu");
		title.setFont(new Font("", Font.BOLD, 18));
		
		topPanel.add(title);


		/*mid panel*/
		midPanel = new JPanel(new GridLayout(4, 2));

		//code
		codeLblPanel = new JPanel();
		codeFieldPanel = new JPanel();
		
		codeLbl = new JLabel("Code");
	
		codeField = new JTextField(generateCode());
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
		midPanel.add(codeLblPanel);
		midPanel.add(codeFieldPanel);
		midPanel.add(nameLblPanel);
		midPanel.add(nameFieldPanel);
		midPanel.add(priceLblPanel);
		midPanel.add(priceFieldPanel);
		midPanel.add(stockLblPanel);
		midPanel.add(stockFieldPanel);

		
		/*bot panel*/
		botPanel = new JPanel();
		insertBtn = new JButton("Insert");
		insertBtn.setPreferredSize(new Dimension(100, 30));
		insertBtn.addActionListener(this);
		
		backBtn = new JButton("Back");
		backBtn.setPreferredSize(new Dimension(100, 30));
		backBtn.addActionListener(this);

		botPanel.add(insertBtn);
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

	String generateCode() {
		String code = "PD-";

		//generate 3 random number
		for(int x=0;x<3;x++) {
			int randChar = (int) (Math.random() * 10);
			code += randChar;
		}

		//check if generated code is unique
		boolean checkUnique = true;

		for(User x : user) {
			if(x.getCode().equals(code)) {
				checkUnique = false;
				break;
			}
		}

		//if unique, then send the code
		//else then generate new code
		if(checkUnique) return code;
		else return generateCode();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backBtn) {
			//dispose this frame
			this.dispose();

			//go to home
			new Home(user, dbConnection);
		}
		else if(e.getSource() == insertBtn) {
			//get all data
			String code = codeField.getText();
			String name = nameField.getText();

			//We need to use String because if the text field is empty, 
			//then .Gettext() method will return a null
			String priceStr = priceField.getText();
			String stockStr = stockField.getText();

			//validate the input data
			if(validateInput(name, priceStr, stockStr)) {
				//dispose this frame
				this.dispose();

				//convert String to int
				int price = Integer.valueOf(priceStr);
				int stock = Integer.valueOf(stockStr);

				//give an information to user that data has been inserted
				JOptionPane.showMessageDialog(this, "Successfully insert data with name " + name + "!", "Insert Success", JOptionPane.INFORMATION_MESSAGE);
				
				//add to Vector
				user.add(new User(code, name, price, stock));

				//add to db
				dbConnection.insertUser(code, name, price, stock);

				//go to home
				new Home(user, dbConnection);
			}
		}
	}
	
	boolean validateInput(String name, String price, String stock) {
		if(name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Name can't be empty!", "Insert Error", JOptionPane.ERROR_MESSAGE);
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


}
