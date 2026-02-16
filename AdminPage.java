package MiniProject;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/mini";
    private static final String USER = "root"; 
    private static final String PASSWORD = "1234"; 
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
 class AdminPage{
	public JFrame frmAdminPage;
	public String username;
	public AdminPage(String name) {
		username=name;
		frmAdminPage = new JFrame("ADMIN");
		frmAdminPage.setTitle("ADMIN PAGE");
		frmAdminPage.setBounds(0, 0, 1920, 1080);
		frmAdminPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAdminPage.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(0, 0, 1540, 845);
		frmAdminPage.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel adminpagelabel = new JLabel("");
		adminpagelabel.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\admin.jpg"));
		adminpagelabel.setForeground(new Color(0, 0, 0));
		adminpagelabel.setFont(new Font("Tahoma", Font.PLAIN, 60));
		adminpagelabel.setBounds(0,0, 527, 888);
		panel.add(adminpagelabel);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(792, 10, 0, 2);
		panel.add(separator_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(797, 10, 0, 2);
		panel.add(separator);
		
		JButton viewUsersButton = new JButton("VIEW USERS");
		viewUsersButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\viewusers.png"));
		viewUsersButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		viewUsersButton.setBounds(605, 65, 351, 62);
		panel.add(viewUsersButton);		
		viewUsersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 viewUsersPage.main(null);
				frmAdminPage.dispose();			}
		});
		
		
		JButton deleteFileButton = new JButton("DELETE FILE");
		deleteFileButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\delfile.png"));
		deleteFileButton.setFont(new Font("Tahoma", Font.PLAIN, 34));
		deleteFileButton.setBounds(1042, 308, 449, 75);
		panel.add(deleteFileButton);
		deleteFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteFilePage.main(null);
				frmAdminPage.dispose();			}
		});
		
		JButton viewButton = new JButton("VIEW FILES");
		viewButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\viewfile.png"));
		viewButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		viewButton.setBounds(1042, 65, 449, 62);
		panel.add(viewButton);
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewFilesPage.main(null);
				frmAdminPage.dispose();
			}
		});
		
		JButton filehistoryButton = new JButton(" DOWNLOAD HISTORY");
		filehistoryButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\downloadfilehistor.png"));
		filehistoryButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		filehistoryButton.setBounds(1042, 439, 449, 76);
		panel.add(filehistoryButton);
		filehistoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewFileDownloadHistory.main(null);
				frmAdminPage.dispose();
			}
		});
		
		
		
		JButton delAdminButton = new JButton("DELETE ADMIN");		
		delAdminButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\deladmin.jpg"));
		delAdminButton.setFont(new Font("Tahoma", Font.PLAIN, 31));
		delAdminButton.setBounds(605, 439, 351, 76);
		panel.add(delAdminButton);
		delAdminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteAdminPage.main(null);
				frmAdminPage.dispose();
			}
		});
		
		JButton viewAdminButton = new JButton("VIEW ADMINS");
		viewAdminButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\viewadmins.png"));
		viewAdminButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		viewAdminButton.setBounds(605, 570, 351, 76);
		panel.add(viewAdminButton);
		viewAdminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewAdminPage.main(null);
				frmAdminPage.dispose();
			}
		});
		
		JButton logoutButton = new JButton("");
		logoutButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\log.png"));
		logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 40));
		logoutButton.setBounds(1042, 715, 449, 69);
		panel.add(logoutButton);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				logout();			
			}
		});
		
		JButton addAdminButton = new JButton("ADD ADMIN");
		addAdminButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\adadmin.png"));
		
		addAdminButton.setFont(new Font("Tahoma", Font.PLAIN, 29));
		addAdminButton.setBounds(605, 315, 351, 76);
		panel.add(addAdminButton);
		addAdminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAdminPage.main(null);
				frmAdminPage.dispose();
			}
		});
		
		JButton uploadButton = new JButton("UPLOAD FILE");
		uploadButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\uploadfile.png"));
		uploadButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		uploadButton.setBounds(1042, 177, 449, 77);
		panel.add(uploadButton);
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uploadFilePage.main(null);
				frmAdminPage.dispose();
			}
			
		});
		
		
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uploadFilePage.main(null);
				frmAdminPage.dispose();
				
			}
		});	
		
		
		JButton deleteUserButton = new JButton("DELETE USER");
		deleteUserButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\deluser.jpg"));
		deleteUserButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		deleteUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteUserPage.main(null);
				frmAdminPage.dispose();
			}
		});
		deleteUserButton.setBounds(605, 181, 351, 69);
		panel.add(deleteUserButton);
		
		JButton changePasswordButton = new JButton("CHANGE PASSWORD");
		changePasswordButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\passwordchange.jpg"));
		changePasswordButton.setFont(new Font("Tahoma", Font.PLAIN, 34));
		changePasswordButton.setBounds(1042, 572, 449, 76);
		panel.add(changePasswordButton);
		changePasswordButton.addActionListener(e->changePassword());
		JButton historyButton = new JButton("VIEW HISTORY");
		historyButton.setIcon(new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\history image.png"));
		historyButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		historyButton.setBounds(605, 715, 351, 69);
		panel.add(historyButton);
		//historyButton.addActionListener(e->viewHistory.main(null));
		historyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewHistory.main(null);
				frmAdminPage.dispose();
			}
		});
		
		frmAdminPage.setVisible(true);
		
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
	private void changePassword() {
		if(LoginPage.username.equals("admin")) {
			JOptionPane.showMessageDialog(new JFrame(), "Cannot Change Passsword for Default Credentials " , "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
        String newPassword = JOptionPane.showInputDialog(new JFrame(), "Enter the New Password:");
        

        if (newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Please enter a new password.");
            return;
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE admin SET password = ? WHERE userid = ?")) {
            stmt.setString(1, hashPassword(newPassword));
            stmt.setString(2, LoginPage.username);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(new JFrame(), "Password changed successfully!");
            return;
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Password change failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

	private void logout() {
        int confirm = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
        	LoginPage.main(null);				
			adminLogout(username);
        	frmAdminPage.dispose();  
              
        }
    }
	public static void adminLogout(String username) {
		if(username.equals("admin")) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("update admin_logout_history  set date=curdate(),timestamp=curtime() where id=(select max(id) from admin_login_history)")) {
           
           
            
            stmt.executeUpdate();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Error logging logout: " , "History Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
		}
		else
		{
			try (Connection conn = Database.getConnection();
		             PreparedStatement stmt = conn.prepareStatement("update  admin_logout_history  set date='curdate()',timestamp='curtime()' where username=?")) {
		            stmt.setString(1, username);
		           
		            
		            stmt.executeUpdate();
		        } catch (SQLException e) {
		            e.printStackTrace();
		            JOptionPane.showMessageDialog(new JFrame(), "Error logging logout: " , "History Error", JOptionPane.ERROR_MESSAGE);
		        }
		
		}
    }
}
