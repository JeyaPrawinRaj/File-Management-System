package MiniProject;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import java.sql.*;
import java.awt.Color;
import java.awt.EventQueue;

class viewFilesPage {
	 public  JFrame f1;
	 private JComboBox<String> comboBox;
	 
	 
	public static void main(String args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewFilesPage window = new viewFilesPage();
					window.f1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	public viewFilesPage() {
		f1 = new JFrame();
		f1.setTitle("SEARCH FILES");
		f1.setBounds(0,0,1920,1080);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.getContentPane().setLayout(null);		
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(535, 0, 1005, 845);
		f1.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SEARCH FILE BY TYPE ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel.setBounds(290, 314, 255, 56);
		panel.add(lblNewLabel);
		
		comboBox = new JComboBox<>(new String[] {"All","txt","pdf","doc","docx","ppt"});
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
					JOptionPane.showMessageDialog( new JFrame(), "Select the Type Of"," File to View", JOptionPane.ERROR_MESSAGE);
					return;
				}	 
			String filetype=(String) comboBox.getSelectedItem();
			if(filetype.equals("All")) {
			try (Connection conn = Database.getConnection();
	             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM files")){
					
	             ResultSet rs = stmt.executeQuery() ;{

	            
	            String[] columnNames = {"File ID", "File Name", "File Type","Title","Author","Description","Display User Type"};
	            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
	            while (rs.next()) {
	                Object[] row = {rs.getInt("file_id"), rs.getString("file_name"), rs.getString("file_type"),rs.getString("title"),rs.getString("author"),rs.getString("description"),rs.getString("display")};
	                model.addRow(row);
	            }
	            JTable table = new JTable(model);
	            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

	            JScrollPane scrollPane = new JScrollPane(table);
	            f1.getContentPane().add(scrollPane);
	            scrollPane.setBounds(0, 0, 535, 835);
	            
	            f1.setVisible(true);
	             }
			}
	        
			
			catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog( new JFrame(), "Database error: " , "View File Error", JOptionPane.ERROR_MESSAGE);
	        }
		}
			else {
				try (Connection conn = Database.getConnection();
		             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM files where file_type=? ")){
		            	 stmt.setString(1, filetype); 
		            	   
		            	 
					
		             ResultSet rs = stmt.executeQuery() ;

		            JFrame frame = new JFrame("FilesList");
		            frame.setSize(1920, 1080);
		            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		            String[] columnNames = {"File ID", "File Name", "File Type","Title","Author","Description","Display User Type"};
		            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		            while (rs.next()) {
		                Object[] row = {rs.getInt("file_id"), rs.getString("file_name"), rs.getString("file_type"),rs.getString("title"),rs.getString("author"),rs.getString("description"),rs.getString("display")};
		                model.addRow(row);
		            }
		            JTable table = new JTable(model);
		            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		            JScrollPane scrollPane = new JScrollPane(table);
		            frame.getContentPane().add(scrollPane);
		            
		            frame.setVisible(true);
		        }
			catch (SQLException e) {
		            e.printStackTrace();
		            JOptionPane.showMessageDialog( new JFrame(), "Database error: ", "View Users Error", JOptionPane.ERROR_MESSAGE);
		        }
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

		
	
		
		/*JFrame f1=new JFrame("File View Page");
		f1.getContentPane().setBackground(new Color(192, 192, 192));
		f1.setSize(1920,1080);
		JLabel label=new JLabel("File Type");
		label.setBounds(400,700,80,80);
        f1.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SELECT THE TYPE OF FILE");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(615, 300, 370, 55);
		f1.getContentPane().add(lblNewLabel);
		
		JLabel typelabel = new JLabel("FILE TYPE");
		typelabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		typelabel.setBounds(540, 406, 156, 35);
		f1.getContentPane().add(typelabel);
		
		comboBox = new JComboBox<>(new String[] {"All", "txt", "pdf", "doc", "ppt"});
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		comboBox.setBounds(766, 406, 317, 34);
		f1.getContentPane().add(comboBox);
		
		JButton searchButton = new JButton("SEARCH");
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		searchButton.setBounds(519, 552, 232, 46);
		f1.getContentPane().add(searchButton);
		searchButton.addActionListener(e->initialize((String) comboBox.getSelectedItem()));
			
		
		JButton backButton = new JButton("BACK");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		backButton.setBounds(859, 552, 232, 46);
		f1.getContentPane().add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f1.dispose();
				 new AdminPage(LoginPage.username);
			}
		});
		f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f1.setVisible(true);
		
		
		
	}	

	 static class Database {
	        private static final String URL = "jdbc:mysql://localhost:3306/mini";
	        private static final String USER = "root"; 
	        private static final String PASSWORD = "1234"; 
	        public static Connection getConnection() throws SQLException {
	            return DriverManager.getConnection(URL, USER, PASSWORD);
	        }
	    }
	 
	 void initialize(String filetype) {
		 if(comboBox.getSelectedIndex()==-1) {
				JOptionPane.showMessageDialog( new JFrame(), "Select the Type Of"," File to View", JOptionPane.ERROR_MESSAGE);
			}	 
		
		if(filetype.equals("All")) {
		try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM files")){
				
             ResultSet rs = stmt.executeQuery() ;{

            JFrame frame = new JFrame(filetype+"Files List");
            frame.setSize(1920, 1080);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            String[] columnNames = {"File ID", "File Name", "File Type","Title","Author","Description","Display User Type"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                Object[] row = {rs.getInt("file_id"), rs.getString("file_name"), rs.getString("file_type"),rs.getString("title"),rs.getString("author"),rs.getString("description"),rs.getString("display")};
                model.addRow(row);
            }
            JTable table = new JTable(model);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

            JScrollPane scrollPane = new JScrollPane(table);
            frame.getContentPane().add(scrollPane);
            
            frame.setVisible(true);
             }
		}
        
		
		catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( new JFrame(), "Database error: " , "View File Error", JOptionPane.ERROR_MESSAGE);
        }
	}
		else {
			try (Connection conn = Database.getConnection();
	             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM files where file_type=? ")){
	            	 stmt.setString(1, filetype); 
	            	   
	            	 
				
	             ResultSet rs = stmt.executeQuery() ;

	            JFrame frame = new JFrame("FilesList");
	            frame.setSize(1920, 1080);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            String[] columnNames = {"File ID", "File Name", "File Type","Title","Author","Description","Display User Type"};
	            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
	            while (rs.next()) {
	                Object[] row = {rs.getInt("file_id"), rs.getString("file_name"), rs.getString("file_type"),rs.getString("title"),rs.getString("author"),rs.getString("description"),rs.getString("display")};
	                model.addRow(row);
	            }
	            JTable table = new JTable(model);
	            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

	            JScrollPane scrollPane = new JScrollPane(table);
	            frame.getContentPane().add(scrollPane);
	            
	            frame.setVisible(true);
	        }
		catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog( new JFrame(), "Database error: ", "View Users Error", JOptionPane.ERROR_MESSAGE);
	        }
		}
    }*/	 

