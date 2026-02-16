package MiniProject;

import java.io.File;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;
import java.awt.Color;
import java.awt.EventQueue;

class uploadFilePage {
	private static JFrame frmUploadFile;
	public static void main(String arg) {
	EventQueue.invokeLater(new Runnable() {
		@SuppressWarnings("static-access")
		public void run() {
			try {
				uploadFilePage window = new uploadFilePage();
				window.frmUploadFile.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	});
	}
	/*
	public static void main(String arg) {
		initialize();
	}
	private static void initialize() {
		 new uploadFilePage();
	}*/
	public uploadFilePage() {
		
        frmUploadFile = new JFrame();
        frmUploadFile.setTitle("UPLOAD FILE");
        frmUploadFile.getContentPane().setBackground(new Color(192, 192, 192));
        frmUploadFile.setSize(1920,1080);
        
        frmUploadFile.getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel("UPLOAD FILE PAGE");
        lblNewLabel.setBounds(549, 203, 531, 73);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 60));
        frmUploadFile.getContentPane().add(lblNewLabel);
        
        JButton uploadButton = new JButton("UPLOAD");
        uploadButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        uploadButton.setBounds(691, 364, 217, 42);
        frmUploadFile.getContentPane().add(uploadButton);
        uploadButton.addActionListener(e->uploadFile());
        
        JButton backButton = new JButton("BACK ");
        backButton.setFont(new Font("Tahoma", Font.PLAIN, 21));
        backButton.setBounds(1275, 713, 225, 42);
        frmUploadFile.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		new AdminPage(LoginPage.username);
        		frmUploadFile.dispose();
        	}
        });
		}

	static class Database {
        private static final String URL = "jdbc:mysql://localhost:3306/mini";
        private static final String USER = "root"; 
        private static final String PASSWORD = "1234"; 
        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
	
	private static String getFileType(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) return ""; 
        return fileName.substring(dotIndex + 1);  
    }
	private static void uploadFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(frmUploadFile);
        

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            String fileType = getFileType(fileName); 
           
            String filePath	 = selectedFile.getParent()+"\\";

            String title = JOptionPane.showInputDialog(new JFrame(), "Enter the title:");
            if(title==null) {
            	JOptionPane.showMessageDialog(new JFrame(), "Title Cannot be Empty Enter Again", "", JOptionPane.ERROR_MESSAGE);
            	
            }
            while(title != null) {
            	title = JOptionPane.showInputDialog(new JFrame(), "Enter the Title:");
            	
            }
            String author = JOptionPane.showInputDialog(new JFrame(), "Enter the Author:");
            if(author==null) {
            	JOptionPane.showMessageDialog(new JFrame(), "Author Name Cannot be Empty Enter Again", "", JOptionPane.ERROR_MESSAGE);
            	
            }
            while(author != null) {
            	author = JOptionPane.showInputDialog(new JFrame(), "Enter the Author:");
            	
            }
            String description = JOptionPane.showInputDialog(new JFrame(), "Enter a short Description of the file:");
            if(description ==null) {
            	JOptionPane.showMessageDialog(new JFrame(), "Description Cannot be Empty Enter Again", "", JOptionPane.ERROR_MESSAGE);
            	
            }
            while(description  != null) {
            	description = JOptionPane.showInputDialog(new JFrame(), "Enter the Description:");
            	
            }
            String display = JOptionPane.showInputDialog(new JFrame(), "Enter Display type (normal/subscribed):");
            if(display==null) {
            	JOptionPane.showMessageDialog(new JFrame(), "Display Cannot be Empty Enter Again", "", JOptionPane.ERROR_MESSAGE);
            	
            }
            while(display != null) {
            	display= JOptionPane.showInputDialog(new JFrame(), "Enter the Display:");                       
            if (!display.equalsIgnoreCase("normal") && !display.equalsIgnoreCase("subscribed")) {
                JOptionPane.showMessageDialog(new JFrame(), "Invalid Display type. Please enter 'normal' or 'subscribed'.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            }

            
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO files (file_name, file_type, file_path, title, author, description, display) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                stmt.setString(1, fileName);
                stmt.setString(2, fileType);
                stmt.setString(3, filePath);
                stmt.setString(4, title);
                stmt.setString(5, author);
                stmt.setString(6, description);
                stmt.setString(7, display);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(new JFrame(), "File uploaded successfully!\n" +
                        "File Name: " + fileName + "\nTitle: " + title + "\nAuthor: " + author + "\nDescription: " + description + "\nDisplay: " + display);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(), "Database error during file upload: " ,"Upload Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }	

}
