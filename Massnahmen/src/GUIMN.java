import java.awt.EventQueue;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.text.ParsePosition;

import javax.swing.Action;

import java.awt.event.ActionListener;
import java.sql.*;
import java.io.*;

import javax.swing.JFormattedTextField;

import oracle.jdbc.OracleResultSet;
import oracle.sql.NUMBER;

import com.sun.rowset.CachedRowSetImpl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.awt.Font;

public class GUIMN extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String fertigungsauftrag;
	
	private JPanel contentPane;
	private JTextField txtFertigungsauftrag;
	private JLabel lblFertigungsauftrag;
	private JButton btnSuchen;
//	private final Action action = new SwingAction();
	private JButton btnMNAbschliessen;
	private JButton btnMNoffen;
	private JList list;
	private DefaultListModel listModel;
	
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	CachedRowSet myCachedRowSet;
	
	
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMN frame = new GUIMN();
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIMN() {
		// Set Windows title
		super("Maﬂnahmen abschlieﬂen");
		connectToDB();
		
		// Close connections exit the application when the user
		// closes the window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					con.close();
				} catch (SQLException sqle) {
					printSQLException(sqle);
				}
				System.exit(0);
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 254);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JPanel panelInput = new JPanel(new GridBagLayout());
				
		lblFertigungsauftrag = new JLabel("Fertigungsauftrag"); 
		GridBagConstraints gbc_lblFertigungsauftrag = new GridBagConstraints();
		gbc_lblFertigungsauftrag.gridwidth = 1;
		gbc_lblFertigungsauftrag.insets = new Insets(5, 5, 5, 5);
		gbc_lblFertigungsauftrag.anchor = GridBagConstraints.EAST;
		gbc_lblFertigungsauftrag.gridx = 0;
		gbc_lblFertigungsauftrag.gridy = 0;
		panelInput.add(lblFertigungsauftrag, gbc_lblFertigungsauftrag);
		
		txtFertigungsauftrag = new JTextField();
		txtFertigungsauftrag.setText("1234567");
		GridBagConstraints gbc_txtFertigungsauftrag = new GridBagConstraints();
		gbc_txtFertigungsauftrag.gridwidth = 1;
		gbc_txtFertigungsauftrag.insets = new Insets(5, 5, 5, 5);
		gbc_txtFertigungsauftrag.anchor = GridBagConstraints.WEST;
		gbc_txtFertigungsauftrag.gridx = 1;
		gbc_txtFertigungsauftrag.gridy = 0;
		panelInput.add(txtFertigungsauftrag, gbc_txtFertigungsauftrag);
		
		JPanel panelSuchen = new JPanel(new FlowLayout());
		
		btnSuchen = new JButton("Auftrag suchen...");
		btnSuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fertigungsauftrag = txtFertigungsauftrag.getText();
				if (checkConditions()) {
					// Data is OK, now search for all serial numbers to this FAUF 
				    searchSerialNumbers();
//					System.out.println("Done");
					
				}
			}
		});
		panelSuchen.add(btnSuchen);
		
		JPanel panelMassnahmen = new JPanel(new BorderLayout());
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setSelectedIndex(0);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(10);
	    JScrollPane spList = new JScrollPane(list);
		panelMassnahmen.add(spList, BorderLayout.CENTER);
		
		JPanel panelButton = new JPanel(new FlowLayout());
		
		btnMNAbschliessen = new JButton("MN abschlieﬂen");
		btnMNAbschliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeActions();
			}
		});
		panelButton.add(btnMNAbschliessen);
		btnMNoffen= new JButton("MN ˆffnen");
		btnMNoffen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openActions();
			}
		});
		panelButton.add(btnMNoffen);
		
		JPanel panelCopyright = new JPanel(new FlowLayout());
		
		JLabel lblcopyright = new JLabel("Vers. 1.0 \u00A9 Matthias Weg, 22.8.2014");
		lblcopyright.setFont(new Font("Tahoma", Font.PLAIN, 10));
		//lblcopyright.setHorizontalAlignment(SwingConstants.EAST);
		panelCopyright.add(lblcopyright);
		
		contentPane.add(panelInput);
		contentPane.add(panelSuchen);
		contentPane.add(panelMassnahmen);
		contentPane.add(panelButton);
		contentPane.add(panelCopyright);
	}
	
	
    
	public CachedRowSet getContentsOfQueryTable() throws SQLException {
        CachedRowSet crs = null;
        try {
        	crs = new CachedRowSetImpl();
        	crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
        	crs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
        	crs.setUsername("QSYSTEST");
        	crs.setPassword("qsys");
        	crs.setUrl("jdbc:oracle:thin:@atdotrsr26:1521/UNITEST");
        	crs.setCommand("select * from tri_pl_geraete order by id desc");
        	crs.execute();
        } catch (SQLException e) {
        	printSQLException(e);
        }
        return crs;
	}

