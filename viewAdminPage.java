package MiniProject;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;



import java.sql.*;
import java.awt.Color;
import java.awt.EventQueue;

class viewAdminPage {
	 public  JFrame f1;
	 
	 
	public static void main(String args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewAdminPage window = new viewAdminPage();
					window.f1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	public viewAdminPage() {
		
		f1 = new JFrame();
		f1.setTitle("SEARCH ADMIN");
		f1.setBounds(0,0,1920,1080);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.getContentPane().setLayout(null);		
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(535, 0, 1005, 845);
		f1.getContentPane().add(panel);
		panel.setLayout(null);
			
		
		
		JButton searchButton = new JButton("SEARCH");
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		searchButton.setBounds(145, 611, 229, 74);
		
		panel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				
				try (Connection conn = Database.getConnection();
			             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM admin")){
						 
						 
			             ResultSet rs = stmt.executeQuery();

			            
			            String[] columnNames = {"ADMIN ID", "ADMIN NAME","REGISTER DATE"};
			            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
			            while (rs.next()) {
			                Object[] row = {rs.getInt("id"), rs.getString("username"),rs.getString("reg_date")};
			                model.addRow(row);
			            }
			            JTable table = new JTable(model);
			            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			            JScrollPane scrollPane = new JScrollPane(table);
			    		scrollPane.setBounds(0, 0, 535, 835);
			    				
			    		f1.getContentPane().add(scrollPane);		            
			    		
			            
			            f1.setVisible(true);
			        } catch (SQLException e) {
			            e.printStackTrace();
			            JOptionPane.showMessageDialog( new JFrame(), "Database error: " , "View Admins Error", JOptionPane.ERROR_MESSAGE);
			        }
		
				}
			
		});
		
		
		JButton backButton = new JButton("BACK");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		backButton.setBounds(606, 611, 222, 74);
		panel.add(backButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\file (2).jpg"));
		lblNewLabel.setBounds(10, 10, 985, 512);
		panel.add(lblNewLabel);
		
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f1.dispose();
				new AdminPage(LoginPage.username);
			}
		});
	}
}
		
		
		
	
	
        	
		
		
    

