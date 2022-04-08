/*Invoice Database Editor*/


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
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Invoice extends JFrame{

	private JFrame frmInvoice;
	private JTextField prodnamefield;
	private JTextField perfield;
	private JButton Deletebutton;
	private JButton Createbutton;
	private JLabel lblAddress;
	private JLabel Table_label;
	private JTable invoicetable;
	private JScrollPane scrollPane;
	private JPanel Search_1;
	private JTextField pIDfield;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*Main method execution*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Invoice frame = new Invoice();
					//frame.frmProducts.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*Invoice method*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Invoice() {
		initialize();
		Connect();
		table_load();
	}
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JButton Backbutton;
	private JTextField numfield;
	private JTextField totalfield;
	private JTextField custnamefield;
	private JTextField cIDfield;
	private JLabel lblNewLabel_2_3;
	private JTextField invnumfield;
	private JScrollPane scrollPane_1;
	private JLabel available;
	private JTextField availtext;
	
	public void Connect ()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/tesco",
					"root",  ""); 	
		}
		catch (ClassNotFoundException ex)
		{
			
		}
		catch (SQLException ex)
		{
			
		}
		
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*METHOD TO DISPLAY THE DATABASE INVOICE TABLE*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void table_load()
	{
		try
		{
			pst= con.prepareStatement("select invoice.Invoice_Number,invoice.Customer_ID,customer.Customer_name,"
					+ "invoice.Product_ID,product.product_name,invoice.product_count,invoice.total_cost,invoice.date_created from invoice inner join"
					+ " customer on invoice.Customer_ID = customer.Customer_ID inner join product on invoice.Product_ID"
					+ " = product.product_ID order by invoice.Invoice_Number");
			rs = pst.executeQuery ();
			invoicetable.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch (SQLException e)
		{
			e.printStackTrace();//failure to query carries out this method
		}
		
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*Initialization of the frame*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void initialize() {
		frmInvoice = new JFrame();
		frmInvoice.setTitle("Tesco Database");
		frmInvoice.setVisible(true);
		frmInvoice.setBounds(100, 100, 943, 647);
		frmInvoice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInvoice.getContentPane().setLayout(null);
		
		prodnamefield = new JTextField();
		prodnamefield.setBounds(143, 405, 121, 19);
		prodnamefield.setEditable(false);
		prodnamefield.setColumns(10);
		frmInvoice.getContentPane().add(prodnamefield);
		
		perfield = new JTextField();
		perfield.setBounds(143, 442, 121, 19);
		perfield.setEditable(false);
		perfield.setColumns(10);
		frmInvoice.getContentPane().add(perfield);
		
		JLabel lblCustomerName = new JLabel("Product Name");
		lblCustomerName.setBounds(7, 408, 109, 13);
		frmInvoice.getContentPane().add(lblCustomerName);
		lblAddress = new JLabel("Price per Unit");
		lblAddress.setBounds(7, 449, 109, 13);
		frmInvoice.getContentPane().add(lblAddress);
		
		Table_label = new JLabel("Invoice Table");
		Table_label.setBounds(7, 7, 653, 25);
		Table_label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frmInvoice.getContentPane().add(Table_label);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(7, 36, 915, 365);
		frmInvoice.getContentPane().add(scrollPane_1);
		
		scrollPane = new JScrollPane();
		scrollPane_1.setViewportView(scrollPane);
		
		invoicetable = new JTable();
		scrollPane.setViewportView(invoicetable);
		
		Search_1 = new JPanel();
		Search_1.setBounds(7, 478, 257, 125);
		Search_1.setBorder(new TitledBorder(null, "Search Tool", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmInvoice.getContentPane().add(Search_1);
		Search_1.setLayout(null);
		
		JPanel Search = new JPanel();
		Search.setBounds(10, 15, 240, 66);
		Search_1.add(Search);
		Search.setLayout(null);
		
		JLabel productIDlabel = new JLabel("Product ID");
		productIDlabel.setBounds(10, 37, 84, 22);
		Search.add(productIDlabel);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*CREATE METHOD*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Createbutton = new JButton("Create Invoice");
		Createbutton.setBounds(325, 561, 274, 42);
		
		Createbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			
				String custid, custname, prodid,prodname,count,cost,available;
				
				custid = cIDfield.getText();
				custname = custnamefield.getText();
				prodid = pIDfield.getText();
				prodname = prodnamefield.getText();
				count = numfield.getText();
				cost = totalfield.getText();
				available = availtext.getText();
				
				java.util.Date date=new java.util.Date();
				java.sql.Date sqlDate=new java.sql.Date(date.getTime());
				
				boolean validnumber = validateNumber(count);
				boolean custprod = validatePresence(custname,prodname,available);
				if(validnumber && custprod) {
				
			try {
				pst = con.prepareStatement("insert into invoice(Customer_ID,Product_ID,product_count,total_cost,date_created) values(?,?,?,?,?)");
				
				pst.setString(1,custid);
				pst.setString(2,prodid);
				pst.setString(3,count);
				pst.setString(4,cost);
				pst.setDate(5,sqlDate);
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Record Added");
				table_load();
				
				cIDfield.setText("");
				custnamefield.setText("");
				pIDfield.setText("");
				prodnamefield.setText("");
				perfield.setText("");
				numfield.setText("");
				totalfield.setText("");
				availtext.setText("");
				
				
				prodnamefield.requestFocus();
			}	
			catch (SQLException e1)
			{
				JOptionPane.showMessageDialog(null, "Failed");
			}
				}
				else {
				JOptionPane.showMessageDialog(null, "Failed");
				}
			}
			
			
			
			
		});
		frmInvoice.getContentPane().add(Createbutton);
		
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*METHOD TO PUT DATA INTO JTEXTFIELD USING ONLY PRODUCT ID*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		pIDfield = new JTextField();
		pIDfield.setBounds(89, 39, 96, 19);
		pIDfield.addKeyListener(new KeyAdapter()
		
		{ 
			public void keyReleased(KeyEvent e)
 
			{
				
				try 
				{
					String product_id= pIDfield.getText();
					pst =con.prepareStatement("select product_ID,product_name,Price_per_Unit,Availability from product where product_ID=?");

					pst.setString(1, product_id);
					ResultSet rs =pst.executeQuery();
					  
			if(rs.next () == true)
			{
				
				String productname = rs.getString(2);
				String cost = rs.getString(3);
				String available = rs.getString(4);
				
				
				
				prodnamefield.setText(productname);
				perfield.setText(cost);
				availtext.setText(available);
				
				
				
			}
			else
			{
				prodnamefield.setText("");
				perfield.setText("");
				availtext.setText("");

				
			}
				}
				
				catch(SQLException ex) {
					}
				
				
			} 
			
		}
		);
		Search.add(pIDfield);
		pIDfield.setColumns(10);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*METHOD TO PUT DATA INTO JTEXTFIELD USING ONLY CUSTOMER ID*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		cIDfield = new JTextField();
		cIDfield.setColumns(10);
		cIDfield.setBounds(89, 10, 96, 19);
		cIDfield.addKeyListener(new KeyAdapter()
		
		{ 
			public void keyReleased(KeyEvent e)//upon entering the ID
 
			{
			
				try 
				{
					String customer_id= cIDfield.getText();
					pst =con.prepareStatement("select Customer_ID,Customer_name from customer where Customer_ID=?");
					pst.setString(1, customer_id);
					ResultSet rs =pst.executeQuery();
					  
			if(rs.next () == true)
			{
				
				String customername = rs.getString(2);
				
				
				
				
				custnamefield.setText(customername);
				
				
				
			}
			else
			{
				custnamefield.setText("");


				
			}
				}
				
				catch(SQLException ex) {
					
					}
				
				
			} 
			
		}
		);
		Search.add(cIDfield);
		
		JLabel custIDlabel = new JLabel("Customer ID");
		custIDlabel.setBounds(10, 13, 84, 22);
		Search.add(custIDlabel);
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*BACK TO MENU BUTTON*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		Backbutton = new JButton("Back");
		Backbutton.setBounds(730, 561, 192, 42);
		frmInvoice.getContentPane().add(Backbutton);
		Backbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmInvoice.dispose();
				mainMenu frame = new mainMenu();
			}
		});
		
		numfield = new JTextField();
		numfield.setBounds(468, 481, 131, 19);
		numfield.setColumns(10);
		numfield.addKeyListener(new KeyAdapter()
		
		{ 
			public void keyReleased(KeyEvent e)
 
			{
				String unitnum= numfield.getText();
				String pricenum = perfield.getText();
				try 
				{
					
					double unitsdbl = Double.parseDouble(unitnum);
					double costdbl = Double.parseDouble(pricenum) ;
					
					
						double totaldbl=unitsdbl*costdbl;
						DecimalFormat dc =  new DecimalFormat("0.00");
						String total = dc.format(totaldbl);
						
						totalfield.setText(total);
					
					
					  
			}
			
				
				
				 catch (NumberFormatException ex){
					 totalfield.setText("");
			        }
			} 
			
		}
		);
		frmInvoice.getContentPane().add(numfield);
		
		totalfield = new JTextField();
		totalfield.setBounds(468, 523, 131, 19);
		totalfield.setEditable(false);
		totalfield.setColumns(10);
		frmInvoice.getContentPane().add(totalfield);
		
		custnamefield = new JTextField();
		custnamefield.setBounds(468, 442, 131, 19);
		custnamefield.setEditable(false);
		custnamefield.setColumns(10);
		frmInvoice.getContentPane().add(custnamefield);
		
		JLabel lblCustomerName_1 = new JLabel("Customer Name");
		lblCustomerName_1.setBounds(325, 445, 105, 13);
		frmInvoice.getContentPane().add(lblCustomerName_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("Number of Units");
		lblNewLabel_2_2.setBounds(325, 478, 105, 22);
		frmInvoice.getContentPane().add(lblNewLabel_2_2);
		
		lblNewLabel_2_3 = new JLabel("Total Cost");
		lblNewLabel_2_3.setBounds(325, 526, 105, 13);
		frmInvoice.getContentPane().add(lblNewLabel_2_3);
		
		JPanel InvoiceSearch = new JPanel();
		InvoiceSearch.setBounds(621, 405, 301, 137);
		InvoiceSearch.setLayout(null);
		InvoiceSearch.setBorder(new TitledBorder(null, "Invoice Deletion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmInvoice.getContentPane().add(InvoiceSearch);
		
		JPanel Search_2 = new JPanel();
		Search_2.setLayout(null);
		Search_2.setBounds(6, 15, 263, 66);
		InvoiceSearch.add(Search_2);
		
		JLabel invoicenumlabel = new JLabel("Invoice Number");
		invoicenumlabel.setBounds(10, 10, 108, 13);
		Search_2.add(invoicenumlabel);
		
		invnumfield = new JTextField();
		invnumfield.setBounds(0, 33, 96, 19);
		Search_2.add(invnumfield);
		invnumfield.setColumns(10);
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*DELETE BUTTON MECHANISM*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Deletebutton = new JButton("Delete Invoice");
		Deletebutton.setBounds(128, 10, 124, 42);
		Search_2.add(Deletebutton);
		
		available = new JLabel("Availability");
		available.setBounds(325, 408, 105, 13);
		frmInvoice.getContentPane().add(available);
		
		availtext = new JTextField();
		availtext.setBounds(468, 405, 131, 19);
		availtext.setEditable(false);
		frmInvoice.getContentPane().add(availtext);
		availtext.setColumns(10);
		
		
		
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*DELETE BUTTON METHOD*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		Deletebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String invnum = invnumfield.getText();
				boolean validation = validateID(invnum);
				if(validation) {
				
				try {
					pst = con.prepareStatement(" delete from invoice where Invoice_Number=? ");
					
					pst.setString(1, invnum);
					
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted");

					table_load();
					
					cIDfield.setText("");
					custnamefield.setText("");
					pIDfield.setText("");
					prodnamefield.setText("");
					perfield.setText("");
					totalfield.setText("");
					invnumfield.setText("");
					
					
					pIDfield.requestFocus();
					
				}
				
				catch(SQLException e1) 
				{
					
					e1.printStackTrace();
				}
				}
				else {
					JOptionPane.showMessageDialog(null, "No change");
				}
				}
			
		});
		
	}
	
	
	public void Clear() {
		invoicetable.setModel(new DefaultTableModel());
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*METHOD TO VALIDATE NUMERAL FIELDS*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public boolean validateNumber(String number) {
	    
		Pattern numpattern = Pattern.compile("^[0-9]+$");
	    Matcher nummatcher = numpattern.matcher(number);
	    boolean nummatch = nummatcher.find();
		
	    
	    boolean completeMatch = true;
	    if(!nummatch) {
	    	completeMatch = false;
	    	JOptionPane.showMessageDialog(null, "Invalid Number of products");
	    }

	    return completeMatch;
	}

public boolean validateID(String number) {
    
	Pattern numpattern = Pattern.compile("^[0-9]+$");
    Matcher nummatcher = numpattern.matcher(number);
    boolean nummatch = nummatcher.find();
	
    
    
    
    boolean completeMatch = true;
    if(!nummatch) {
    	completeMatch = false;
    	JOptionPane.showMessageDialog(null, "Invalid Invoice ID");
    }

    return completeMatch;
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*METHOD TO VALIDATE THE TEXT AND AVAILABILITY OF CUSTOMER AND PRODUCT*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public boolean validatePresence(String testname, String testproduct, String availability) {
    
	Pattern namepattern = Pattern.compile("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$");
    Matcher namematcher = namepattern.matcher(testname);
    boolean namematch = namematcher.find();
	
    Pattern productpattern = Pattern.compile("^(?!^$)([^\\s])");
    Matcher productmatcher = productpattern.matcher(testproduct);
    boolean productmatch = productmatcher.find();
    
    boolean completeMatch = true;
    String no = "No";
    if(!namematch) {
    	completeMatch = false;
    	JOptionPane.showMessageDialog(null, "Customer does not exist");
    }

    else if(!productmatch) {
    	completeMatch = false;
    	JOptionPane.showMessageDialog(null, "Product does not exist");
    }
    else if (availability.equals(no)) {//if the availability text matches the word no
    	completeMatch = false;
    	JOptionPane.showMessageDialog(null, "Product is not available");
    }
    return completeMatch;
}
}
