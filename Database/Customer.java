/*Customer Database Editor*/
package Database;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javax.swing.SwingConstants;

public class Customer extends JFrame {

	private JFrame frmCustomers;
	private JTextField namefield;
	private JTextField phonefield;
	private JTextField emailfield;
	private JButton deletebutton;
	private JButton createbutton;
	private JTextField addrfield;
	private JLabel lblAddress;
	private JLabel customertitle;
	private JTable customertable;
	private JScrollPane scrollPane;
	private JPanel searchtool;
	private JTextField idfield;

	/*main method*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customer frame = new Customer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*Creation of Customer Table GUI by creating a customer object*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////



	public Customer() {
		initialize();//initialize the frame
		Connect();//connects to the database
		table_load();//loads the table
	}
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JButton updatebutton;
	private JButton backbutton;
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*Connect to Database Method*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void Connect ()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/tesco",//must enter the correct database name, username and password
					"root",  ""); 	
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*Method to display the table*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void table_load()
	{
		try
		{
			pst= con.prepareStatement("select * from customer");//SQL query to call all of the data from the customer table
			rs = pst.executeQuery ();
			customertable.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			
		}
		
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*Initializing the JFrame*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void initialize() {
		frmCustomers = new JFrame();//creates frame
		frmCustomers.setResizable(false);//prevents resizing of frame
		frmCustomers.setTitle("Tesco Database");
		frmCustomers.setVisible(true);
		frmCustomers.setBounds(100, 100, 1070, 520);
		frmCustomers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCustomers.getContentPane().setLayout(null);
		
		namefield = new JTextField();
		namefield.setBounds(877, 36, 169, 19);
		namefield.setColumns(10);
		frmCustomers.getContentPane().add(namefield);
		
		phonefield = new JTextField();
		phonefield.setBounds(877, 65, 169, 19);
		phonefield.setColumns(10);
		frmCustomers.getContentPane().add(phonefield);
		
		emailfield = new JTextField();
		emailfield.setBounds(877, 94, 169, 19);
		emailfield.setColumns(10);
		frmCustomers.getContentPane().add(emailfield);
		
		JLabel lblCustomerName = new JLabel("Name");
		lblCustomerName.setBounds(731, 39, 122, 13);
		lblCustomerName.setHorizontalAlignment(SwingConstants.CENTER);
		frmCustomers.getContentPane().add(lblCustomerName);
		
		JLabel lblCustomerPhone = new JLabel("Phone number");
		lblCustomerPhone.setBounds(731, 68, 122, 13);
		lblCustomerPhone.setHorizontalAlignment(SwingConstants.CENTER);
		frmCustomers.getContentPane().add(lblCustomerPhone);
		
		JLabel lblCustomerEmail = new JLabel("Email");
		lblCustomerEmail.setBounds(731, 97, 122, 13);
		lblCustomerEmail.setHorizontalAlignment(SwingConstants.CENTER);
		frmCustomers.getContentPane().add(lblCustomerEmail);
		
		addrfield = new JTextField();
		addrfield.setBounds(877, 127, 169, 19);
		addrfield.setColumns(10);
		frmCustomers.getContentPane().add(addrfield);
		
		lblAddress = new JLabel("Address");
		lblAddress.setBounds(731, 130, 122, 13);
		lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
		frmCustomers.getContentPane().add(lblAddress);
		
		customertitle = new JLabel("Customer Table");
		customertitle.setBounds(7, 7, 737, 25);
		customertitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frmCustomers.getContentPane().add(customertitle);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(7, 36, 725, 418);
		frmCustomers.getContentPane().add(scrollPane);
		
		customertable = new JTable();
		scrollPane.setViewportView(customertable);
		
		searchtool = new JPanel();
		searchtool.setBounds(740, 175, 306, 231);
		searchtool.setBorder(new TitledBorder(null, "Search Tool", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmCustomers.getContentPane().add(searchtool);
		searchtool.setLayout(null);
		
		JPanel Search = new JPanel();
		Search.setBounds(6, 15, 291, 95);
		searchtool.add(Search);
		Search.setLayout(null);
		
		JLabel idlabel = new JLabel("Customer ID");
		idlabel.setBounds(35, 37, 84, 22);
		Search.add(idlabel);
		
		idfield = new JTextField();
		idfield.setBounds(154, 39, 96, 19);
		idfield.addKeyListener(new KeyAdapter()
		
		{ 
			public void keyReleased(KeyEvent e)
 
			{
			
				try 
				{
					String customer_id= idfield.getText();
					pst =con.prepareStatement("select Customer_ID,Customer_name,Phone,Email,Address from customer where Customer_ID=?");
					pst.setString(1, customer_id);
					ResultSet rs =pst.executeQuery();
					  
			if(rs.next () == true)
			{
				
				String name = rs.getString(2);
				String phone = rs.getString(3);
				String email = rs.getString(4);
				String address = rs.getString(5);
				
				
				
				namefield.setText(name);
				phonefield.setText(phone);
				emailfield.setText(email);
				addrfield.setText(address);
				
				
			}
			else
			{
				namefield.setText("");
				phonefield.setText("");
				emailfield.setText("");
				addrfield.setText("");
				
			}
				}
				
				catch(SQLException ex) {
					}
				
				
			} 
			
		}
		);
		Search.add(idfield);
		idfield.setColumns(10);
		
		
		
		
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*UPDATE METHOD*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		updatebutton = new JButton("Update");
		updatebutton.setBounds(6, 120, 140, 40);
		searchtool.add(updatebutton);
		updatebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String Name, Phone, Email, Address, ID;//initialize the strings
				
				Name = namefield.getText();
				Phone = phonefield.getText();
				Email =emailfield.getText();
				Address = addrfield.getText();
				ID =idfield.getText();
				boolean validation = validateInput(Name,Phone,Email,Address);
				boolean idpresent = validateID(ID);
				if (!idpresent) {//if id is present
				if(validation) {//if the textfields are validated
				
			try {
				pst = con.prepareStatement("update customer set Customer_name=?,Phone=?,Email=?,Address=? where Customer_ID=?");
				
				pst.setString(1,Name);
				pst.setString(2,Phone);
				pst.setString(3,Email);
				pst.setString(4, Address);
				pst.setString(5, ID);
				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Record Updated");
				table_load();
				
				namefield.setText("");
				phonefield.setText("");
				emailfield.setText("");
				addrfield.setText("");
				idfield.setText("");
				idfield.requestFocus();
				
				
				namefield.requestFocus();
			}	
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
				}
				else {
					JOptionPane.showMessageDialog(null, "Invalid input detected");
				}
				}
				else {
					JOptionPane.showMessageDialog(null, "No matching Customer ID");
			}
			}
			
			
			
			
			
		});
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*CREATE METHOD*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		createbutton = new JButton("Create customer");
		createbutton.setBounds(6, 181, 140, 40);
		searchtool.add(createbutton);
		createbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String Name, Phone, Email, Address, ID;
				
				
				ID = idfield.getText();
				Name = namefield.getText();
				Phone = phonefield.getText();
				Email =emailfield.getText();
				Address =addrfield.getText();
				boolean testID = validateID(ID);
				if(testID) {
				boolean validation = validateInput(Name,Phone,Email,Address);
				if(validation) {
				try {
				
				
				
				pst = con.prepareStatement("insert into customer(Customer_name,Phone,Email,Address) values(?,?,?,?)");
				
				pst.setString(1,Name);
				pst.setString(2,Phone);
				pst.setString(3,Email);
				pst.setString(4, Address);
				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Record Added");
				table_load();
				
				namefield.setText("");
				phonefield.setText("");
				emailfield.setText("");
				addrfield.setText("");
				idfield.setText("");
				
				namefield.requestFocus();
			}	
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			}
				else {
					JOptionPane.showMessageDialog(null, "Invalid input detected");
				}
				
			}
				else {
					JOptionPane.showMessageDialog(null, "Clear ID Field");
				}
				
			}
			
			
		});

		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		deletebutton = new JButton("Delete");
		deletebutton.setBounds(152, 120, 145, 40);
		searchtool.add(deletebutton);
		deletebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String ID ;
				
				
				ID = idfield.getText();
				
				try {
					pst = con.prepareStatement(" delete from customer where customer_id=? ");
					
					pst.setString(1, ID);
					pst.executeUpdate();
					
					
					JOptionPane.showMessageDialog(null, "Record Deleted");
					
					table_load();
			
					namefield.setText("");
					phonefield.setText("");
					emailfield.setText("");	
					addrfield.setText("");
					idfield.setText("");
					idfield.requestFocus();
					
				}
				
				catch(SQLException e1) 
				{
					
					e1.printStackTrace();
				}
				}
			
		});
		

	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Back Button
		backbutton = new JButton("Back");
		backbutton.setBounds(748, 433, 298, 21);
		frmCustomers.getContentPane().add(backbutton);
		backbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCustomers.dispose();
				mainMenu frame = new mainMenu();
			}
		});
		
	}
/*method to clear the table when called*/
	public void Clear() {
		customertable.setModel(new DefaultTableModel());
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//*Method to validate the inputs for the fields*/
	public boolean validateInput(String testname,String testphone,String testemail, String testaddress) {
	    
		Pattern namepattern = Pattern.compile("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$");
	    Matcher namematcher = namepattern.matcher(testname);
	    boolean namematch = namematcher.find();
		
	    
		Pattern numpattern = Pattern.compile("^[0-9]{9,10}+$");
	    //"^[0-9]+\\.?[0-9]*$"
	    Matcher nummatcher = numpattern.matcher(testphone);
	    boolean nummatch = nummatcher.find();
	    
	    Pattern emailpattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
	    Matcher emailmatcher = emailpattern.matcher(testemail);
	    boolean emailmatch = emailmatcher.find();
	    
	    Pattern addresspattern = Pattern.compile("[-0-9A-Za-z.,/ ]+");
	    Matcher addressmatcher = addresspattern.matcher(testaddress);
	    boolean addressmatch = addressmatcher.find();
	    
	    
	    boolean completeMatch = true;
	    if(!namematch) {
	    	completeMatch = false;
	    	JOptionPane.showMessageDialog(null, "Invalid Name");
	    }
	    else if(!nummatch) {
	    	completeMatch = false;
	    	
	    	JOptionPane.showMessageDialog(null, "Invalid Phone Number");
	    }
	    else if(!emailmatch) {
	    	completeMatch = false;
	    	
	    	JOptionPane.showMessageDialog(null, "Invalid Email");
	    }
	    else if(!addressmatch) {
	    	completeMatch = false;
	    	
	    	JOptionPane.showMessageDialog(null, "Invalid Address");
	    }

	    return completeMatch;
	}
/*validation method to test the presence of an ID*/
public boolean validateID(String testid) {
	    
		Pattern idpattern = Pattern.compile("^$|\\s+");
	    Matcher idmatcher = idpattern.matcher(testid);
	    boolean idmatch = idmatcher.find();
	    boolean completeMatch = true;
	    if(!idmatch) {
	    	completeMatch = false;
	    }
	    return completeMatch;
}
}
