package MiniProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;



import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

class addAdminPage {
	private JFrame frmRegisteerPage;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JComboBox<String> userTypeCombo;	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addAdminPage  window = new addAdminPage ();
					window.frmRegisteerPage.setVisible(true);
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
	public addAdminPage () {
		
		frmRegisteerPage = new JFrame();
		frmRegisteerPage.setTitle(" ADMIN REGISTER PAGE");
		frmRegisteerPage.setBounds(0,0, 1920, 1080);
		frmRegisteerPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRegisteerPage.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(0,0,1540,845);
		frmRegisteerPage.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel regLabel = new JLabel("REGISTER HERE");
		regLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		regLabel.setBounds(1020, 251, 300, 54);
		panel.add(regLabel);
		
		JLabel userlabel = new JLabel("ADMIN NAME");
		userlabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
		userlabel.setBounds(938, 359, 181, 36);
		panel.add(userlabel);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usernameField.setBounds(1184, 363, 217, 36);
		panel.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel passwordlabel = new JLabel("PASSWORD");
		passwordlabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
		passwordlabel.setBounds(941, 465, 153, 36);
		panel.add(passwordlabel);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordField.setBounds(1184, 465, 217, 36);
		panel.add(passwordField);
		
		
		JButton registerButton = new JButton("ADD ADMIN");
		registerButton.setFont(new Font("Tahoma", Font.PLAIN, 21));
		registerButton.setBounds(902, 581, 217, 46);
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAdmin();
			}
		});
		
		panel.add(registerButton);
		
		JButton backButton = new JButton("BACK");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage.main(null);
			}
		});
		backButton.setBounds(1222, 579, 179, 46);
		panel.add(backButton);
		
		JLabel imagelabel = new JLabel("");
		imagelabel.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\reg2.png"));
		imagelabel.setBounds(0, 0,841 , 845);
		panel.add(imagelabel);
		}
	
		private boolean isUsernameTaken(String username) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM admin WHERE username = ?")) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
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
		
		private void addAdmin() {
            String username = usernameField.getText();
            String password = hashPassword(new String(passwordField.getPassword()));
            String userType = (String) userTypeCombo.getSelectedItem();
            

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(new JFrame(), "Please fill in all fields.");
                return;
            }
            if (isUsernameTaken(username)) {
                JOptionPane.showMessageDialog(new JFrame(), "Admin Name already exists. Please choose a different username.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            try (Connection conn = Database.getConnection())
                 
            {               
                	PreparedStatement stmt = conn.prepareStatement("INSERT INTO admin (username, password, reg_date) VALUES (?, ?, curdate())");
                	 stmt.setString(1, username);
                     stmt.setString(2, password); 
                     stmt.setString(3, userType);
                     stmt.setString(4, "");
                     
                     
                	stmt.executeUpdate();
                	JOptionPane.showMessageDialog(new JFrame(), "Registration successful!");
                	frmRegisteerPage.dispose();
                	}
                 catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(), "Registration failed: " , "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        }
	}

