/*Login Page*/

/*packages used*/
package Database;
import java.sql.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.SystemColor;
import java.awt.Color;

public class login extends JFrame {
	
/*initializing the frames and text fields*/
	private JFrame frmTescoDatabase;
	private JTextField usernameTextField;
	private JPasswordField passwordField;

	/* Main method to create the Login Frame*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

/*Method to call for initialization of the frame*/
	public login() {
		initialize();
	}

	/*Initialization of frame*/
	private void initialize() {
		frmTescoDatabase = new JFrame();
		frmTescoDatabase.setTitle("Tesco Database");
		frmTescoDatabase.setVisible(true);
		frmTescoDatabase.setResizable(false);
		frmTescoDatabase.setBounds(100, 100, 450, 308);
		frmTescoDatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTescoDatabase.getContentPane().setLayout(null);
		
		
		JLabel lblNewLabel_2 = new JLabel("ADMINISTRATOR LOGIN PAGE");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(10, 10, 295, 42);
		frmTescoDatabase.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(120, 88, 67, 14);
		frmTescoDatabase.getContentPane().add(lblNewLabel);
		
		usernameTextField = new JTextField();
		usernameTextField.setFont(new Font("Tahoma", Font.BOLD, 12));
		usernameTextField.setColumns(10);
		usernameTextField.setBounds(203, 85, 200, 20);
		frmTescoDatabase.getContentPane().add(usernameTextField);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(120, 125, 81, 14);
		frmTescoDatabase.getContentPane().add(lblNewLabel_1);
		
		/*Instead of a regular JtextField, we use a JPasswordField for encrypted input*/
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 12));
		passwordField.setBounds(203, 122, 202, 20);
		frmTescoDatabase.getContentPane().add(passwordField);
		
		/*checkbox that creates the function of hiding and displaying hidden text*/
		JCheckBox chckbxNewCheckBox = new JCheckBox("display password");
		chckbxNewCheckBox.setBackground(Color.LIGHT_GRAY);
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(chckbxNewCheckBox.isSelected())
				{
					passwordField.setEchoChar((char)0);//reveals the text
				}
				else
					passwordField.setEchoChar('*');//replaces text with asterisk or '*'
			}
		});
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxNewCheckBox.setBounds(203, 151, 136, 23);
		frmTescoDatabase.getContentPane().add(chckbxNewCheckBox);
		
		/*exit button*/
		JButton btnNewButton_1 = new JButton("exit");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setBounds(203, 208, 89, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		int a=JOptionPane.showConfirmDialog(null, "Would you like to exit the application?","Closing application",JOptionPane.YES_NO_CANCEL_OPTION);
		if(a==0)
		{
			System.exit(0);
		}
		}
	});
		btnNewButton_1.setBounds(314, 206, 89, 23);
		frmTescoDatabase.getContentPane().add(btnNewButton_1);
		
		
		/*login button function*/
		JButton btnNewButton = new JButton("login");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setBounds(203, 208, 89, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				
					String password= new String(passwordField.getPassword());
	/*This is where the program will check if the text entered matches the needed name and password*/
					if(usernameTextField.getText().equals("KariDaniels") && password.equals("IoaBMW1905"))
					{
						JOptionPane.showMessageDialog(null, "Main Menu Opened");
						frmTescoDatabase.dispose();
						mainMenu menuframe = new mainMenu();
					}
					else 
					{
						JOptionPane.showMessageDialog(null, "Invalid username/password");
					}
					
					
				}
			});
		frmTescoDatabase.getContentPane().add(btnNewButton);
		


	}
}

