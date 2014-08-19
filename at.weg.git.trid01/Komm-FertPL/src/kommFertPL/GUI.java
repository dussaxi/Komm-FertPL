package kommFertPL;

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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
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

import com.sun.rowset.CachedRowSetImpl;

import java.util.Date;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.awt.Font;

public class GUI extends JFrame implements RowSetListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String fertigungsauftrag;
	public String anzahl;
	public String anlage;
//	public SimpleDateFormat datum = new SimpleDateFormat ("yyyy-MM-dd");;
//	public Date datum;
	public String datum;
//	public SimpleDateFormat zeitMeldung = new SimpleDateFormat ("H:mm");;
//	public Date zeitMeldung;
	public String zeitMeldung;
	public String nameMA;

	private JPanel contentPane;
	private JTextField txtFertigungsauftrag;
	private JLabel lblFertigungsauftrag;
	private JTextField txtAnzahl;
	private JLabel lblAnzahl;
	private JTextField txtAnlage;
	private JLabel lblAnlage;
	private JFormattedTextField txtDatum;
	private JLabel lblDatum;
	private JFormattedTextField txtZeitAnlieferung;
	private JLabel lblZeitAnlieferung;
	private JTextField txtNameMA;
	private JLabel lblNameMA;
	private JButton btnDatenSpeichern;
	private final Action action = new SwingAction();
	
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	QueryTableModel myQueryTableModel;
	JTable table;
	
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		// Set Windows title
		super("Interface Fertigung - Prüflabor");
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
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
//		setContentPane(contentPane);
//		GridBagLayout gbl_contentPane = new GridBagLayout();
//		gbl_contentPane.columnWidths = new int[]{474, 0};
//		gbl_contentPane.rowHeights = new int[]{10, 190, 0};
//		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
//		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
//		contentPane.setLayout(gbl_contentPane);
		
		JPanel panelInput = new JPanel(new GridBagLayout());
				
//		GridBagConstraints gbc_panelInput = new GridBagConstraints();
//		gbc_panelInput.anchor = GridBagConstraints.PAGE_START;
//		gbc_panelInput.fill = GridBagConstraints.HORIZONTAL;
//		gbc_panelInput.insets = new Insets(0, 0, 5, 0);  // top, left, bottom, right
//		gbc_panelInput.gridx = 0;
//		gbc_panel.gridy = 0;
//		contentPane.setLayout(new GridBagLayout());
		
