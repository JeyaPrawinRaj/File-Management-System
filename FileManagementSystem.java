package MiniProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class FileManagementSystem {
	private JFrame frmFileManagementSystem;	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileManagementSystem window = new FileManagementSystem();
					window.frmFileManagementSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	public FileManagementSystem() {
		initialize();
	}	
	private void initialize() {
		frmFileManagementSystem = new JFrame();
		frmFileManagementSystem.setTitle("FILE MANAGEMENT SYSTEM");
		frmFileManagementSystem.setBackground(new Color(240, 240, 240));
		frmFileManagementSystem.setBounds(0, 0,1920, 1080);
		frmFileManagementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFileManagementSystem.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel()
				{
			 
					private static final long serialVersionUID = 1L;

			@Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                
	                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\arunp\\Music\\mini project images\\filemanage.jpg"); // Update with your image path
	                g.drawImage(backgroundImage.getImage(), -385, -10, 1920,1080, this);
	            }
	        };
				
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(0, 0, 1920, 1080);
		frmFileManagementSystem.getContentPane().add(panel);
		panel.setLayout(null);    
		
		JLabel lblNewLabel = new JLabel("FILE  MANAGEMENT SYSTEM");
		lblNewLabel.setForeground(new Color(255, 215, 0));
		lblNewLabel.setBackground(new Color(192, 192, 192));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 70));
		lblNewLabel.setBounds(361, 37, 971, 139);
		panel.add(lblNewLabel);
		
		JButton startButton = 
				new JButton("START ");
		startButton.setForeground(new Color(220, 20, 60));
		startButton.setBackground(new Color(0, 0, 0));
		startButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		startButton.setBounds(689, 714, 205, 59);
		panel.add(startButton);
		
		startButton.addActionListener(new ActionListener() {
			@Override			
			public void actionPerformed(ActionEvent e) {
				LoginPage.main(null);
				frmFileManagementSystem.dispose();
			}
				
			
		});
		
		JLabel lblNewLabel_2 = new JLabel("BY");
		lblNewLabel_2.setBackground(new Color(240, 240, 240));
		lblNewLabel_2.setForeground(new Color(255, 255, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 31));
		lblNewLabel_2.setBounds(1213, 669, 43, 59);
		panel.add(lblNewLabel_2);
		
		
		JLabel lblNewLabel_3 = new JLabel("ARUN PRASATH.M");
		lblNewLabel_3.setForeground(new Color(255, 255, 0));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblNewLabel_3.setBounds(1266, 678, 260, 43);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("JEYA PRAWIN RAJ.K");
		lblNewLabel_3_1.setForeground(new Color(255, 255, 0));
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblNewLabel_3_1.setBounds(1266, 714, 260, 43);
		panel.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("RAM KUMAR.K");
		lblNewLabel_3_2.setForeground(new Color(255, 255, 0));
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblNewLabel_3_2.setBounds(1266, 757, 250, 33);
		panel.add(lblNewLabel_3_2);
	}

}
