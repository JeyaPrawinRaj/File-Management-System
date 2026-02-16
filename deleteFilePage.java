package MiniProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;




class deleteFilePage {

	private JFrame frmDeleteUser;
	private JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					deleteFilePage window = new deleteFilePage();
					window.frmDeleteUser.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public deleteFilePage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDeleteUser = new JFrame();
		frmDeleteUser.setTitle("DELETE FILE");
		frmDeleteUser.setBounds(0,0,1920,1080);
		frmDeleteUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDeleteUser.getContentPane().setLayout(null);
		
		
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(533, 0, 1007, 845);
		frmDeleteUser.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DELETE FILE  BY");
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
					JOptionPane.showMessageDialog( new JFrame(), "Select the Type Of"," File to delete", JOptionPane.ERROR_MESSAGE);
				}
				try (Connection conn = Database.getConnection();
			             PreparedStatement stmt = conn.prepareStatement("SELECT file_id,file_name FROM files");
			             ResultSet rs = stmt.executeQuery()) {

			            
			            String[] columnNames = {"File ID", "File Name"};
			            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
			            while (rs.next()) {
			                Object[] row = {rs.getInt("file_id"), rs.getString("file_name")};
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
			            JOptionPane.showMessageDialog( new JFrame(), "Database error: " , "Search File Error", JOptionPane.ERROR_MESSAGE);
			        }
		
				}
			
		});
		
		
		
		JButton deleteButton = new JButton("DELETE");
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		deleteButton.setBounds(367, 474, 168, 38);
		deleteButton.addActionListener(e->deleteUser((String) comboBox.getSelectedItem()));
		panel.add(deleteButton);
		
		
		JButton backButton = new JButton("BACK");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
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
		 
        String id = JOptionPane.showInputDialog(new JFrame(), "Enter userid to delete:");
        if (id != null ) {
        	int i=Integer.parseInt(id);
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM files WHERE file_id = ?")) {
                stmt.setInt(1, i);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "File deleted successfully!");
                    return;
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "File not found.", "Delete Error", JOptionPane.ERROR_MESSAGE);
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
        	
        { String username = JOptionPane.showInputDialog(new JFrame(), "Enter File Name  to delete:");      	
        	if(username!=null)
        	{        	
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM files WHERE file_name = ?")) {
                stmt.setString(1, username);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(new JFrame(), "File deleted successfully!");
                    return;
                }
                else 
                {
                    JOptionPane.showMessageDialog(new JFrame(), "File not found.", "Delete Error", JOptionPane.ERROR_MESSAGE);
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
