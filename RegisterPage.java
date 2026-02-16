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

class RegisterPage {

	private JFrame frmRegisteerPage;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JComboBox<String> userTypeCombo;
	//private JButton payButton;
	private boolean payStatus=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterPage window = new RegisterPage();
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
	
	public RegisterPage() {
		
		frmRegisteerPage = new JFrame();
		frmRegisteerPage.setTitle("REGISTEER PAGE");
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
		regLabel.setBounds(1010, 182, 300, 54);
		panel.add(regLabel);
		
		JLabel userlabel = new JLabel("USERNAME");
		userlabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
		userlabel.setBounds(928, 297, 153, 36);
		panel.add(userlabel);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usernameField.setBounds(1176, 301, 217, 36);
		panel.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel passwordlabel = new JLabel("PASSWORD");
		passwordlabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
		passwordlabel.setBounds(928, 384, 153, 36);
		panel.add(passwordlabel);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordField.setBounds(1176, 388, 217, 36);
		panel.add(passwordField);
		
		JLabel userTypeLabel = new JLabel("USER TYPE");
		userTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
		userTypeLabel.setBounds(928, 477, 153, 36);
		panel.add(userTypeLabel);
		
		userTypeCombo = new JComboBox<>(new String[] {"NORMAL", "SUBSCRIBED"});
		userTypeCombo.setSelectedIndex(-1);
		userTypeCombo.setFont(new Font("Tahoma", Font.PLAIN, 22));
		
		userTypeCombo.setBounds(1176, 479, 217, 36);
		panel.add(userTypeCombo);
		
		/*payButton = new JButton("PAY");
		payButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pay();
				}
			
		});
		payButton.setFont(new Font("Tahoma", Font.PLAIN, 21));
		payButton.setBounds(947, 579, 134, 36);
		
		panel.add(payButton);*/
		
		
		JButton registerButton = new JButton("REGISTER");
		registerButton.setFont(new Font("Tahoma", Font.PLAIN, 21));
		registerButton.setBounds(1203, 580, 217, 35);
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
		
		panel.add(registerButton);
		
		JButton backButton = new JButton("BACK");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 21));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage.main(null);
			}
		});
		backButton.setBounds(1377, 755, 153, 36);
		panel.add(backButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\reg2.png"));
		lblNewLabel.setBounds(0, 10, 831, 835);
		panel.add(lblNewLabel);
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
		private boolean isUsernameTaken(String username) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?")) {
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
		private void register() {
            String username = usernameField.getText();
            String password = hashPassword(new String(passwordField.getPassword()));
            String userType = (String) userTypeCombo.getSelectedItem();
            

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(new JFrame(), "Please fill in all fields.");
                return;
            }
            if (isUsernameTaken(username)) {
                JOptionPane.showMessageDialog(new JFrame(), "Username already exists. Please choose a different username.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            try (Connection conn = Database.getConnection())
                 
            {
               
                if(userType=="NORMAL" ) {
                	PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, usertype,payment,start_date,end_date) VALUES (?, ?, ?,?,curdate(),?)");
                	 stmt.setString(1, username);
                     stmt.setString(2,password); 
                     stmt.setString(3, userType);
                     stmt.setString(4, "notpayed");
                     
                     
                	stmt.executeUpdate();
                	JOptionPane.showMessageDialog(new JFrame(), "Registration successful!");
                	frmRegisteerPage.dispose();
                	return;
                	}
                if(userType=="SUBSCRIBED") {
                	pay();
                	if(payStatus) {
                	PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, usertype,payment,start_date,end_date) VALUES (?, ?,?, ?,curdate(),date(curdate()+10000))");
                	stmt.setString(1, username);
                    stmt.setString(2, password); 
                    stmt.setString(3, userType);
                    stmt.setString(4, "payed");                    
                	stmt.executeUpdate();
                	JOptionPane.showMessageDialog(new JFrame(), "Registration successful!");
                	frmRegisteerPage.dispose();
                	return;}                      
                
                else {
                	JOptionPane.showMessageDialog( new JFrame(), "Registeration cannot be done payment error", "", JOptionPane.ERROR_MESSAGE);}
            }
            }catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(), "Registration failed: " , "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        }
		private void pay() {
            int response = JOptionPane.showConfirmDialog(new JFrame(), 
                "Would you like to complete the payment for the subscribed plan?", 
                "Payment", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(new JFrame(), "Payment completed successfully!");
                payStatus=true;               
            }
            else 
            {
                JOptionPane.showMessageDialog(new JFrame(), "Payment cancelled.");
                payStatus=false;
            }
        }
	}