//		JPanel panel_1 = new JPanel();
//		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
//		gbc_panel_1.fill = GridBagConstraints.BOTH;
//		gbc_panel_1.gridx = 0;
//		gbc_panel_1.gridy = 1;
//		contentPane.add(panel_1, gbc_panel_1);
//		GridBagLayout gbl_panel_1 = new GridBagLayout();
//		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
//		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
//		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
//		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
//		panel_1.setLayout(gbl_panel_1);
		
		lblFertigungsauftrag = new JLabel("Fertigungsauftrag"); 
		GridBagConstraints gbc_lblFertigungsauftrag = new GridBagConstraints();
		gbc_lblFertigungsauftrag.gridwidth = 1;
		gbc_lblFertigungsauftrag.insets = new Insets(0, 0, 5, 5);
		gbc_lblFertigungsauftrag.anchor = GridBagConstraints.EAST;
		gbc_lblFertigungsauftrag.gridx = 0;
		gbc_lblFertigungsauftrag.gridy = 0;
		//gbc_lblFertigungsauftrag.fill = GridBagConstraints.HORIZONTAL;
		panelInput.add(lblFertigungsauftrag, gbc_lblFertigungsauftrag);
		
		txtFertigungsauftrag = new JTextField();
		txtFertigungsauftrag.setText("1234567");
		GridBagConstraints gbc_txtFertigungsauftrag = new GridBagConstraints();
		gbc_txtFertigungsauftrag.gridwidth = 1;
		gbc_txtFertigungsauftrag.insets = new Insets(0, 0, 5, 5);
		gbc_txtFertigungsauftrag.anchor = GridBagConstraints.WEST;
		gbc_txtFertigungsauftrag.gridx = 1;
		gbc_txtFertigungsauftrag.gridy = 0;
		panelInput.add(txtFertigungsauftrag, gbc_txtFertigungsauftrag);
		
		lblAnzahl = new JLabel("Anzahl");
		GridBagConstraints gbc_lblAnzahl = new GridBagConstraints();
		gbc_lblAnzahl.gridwidth = 1;
		gbc_lblAnzahl.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnzahl.anchor = GridBagConstraints.EAST;
		gbc_lblAnzahl.gridx = 0;
		gbc_lblAnzahl.gridy = 1;
		//gbc_lblAnzahl.fill = GridBagConstraints.HORIZONTAL;
		panelInput.add(lblAnzahl, gbc_lblAnzahl);
		
		txtAnzahl = new JTextField();
		txtAnzahl.setText("10");
		GridBagConstraints gbc_txtAnzahl = new GridBagConstraints();
		gbc_txtAnzahl.gridwidth = 1;
		gbc_txtAnzahl.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnzahl.anchor = GridBagConstraints.WEST;
		gbc_txtAnzahl.gridx = 1;
		gbc_txtAnzahl.gridy = 1;
		panelInput.add(txtAnzahl, gbc_txtAnzahl);
		txtAnzahl.setColumns(10);
		
		lblAnlage = new JLabel("Anlage");
		GridBagConstraints gbc_lblAnlage = new GridBagConstraints();
		gbc_lblAnlage.gridwidth = 1;
		gbc_lblAnlage.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnlage.anchor = GridBagConstraints.EAST;
		gbc_lblAnlage.gridx = 0;
		gbc_lblAnlage.gridy = 2;
		//gbc_lblAnlage.fill = GridBagConstraints.HORIZONTAL;
		panelInput.add(lblAnlage, gbc_lblAnlage);
		
		txtAnlage = new JTextField();
		txtAnlage.setText("TEST1A");
		GridBagConstraints gbc_txtAnlage = new GridBagConstraints();
		gbc_txtAnlage.gridwidth = 1;
		gbc_txtAnlage.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnlage.anchor = GridBagConstraints.WEST;
		gbc_txtAnlage.gridx = 1;
		gbc_txtAnlage.gridy = 2;
		panelInput.add(txtAnlage, gbc_txtAnlage);
		txtAnlage.setColumns(10);
		
		lblDatum = new JLabel("Datum");
		GridBagConstraints gbc_lblDatum = new GridBagConstraints();
		gbc_lblDatum.gridwidth = 1;
		gbc_lblDatum.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatum.anchor = GridBagConstraints.EAST;
		gbc_lblDatum.gridx = 0;
		gbc_lblDatum.gridy = 3;
		//gbc_lblDatum.fill = GridBagConstraints.HORIZONTAL;
		panelInput.add(lblDatum, gbc_lblDatum);
		
		txtDatum = new JFormattedTextField(new SimpleDateFormat("d.M.yyyy"));
		txtDatum.setValue(new Date());
		GridBagConstraints gbc_txtDatum = new GridBagConstraints();
		gbc_txtDatum.gridwidth = 1;
		gbc_txtDatum.insets = new Insets(0, 0, 5, 5);
		gbc_txtDatum.anchor = GridBagConstraints.WEST;
		gbc_txtDatum.gridx = 1;
		gbc_txtDatum.gridy = 3;
		panelInput.add(txtDatum, gbc_txtDatum);
		txtDatum.setColumns(10);
		
		lblZeitAnlieferung = new JLabel("Zeit der Anlieferung");
		GridBagConstraints gbc_lblZeitAnlieferung = new GridBagConstraints();
		gbc_lblZeitAnlieferung.gridwidth = 1;
		gbc_lblZeitAnlieferung.insets = new Insets(0, 0, 5, 5);
		gbc_lblZeitAnlieferung.anchor = GridBagConstraints.EAST;
		gbc_lblZeitAnlieferung.gridx = 0;
		gbc_lblZeitAnlieferung.gridy = 4;
		//gbc_lblZeitAnlieferung.fill = GridBagConstraints.HORIZONTAL;
		panelInput.add(lblZeitAnlieferung, gbc_lblZeitAnlieferung);
		
		txtZeitAnlieferung = new JFormattedTextField(new SimpleDateFormat("H:mm"));
		txtZeitAnlieferung.setValue(new Date());
		GridBagConstraints gbc_txtZeitAnlieferung = new GridBagConstraints();
		gbc_txtZeitAnlieferung.gridwidth = 1;
		gbc_txtZeitAnlieferung.insets = new Insets(0, 0, 5, 5);
		gbc_txtZeitAnlieferung.anchor = GridBagConstraints.WEST;
		gbc_txtZeitAnlieferung.gridx = 1;
		gbc_txtZeitAnlieferung.gridy = 4;
		panelInput.add(txtZeitAnlieferung, gbc_txtZeitAnlieferung);
		txtZeitAnlieferung.setColumns(10);
		
		lblNameMA = new JLabel("Name MA");
		GridBagConstraints gbc_lblNameMA = new GridBagConstraints();
		gbc_lblNameMA.gridwidth = 1;
		gbc_lblNameMA.insets = new Insets(0, 0, 5, 5);
		gbc_lblNameMA.anchor = GridBagConstraints.EAST;
		gbc_lblNameMA.gridx = 0;
		gbc_lblNameMA.gridy = 5;
		//gbc_lblNameMA.fill = GridBagConstraints.HORIZONTAL;
		panelInput.add(lblNameMA, gbc_lblNameMA);
		
		txtNameMA = new JTextField();
		txtNameMA.setText("Matthias Weg");
		GridBagConstraints gbc_txtNameMA = new GridBagConstraints();
		gbc_txtNameMA.gridwidth = 1;
		gbc_txtNameMA.insets = new Insets(0, 0, 5, 5);
		gbc_txtNameMA.anchor = GridBagConstraints.WEST;
		gbc_txtNameMA.gridx = 1;
		gbc_txtNameMA.gridy = 5;
		panelInput.add(txtNameMA, gbc_txtNameMA);
		txtNameMA.setColumns(10);
		
		JPanel panelButtons = new JPanel(new FlowLayout());
		
		btnDatenSpeichern = new JButton("Daten Speichern");
		btnDatenSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fertigungsauftrag = txtFertigungsauftrag.getText();
				anzahl = txtAnzahl.getText();
				anlage = txtAnlage.getText();
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
				datum = DATE_FORMAT.format(txtDatum.getValue());
				SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
				zeitMeldung = TIME_FORMAT.format(txtZeitAnlieferung.getValue());
				nameMA = txtNameMA.getText();		
				if (checkConditions()) {
					// Data is OK, now save the Data.
					// writeToFile();
				    saveToDB();
					System.out.println("Done");
					table.setModel(myQueryTableModel);
				}
			}
		});
		btnDatenSpeichern.setAction(action);
		panelButtons.add(btnDatenSpeichern);
		
		JPanel panelTable = new JPanel(new BorderLayout());
		
	    CachedRowSet myCachedRowSet;
		try {
			myCachedRowSet = getContentsOfQueryTable();
			myQueryTableModel = new QueryTableModel(myCachedRowSet);
		    myQueryTableModel.addEventHandlersToRowSet(this);
		} catch (SQLException e1) {
			printSQLException(e1);
		}	    

	    table = new JTable(); // Displays the table
	    table.setModel(myQueryTableModel);
		
	    JScrollPane spTable = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		panelTable.add(spTable, BorderLayout.CENTER);
		
		JPanel panelCopyright = new JPanel(new FlowLayout());
		
		JLabel lblcopyright = new JLabel("Vers. 1.1 \u00A9 Matthias Weg, 5.8.2014");
		lblcopyright.setFont(new Font("Tahoma", Font.PLAIN, 10));
		//lblcopyright.setHorizontalAlignment(SwingConstants.EAST);
		panelCopyright.add(lblcopyright);
		
		contentPane.add(panelInput);
		contentPane.add(panelButtons);
		contentPane.add(panelTable);
		contentPane.add(panelCopyright);
	}
	
	// From http://docs.oracle.com/javase/tutorial/uiswing/components/table.html#data
	// and http://www.java2s.com/Code/Java/Swing-JFC/DisplayResultSetinTableJTable.htm
    class QueryTableModel extends AbstractTableModel {
    	CachedRowSet myRowSet; // The ResultSet to interpret
    	ResultSetMetaData metadata; // Additional information about the results
    	int numcols, numrows; // How many rows and columns in the table
 
    	public CachedRowSet getCoffeesRowSet() {
    		return myRowSet;
    	}
    	
        public QueryTableModel(CachedRowSet rowSetArg) throws SQLException {
        	this.myRowSet = rowSetArg;
            this.metadata = this.myRowSet.getMetaData();
            numcols = metadata.getColumnCount();

            // Retrieve the number of rows.
            this.myRowSet.beforeFirst();
            this.numrows = 0;
            while (this.myRowSet.next()) {
              this.numrows++;
            }
            this.myRowSet.beforeFirst();
        }
        
        public void addEventHandlersToRowSet(RowSetListener listener) {
        	this.myRowSet.addRowSetListener(listener);
        }
        
        public void close() {
        	try {
        		myRowSet.getStatement().close();
        	} catch (SQLException e) {
        		printSQLException(e);
        	}
        }

        /** Automatically close when we're garbage collected */
        protected void finalize() {
        	close();
        }

        public int getColumnCount() {
            return numcols;
        }
 
        public int getRowCount() {
            return numrows;
        }
 
        public String getColumnName(int col) {
        	try {
        		return this.metadata.getColumnLabel(col + 1);
        	} catch (SQLException e) {
        		return e.toString();
        	}
        }
 
        public Object getValueAt(int row, int col) {
        	try {
        		this.myRowSet.absolute(row + 1);
        		Object o = this.myRowSet.getObject(col + 1);
        		if (o == null)
        			return null;
        		else
        			return o.toString();
        	} catch (SQLException e) {
        		return e.toString();
        	}
        }
 
        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return String.class;
        }
 
        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
