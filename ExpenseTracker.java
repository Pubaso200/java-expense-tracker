import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ExpenseTracker extends JFrame {

    private JTextField titleInput, amountInput;
    private JComboBox<String> categoryInput;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private final String FILE_NAME = "expenses.csv";

    public ExpenseTracker() {
        setTitle("💸 Personal Expense Analyzer (Java Edition)");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- TOP PANEL: INPUT FORM ---
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBackground(new Color(240, 240, 240));

        inputPanel.add(new JLabel("Title:"));
        titleInput = new JTextField(10);
        inputPanel.add(titleInput);

        inputPanel.add(new JLabel("Amount (₹):"));
        amountInput = new JTextField(7);
        inputPanel.add(amountInput);

        inputPanel.add(new JLabel("Category:"));
        String[] categories = {"Food", "Transport", "Entertainment", "Utilities", "Other"};
        categoryInput = new JComboBox<>(categories);
        inputPanel.add(categoryInput);

        JButton addButton = new JButton("Add Expense");
        addButton.setBackground(new Color(79, 70, 229));
        addButton.setForeground(Color.WHITE);
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // --- CENTER PANEL: DATA TABLE ---
        String[] columns = {"Title", "Category", "Amount (₹)"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable expenseTable = new JTable(tableModel);
        expenseTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(expenseTable);
        add(scrollPane, BorderLayout.CENTER);

        // --- BOTTOM PANEL: SUMMARY ---
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total Spent: ₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        summaryPanel.add(totalLabel);
        add(summaryPanel, BorderLayout.SOUTH);

        // --- BUTTON ACTION LISTENER ---
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });

        // Load existing data on startup
        loadData();
    }

    private void addExpense() {
        String title = titleInput.getText().trim();
        String amountStr = amountInput.getText().trim();
        String category = (String) categoryInput.getSelectedItem();

        if (title.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            // Add to table
            tableModel.addRow(new Object[]{title, category, amount});
            
            // Save to file
            saveData(title, category, amount);

            // Clear inputs
            titleInput.setText("");
            amountInput.setText("");
            
            // Update summary
            updateSummary();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveData(String title, String category, double amount) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(title + "," + category + "," + amount);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    tableModel.addRow(new Object[]{data[0], data[1], Double.parseDouble(data[2])});
                }
            }
            updateSummary();
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private void updateSummary() {
        double total = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            total += (Double) tableModel.getValueAt(i, 2);
        }
        totalLabel.setText(String.format("Total Spent: ₹%.2f", total));
    }

    public static void main(String[] args) {
        // Set modern look and feel if available
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            ExpenseTracker app = new ExpenseTracker();
            app.setLocationRelativeTo(null); // Center on screen
            app.setVisible(true);
        });
    }
}
