import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BankApp extends Management {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BANK");
        frame.setSize(800, 650);
        ImageIcon icon = new ImageIcon("C:/Users/BL9/Desktop/learning/Java Project/bank_icon.png");
        frame.setIconImage(icon.getImage());
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), 50, 50));

        JPanel backgroundPanel = new JPanel() {
            private final Image backgroundImage = new ImageIcon("C:/Users/BL9/Desktop/learning/Java Project/bankbg.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new CardLayout());

        Color backgroundColor = new Color(0, 0, 0);
        Color textColor = Color.WHITE;
        Color buttonColor = new Color(0, 27, 122);
        JButton exitButton = createStyledButton("exit", buttonColor);
        JPanel headerPanel = new JPanel(new BorderLayout());
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
        //--------------------------------------------------------------------------------------------------------(Login Panel)--------------------------------------------------------------------------------------------------------------------------------------
        JPanel loginPanel = createStyledPanel(backgroundColor);
        JLabel cinLabel = createStyledLabel("CIN:", textColor);
        JTextField cinField = createStyledTextField();
        JLabel passwordLabel = createStyledLabel("Password:", textColor);
        JPasswordField passwordField = createStyledPasswordField();
        JButton loginButton = createStyledButton("Login", buttonColor);
        JButton switchToSignUpButton = createStyledButton("Sign Up", buttonColor);

        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        loginPanel.add(cinLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(cinField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        gbc.gridy = 2;
        loginPanel.add(loginButton, gbc);

        gbc.gridx = 0;
        loginPanel.add(switchToSignUpButton, gbc);
        //-----------------------------------------------------------------------------------------------------(bank account panel)------------------------------------------------------------------------------------------------------------------------------------------
        JPanel accountPanel = createStyledPanel(backgroundColor);
        JTextField id = createStyledTextField();
        JPasswordField pin = createStyledPasswordField();
        JButton loginbutton = createStyledButton("login", buttonColor);
        JButton createAccountButton = createStyledButton("Create New Account",buttonColor);
        JButton loginMenu = createStyledButton("Back", buttonColor);
        JLabel idLabel = createStyledLabel("ID:", textColor);
        JLabel pinLabel = createStyledLabel("PIN:", textColor);
        accountPanel.setLayout(new GridBagLayout());
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        accountPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        accountPanel.add(id, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        accountPanel.add(pinLabel, gbc);

        gbc.gridx = 1;
        accountPanel.add(pin, gbc);

        gbc.gridy = 2;
        accountPanel.add(loginbutton, gbc);

        gbc.gridy = 3;
        accountPanel.add(createAccountButton, gbc);

        gbc.gridx = 0;
        accountPanel.add(loginMenu, gbc);

        //---------------------------------------------------------------------------------------------------------(Sign-Up Panel)---------------------------------------------------------------------------------------------------------------------------------------
        JPanel signUpPanel = createStyledPanel(backgroundColor);
        JTextField firstNameField = createStyledTextField();
        JTextField lastNameField = createStyledTextField();
        JTextField addressField = createStyledTextField();
        JPasswordField signUpPasswordField = createStyledPasswordField();
        JPasswordField confirmPasswordField = createStyledPasswordField();
        JTextField emailField = createStyledTextField();
        JTextField phoneNumberField = createStyledTextField();
        JTextField cinSignUpField = createStyledTextField();

        JTextField birthDayField = new JTextField(2);
        styler(birthDayField);
        JTextField birthMonthField = new JTextField(2);
        styler(birthMonthField);
        JTextField birthYearField = new JTextField(4);
        styler(birthYearField);

        Date currentDate = new Date();
        JTextField openingDayField = new JTextField(2);
        styler(openingDayField);
        openingDayField.setText(Integer.toString(currentDate.day));
        openingDayField.setEditable(false);

        JTextField openingMonthField = new JTextField(2);
        openingMonthField.setText(Integer.toString(currentDate.month));
        openingMonthField.setEditable(false);

        styler(openingMonthField);
        JTextField openingYearField = new JTextField(4);
        openingYearField.setText(Integer.toString(currentDate.year));
        openingYearField.setEditable(false);
        styler(openingYearField);

        JTextField initialDepositField = createStyledTextField();
        JButton signUpButton = createStyledButton("Sign Up", buttonColor);
        JButton switchToLoginButton = createStyledButton("Back to Login", buttonColor);

        signUpPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0;
        signUpPanel.add(createStyledLabel("First Name:", textColor), gbc);
        gbc.gridx = 1;
        signUpPanel.add(firstNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        signUpPanel.add(createStyledLabel("Last Name:", textColor), gbc);
        gbc.gridx = 1;
        signUpPanel.add(lastNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        signUpPanel.add(createStyledLabel("Address:", textColor), gbc);
        gbc.gridx = 1;
        signUpPanel.add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        signUpPanel.add(createStyledLabel("Password:", textColor), gbc);
        gbc.gridx = 1;
        signUpPanel.add(signUpPasswordField, gbc);

        gbc.gridx =0; gbc.gridy = 4;
        signUpPanel.add(createStyledLabel("Confirm Password:", textColor), gbc);
        gbc.gridx = 1;
        signUpPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        signUpPanel.add(createStyledLabel("E-mail:", textColor), gbc);
        gbc.gridx = 1;
        signUpPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        signUpPanel.add(createStyledLabel("Phone Number:", textColor), gbc);
        gbc.gridx = 1;
        signUpPanel.add(phoneNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        signUpPanel.add(createStyledLabel("CIN:", textColor), gbc);
        gbc.gridx = 1;
        signUpPanel.add(cinSignUpField, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        signUpPanel.add(createStyledLabel("Birthdate (D/M/Y):", textColor), gbc);
        JPanel birthdatePanel = new JPanel(new GridLayout(1, 3, 5, 5));
        birthdatePanel.setPreferredSize(new Dimension(250, 40));
        birthdatePanel.setBackground(backgroundColor);
        birthdatePanel.add(birthDayField);
        birthdatePanel.add(birthMonthField);
        birthdatePanel.add(birthYearField);
        gbc.gridx = 1;
        signUpPanel.add(birthdatePanel, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        signUpPanel.add(createStyledLabel("Opening Date (D/M/Y):", textColor), gbc);

        JPanel openingDatePanel = new JPanel(new GridLayout(1, 3, 5, 5));
        openingDatePanel.setBackground(backgroundColor);
        openingDatePanel.setPreferredSize(new Dimension(250, 40));
        openingDatePanel.add(openingDayField);
        openingDatePanel.add(openingMonthField);
        openingDatePanel.add(openingYearField);

        gbc.gridx = 1;
        signUpPanel.add(openingDatePanel, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        signUpPanel.add(createStyledLabel("Initial Deposit:", textColor), gbc);

        gbc.gridx = 1;
        signUpPanel.add(initialDepositField, gbc);

        gbc.gridx = 0; gbc.gridy = 11;
        signUpPanel.add(switchToLoginButton, gbc);

        gbc.gridx = 1; gbc.gridy = 11;
        signUpPanel.add(signUpButton, gbc);
        // Create a panel for the header containing the exit button
        headerPanel.setBackground(backgroundColor);
        JButton xButton = new JButton("X");
        xButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        xButton.setForeground(new Color(0, 72, 255));
        xButton.setBorder(BorderFactory.createEmptyBorder());
        xButton.setFocusPainted(false);
        xButton.setContentAreaFilled(false);
        xButton.setPreferredSize(new Dimension(40, 35));
        headerPanel.add(xButton, BorderLayout.EAST);
        makeWindowDraggable(frame, headerPanel);
        //---------------------------------------------------------------------------------------------------(Banking Options Panel)--------------------------------------------------------------------------------------------------------------------------------
        JPanel bankingPanel = createStyledPanel(backgroundColor);
        bankingPanel.setLayout(new GridBagLayout());
        JButton withdrawButton = createStyledButton("Withdraw", buttonColor);
        JButton depositButton = createStyledButton("Deposit", buttonColor);
        JButton transferButton = createStyledButton("Transfer", buttonColor);
        JButton balanceButton = createStyledButton("Check Balance", buttonColor);
        JButton TransactionHistoryButton = createStyledButton("Transaction History", buttonColor);
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.gridx = 0; gbc.gridy = 0;
        bankingPanel.add(withdrawButton, gbc);
        gbc.gridx = 1;
        bankingPanel.add(transferButton, gbc);
        gbc.gridy = 1;
        bankingPanel.add(depositButton, gbc);
        gbc.gridx = 0;
        bankingPanel.add(balanceButton, gbc);
        gbc.gridy = 2;
        bankingPanel.add(TransactionHistoryButton, gbc);
        gbc.gridx = 1;
        bankingPanel.add(exitButton, gbc);
        //----------------------------------------------------------------------------------------------(adding the panels the main panel)--------------------------------------------------------------------------------------------------------------------------------
        mainPanel.add(loginPanel, "login");
        mainPanel.add(accountPanel, "account");
        mainPanel.add(signUpPanel, "signUp");
        mainPanel.add(bankingPanel, "banking");
        //----------------------------------------------------------------------------------(ActionListeners : Withdraw, Deposit, Transfer / login, sign-up )----------------------------------------------------------------------------------------------------------
        exitButton.addActionListener(_ -> frame.dispose());
        xButton.addActionListener(_ -> frame.dispose());
        switchToSignUpButton.addActionListener(_ -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "signUp");
        });
        switchToLoginButton.addActionListener(_ -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "login");
        });
        TransactionHistoryButton.addActionListener(_ -> {
            String idText = id.getText();
            int accountId = Integer.parseInt(idText);

            JPanel panel = createStyledPanel(backgroundColor);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            String[] columnNames = {"Transaction ID", "Transaction Type", "Date", "Amount"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable table = new JTable(tableModel);

            try (Connection conn = getConnection()) {
                String query = "SELECT num, transaction_type, transaction_date, transaction_amount " +
                        "FROM transaction_history WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setInt(1, accountId);

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int transactionId = resultSet.getInt("num");
                    String transactionType = resultSet.getString("transaction_type");
                    java.sql.Date sqlDate = resultSet.getDate("transaction_date");
                    double transactionAmount = resultSet.getDouble("transaction_amount");
                    tableModel.addRow(new Object[]{transactionId, transactionType, sqlDate, transactionAmount});
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error fetching transaction history: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }

            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane);
            JFrame transactionHistoryFrame = new JFrame("Transaction History");
            transactionHistoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            transactionHistoryFrame.setSize(500, 500);
            transactionHistoryFrame.add(panel);
            transactionHistoryFrame.setLocationRelativeTo(null);
            transactionHistoryFrame.setVisible(true);
        });
        loginButton.addActionListener(_ -> {
            String password = new String(passwordField.getPassword());
            String cinText = cinField.getText();
            if (cinText.isEmpty() && password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your CIN number and password.");
            } else if (cinText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your CIN number.");
            } else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your password.");
            } else {
                try {
                    int cin = Integer.parseInt(cinText);
                    if (validClient(cin, password)) {
                        JOptionPane.showMessageDialog(frame, "Login successful!");


                        passwordField.setText("");

                        CardLayout cl = (CardLayout) mainPanel.getLayout();
                        cl.show(mainPanel, "account");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid CIN or password.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid CIN.");
                }
            }
        });
        createAccountButton.addActionListener(_ -> {
            String cinText = cinField.getText();
            cinField.setText("");
            int cin = Integer.parseInt(cinText);
            Account account = generateAccount(0);
            addAccount(account,cin);
            JOptionPane.showMessageDialog(frame, "New account has been created successfully!");
            JOptionPane.showMessageDialog(frame, "ID :"+account.id+"\nPIN :"+account.pinCode);
        });
        signUpButton.addActionListener(_ -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String address = addressField.getText();
            String password = new String(signUpPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String email = emailField.getText();
            String phone = phoneNumberField.getText();
            String cinText = cinSignUpField.getText();
            String birthDayText = birthDayField.getText();
            String birthMonthText = birthMonthField.getText();
            String birthYearText = birthYearField.getText();
            String openingDayText = openingDayField.getText();
            String openingMonthText = openingMonthField.getText();
            String openingYearText = openingYearField.getText();
            String initialDepositText = initialDepositField.getText();

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match.");
                return;
            } else if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || password.isEmpty() ||
                    email.isEmpty() || phone.isEmpty() || cinText.isEmpty() || birthDayText.isEmpty() ||
                    birthMonthText.isEmpty() || birthYearText.isEmpty() || openingDayText.isEmpty() ||
                    openingMonthText.isEmpty() || openingYearText.isEmpty() || initialDepositText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                return;
            }
            try {
                int cin = Integer.parseInt(cinText);
                int birthDay = Integer.parseInt(birthDayText);
                int birthMonth = Integer.parseInt(birthMonthText);
                int birthYear = Integer.parseInt(birthYearText);
                Date birthDate = new Date(birthDay, birthMonth, birthYear);
                float bal = Float.parseFloat(initialDepositText);
                int phoneNumber = Integer.parseInt(phone);

                Client newClient = new Client(firstName, lastName, address, password, email, phoneNumber, birthDate, cin);

                if (clientExists(newClient)) {
                    addClient(newClient);
                    Account new_account = generateAccount(bal);
                    addAccount(new_account, cin);
                    JOptionPane.showMessageDialog(frame, "Sign-up successful! You can now log in.");
                    JOptionPane.showMessageDialog(frame, "ID :" + new_account.id + "\nPIN :" + new_account.pinCode);

                    firstNameField.setText("");
                    lastNameField.setText("");
                    addressField.setText("");
                    signUpPasswordField.setText("");
                    emailField.setText("");
                    phoneNumberField.setText("");
                    cinSignUpField.setText("");
                    birthDayField.setText("");
                    birthMonthField.setText("");
                    birthYearField.setText("");
                    openingDayField.setText("");
                    openingMonthField.setText("");
                    openingYearField.setText("");
                    initialDepositField.setText("");

                    CardLayout cl = (CardLayout) mainPanel.getLayout();
                    cl.show(mainPanel, "login");
                } else {
                    JOptionPane.showMessageDialog(frame, "Client already exists!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Please ensure that the CIN, birth date, phone number, and initial deposit values are valid and numeric.");
            }
        });
        loginbutton.addActionListener(_ -> {
            String pinpass = new String(pin.getPassword());
            String idText = id.getText();
            if (idText.isEmpty() && pinpass.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your CIN number and password.");
            } else if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your CIN number.");
            } else if (pinpass.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your password.");
            } else {
                try {
                    int ib = Integer.parseInt(idText);
                    int pass = Integer.parseInt(pinpass);
                    // Check if the client is valid
                    if (ValidAccount(ib, pass)) {
                        JOptionPane.showMessageDialog(frame, "Login successful!");
                        CardLayout cl = (CardLayout) mainPanel.getLayout();
                        cl.show(mainPanel, "banking");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid ID or PIN.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid ID.");
                }
            }
        });
        loginMenu.addActionListener(_ -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "login");
        });
        balanceButton.addActionListener(_ -> {
            int ib = Integer.parseInt(id.getText());
            Account a =getAccount(ib);
            float balance = getBalance(a);
            JOptionPane.showMessageDialog(frame, "Your balance is: " + balance+" DT");
        });
        withdrawButton.addActionListener(_ -> {
            try {
                int ib = Integer.parseInt(id.getText());
                String input = JOptionPane.showInputDialog("Enter the withdrawal amount:");
                if (input == null) {
                    JOptionPane.showMessageDialog(frame, "No input provided. Transaction canceled.");
                    return;
                }
                if (input.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Input cannot be empty. Please enter a valid withdrawal amount.");
                    return;
                }
                float amount = Float.parseFloat(input);
                if (Withdraw(amount, ib)) {
                    JOptionPane.showMessageDialog(frame, "Transaction successful! Your request has been completed.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Transaction unsuccessful. Please enter a valid withdrawal amount to proceed.");
                }
            } catch (Exception _) {
                JOptionPane.showMessageDialog(frame, "Invalid number format. Please enter a valid withdrawal amount.");
            }
        });
        depositButton.addActionListener(_ -> {
            try {
                int ib = Integer.parseInt(id.getText());
                String input = JOptionPane.showInputDialog("Enter the deposit amount:");

                if (input == null) {
                    JOptionPane.showMessageDialog(frame, "No input provided. Transaction canceled.");
                    return;
                }

                if (input.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Input cannot be empty. Please enter a valid deposit amount.");
                    return;
                }

                float amount = Float.parseFloat(input);

                if (Deposit(amount, ib)) {
                    JOptionPane.showMessageDialog(frame, "Your deposit was successfully processed!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Your deposit could not be processed successfully.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid number format. Please enter a valid deposit amount.");
            }
        });
        transferButton.addActionListener(_ -> {
            try {
                int ib1 = Integer.parseInt(id.getText());
                String recipientidInput = JOptionPane.showInputDialog("Enter recipient ID:");

                if (recipientidInput == null) {
                    JOptionPane.showMessageDialog(frame, "No recipient ID provided. Transaction canceled.");
                    return;
                }

                if (recipientidInput.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Recipient ID cannot be empty. Please enter a valid ID.");
                    return;
                }

                int ib2 = Integer.parseInt(recipientidInput);
                String amountInput = JOptionPane.showInputDialog("Enter the transfer amount:");

                if (amountInput == null) {
                    JOptionPane.showMessageDialog(frame, "No input proved. Transaction canceled.");
                    return;
                }

                if (amountInput.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Input cannot be empty. Please enter a valid transfer amount.");
                    return;
                }

                float amount = Float.parseFloat(amountInput);

                if (Transfer(amount, ib1, ib2)) {
                    JOptionPane.showMessageDialog(frame, "Transfer successful! Your transaction is complete.");
                } else {
                    JOptionPane.showMessageDialog(frame, "The transfer could not be completed successfully.");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid number format. Please enter valid inputs.");
            }

        });
        //-----------------------------------------------------------------------------------------(Creating the main panel / setting it to be visible)---------------------------------------------------------------------------------------------
        frame.add(mainPanel);
        frame.setVisible(true);
    }
    private static JPanel createStyledPanel(Color backgroundColor) {
        // Create the panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout()) {
            private final Image backgroundImage = new ImageIcon("C:/Users/BL9/Desktop/learning/Java Project/bankbg.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Fill the background with the background color first
                g.setColor(backgroundColor);
                g.fillRect(0, 0, getWidth(), getHeight());

                // Then, draw the background image on top
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setOpaque(false); // Ensure the panel is transparent so the background image shows
        return panel;
    }
    private static void styler(JTextField field) {
        field.setBackground(new Color(0, 12, 87));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, 18));
        field.setPreferredSize(new Dimension(250, 40));
        field.setBorder(BorderFactory.createLineBorder(new Color(0, 140, 255)));
        field.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                field.setBorder(BorderFactory.createLineBorder(new Color(0, 180, 255), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                field.setBorder(BorderFactory.createLineBorder(new Color(0, 140, 255)));
            }
        });
    }
    private static JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);

                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0, 140, 255));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

                g2.dispose();
            }
        };

        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 24));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                javax.swing.Timer timer = new javax.swing.Timer(10, new java.awt.event.ActionListener() {
                    float factor = 1.0f;

                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        factor -= 0.05f;
                        if (factor <= 0.5f) {
                            ((javax.swing.Timer) evt.getSource()).stop();
                        }
                        button.setBackground(new Color(
                                (int) (color.getRed() * factor),
                                (int) (color.getGreen() * factor),
                                (int) (color.getBlue() * factor)
                        ));
                    }
                });
                timer.start();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                javax.swing.Timer timer = new javax.swing.Timer(10, new java.awt.event.ActionListener() {
                    float factor = 0.5f;

                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        factor += 0.05f;
                        if (factor >= 1.0f) {
                            ((javax.swing.Timer) evt.getSource()).stop();
                        }
                        button.setBackground(new Color(
                                (int) (color.getRed() * factor),
                                (int) (color.getGreen() * factor),
                                (int) (color.getBlue() * factor)
                        ));
                    }
                });
                timer.start();
            }
        });

        return button;
    }
    private static JPasswordField createStyledPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBackground(new Color(0, 12, 87));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setPreferredSize(new Dimension(250, 40));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 140, 255), 1));
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 140, 255), 2));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 140, 255), 1));
            }
        });
        passwordField.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 140, 255), 2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 140, 255), 1));
            }
        });
        return passwordField;
    }
    private static JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);

        textField.setBackground(new Color(0, 12, 87)); // Dark gray background for a sleek look
        textField.setForeground(Color.WHITE); // White text
        textField.setCaretColor(Color.WHITE); // White caret (cursor)
        textField.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font style
        textField.setPreferredSize(new Dimension(250, 40));
        textField.setBorder(BorderFactory.createLineBorder(new Color(0, 140, 255), 1)); // Light blue border
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(new Color(0, 140, 255), 2)); // Brighter red on focus
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(new Color(0, 140, 255), 1)); // Default red when focus is lost
            }
        });
        textField.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(new Color(0, 140, 255), 2)); // Brighter red on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(new Color(0, 140, 255), 1)); // Default red border when not hovered
            }
        });

        return textField;
    }
}