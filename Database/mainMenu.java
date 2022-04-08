/*Main menu page*/

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


/*Main method*/
public class mainMenu extends JFrame {

	private JFrame frmTescoDatabase;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainMenu frame = new mainMenu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

/*Method to call for initialization*/
	public mainMenu() {
		initialize();
	}

/*Initialization method*/
	private void initialize() {
		frmTescoDatabase = new JFrame();
		frmTescoDatabase.setTitle("Tesco Database");
		frmTescoDatabase.setVisible(true);
		frmTescoDatabase.setResizable(false);
		frmTescoDatabase.setBounds(100, 100, 450, 308);
		frmTescoDatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTescoDatabase.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Select Database");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(130, 31, 156, 52);
		frmTescoDatabase.getContentPane().add(lblNewLabel);
		
		/*Combobox method*/
		String s1[] ={"Invoice", "Product", "Customer"};
		JComboBox comboBox = new JComboBox(s1);
		comboBox.setBounds(36, 125, 149, 21);
		frmTescoDatabase.getContentPane().add(comboBox);
		
		/*Go button functionality*/
		JButton gobutton = new JButton("Go");
		gobutton.setBounds(240, 125, 85, 21);
		frmTescoDatabase.getContentPane().add(gobutton);
		
		/*Exit button functionality*/
		JButton exitbutton = new JButton("Exit");
		exitbutton.setBounds(240, 199, 85, 21);
		frmTescoDatabase.getContentPane().add(exitbutton);
		exitbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		int a=JOptionPane.showConfirmDialog(null, "Would you like to exit the application?","Closing application",JOptionPane.YES_NO_CANCEL_OPTION);
		if(a==0)
		{
			System.exit(0);
		}
		}
	});
		/*Logout button*/
		JButton logoutbutton = new JButton("Logout");
		logoutbutton.setBounds(73, 199, 85, 21);
		frmTescoDatabase.getContentPane().add(logoutbutton);
		logoutbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int a=JOptionPane.showConfirmDialog(null, "Would you like to logout?","Logging out",JOptionPane.YES_NO_CANCEL_OPTION);
					{
					frmTescoDatabase.dispose();//gets rid of the main menu window
					login lframe = new login();//opens back the login window
					}
				}
		});
		/*Go button that takes the user to the targeted database table editor.*/
		gobutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(e.getSource()==gobutton) {
					String chosen = (String)comboBox.getSelectedItem();
					if(chosen == "Customer") {
						JOptionPane.showMessageDialog(null, "Customer Window Opened");
						frmTescoDatabase.dispose();//gets rid of the current main menu frame
						Customer frame1 = new Customer();		//creates the new customer table editor window
					}
					if(chosen == "Invoice") {
						JOptionPane.showMessageDialog(null, "Invoice Window Opened");
						frmTescoDatabase.dispose();
						Invoice frame2 = new Invoice();
					}
					if(chosen == "Product") {
						JOptionPane.showMessageDialog(null, "Product Window Opened");
						frmTescoDatabase.dispose();
						Product frame3 = new Product();
					}
				}
			}
		});
	}
}
