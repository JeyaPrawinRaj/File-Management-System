package MiniProject;

import java.awt.EventQueue;
import java.sql.*;
import java.awt.Color;
import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

class LoginPage {
	
	private JFrame loginframe;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JComboBox<String> userTypeCombo;
	public static String username;
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.loginframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
	public LoginPage() {
		initialize();
	}
	private void initialize() {			
		loginframe = new JFrame();
		loginframe.setBackground(new Color(255, 255, 255));
		loginframe.setSize(1920,1080);;
		loginframe.getContentPane().setFont(new Font("Times New Roman", Font.BOLD, 14));
		loginframe.getContentPane().setBackground(new Color(192, 192, 192));
		loginframe.setBounds(0, 0, 1920, 1080);
		loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginframe.getContentPane().setLayout(null);
		
	
		
		JLabel LOGINLABEL = new JLabel("WELCOME !");
		LOGINLABEL.setBounds(556, 105, 394, 84);
		LOGINLABEL.setForeground(new Color(220, 20, 60));
		LOGINLABEL.setFont(new Font("Tahoma", Font.PLAIN, 68));
		loginframe.getContentPane().add(LOGINLABEL);
		
		JLabel userLabel = new JLabel("USERNAME");
		userLabel.setBounds(311, 250, 362, 84);
		userLabel.setForeground(new Color(255, 0, 0));
		userLabel.setFont(new Font("Tahoma", Font.PLAIN, 68));
		loginframe.getContentPane().add(userLabel);
		
		usernameField = new JTextField();
		usernameField.setBounds(848, 263, 388, 60);
		usernameField.setForeground(new Color(0, 255, 0));
		usernameField.setToolTipText("USERNAME FIELD");
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 40));
		loginframe.getContentPane().add(usernameField);
		usernameField.setColumns(30);
		
		JLabel passwordLabel = new JLabel("PASSWORD");
		passwordLabel.setBounds(311, 373, 362, 84);
		passwordLabel.setForeground(new Color(255, 0, 0));
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 68));
		loginframe.getContentPane().add(passwordLabel);
		
		JLabel typelabel = new JLabel("LOGIN  AS");
		typelabel.setBounds(321, 496, 362, 84);
		typelabel.setForeground(new Color(255, 0, 0));
		typelabel.setFont(new Font("Tahoma", Font.PLAIN, 68));
		loginframe.getContentPane().add(typelabel);
		
		JButton loginButton = new JButton("");
		loginButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\lg.jpeg"));
		loginButton.setBounds(364, 682, 235, 69);
		loginButton.setForeground(new Color(0, 191, 255));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		loginButton.setBackground(new Color(255, 255, 255));
		loginButton.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		loginframe.getContentPane().add(loginButton);
		
		JButton registerButton = new JButton("REGISTER");
		registerButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\clicktoregisterpage.jpeg"));
		registerButton.setBounds(771, 682, 316, 60);
		registerButton.setForeground(new Color(0, 255, 255));
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 RegisterPage.main(null);
			}
		});
		registerButton.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		registerButton.setBackground(new Color(255, 255, 255));
		loginframe.getContentPane().add(registerButton);
		
		JButton backButton = new JButton("");
		backButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\bck1.png"));
		backButton.setBounds(1229, 672, 189, 60);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginframe.dispose();
				FileManagementSystem.main(null);
			}
		});
		backButton.setForeground(new Color(0, 255, 255));
		backButton.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		backButton.setBackground(Color.WHITE);
		loginframe.getContentPane().add(backButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(848, 399, 388, 54);
		passwordField.setForeground(new Color(0, 255, 0));
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 40));
		passwordField.setEchoChar('*');
		passwordField.setColumns(100);
		loginframe.getContentPane().add(passwordField);
		
		userTypeCombo = new JComboBox<>(new String[]{"Admin", "User"});
		userTypeCombo.setSelectedIndex(-1);
		userTypeCombo.setBounds(848, 504, 388, 60);
		userTypeCombo.setForeground(new Color(0, 255, 0));
		
		userTypeCombo.setToolTipText("Type of User");
		userTypeCombo.setMaximumRowCount(2);
		userTypeCombo.setFont(new Font("Tahoma", Font.PLAIN, 40));
		loginframe.getContentPane().add(userTypeCombo);
	}
	private void userlogin(String username) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_login_history (username, action,date) VALUES (?, ?,CURDATE())")) {
            stmt.setString(1, username);
            stmt.setString(2, "login");  
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Error logging history: " + "", "History Error", JOptionPane.ERROR_MESSAGE);
        }
	}
	private void adminlogin(String username) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO admin_login_history (username, action,date) VALUES (?, ?,CURDATE())")) {
            stmt.setString(1, username);
            stmt.setString(2, "login");  
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Error logging history: " , "History Error", JOptionPane.ERROR_MESSAGE);
        }
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO admin_logout_history (username, action ,date,timestamp) VALUES (?,?,?,?)")) {
               stmt.setString(1, username);
               stmt.setString(2, "logout"); 
               stmt.setString(3, " ");
               stmt.setString(4, " ");
               
               stmt.executeUpdate();
           } catch (SQLException e) {
               e.printStackTrace();
               JOptionPane.showMessageDialog(new JFrame(), "Error logging history: " , "History Error", JOptionPane.ERROR_MESSAGE);
           }
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password: " );
        }
    }
    private boolean validateAdmin(String username, String password) {
        // Hash the input password
        String hashedPassword = hashPassword(password);
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if a matching record is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Database error: " , "Login Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void login() {
        username = usernameField.getText();
        String password = hashPassword(new String(passwordField.getPassword()));
        String userType = (String) userTypeCombo.getSelectedItem();
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Please fill in all fields.");
            return;
        }
        if ("Admin".equals(userType) ) {
        	if(username.equals("admin") && password.equals(hashPassword("admin"))) {
        		
        		JOptionPane.showMessageDialog(new JFrame(), "Admin login successful!");
        		usernameField.setText("");
        		passwordField.setText("");
        		userTypeCombo.setSelectedItem(-1);
        		
                
                new AdminPage(username);
                adminlogin("admin");
                loginframe.dispose();
                
                return;}
        	else {
        		
        		if(validateAdmin(username,password)) {
        			JOptionPane.showMessageDialog(new JFrame(), "Admin login successful!");
        			usernameField.setText("");
            		passwordField.setText("");
            		userTypeCombo.setSelectedItem(-1);
                    
                    new AdminPage(username);
                   
                    adminlogin(username);
                    loginframe.dispose();
                    return;
        			
        		}
        		
        	}
        }
        else if ("User".equals(userType)) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
                stmt.setString(1, username);
                stmt.setString(2,password); 

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    
                	usernameField.setText("");
            		passwordField.setText("");
            		userTypeCombo.setSelectedItem(-1);
                    
                    userlogin(username);
                    loginframe.dispose();
                    userPage.main(null);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(), "Database error: " , "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog( new JFrame(), "Invalid user type.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}





