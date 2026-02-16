package MiniProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;

public class viewHistory {

	private JFrame frmViewHistory;
	

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewHistory window = new viewHistory();
					window.frmViewHistory.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public viewHistory() {
		initialize();
	}

	
	private void initialize() {
		frmViewHistory = new JFrame();
		frmViewHistory.setTitle("VIEW HISTORY");
		frmViewHistory.getContentPane().setBackground(new Color(192, 192, 192));
		frmViewHistory.setBounds(0,0,1920,1080);
		frmViewHistory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmViewHistory.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SELECT TO VIEW HISTORY");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel.setBounds(552, 290, 326, 47);
		frmViewHistory.getContentPane().add(lblNewLabel);
		
		JButton adminHistory = new JButton("ADMINS");
		adminHistory.setFont(new Font("Tahoma", Font.PLAIN, 20));
		adminHistory.setBounds(474, 436, 156, 40);
		frmViewHistory.getContentPane().add(adminHistory);
		adminHistory.addActionListener(e->viewadminHistory());
		
		JButton userHistory = new JButton("USERS");
		userHistory.setFont(new Font("Tahoma", Font.PLAIN, 21));
		userHistory.setBounds(842, 436, 156, 40);
		frmViewHistory.getContentPane().add(userHistory);
		userHistory.addActionListener(e->viewuserHistory());
		
		
		
		JButton backButton = new JButton("BACK ");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 28));
		backButton.setBounds(1338, 751, 164, 47);
		frmViewHistory.getContentPane().add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AdminPage(LoginPage.username);
				frmViewHistory.dispose();
			}
		});
	}
	private void viewadminHistory() {
		
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT t1.id,t1.username,t1.date,t1.timestamp,t2.date,t2.timestamp FROM admin_login_history t1,admin_logout_history t2 ORDER BY t1.timestamp DESC")) {

            JFrame frame = new JFrame("Login History");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String[] columnNames = {" Admin ID", "Admin name", "Login Date", "Login Time","Logout Date","Logout Time"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (rs.next()) {
                Object[] row = {rs.getInt("t1.id"), rs.getString("t1.username"), rs.getString("t1.date"), rs.getTimestamp("t1.timestamp"),rs.getString("t2.date"),rs.getString("t2.timestamp")};
                model.addRow(row);
            }

            JTable table = new JTable(model);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.getContentPane().add(scrollPane);
            frame.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Error fetching login history: ", "History Error", JOptionPane.ERROR_MESSAGE);
        }
       
    }
	private void viewuserHistory() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT t1.id,t1.username,t1.date,t1.timestamp,t2.date,t2.timestamp FROM user_login_history t1,user_logout_history t2 ORDER BY t1.timestamp DESC")) {

            JFrame frame = new JFrame("Login History");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String[] columnNames = {" User ID", "Username", "Login Date", "Login Time","Logout Date","Logout Time"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (rs.next()) {
                Object[] row = {rs.getInt("t1.id"), rs.getString("t1.username"), rs.getString("t1.date"), rs.getTimestamp("t1.timestamp"),rs.getString("t2.date"),rs.getString("t2.timestamp")};
                model.addRow(row);
            }

            JTable table = new JTable(model);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.getContentPane().add(scrollPane);
            frame.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Error fetching login history: ", "History Error", JOptionPane.ERROR_MESSAGE);
        }
       
	}
    
}
