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

public class viewFileDownloadHistory {

	private JFrame frmViewHistory;
	

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewFileDownloadHistory window = new viewFileDownloadHistory();
					window.frmViewHistory.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public viewFileDownloadHistory() {
		initialize();
	}

	
	private void initialize() {
		frmViewHistory = new JFrame();
		frmViewHistory.setTitle("VIEW  FILE DOWNLOAD HISTORY");
		frmViewHistory.getContentPane().setBackground(new Color(192, 192, 192));
		frmViewHistory.setBounds(0,0,1920,1080);
		frmViewHistory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmViewHistory.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CLICK TO VIEW  DOWNLOAD HISTORY");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel.setBounds(552, 290, 491, 47);
		frmViewHistory.getContentPane().add(lblNewLabel);
		
		JButton adminHistory = new JButton("VIEW");
		adminHistory.setFont(new Font("Tahoma", Font.PLAIN, 20));
		adminHistory.setBounds(714, 436, 156, 40);
		frmViewHistory.getContentPane().add(adminHistory);
		adminHistory.addActionListener(e->viewadminHistory());	
		
		
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
             ResultSet rs = stmt.executeQuery("SELECT * from file_download_history")) {

            JFrame frame = new JFrame("Login History");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String[] columnNames = {"ID", "USER ID", "USERNAME", "FILE ID ","FILE NAME","DOWNLOAD DATE","DOWNLOAD TIME"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (rs.next()) {
                Object[] row = {rs.getInt("id"), rs.getString("user_id"), rs.getString("username"), rs.getString("file_id"),rs.getString("file_name"),rs.getString("download_date"),rs.getString("download_time")};
                model.addRow(row);
            }

            JTable table = new JTable(model);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.getContentPane().add(scrollPane);
            frame.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Error fetching login history: " , "History Error", JOptionPane.ERROR_MESSAGE);
        }
       
    }
	
}
