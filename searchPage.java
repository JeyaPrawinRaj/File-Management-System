package MiniProject;

import java.awt.*;

import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

class searchPage  {

	private static JFrame frame =new JFrame();
	private static JComboBox<String> searchCriteriaCombo;
    private static JTextField searchField;
    static DefaultTableModel tableModel;
     static JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new searchPage();
			}
		});
	}

	/**
	 * Create the application.
	 */
	public searchPage() {
		 

   
		frame.setTitle("Search Files");
		frame.setSize(1920,1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		frame.getContentPane().setBackground(new Color(192, 192, 192));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel criteriaLabel = new JLabel("Search by:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(criteriaLabel, gbc);

        searchCriteriaCombo = new JComboBox<>(new String[]{"file_name", "title", "author", "description"});
        gbc.gridx = 1;
        frame.add(searchCriteriaCombo, gbc);

        searchField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        frame.add(searchField, gbc);

        JButton searchButton = new JButton("SEARCH");
        searchButton.setBackground(new Color(0, 120, 215));
        searchButton.setForeground(Color.WHITE);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        frame.add(searchButton, gbc);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFiles();
            }
        });

        String[] columnNames = {"File Name", "Title", "Author", "Description", "View"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

       
        table.getColumn("View").setCellRenderer(new ButtonRenderer());
        table.getColumn("View").setCellEditor(new ButtonEditor(new JCheckBox(), this));

        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        frame.add(scrollPane, gbc);
        
        JButton backButton = new JButton("BACK ");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		backButton.setBounds(1317, 704, 194, 53);
		gbc.gridx = 5;
        gbc.gridy = 6;
        frame.add(backButton,gbc);
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userPage.main(null);
				frame.dispose();
				
			}
		});
		

		frame.setVisible(true);
    }

    private static void searchFiles() {
        String searchCriteria = (String) searchCriteriaCombo.getSelectedItem();
        String searchText = searchField.getText();

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter search text.");
            return;
        }

        String query = "SELECT file_name, title, author, description, file_path FROM files WHERE " + searchCriteria + " LIKE ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchText);
            ResultSet rs = stmt.executeQuery();
            

            tableModel.setRowCount(0); 
            boolean fileFound=false;

            while (rs.next()) {
                String filename = rs.getString("file_name");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String description = rs.getString("description");
                String filePath = rs.getString("file_path");

                
                File file = new File(filePath);
                if (!file.exists()) {
                    JOptionPane.showMessageDialog(frame, "File not found: " + filePath, "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                
                JButton viewButton = new JButton("View");
                viewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	File file = new File(filename);
                        if (file.exists()) {
                            
                            JOptionPane.showMessageDialog(frame, "File found and ready to open: ");
                            viewFilePage.main(filePath);
                            
                        } else {
                            JOptionPane.showMessageDialog(frame, "File not found at: " + filePath, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                tableModel.addRow(new Object[]{filename, title, author, description,"View"});
                fileFound=true;
            }
            if(!fileFound) {
            	JOptionPane.showMessageDialog(frame, "no" + "results found", "", JOptionPane.ERROR_MESSAGE);
            	
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error: " , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   

    @SuppressWarnings("serial")
	class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true); 
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("View");
            return this;
        }
    }
    
    @SuppressWarnings("serial")
	class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        public ButtonEditor(JCheckBox checkBox, searchPage parent) {
            super(checkBox);
            button = new JButton("View");
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    int row = searchPage.table.getSelectedRow();
                    searchPage.tableModel.getValueAt(row, 0);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "view" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }

        @Override
        public void cancelCellEditing() {
            super.cancelCellEditing();
        }
    }
}

