package MiniProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class userPage {

	private JFrame frmUserPage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					userPage window = new userPage();
					window.frmUserPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public userPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUserPage = new JFrame();
		frmUserPage.setTitle("USER PAGE");
		frmUserPage.setBounds(0,0,1920,1080);
		frmUserPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUserPage.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(-12, 0, 1920,1080);
		frmUserPage.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("WELCOME "+LoginPage.username);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 31));
		lblNewLabel.setBounds(702, 194, 498, 45);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("CLICK HERE TO SEARCH FILE");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel_1.setBounds(726, 302, 351, 53);
		panel.add(lblNewLabel_1);
		
		JButton searchButton = new JButton("SEARCH");
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		searchButton.setBounds(816, 440, 210, 62);
		panel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchPage.main(null);
				frmUserPage.dispose();
			}
		});
		
		JButton backButton = new JButton("BACK");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		backButton.setBounds(1317, 704, 194, 53);
		panel.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage.main(null);
				frmUserPage.dispose();
			}
		});
	}
}
