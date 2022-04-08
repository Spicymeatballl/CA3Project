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
public class test extends JFrame{

	private JFrame frmInvoice;
	private JTextField prodnamefield;
	private JTextField perfield;
	private JButton amendButton;
	private JButton btnNewButton_1;
	private JLabel lblAddress;
	private JLabel lblNewLabel_1;
	private JTable producttable;
	private JScrollPane scrollPane;
	private JPanel Search_1;
	private JTextField pIDfield;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
					//frame.frmProducts.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
		Connect();
		table_load();
	}
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JButton btnNewButton_2;
	private JTextField numfield;
	private JTextField totalfield;
	private JTextField custnamefield;
	private JTextField cIDfield;
	private JLabel lblNewLabel_2_3;
	private JTextField invnumfield;
	private JScrollPane scrollPane_1;
	
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
	
	public void table_load()
	{
		try
		{
		/*	pst= con.prepareStatement("select invoice.Invoice_Number,invoice.Customer_ID,customer.Customer_name,"
					+ "invoice.Product_ID,product.product_name,invoice.product_count,invoice.total_cost,invoice.date_created from invoice inner join"
					+ " customer on invoice.Customer_ID = customer.Customer_ID inner join product on invoice.Product_ID"
					+ " = product.product_ID order by invoice.Invoice_Number");
					
			*/
	pst =con.prepareStatement("select *from invoice;");
			/*pst= con.prepareStatement("select invoice.Invoice_Number,invoice.Customer_ID,customer.Customer_name"
					+ " from invoice inner join"
					+ " customer on invoice.Customer_ID = customer.Customer_ID");
					*/
			rs = pst.executeQuery ();
			producttable.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frmInvoice = new JFrame();
		frmInvoice.setTitle("Invoice");
		frmInvoice.setVisible(true);
		frmInvoice.setBounds(100, 100, 943, 647);
		frmInvoice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInvoice.getContentPane().setLayout(null);
		
		prodnamefield = new JTextField();
		prodnamefield.setBounds(142, 408, 91, 19);
		prodnamefield.setEditable(false);
		prodnamefield.setColumns(10);
		frmInvoice.getContentPane().add(prodnamefield);
		
		perfield = new JTextField();
		perfield.setBounds(142, 439, 91, 19);
		perfield.setEditable(false);
		perfield.setColumns(10);
		frmInvoice.getContentPane().add(perfield);
		
		JLabel lblCustomerName = new JLabel("Product Name");
		lblCustomerName.setBounds(7, 411, 113, 13);
		frmInvoice.getContentPane().add(lblCustomerName);
		
		btnNewButton_1 = new JButton("Create Invoice");
		btnNewButton_1.setBounds(407, 548, 193, 42);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String custid, custname, prodid,prodname,count,cost;
				
				custid = cIDfield.getText();
				custname = custnamefield.getText();
				prodid = pIDfield.getText();
				prodname = prodnamefield.getText();
				count = numfield.getText();
				cost = totalfield.getText();
				
				java.util.Date date=new java.util.Date();
				java.sql.Date sqlDate=new java.sql.Date(date.getTime());
				
				boolean validnumber = validateNumber(count);
				boolean custprod = validatePresence(custname,prodname);
				if(validnumber && custprod) {
				
			try {
				pst = con.prepareStatement("insert into invoice(Customer_ID,Product_ID,product_count,total_cost,date_created,Customer_name,Product_name) values(?,?,?,?,?,?,?)");
				
				pst.setString(1,custid);
				pst.setString(2,prodid);
				pst.setString(3,count);
				pst.setString(4,cost);
				pst.setDate(5,sqlDate);
				pst.setString(6, custname);
				pst.setString(7, prodname);
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
		frmInvoice.getContentPane().add(btnNewButton_1);
		
		lblAddress = new JLabel("Price per Unit");
		lblAddress.setBounds(7, 445, 113, 13);
		frmInvoice.getContentPane().add(lblAddress);
		
		lblNewLabel_1 = new JLabel("Invoice Table");
		lblNewLabel_1.setBounds(7, 7, 654, 25);
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Jiren\\eclipse-workspace\\Software Development\\src\\Database\\search.png"));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frmInvoice.getContentPane().add(lblNewLabel_1);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(7, 36, 915, 368);
		frmInvoice.getContentPane().add(scrollPane_1);
		
		scrollPane = new JScrollPane();
		scrollPane_1.setViewportView(scrollPane);
		
		producttable = new JTable();
		scrollPane.setViewportView(producttable);
		
		Search_1 = new JPanel();
		Search_1.setBounds(7, 476, 226, 114);
		Search_1.setBorder(new TitledBorder(null, "Search Tool", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmInvoice.getContentPane().add(Search_1);
		Search_1.setLayout(null);
		
		JPanel Search = new JPanel();
		Search.setBounds(10, 15, 191, 66);
		Search_1.add(Search);
		Search.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Product ID");
		lblNewLabel_2.setBounds(10, 37, 84, 22);
		Search.add(lblNewLabel_2);
		
		pIDfield = new JTextField();
		pIDfield.setBounds(89, 39, 96, 19);
		pIDfield.addKeyListener(new KeyAdapter()
		
		{ 
			public void keyReleased(KeyEvent e)
 
			{
				
				try 
				{
					String product_id= pIDfield.getText();
					pst =con.prepareStatement("select invoice.Product_ID,product.product_name, product.Price_per_Unit from invoice "
							+ "inner join product on invoice.Product_ID = product.product_ID where invoice.product_ID=?");
					pst.setString(1, product_id);
					ResultSet rs =pst.executeQuery();
					  
			if(rs.next () == true)
			{
				
				String productname = rs.getString(2);
				String cost = rs.getString(3);
				
				
				
				prodnamefield.setText(productname);
				perfield.setText(cost);
				
				
				
			}
			else
			{
				prodnamefield.setText("");
				perfield.setText("");

				
			}
				}
				
				catch(SQLException ex) {
					}
				
				
			} 
			
		}
		);
		Search.add(pIDfield);
		pIDfield.setColumns(10);
		
		cIDfield = new JTextField();
		cIDfield.setColumns(10);
		cIDfield.setBounds(89, 10, 96, 19);
		cIDfield.addKeyListener(new KeyAdapter()
		
		{ 
			public void keyReleased(KeyEvent e)
 
			{
			
				try 
				{
					String customer_id= cIDfield.getText();
					pst =con.prepareStatement("select invoice.Customer_ID,customer.Customer_name from invoice "
							+ "inner join customer on invoice.Customer_ID = customer.Customer_ID where invoice.Customer_ID=?");
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
		
		
		
		btnNewButton_2 = new JButton("Back");
		btnNewButton_2.setBounds(726, 548, 196, 42);
		frmInvoice.getContentPane().add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmInvoice.dispose();
				mainMenu frame = new mainMenu();
			}
		});
		
		numfield = new JTextField();
		numfield.setBounds(508, 439, 92, 19);
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
		totalfield.setBounds(508, 476, 92, 19);
		totalfield.setEditable(false);
		totalfield.setColumns(10);
		frmInvoice.getContentPane().add(totalfield);
		
		custnamefield = new JTextField();
		custnamefield.setBounds(508, 408, 92, 19);
		custnamefield.setEditable(false);
		custnamefield.setColumns(10);
		frmInvoice.getContentPane().add(custnamefield);
		
		JLabel lblCustomerName_1 = new JLabel("Customer Name");
		lblCustomerName_1.setBounds(324, 411, 108, 13);
		frmInvoice.getContentPane().add(lblCustomerName_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("Number of Units");
		lblNewLabel_2_2.setBounds(324, 439, 74, 25);
		frmInvoice.getContentPane().add(lblNewLabel_2_2);
		
		lblNewLabel_2_3 = new JLabel("Total Cost");
		lblNewLabel_2_3.setBounds(324, 476, 108, 13);
		frmInvoice.getContentPane().add(lblNewLabel_2_3);
		
		JPanel Search_1_1 = new JPanel();
		Search_1_1.setBounds(618, 408, 304, 110);
		Search_1_1.setLayout(null);
		Search_1_1.setBorder(new TitledBorder(null, "Invoice Deletion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmInvoice.getContentPane().add(Search_1_1);
		
		JPanel Search_2 = new JPanel();
		Search_2.setLayout(null);
		Search_2.setBounds(6, 15, 263, 66);
		Search_1_1.add(Search_2);
		
		JLabel invoicenumlabel = new JLabel("Invoice Number");
		invoicenumlabel.setBounds(10, 10, 108, 13);
		Search_2.add(invoicenumlabel);
		
		invnumfield = new JTextField();
		invnumfield.setBounds(0, 33, 96, 19);
		Search_2.add(invnumfield);
		invnumfield.setColumns(10);
		
		amendButton = new JButton("Delete Invoice");
		amendButton.setBounds(128, 10, 124, 42);
		Search_2.add(amendButton);
		amendButton.addActionListener(new ActionListener() {
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
		producttable.setModel(new DefaultTableModel());
	}
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
public boolean validatePresence(String testname, String testproduct) {
    
	Pattern namepattern = Pattern.compile("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$");
    Matcher namematcher = namepattern.matcher(testname);
    boolean namematch = namematcher.find();
	
    Pattern productpattern = Pattern.compile("^(?!^$)([^\\s])");
    Matcher productmatcher = productpattern.matcher(testproduct);
    boolean productmatch = productmatcher.find();
    
    
    
    boolean completeMatch = true;
    if(!namematch) {
    	completeMatch = false;
    	JOptionPane.showMessageDialog(null, "Customer does not exist");
    }

    else if(!productmatch) {
    	completeMatch = false;
    	JOptionPane.showMessageDialog(null, "Product does not exist");
    }
    return completeMatch;
}
}
