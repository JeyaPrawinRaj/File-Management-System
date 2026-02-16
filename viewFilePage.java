package MiniProject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

class viewFilePage {

	private JFrame frame;
	private String filename;

	/**
	 * Launch the application.
	 */
	public static void main(String args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewFilePage window = new viewFilePage(args);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public viewFilePage(String name) {
		this.filename=name;
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setTitle("View File");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().setBackground(new Color(192, 192, 192));

        JTextArea fileContentArea = new JTextArea(15, 30);
            fileContentArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(fileContentArea);
            frame.add(scrollPane, BorderLayout.CENTER);

            JButton downloadButton = new JButton("Download");
            downloadButton.setBackground(new Color(0, 120, 215));
            downloadButton.setForeground(Color.WHITE);
            downloadButton.addActionListener(e -> downloadFile());
            frame.add(downloadButton, BorderLayout.SOUTH);

            loadFileContent(fileContentArea);

            frame.setVisible(true);
        }
            private void loadFileContent(JTextArea fileContentArea) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM files WHERE file_name = ?")) {
                stmt.setString(1, filename); 
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                   
                    File file = new File("D:\\MINI PROJECT FILES FOLDER\\UPLOADED FILES\\"+filename);
                    if (file.exists()) {
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            StringBuilder content = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                content.append(line).append("\n");
                            }
                            fileContentArea.setText(content.toString()); // Display content in JTextArea
                        } catch (IOException e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(new JFrame(), "Error reading the file: " , "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } 
                    else 
                    {
                        JOptionPane.showMessageDialog(new JFrame(), "File not found at: " + "UPLOADED FILES","", JOptionPane.ERROR_MESSAGE);
                    }
                } 
                else
                {
                    JOptionPane.showMessageDialog(new JFrame(), "File not found in the database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(), "Database Error " , "", JOptionPane.ERROR_MESSAGE);
            }
        }
        public static void logDownload(int userId, String username, int fileId, String fileName) {
        	
            String insertQuery = "INSERT INTO file_download_history (user_id, username, file_id, file_name,download_date ,download_time ) " +
                                 "VALUES (?, ?, ?, ?,CURDATE(), CURTIME())";            
                   
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                 
                stmt.setInt(1, userId);   
                stmt.setString(2, username);     
                stmt.setInt(3, fileId);                   
    	        stmt.setString(4, fileName);  
                stmt.executeUpdate();} 
            catch (SQLException e) {
            	JOptionPane.showMessageDialog(new JFrame(), "Error While Updating File Download History " , "", JOptionPane.ERROR_MESSAGE);
            	             
            }
        }
        
        

        

        private void downloadFile() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("D:\\MINI PROJECT FILES FOLDER\\DOWNLOADED FILES\\" + filename));
            int result = fileChooser.showSaveDialog(new JFrame());

            if (result == JFileChooser.APPROVE_OPTION) {
                File destination = fileChooser.getSelectedFile();
                try {
                    Files.copy(new File("D:\\MINI PROJECT FILES FOLDER\\UPLOADED FILES\\" + filename).toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(new JFrame(), "File downloaded successfully!");
                    
                    try (Connection conn = Database.getConnection();
                            PreparedStatement stmt = conn.prepareStatement("select u.userid,f.file_id from users u,files f where u.username=? and file_name=?")){
                            
                           stmt.setString(1, LoginPage.username); 
                           stmt.setString(2, filename);
                           ResultSet rs=stmt.executeQuery();
                           logDownload(rs.getInt("u.userid"),rs.getString("u.username"),rs.getInt("f.file_id"),rs.getString(filename));
                           }
                    catch (SQLException e) {
                    	e.printStackTrace();              
                    }
                    
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(new JFrame(), "File Download Failed:", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    @SuppressWarnings("serial") 
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if(value==null)
            	return this;
        	setText((value == null) ?  value.toString():"View");
            return this;
        }
    }

    @SuppressWarnings("serial") 
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox, searchPage sp) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    int row = searchPage.table.getSelectedRow();
                    String filename = (String) searchPage.tableModel.getValueAt(row, 0); 
                    viewFilePage.main(filename); 
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            setPushed(true);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            setPushed(false);
            return super.stopCellEditing();
        }

        @Override
        public void cancelCellEditing() {
            setPushed(false);
            super.cancelCellEditing();
        }

		public boolean isPushed() {
			return isPushed;
		}

		public void setPushed(boolean isPushed) {
			this.isPushed = isPushed;
		}
    
    
    }

	
	

