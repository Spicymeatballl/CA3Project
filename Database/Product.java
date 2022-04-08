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
import javax.swing.JRadioButton;
import javax.swing.*;    
public class Product extends JFrame{

	private JFrame frmProducts;
	private JTextField productfield;
	private JTextField categoryfield;
	private JTextField manufield;
	private JButton deletebutton;
	private JButton createbutton;
	private JTextField costfield;
	private JLabel pricelabel;
	private JLabel Product_Title;
	private JTable producttable;
	private JScrollPane scrollPane;
	private JPanel Search_1;
	private JTextField idfield;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Product frame = new Product();
					//frame.frmProducts.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*Product object*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Product() {
		initialize();
		Connect();
		table_load();
	}
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JButton updatebutton;
	private JButton backbutton;
	private JTextField availtext;
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*CONNECTING TO THE DATABASE*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
			pst= con.prepareStatement("select * from product");
			rs = pst.executeQuery ();
			producttable.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*INITIALIZING THE PRODUCT DATABSE EDITOR FRAME*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void initialize() {
		frmProducts = new JFrame();//creating the frame
		frmProducts.setTitle("Tesco Database");
		frmProducts.setVisible(true);
		frmProducts.setBounds(100, 100, 934, 512);
		frmProducts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProducts.getContentPane().setLayout(null);
		
		productfield = new JTextField();
		productfield.setBounds(146, 55, 109, 19);
		productfield.setColumns(10);
		frmProducts.getContentPane().add(productfield);
		
		categoryfield = new JTextField();
		categoryfield.setBounds(146, 96, 109, 19);
		categoryfield.setColumns(10);
		frmProducts.getContentPane().add(categoryfield);
		
		manufield = new JTextField();
		manufield.setBounds(146, 132, 109, 19);
		manufield.setColumns(10);
		frmProducts.getContentPane().add(manufield);
		
		JLabel productlabel = new JLabel("Product Name");
		productlabel.setBounds(7, 55, 117, 13);
		frmProducts.getContentPane().add(productlabel);
		
		JLabel categorylabel = new JLabel("Category");
		categorylabel.setBounds(7, 99, 117, 13);
		frmProducts.getContentPane().add(categorylabel);
		
		JLabel manufacturerlabel = new JLabel("Manufacturer");
		manufacturerlabel.setBounds(7, 135, 117, 13);
		frmProducts.getContentPane().add(manufacturerlabel);
		
		costfield = new JTextField();
		costfield.setBounds(146, 168, 109, 19);
		costfield.setColumns(10);
		frmProducts.getContentPane().add(costfield);
		
		pricelabel = new JLabel("Price per Unit");
		pricelabel.setBounds(7, 171, 117, 13);
		frmProducts.getContentPane().add(pricelabel);
		
		Product_Title = new JLabel("Product Table");
		Product_Title.setBounds(7, 7, 621, 25);
		Product_Title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frmProducts.getContentPane().add(Product_Title);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(270, 7, 643, 434);
		frmProducts.getContentPane().add(scrollPane);
		
		producttable = new JTable();
		scrollPane.setViewportView(producttable);
		
		Search_1 = new JPanel();
		Search_1.setBounds(7, 286, 248, 117);
		Search_1.setBorder(new TitledBorder(null, "Search Tool", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmProducts.getContentPane().add(Search_1);
		Search_1.setLayout(null);
		
		JPanel Search = new JPanel();
		Search.setBounds(6, 15, 235, 91);
		Search_1.add(Search);
		Search.setLayout(null);
		
		JLabel idlabel = new JLabel("Product ID");
		idlabel.setBounds(10, 37, 84, 22);
		Search.add(idlabel);
		
		deletebutton = new JButton("Delete");
		deletebutton.setBounds(483, 445, 168, 23);
		deletebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String ID ;
				
				
				ID = idfield.getText();
				
				try {
					pst = con.prepareStatement(" delete from product where product_ID=? ");
					
					pst.setString(1, ID);
					
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted");
					
					table_load();
					
					productfield.setText("");
					categoryfield.setText("");
					manufield.setText("");
					costfield.setText("");
					
					idfield.requestFocus();
					
				}
				
				catch(SQLException e1) 
				{
					
					e1.printStackTrace();
				}
				}
			
		});
		frmProducts.getContentPane().add(deletebutton);
		
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*CREATE BUTTON METHOD*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		createbutton = new JButton("Create product");
		createbutton.setBounds(36, 445, 146, 23);
		
		createbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String productname, category, manufacturer, price, ID, available;
				ID = idfield.getText();
				productname = productfield.getText();
				category = categoryfield.getText();
				manufacturer =manufield.getText();
				price = costfield.getText();
				available = availtext.getText();
				   
					
				boolean Idnotpresent = validateID(ID);
			
				boolean validation = validateProduct(productname,category,manufacturer,price);
				if(!Idnotpresent) {
				if(validation) {
				
			try {
				pst = con.prepareStatement("insert into product(product_name,category,manufacturer,Price_per_Unit,Availability) values(?,?,?,?,?)");
				
				pst.setString(1,productname);
				pst.setString(2,category);
				pst.setString(3,manufacturer);
				pst.setString(4, price);
				pst.setString(5, available);
				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Record Added");
				table_load();
				
				productfield.setText("");
				categoryfield.setText("");
				manufield.setText("");
				costfield.setText("");
				
				
				productfield.requestFocus();
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
				else{
					JOptionPane.showMessageDialog(null, "Clear ID field");
				}
				
			
			}
			
			
			
			
			
		});
		frmProducts.getContentPane().add(createbutton);
		

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*METHOD TO PUT DATA INTO JTEXTFIELD USING ONLY PRODUCT ID*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		idfield = new JTextField();
		idfield.setBounds(89, 39, 96, 19);
		idfield.addKeyListener(new KeyAdapter()
		
		{ 
			public void keyReleased(KeyEvent e)
 
			{
			
				try 
				{
					String product_id= idfield.getText();
					pst =con.prepareStatement("select product_ID,product_name,category,manufacturer, Price_per_Unit, Availability from product where product_ID=?");
					pst.setString(1, product_id);
					ResultSet rs =pst.executeQuery();
					  
			if(rs.next () == true)
			{
				
				String productname = rs.getString(2);
				String category = rs.getString(3);
				String manufacturer = rs.getString(4);
				String cost = rs.getString(5);
				String available = rs.getString(6);
				
				
				
				productfield.setText(productname);
				categoryfield.setText(category);
				manufield.setText(manufacturer);
				costfield.setText(cost);
				availtext.setText(available);
				
				
				
			}
			else
			{
				productfield.setText("");
				categoryfield.setText("");
				manufield.setText("");
				costfield.setText("");
				
			}
				}
				
				catch(SQLException ex) {
					}
				
				
			} 
			
		}
		);
		Search.add(idfield);
		idfield.setColumns(10);
		
		
		
		backbutton = new JButton("Back");
		backbutton.setBounds(739, 445, 174, 23);
		frmProducts.getContentPane().add(backbutton);
		backbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmProducts.dispose();
				mainMenu frame = new mainMenu();
			}
		});
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*UPDATE METHOD*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		updatebutton = new JButton("Update");
		updatebutton.setBounds(270, 445, 153, 23);
		frmProducts.getContentPane().add(updatebutton);
		
		
		updatebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String productname, category, manufacturer, price, ID,available;
				
				productname = productfield.getText();
				category = categoryfield.getText();
				manufacturer =manufield.getText();
				price = costfield.getText();
				available = availtext.getText();
				ID =idfield.getText();
				boolean validation = validateProduct(productname,category,manufacturer,price);
				boolean IDpresent = validateID(ID);
				if (IDpresent) {
				if(validation) {
			try {
				pst = con.prepareStatement("update product set product_name=?,category=?,manufacturer=?,Price_per_Unit=?,Availability=? where product_ID=?");
				
				pst.setString(1,productname);
				pst.setString(2,category);
				pst.setString(3,manufacturer);
				pst.setString(4, price);
				pst.setString(5, available);
				pst.setString(6, ID);
				
				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Record Updated");
				table_load();
				
				productfield.setText("");
				categoryfield.setText("");
				manufield.setText("");
				costfield.setText("");
				idfield.setText("");
				idfield.requestFocus();
				
				
				productfield.requestFocus();
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
					JOptionPane.showMessageDialog(null, "No matching Product ID");
				}
			}
			
			
			
			
		});
		
		JLabel availability = new JLabel("Availability");
		availability.setBounds(7, 206, 117, 13);
		frmProducts.getContentPane().add(availability);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*RADIOBUTTON MECHANISM*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JRadioButton yesbutton = new JRadioButton("Yes");//yes option
		yesbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				availtext.setText("Yes");
			}
		});
		yesbutton.setSelected(true);
		yesbutton.setBounds(201, 202, 54, 21);
		frmProducts.getContentPane().add(yesbutton);
		
		JRadioButton notbutton = new JRadioButton("No");//no option
		notbutton.setBounds(201, 227, 54, 21);
		frmProducts.getContentPane().add(notbutton);
		notbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				availtext.setText("No");
			}
		});
		ButtonGroup bg=new ButtonGroup();    
		bg.add(yesbutton);
		bg.add(notbutton); 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*Set default availability value to Yes*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		availtext = new JTextField();
		availtext.setText("Yes");
		availtext.setEditable(false);
		availtext.setBounds(146, 203, 44, 19);
		frmProducts.getContentPane().add(availtext);
		availtext.setColumns(10);

		
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*METHOD TO VALIDATE THE PRODUCT DETAILS*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public boolean validateProduct(String testproduct,String testcategory,String testmanufacturer, String testprice) {
	    
		Pattern productpattern = Pattern.compile("^(?!^$)([^\\s])");
	    Matcher productmatcher = productpattern.matcher(testproduct);
	    boolean productmatch = productmatcher.find();
		
	    
		Pattern categorypattern = Pattern.compile("^[a-zA-Z\\s]+$");
	    Matcher categorymatcher = categorypattern.matcher(testcategory);
	    boolean categorymatch = categorymatcher.find();
	    
	    Pattern manufacturerpattern = Pattern.compile("^(?!^$)([^\\s])");
	    Matcher manufacturermatcher = manufacturerpattern.matcher(testmanufacturer);
	    boolean manufacturermatch = manufacturermatcher.find();
	    
	    Pattern pricepattern = Pattern.compile("^[0-9]+\\.?[0-9]*$");
	    Matcher pricematcher = pricepattern.matcher(testprice);
	    boolean pricematch = pricematcher.find();
	    
	    
	    boolean completeMatch = true;
	    if(!productmatch) {
	    	completeMatch = false;
	    	JOptionPane.showMessageDialog(null, "Invalid Product Name");
	    }
	    else if(!categorymatch) {
	    	completeMatch = false;
	    	
	    	JOptionPane.showMessageDialog(null, "Invalid Category");
	    }
	    else if(!manufacturermatch) {
	    	completeMatch = false;
	    	
	    	JOptionPane.showMessageDialog(null, "Invalid manufacturer");
	    }
	    else if(!pricematch) {
	    	completeMatch = false;
	    	
	    	JOptionPane.showMessageDialog(null, "Invalid price");
	    }


	    return completeMatch;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*METHOD TO PUT VALIDATE THE ID TEXT*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public boolean validateID(String testid) {
    
	Pattern idpattern = Pattern.compile("^[0-9]+$");
    Matcher idmatcher = idpattern.matcher(testid);
    boolean idmatch = idmatcher.find();
    boolean completeMatch = true;
    if(!idmatch) {
    	completeMatch = false;
    }
    return completeMatch;
}
	}

