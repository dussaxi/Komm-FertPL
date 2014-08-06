package kommFertPL;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.text.ParsePosition;

import javax.swing.Action;

import java.awt.event.ActionListener;
import java.sql.*;
import java.io.*;

import javax.swing.JFormattedTextField;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.Font;


public class GUI extends JFrame {
	
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
	private JLabel lblNewLabel;
	private JTextField txtAnzahl;
	private JLabel lblNewLabel_1;
	private JTextField txtAnlage;
	private JLabel lblNewLabel_2;
	private JFormattedTextField txtDatum;
	private JLabel lblNewLabel_3;
	private JFormattedTextField txtZeitAnlieferung;
	private JLabel lblNewLabel_4;
	private JTextField txtNameMA;
	private JLabel lblNewLabel_5;
	private JButton btnDatenSpeichern;
	private final Action action = new SwingAction();
	
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private JLabel lblNewLabel_6;
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 254);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{474, 0};
		gbl_contentPane.rowHeights = new int[]{10, 190, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		contentPane.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		lblNewLabel = new JLabel("Fertigungsauftrag");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		txtFertigungsauftrag = new JTextField();
		txtFertigungsauftrag.setText("1234567");
		GridBagConstraints gbc_txtFertigungsauftrag = new GridBagConstraints();
		gbc_txtFertigungsauftrag.insets = new Insets(0, 0, 5, 5);
		gbc_txtFertigungsauftrag.gridwidth = 2;
		gbc_txtFertigungsauftrag.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFertigungsauftrag.gridx = 5;
		gbc_txtFertigungsauftrag.gridy = 0;
		panel_1.add(txtFertigungsauftrag, gbc_txtFertigungsauftrag);
		
		lblNewLabel_1 = new JLabel("Anzahl");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 1;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtAnzahl = new JTextField();
		txtAnzahl.setText("10");
		GridBagConstraints gbc_txtAnzahl = new GridBagConstraints();
		gbc_txtAnzahl.gridwidth = 2;
		gbc_txtAnzahl.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnzahl.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAnzahl.gridx = 5;
		gbc_txtAnzahl.gridy = 1;
		panel_1.add(txtAnzahl, gbc_txtAnzahl);
		txtAnzahl.setColumns(10);
		
		lblNewLabel_2 = new JLabel("Anlage");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridwidth = 2;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.gridx = 3;
		gbc_lblNewLabel_2.gridy = 2;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		txtAnlage = new JTextField();
		txtAnlage.setText("TEST1A");
		GridBagConstraints gbc_txtAnlage = new GridBagConstraints();
		gbc_txtAnlage.gridwidth = 2;
		gbc_txtAnlage.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnlage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAnlage.gridx = 5;
		gbc_txtAnlage.gridy = 2;
		panel_1.add(txtAnlage, gbc_txtAnlage);
		txtAnlage.setColumns(10);
		
		lblNewLabel_3 = new JLabel("Datum");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.gridwidth = 2;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.gridx = 3;
		gbc_lblNewLabel_3.gridy = 3;
		panel_1.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		txtDatum = new JFormattedTextField(new SimpleDateFormat("d.M.yyyy"));
		txtDatum.setValue(new Date());
		GridBagConstraints gbc_txtDatum = new GridBagConstraints();
		gbc_txtDatum.gridwidth = 2;
		gbc_txtDatum.insets = new Insets(0, 0, 5, 5);
		gbc_txtDatum.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDatum.gridx = 5;
		gbc_txtDatum.gridy = 3;
		panel_1.add(txtDatum, gbc_txtDatum);
		txtDatum.setColumns(10);
		
		lblNewLabel_4 = new JLabel("Zeit der Anlieferung");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.gridwidth = 2;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.gridx = 3;
		gbc_lblNewLabel_4.gridy = 4;
		panel_1.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		txtZeitAnlieferung = new JFormattedTextField(new SimpleDateFormat("H:mm"));
		txtZeitAnlieferung.setValue(new Date());
		GridBagConstraints gbc_txtZeitAnlieferung = new GridBagConstraints();
		gbc_txtZeitAnlieferung.gridwidth = 2;
		gbc_txtZeitAnlieferung.insets = new Insets(0, 0, 5, 5);
		gbc_txtZeitAnlieferung.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtZeitAnlieferung.gridx = 5;
		gbc_txtZeitAnlieferung.gridy = 4;
		panel_1.add(txtZeitAnlieferung, gbc_txtZeitAnlieferung);
		txtZeitAnlieferung.setColumns(10);
		
		lblNewLabel_5 = new JLabel("Name MA");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.gridwidth = 2;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.gridx = 3;
		gbc_lblNewLabel_5.gridy = 5;
		panel_1.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		txtNameMA = new JTextField();
		txtNameMA.setText("Matthias Weg");
		GridBagConstraints gbc_txtNameMA = new GridBagConstraints();
		gbc_txtNameMA.gridwidth = 2;
		gbc_txtNameMA.insets = new Insets(0, 0, 5, 5);
		gbc_txtNameMA.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNameMA.gridx = 5;
		gbc_txtNameMA.gridy = 5;
		panel_1.add(txtNameMA, gbc_txtNameMA);
		txtNameMA.setColumns(10);
		
		btnDatenSpeichern = new JButton("Daten Speichern");
		btnDatenSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnDatenSpeichern.setAction(action);
		GridBagConstraints gbc_btnDatenSpeichern = new GridBagConstraints();
		gbc_btnDatenSpeichern.insets = new Insets(0, 0, 5, 5);
		gbc_btnDatenSpeichern.gridx = 5;
		gbc_btnDatenSpeichern.gridy = 6;
		panel_1.add(btnDatenSpeichern, gbc_btnDatenSpeichern);
		
		lblNewLabel_6 = new JLabel("Vers. 1.0 \u00A9 Matthias Weg, 5.8.2014");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.gridwidth = 3;
		gbc_lblNewLabel_6.gridx = 5;
		gbc_lblNewLabel_6.gridy = 7;
		panel_1.add(lblNewLabel_6, gbc_lblNewLabel_6);
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
				
			}
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
        	JOptionPane.showMessageDialog(null,e.getMessage(), "Fehler beim Schlieﬂen der Datenbankverbindung!",JOptionPane.ERROR_MESSAGE);
        	e.printStackTrace();
            return false;
        } 
	}

	private void saveToDB() {
		if (connectToDB() == true) {
			insertData(); 
			closeConnection();
			JOptionPane.showMessageDialog(null,
				    "Daten erfolgreich gespeichert!",
				    "Data saved",
				    JOptionPane.INFORMATION_MESSAGE);
		}
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
}
