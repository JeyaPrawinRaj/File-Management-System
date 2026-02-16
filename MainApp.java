package MiniProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.*;
import java.sql.*;
//import org.apache.poi.xslf.usermodel.XMLSlideShow; // For PPTX
//import org.apache.poi.hslf.usermodel.HSLFSlideShow; // For PPT
//import org.apache.poi.ss.usermodel.*;
//import org.apache.pdfbox.pdmodel.PDDocument; // For PDF

// Main Application Class
public class MainApp extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // Start the application
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainApp frame = new MainApp();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // MainApp Constructor
    public MainApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        setContentPane(contentPane);

        // Example content setup
        JButton userDashboardButton = new JButton("Go to User Dashboard");
        userDashboardButton.addActionListener(e -> new UserDashboard("user123", 1)); // Example user data

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(userDashboardButton, gbc);
    }

    // User Dashboard Class
    public static class UserDashboard extends JFrame {
        private String username;
        private int userId;

        private JButton downloadButton;
        private JButton changePasswordButton;
        private JButton backButton;
        private JButton logoutButton;

        public UserDashboard(String usr, int userId) {
            this.username = usr;
            this.userId = userId;

            setTitle("User Dashboard");
            setSize(1920, 1080);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new GridBagLayout());
            getContentPane().setBackground(new Color(240, 248, 255));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            // Search File Button
            downloadButton = new JButton("Search File");
            downloadButton.setBackground(new Color(0, 120, 215));
            downloadButton.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(downloadButton, gbc);
            downloadButton.addActionListener(e -> {
                dispose(); // Close User Dashboard
                new SearchPage(); // Open Search Page
            });

            // Change Password Button
            changePasswordButton = new JButton("Change Password");
            changePasswordButton.setBackground(new Color(0, 120, 215));
            changePasswordButton.setForeground(Color.WHITE);
            gbc.gridy = 1;
            changePasswordButton.addActionListener(e -> new ChangePasswordPage(userId));
            add(changePasswordButton, gbc);

            // Back Button
            backButton = new JButton("Back");
            backButton.setBackground(new Color(200, 0, 0));
            backButton.setForeground(Color.WHITE);
            gbc.gridy = 2;
            backButton.addActionListener(e -> dispose()); // Close User Dashboard
            add(backButton, gbc);

            // Logout Button
            logoutButton = new JButton("Logout");
            logoutButton.setBackground(new Color(200, 0, 0));
            logoutButton.setForeground(Color.WHITE);
            gbc.gridx = 2;
            gbc.gridy = 5;
            logoutButton.addActionListener(e -> logout());
            add(logoutButton, gbc);

            setVisible(true);
        }

        private void logout() {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginPage();
                userLogout(username);
            }
        }

        public static void userLogout(String username) {
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_logout_history (username, action, date) VALUES (?, ?, CURDATE())")) {
                stmt.setString(1, username);
                stmt.setString(2, "Logout");
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error logging logout: " + e.getMessage(), "History Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ChangePasswordPage Class
    public static class ChangePasswordPage extends JFrame {
        private JTextField newPasswordField;
        private int userId;

        public ChangePasswordPage(int userId) {
            this.userId = userId;
            setTitle("Change Password");
            setSize(300, 200);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new GridBagLayout());
            getContentPane().setBackground(new Color(240, 248, 255));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.EAST;

            JLabel passwordLabel = new JLabel("New Password:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(passwordLabel, gbc);

            newPasswordField = new JPasswordField(20); // Correct type to JPasswordField
            gbc.gridx = 1;
            add(newPasswordField, gbc);

            JButton changeButton = new JButton("Change");
            changeButton.setBackground(new Color(0, 120, 215));
            changeButton.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(changeButton, gbc);
            changeButton.addActionListener(e -> changePassword());

            JButton backButton = new JButton("Back");
            backButton.setBackground(new Color(200, 0, 0));
            backButton.setForeground(Color.WHITE);
            gbc.gridx = 1;
            add(backButton, gbc);
            backButton.addActionListener(e -> dispose());

            setVisible(true);
        }

        private void changePassword() {
            String newPassword = newPasswordField.getText();

            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a new password.");
                return;
            }

            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE users SET password = ? WHERE userid = ?")) {
                stmt.setString(1, newPassword);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Password changed successfully!");
                dispose();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Password change failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // SearchPage Class
    public static class SearchPage extends JFrame {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JComboBox<String> searchCriteriaCombo;
        private JTextField searchField;
        private DefaultTableModel tableModel;
        private JTable table;

        public SearchPage() {
            setTitle("Search Files");
            setSize(500, 400);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new GridBagLayout());
            getContentPane().setBackground(new Color(240, 248, 255));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel criteriaLabel = new JLabel("Search by:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(criteriaLabel, gbc);

            searchCriteriaCombo = new JComboBox<>(new String[]{"file_name", "title", "author", "description"});
            gbc.gridx = 1;
            add(searchCriteriaCombo, gbc);

            searchField = new JTextField(20);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            add(searchField, gbc);

            JButton searchButton = new JButton("Search");
            searchButton.setBackground(new Color(0, 120, 215));
            searchButton.setForeground(Color.WHITE);
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            add(searchButton, gbc);
            searchButton.addActionListener(e -> searchFiles());

            String[] columnNames = {"File Name", "Title", "Author", "Description", "View"};
            tableModel = new DefaultTableModel(columnNames, 0);
            table = new JTable(tableModel);

            JScrollPane scrollPane = new JScrollPane(table);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            add(scrollPane, gbc);

            setVisible(true);
        }

        private void searchFiles() {
            String searchCriteria = (String) searchCriteriaCombo.getSelectedItem();
            String searchText = searchField.getText();

            if (searchText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter search text.");
                return;
            }

            String query = "SELECT file_name, title, author, description, file_path FROM files WHERE " + searchCriteria + " LIKE ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, "%" + searchText + "%");
                ResultSet rs = stmt.executeQuery();

                // Clear previous results
                tableModel.setRowCount(0);

                while (rs.next()) {
                    String fileName = rs.getString("file_name");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String description = rs.getString("description");
                    String filePath = rs.getString("file_path");

                    Object[] row = {fileName, title, author, description, "View"};
                    tableModel.addRow(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error searching files: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Database Connection Helper Class
    public static class Database {
        public static Connection getConnection() throws SQLException {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection("jdbc:mysql://localhost:3306/mini", "root", "1234");
            } catch (ClassNotFoundException | SQLException e) {
                throw new SQLException("Database connection error: " + e.getMessage(), e);
            }
        }
    }

    // ViewFilePage Class (Displays file content and allows download)
    public static class ViewFilePage extends JFrame {
        public ViewFilePage(String fileName, String filePath) {
            setTitle("View File");
            setSize(500, 400);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            // Download Button
            JButton downloadButton = new JButton("Download File");
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(downloadButton, gbc);
            downloadButton.addActionListener(e -> downloadFile(filePath));

            // Close Button
            JButton closeButton = new JButton("Close");
            gbc.gridx = 1;
            add(closeButton, gbc);
            closeButton.addActionListener(e -> dispose());

            // File Display Label
            JLabel fileLabel = new JLabel("File: " + fileName);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            add(fileLabel, gbc);

            // Display file based on extension
            String extension = getFileExtension(filePath);
            switch (extension) {
                case "txt":
                    displayTextFile(filePath, gbc);
                    break;
                case "pdf":
                    //displayPDF(filePath, gbc);
                    break;
                case "ppt":
                case "pptx":
                    //displayPowerPoint(filePath, gbc);
                    break;
                case "xls":
                case "xlsx":
                    //displayExcel(filePath, gbc);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Unsupported file type.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }

            setVisible(true);
        }

        // Helper method to get file extension
        private String getFileExtension(String filePath) {
            String extension = "";
            int i = filePath.lastIndexOf('.');
            if (i > 0) {
                extension = filePath.substring(i + 1).toLowerCase();
            }
            return extension;
        }

        // Display content for text files
        private void displayTextFile(String filePath, GridBagConstraints gbc) {
            try {
                JTextArea textArea = new JTextArea(20, 40);
                textArea.read(new FileReader(filePath), null);
                JScrollPane scrollPane = new JScrollPane(textArea);
                gbc.gridy = 2;
                add(scrollPane, gbc);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading text file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Display content for PDF files
       /* private void displayPDF(String filePath, GridBagConstraints gbc) {
            try {
                PDDocument document = PDDocument.load(new File(filePath));
                JOptionPane.showMessageDialog(this, "PDF File: " + filePath, "PDF Viewer", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening PDF file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Display content for PowerPoint files (PPTX and PPT)
        private void displayPowerPoint(String filePath, GridBagConstraints gbc) {
            try {
                if (filePath.endsWith(".pptx")) {
                    XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(filePath));
                    JOptionPane.showMessageDialog(this, "PowerPoint Presentation: " + filePath, "PowerPoint Viewer", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    HSLFSlideShow ppt = new HSLFSlideShow(new FileInputStream(filePath));
                    JOptionPane.showMessageDialog(this, "PowerPoint Presentation: " + filePath, "PowerPoint Viewer", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening PowerPoint file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Display content for Excel files
        private void displayExcel(String filePath, GridBagConstraints gbc) {
            try {
                FileInputStream fis = new FileInputStream(new File(filePath));
                Workbook wb = new XSSFWorkbook(fis);
                Sheet sheet = wb.getSheetAt(0);
                StringBuilder content = new StringBuilder();
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        content.append(cell.toString()).append("\t");
                    }
                    content.append("\n");
                }
                JTextArea textArea = new JTextArea(content.toString());
                JScrollPane scrollPane = new JScrollPane(textArea);
                gbc.gridy = 2;
                add(scrollPane, gbc);
                wb.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading Excel file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }*/

        // Download the file to a specific location
        private void downloadFile(String filePath) {
            try {
                Path sourcePath = Paths.get(filePath);
                Path destinationPath = Paths.get("downloads", sourcePath.getFileName().toString());
                Files.createDirectories(destinationPath.getParent());
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(this, "File downloaded successfully to " + destinationPath);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error downloading file: " + e.getMessage(), "Download Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}