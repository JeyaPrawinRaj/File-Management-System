package MiniProject;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;



import java.sql.*;
import java.awt.Color;
import java.awt.EventQueue;

class viewUsersPage {
	 public  JFrame f1;
	 private JComboBox<String> comboBox;
	 
	 
	public static void main(String args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewUsersPage window = new viewUsersPage();
					window.f1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	public viewUsersPage() {
		
		f1 = new JFrame();
		f1.setTitle("SEARCH USER");
		f1.setBounds(0,0,1920,1080);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.getContentPane().setLayout(null);		
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(535, 0, 1005, 845);
		f1.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SEARCH USER  BY TYPE ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel.setBounds(290, 314, 255, 56);
		panel.add(lblNewLabel);
		
		comboBox = new JComboBox<>(new String[] {"NORMAL", "SUBSCRIBED"});
		comboBox.setSelectedIndex(-1);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox.setBounds(631, 323, 255, 43);
		panel.add(comboBox);
			
		
		
		JButton searchButton = new JButton("SEARCH");
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		searchButton.setBounds(300, 474, 168, 38);
		
		panel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(comboBox.getSelectedIndex()==-1) {
					JOptionPane.showMessageDialog( new JFrame(), "Select the Type Of"," User to Search", JOptionPane.ERROR_MESSAGE);
				}
				try (Connection conn = Database.getConnection();
			             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users where usertype=?")){
						 try{stmt.setString(1,(String) comboBox.getSelectedItem().toString().toLowerCase());}
						 catch(NullPointerException e) {
							 JOptionPane.showMessageDialog( new JFrame(), "type cannot "," empty", JOptionPane.ERROR_MESSAGE);
							 
						 }
			             ResultSet rs = stmt.executeQuery();

			            
			            String[] columnNames = {"User ID", "UserName","User Type","Payment ","Start Date"};
			            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
			            while (rs.next()) {
			                Object[] row = {rs.getInt("userid"), rs.getString("username"),rs.getString("usertype"),rs.getString("payment"),rs.getString("start_date")};
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
			            JOptionPane.showMessageDialog( new JFrame(), "Database error: " , "View Users Error", JOptionPane.ERROR_MESSAGE);
			        }
		
				}
			
		});
		
		
		JButton backButton = new JButton("BACK");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		backButton.setBounds(647, 474, 168, 38);
		panel.add(backButton);
		
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f1.dispose();
				new AdminPage(LoginPage.username);
			}
		});
	}
}
		
		
		
	
	
        	
		
		
    