//	private class SwingAction extends AbstractAction {
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;
//		public SwingAction() {
//			putValue(NAME, "Daten speichern");
//			putValue(SHORT_DESCRIPTION, "Speichern der eingegebenen Daten in CAQ");
//		}
//		public void actionPerformed(ActionEvent e) {
//			
//		}
//	}
	
//	public void writeToFile() {
//		try {
//			File file = new File("\\\\atdotrsr26\\QSYSTESTDATA\\NC\\FILES\\HOST\\NC_RQMS_STAMM.DAT");
//			// if file doesn't exists, then create it
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//			FileWriter fw = new FileWriter(file.getAbsoluteFile());
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write("UI");
//			bw.close();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}
	
	public boolean connectToDB() {
		try	{
		    Class.forName("oracle.jdbc.driver.OracleDriver");
		    String url = "jdbc:oracle:thin:@atdotrsr26:1521/UNITEST"; //SID
            String usr = "QSYSTEST";
            String pwd = "qsys";
		    con = DriverManager.getConnection(url, usr, pwd);
		    return true;
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(), "Verbindungsfehler!",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		} catch(SQLException e) { 
			JOptionPane.showMessageDialog(null,e.getMessage(), "Verbindungsfehler!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch(Exception e) { 
			JOptionPane.showMessageDialog(null,e.getMessage(), "Verbindungsfehler!",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		} 	
	}
	
	private boolean closeConnection() {
		try {
            con.close();
            return true;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null,e.getMessage(), "Fehler beim Schlieﬂen der Datenbankverbindung!",JOptionPane.ERROR_MESSAGE);
        	e.printStackTrace();
            return false;
        } 
	}

	private void searchSerialNumbers() {
		String query = null;
		boolean dataFound = false;
		try {
			query = "select charge.schargennr as ChargenNr, " +
					"case when rqms_mass.nlfdmasnr is null then \'null\' else case when rqms_mass.dterledigtam is null then \'offen\' else \'abgeschl.\' end end as MN " +
					"from charge " +
					"left outer join rqms_pos p1 on charge.NLFDCHARGENNR = p1.NLFDCHARGENNR " +
					"left outer join rqms_pos p2 on p1.nrqnr = p2.nrqnr and p1.nlfdposnr = p2.nlfdposnrref " +
					"left outer join rqms_mass on p2.nrqnr = rqms_mass.nrqnr and p2.nlfdposnr = rqms_mass.nlfdposnr and rqms_mass.nlfdmasnr = 299001 " +
					"where schargennr like \'" + fertigungsauftrag + "-A%\'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			listModel.clear();
			while (rs.next()) {
				dataFound = true;
				// Fill the panelMassnahmen
				listModel.addElement(rs.getString(1) + "     " + rs.getString(2));
			}
		} catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	if (stmt != null) { 
        		try {
        			stmt.close();
        		} catch (SQLException e) {
        			e.printStackTrace();
        		} 
        	}
        }
		if (!dataFound) {
			JOptionPane.showMessageDialog(null,
				    "Keine Maﬂnahmen vorhanden",
				    "Maﬂnahmen zum FAUF",
				    JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void closeActions() {
		// Get marked actions from the list and close them in RQMS
		// First we get the marked items from the list and cycle through it
		List<String> values = list.getSelectedValuesList();
		boolean dataFound = false;
		String query;
		long nrqnr_long;
		long nlfdposnr_long;
		long nlfdmasnr_long;
		int nUpdated = 0;
		
		try {
			for(Iterator<String> i = values.iterator(); i.hasNext(); ) {
			    String item = i.next();
			    String[] parts = item.split("     ");
			    String part1 = parts[0]; // 1234567-A111111
			    String part2 = parts[1]; // abgeschl. etc.
			    // Now close the action on the database.
		    	query = "select rqms_mass.nrqnr, rqms_mass.nlfdposnr, rqms_mass.nlfdmasnr " +
		    		"from rqms_mass " +
		    		"join rqms_pos p2 on rqms_mass.nrqnr = p2.nrqnr and rqms_mass.nlfdposnr = p2.nlfdposnr " +
		    		"join rqms_pos p1 on p2.nrqnr = p1.nrqnr and p2.nlfdposnrref = p1.nlfdposnr " +
		    		"join charge on p1.NLFDCHARGENNR = charge.NLFDCHARGENNR " +
		    		"where charge.SCHARGENNR = \'" + part1 + "\'";
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					dataFound = true;
					// Close the action
					nrqnr_long = rs.getLong(1);
					nlfdposnr_long = rs.getLong(2);
					nlfdmasnr_long = rs.getLong(3);
					query = "update rqms_mass " +
							"set dterledigtam = sysdate, " + 
							"NPERSERLEDIGT = 1 " + 
							"where nrqnr = " + nrqnr_long + 
							" and nlfdposnr = " + nlfdposnr_long + 
							" and nlfdmasnr = " + nlfdmasnr_long;
					stmt = con.createStatement();
					rs = stmt.executeQuery(query);
					nUpdated++;					
				}
			}
			searchSerialNumbers();
			JOptionPane.showMessageDialog(null,
				    nUpdated + " Maﬂnahmen wurden abgeschlossen.",
				    "Anzahl Maﬂnahmen abgeschlossen",
				    JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void openActions() {
		// TODO open the actions
		// Get marked actions from the list and open them in RQMS
		// First we get the marked items from the list and cycle through it
		List<String> values = list.getSelectedValuesList();
		boolean dataFound = false;
		String query;
		long nrqnr_long;
		long nlfdposnr_long;
		long nlfdmasnr_long;
		int nUpdated = 0;
		
		try {
			for(Iterator<String> i = values.iterator(); i.hasNext(); ) {
			    String item = i.next();
			    String[] parts = item.split("     ");
			    String part1 = parts[0]; // 1234567-A111111
			    String part2 = parts[1]; // abgeschl. etc.
			    // Now close the action on the database.
		    	query = "select rqms_mass.nrqnr, rqms_mass.nlfdposnr, rqms_mass.nlfdmasnr " +
		    		"from rqms_mass " +
		    		"join rqms_pos p2 on rqms_mass.nrqnr = p2.nrqnr and rqms_mass.nlfdposnr = p2.nlfdposnr " +
		    		"join rqms_pos p1 on p2.nrqnr = p1.nrqnr and p2.nlfdposnrref = p1.nlfdposnr " +
		    		"join charge on p1.NLFDCHARGENNR = charge.NLFDCHARGENNR " +
		    		"where charge.SCHARGENNR = \'" + part1 + "\'";
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				if (rs.next()) {
					dataFound = true;
					// open the action
					nrqnr_long = rs.getLong(1);
					nlfdposnr_long = rs.getLong(2);
					nlfdmasnr_long = rs.getLong(3);
					query = "update rqms_mass " +
							"set dterledigtam = NULL, " + 
							"NPERSERLEDIGT = NULL " + 
							"where nrqnr = " + nrqnr_long + 
							" and nlfdposnr = " + nlfdposnr_long + 
							" and nlfdmasnr = " + nlfdmasnr_long;
					stmt = con.createStatement();
					rs = stmt.executeQuery(query);
					nUpdated++;					
				}
			}
			searchSerialNumbers();
			JOptionPane.showMessageDialog(null,
				    nUpdated + " Maﬂnahmen wurden wieder geˆffnet.",
				    "Anzahl Maﬂnahmen geˆffnet",
				    JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean checkConditions() {
	// Check if data input is OK.
		// Feritungsauftrag = nummerisch?
		if (!isNumeric(fertigungsauftrag)) {
			return false;
		}
		
		// Fertigungsauftrag im erlaubten Bereich?
		int num = Integer.parseInt(fertigungsauftrag);
		if ((num < 700000) || (num > 2000000)) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isNumeric(String str)
	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  if (str.isEmpty()) {
		  return false;
	  }
	  else {
		  formatter.parse(str, pos);
		  return str.length() == pos.getIndex();
	  }
	}
	
	public static boolean ignoreSQLException(String sqlState) {
		if (sqlState == null) {
			System.out.println("The SQL state is not defined!");
			return false;
		}
		// X0Y32: Jar file already exists in schema
		if (sqlState.equalsIgnoreCase("X0Y32"))
			return true;
		// 42Y55: Table already exists in schema
		if (sqlState.equalsIgnoreCase("42Y55"))
			return true;
		return false;
	}
	
	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				if (ignoreSQLException(((SQLException)e).getSQLState()) == false) {
					e.printStackTrace(System.err);
					System.err.println("SQLState: " + ((SQLException)e).getSQLState());
					System.err.println("Error Code: " + ((SQLException)e).getErrorCode());
					System.err.println("Message: " + e.getMessage());
					Throwable t = ex.getCause();
					while (t != null) {
						System.out.println("Cause: " + t);
						t = t.getCause();
					}
				}
			}
		}
	}
	
}