//            if (col < 2) {
//                return false;
//            } else {
//                return true;
//            }
        	return false;
        }
 
        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
        	System.out.println("Calling setValueAt row " + row + ", column " + col);
        }
 
        public void addTableModelListener(TableModelListener l) {
        }

        public void removeTableModelListener(TableModelListener l) {
        }
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

	private class SwingAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "Daten speichern");
			putValue(SHORT_DESCRIPTION, "Speichern der eingegebenen Daten in CAQ");
		}
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	public void writeToFile() {
		try {
			File file = new File("\\\\atdotrsr26\\QSYSTESTDATA\\NC\\FILES\\HOST\\NC_RQMS_STAMM.DAT");
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("UI");
			bw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
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
        	JOptionPane.showMessageDialog(null,e.getMessage(), "Fehler beim Schließen der Datenbankverbindung!",JOptionPane.ERROR_MESSAGE);
        	e.printStackTrace();
            return false;
        } 
	}

	private void saveToDB() {
		insertData(); 
		JOptionPane.showMessageDialog(null,
			    "Daten erfolgreich gespeichert!",
			    "Data saved",
			    JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void insertData() {
		String query = null;
		int maxID = 0;
		try {
			query = "SELECT max(id) FROM tri_pl_geraete";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				maxID = rs.getInt(1);
				if (rs.wasNull()) {
					maxID = 1;
				} else {
					maxID++;
				}
			}
			String zeitpunkt = datum + " " + zeitMeldung + ":00";
			query = "INSERT INTO tri_pl_geraete (id, fauf, anzahl, anlage, dt_anlieferung, name_ma) values (" + 
					Integer.toString(maxID) + ", " + fertigungsauftrag + ", " + anzahl + ", \'" + 
					anlage + "\', to_date(\'" + zeitpunkt + "\', \'yyyy-mm-dd hh24:mi:ss\'), \'" + nameMA + "\')";
			rs = stmt.executeQuery(query);
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
		
		// Anzahl numerisch?
		if (!isNumeric(anzahl)) {
			return false;
		}
		
		// Anzahl >= 0 ?
		num = Integer.parseInt(anzahl);
		if (num < 0) {
			return false;
		}
		
		// Datum korrekt? Muss im Format TT.MM.JJJJ vorliegen.
		// 1.1.2014 ist auch korrekt.
		if (datum == null) {
			return false;
		}
//		if (datum.contains(".")) {
//			String strTemp = null;
//			int indexBegin = 0;
//			int indexEnd = 0;
//			String tag = null;
//			String monat = null;
//			String jahr = null;
//			String stunde = null;
//			String minute = null;
//			indexEnd = datum.indexOf(".");
//			tag = datum.substring(indexBegin, indexEnd - 1);
//			if (datum.length() > indexEnd + 1) {
//				strTemp = datum.substring(indexEnd + 1, datum.length());
//				monat = 
//			} else {
//				return false;
//			}
//		} else {
//			return false;
//		}
		
		
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
	
	public void actionPerformed(ActionEvent event) {  }

	public void rowSetChanged(RowSetEvent event) {  }

	public void rowChanged(RowSetEvent event) {
		CachedRowSet currentRowSet = this.myQueryTableModel.myRowSet;
		try {
			currentRowSet.moveToCurrentRow();
			myQueryTableModel = new QueryTableModel(myQueryTableModel.getCoffeesRowSet());
			table.setModel(myQueryTableModel);
		} catch (SQLException ex) {
			printSQLException(ex);
			// Display the error in a dialog box.
			JOptionPane.showMessageDialog(
					GUI.this,
					new String[] { // Display a 2-line message
							ex.getClass().getName() + ": ",
							ex.getMessage()
					}
			);
		}
	}

	public void cursorMoved(RowSetEvent event) {  }
	
}
