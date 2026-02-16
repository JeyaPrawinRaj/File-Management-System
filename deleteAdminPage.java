package MiniProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



class deleteAdminPage {
	private JFrame frmDeleteUser;
	private JComboBox<String> comboBox;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					deleteAdminPage window = new deleteAdminPage();
					window.frmDeleteUser.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public deleteAdminPage() {
		initialize();
	}
	private void initialize() {
		frmDeleteUser = new JFrame();
		frmDeleteUser.setTitle("DELETE ADMIN");
		frmDeleteUser.setBounds(0,0,1920,1080);
		frmDeleteUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDeleteUser.getContentPane().setLayout(null);
				
		JPanel panel = new JPanel(){
			 
			private static final long serialVersionUID = 1L;

	@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            ImageIcon backgroundImage = new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\"); 
            g.drawImage(backgroundImage.getImage(), -385, -10, 1920,1080, this);
        }
    };
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(533, 0, 1007, 845);
		frmDeleteUser.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DELETE ADMIN BY");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel.setBounds(347, 314, 202, 56);
		panel.add(lblNewLabel);
		
		comboBox = new JComboBox<>(new String[] {"ID", "NAME"});
		comboBox.setSelectedIndex(-1);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox.setBounds(663, 323, 255, 43);
		panel.add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(comboBox.getSelectedIndex()==-1) {
					JOptionPane.showMessageDialog( new JFrame(), "Select ID/NAME "," to Delete", JOptionPane.ERROR_MESSAGE);
				}
				try (Connection conn = Database.getConnection();
			             PreparedStatement stmt = conn.prepareStatement("SELECT id,username FROM admin");
			             ResultSet rs = stmt.executeQuery()) {

			            
			            String[] columnNames = {"User ID", "UserName"};
			            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
			            while (rs.next()) {
			                Object[] row = {rs.getInt("userid"), rs.getString("username")};
			                model.addRow(row);
			            }
			            JTable table = new JTable(model);
			            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			            JScrollPane scrollPane = new JScrollPane(table);
			    		scrollPane.setBounds(0, 0, 535, 835);
			    				
			    		frmDeleteUser.getContentPane().add(scrollPane);		            
			            
			            frmDeleteUser.setVisible(true);
			        } catch (SQLException e) {
			            e.printStackTrace();
			            JOptionPane.showMessageDialog( new JFrame(), "Database error: " , "View Users Error", JOptionPane.ERROR_MESSAGE);
			        }
		
				}
			
		});	
		
		JButton deleteButton = new JButton("DELETE");
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		deleteButton.setBounds(367, 474, 168, 38);
		deleteButton.addActionListener(e->deleteUser((String) comboBox.getSelectedItem()));
		panel.add(deleteButton);
		
		
		JButton backButton = new JButton("BACK");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		backButton.setBounds(743, 474, 168, 38);
		panel.add(backButton);
		
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmDeleteUser.dispose();
				new AdminPage(LoginPage.username);
			}
		});		
		
	}
	private void deleteUser(String ty) {
		
		
		if(ty.equals("ID")) {
		 
        String id = JOptionPane.showInputDialog(new JFrame(), "Enter admin id to delete:");
        if (id != null ) {
        	int i=Integer.parseInt(id);
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM admin WHERE id = ?")) {
                stmt.setInt(1, i);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "Admin deleted successfully!");
                    return;
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Admin not found.", "Delete Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(), "Database error: " , "Delete Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
		}
       
        
        if(ty.equals("NAME")) 
        	
        { String username = JOptionPane.showInputDialog(new JFrame(), "Enter adminname  to delete:");      	
        	if(username!=null)
        	{        	
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM admin WHERE username = ?")) {
                stmt.setString(1, username);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Admin deleted successfully!");
                    return;
                }
                else 
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Admin not found.", "Delete Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(), "Database error: " , "Delete Error", JOptionPane.ERROR_MESSAGE);
                return;
                
            }
            }
        	}
		
		
    }
}
