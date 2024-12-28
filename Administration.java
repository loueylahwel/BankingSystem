import java.awt.BasicStroke;
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
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class Administration extends Management {
    public static void main(String[] args) {
        if (get_secret_key()==null) {
            FirstLaunch();
        }else {
            App_Runner();
        }
    }
    public static void FirstLaunch() {
        JFrame launchFrame = new JFrame("First Launch");
        launchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launchFrame.setSize(400, 300); // Set size before calling setShape
        launchFrame.setUndecorated(true);
        launchFrame.setLocationRelativeTo(null);
        launchFrame.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, launchFrame.getWidth(), launchFrame.getHeight(), 50, 50));
        ImageIcon icon = new ImageIcon("C:/Users/BL9/Desktop/learning/Java Project/admin_icon.png");
        launchFrame.setIconImage(icon.getImage());
        // Create the main pane
        JPanel mainPanel = createStyledPanel(new Color(35, 35, 35)); // Dark background for the panel
        // Secret Key Label
        JLabel keyLabel = createStyledLabel("Enter Secret Key:", Color.WHITE);
        // Styled text field for the secret key
        JPasswordField secretKeyField = createStyledPasswordField();
        // Styled button to confirm
        JButton confirmButton = createStyledButton("Confirm", new Color(255, 0, 0));
        // Styled button to exit
        JButton exitButton = createStyledButton("Exit", new Color(255, 0, 0));
        // Add action listener to the confirm button
        confirmButton.addActionListener(_ -> {
            char[] secretKeyChars = secretKeyField.getPassword();
            String secretKey = new String(secretKeyChars).trim(); // Trim to avoid unintended spaces
            if (secretKey.isEmpty()) {
                JOptionPane.showMessageDialog(launchFrame, "Secret key cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {// Save the secret key to the launch file
                try (FileWriter writer = new FileWriter("launch.txt")) { // Change to a directory you know is writable
                    writer.write(secretKey);
                    JOptionPane.showMessageDialog(launchFrame, "Secret key saved. Restarting application.");
                    launchFrame.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(launchFrame, "Error saving the secret key.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // Add action listener to the exit button
        exitButton.addActionListener(_ -> {
            launchFrame.dispose();
            System.exit(0);
        });
        // Layout for components
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(keyLabel, gbc);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(secretKeyField, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        mainPanel.add(confirmButton, gbc);

        gbc.gridx = 1;
        mainPanel.add(exitButton, gbc);

        launchFrame.add(mainPanel);
        launchFrame.setVisible(true);
    }
    private static void App_Runner() {
        JFrame frame = new JFrame("ADMINISTRATION");
        frame.setSize(800, 650);
        ImageIcon icon = new ImageIcon("C:/Users/BL9/Desktop/learning/Java Project/admin_icon.png");
        frame.setIconImage(icon.getImage());
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), 50, 50));

        JPanel mainPanel = new JPanel(new CardLayout());

        Color backgroundColor = new Color(0, 0, 0);
        Color textColor = Color.WHITE;
        Color buttonColor = new Color(134, 0, 0);

        JButton xButton = new JButton("X");
        xButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        xButton.setForeground(Color.RED);
        xButton.setBackground(null);
        xButton.setBorder(BorderFactory.createEmptyBorder());
        xButton.setFocusPainted(false);
        xButton.setContentAreaFilled(false);
        xButton.setPreferredSize(new Dimension(40, 35));

        // Create a panel for the header containing the exit button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(backgroundColor);
        headerPanel.add(xButton, BorderLayout.EAST);  // Position it at the top right
        makeWindowDraggable(frame, headerPanel);
        xButton.addActionListener(_ -> {
            frame.dispose();  // Close the window
        });
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
        //--------------------------------------------------------------------------------------------------------(Login Panel)---------------------------------------------------------------------------------------------------------
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

        //-------------------------------------------------------------------------------------------------------(Sign-Up Panel)-----------------------------------------------------------------------------------------
        JPanel signUpPanel = createStyledPanel(backgroundColor);
        JTextField cinSignUpField = createStyledTextField();
        JTextField firstNameField = createStyledTextField();
        JTextField lastNameField = createStyledTextField();
        JPasswordField signUpPasswordField = createStyledPasswordField();
        JTextField phoneNumberField = createStyledTextField();

        JTextField birthDayField = new JTextField(2);
        styler(birthDayField);
        JTextField birthMonthField = new JTextField(2);
        styler(birthMonthField);
        JTextField birthYearField = new JTextField(4);
        styler(birthYearField);

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
        signUpPanel.add(createStyledLabel("Password:", textColor), gbc);
        gbc.gridx = 1;
        signUpPanel.add(signUpPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        signUpPanel.add(createStyledLabel("Phone Number:", textColor), gbc);
        gbc.gridx = 1;
        signUpPanel.add(phoneNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        signUpPanel.add(createStyledLabel("CIN:", textColor), gbc);
        gbc.gridx = 1;
        signUpPanel.add(cinSignUpField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        signUpPanel.add(createStyledLabel("Birthdate (D/M/Y):", textColor), gbc);
        JPanel birthdatePanel = new JPanel(new GridLayout(1, 3, 5, 5));
        birthdatePanel.setBackground(backgroundColor);
        birthdatePanel.setPreferredSize(new Dimension(250, 40));
        birthdatePanel.add(birthDayField);
        birthdatePanel.add(birthMonthField);
        birthdatePanel.add(birthYearField);
        gbc.gridx = 1;
        signUpPanel.add(birthdatePanel, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        signUpPanel.add(switchToLoginButton, gbc);
        gbc.gridx = 1;
        signUpPanel.add(signUpButton, gbc);
        //------------------------------------------------------------------------------------------------------(Options Panel)--------------------------------------------------------------------------------------------------------------------------------
        JPanel bankingPanel = createStyledPanel(backgroundColor);
        bankingPanel.setLayout(new GridBagLayout());
        JButton modify_first_name = createStyledButton("Modify First Name", buttonColor);
        JButton modify_last_name = createStyledButton("Modify Last Name", buttonColor);
        JButton modify_address = createStyledButton("Modify Address", buttonColor);
        JButton modify_mail = createStyledButton("Modify Mail", buttonColor);
        JButton modify_password = createStyledButton("Modify Password", buttonColor);
        JButton modify_phone = createStyledButton("Modify Phone", buttonColor);
        JButton modify_date = createStyledButton("Modify Birthdate", buttonColor);
        JButton deleteClient = createStyledButton("Delete Client Account", buttonColor);
        JButton deleteAccount = createStyledButton("Delete Bank Account", buttonColor);
        JButton exitButton = createStyledButton("Exit", new Color(63, 0, 0));
        gbc.insets = new Insets(10, 15, 10, 15);

        gbc.gridx = 0; gbc.gridy = 0;
        bankingPanel.add(modify_first_name, gbc);
        gbc.gridx = 1;
        bankingPanel.add(modify_last_name, gbc);
        gbc.gridx=0;gbc.gridy = 3;
        bankingPanel.add(modify_address, gbc);
        gbc.gridx=1;
        bankingPanel.add(modify_mail, gbc);
        gbc.gridx=0;gbc.gridy = 4;
        bankingPanel.add(modify_password, gbc);
        gbc.gridx = 1;
        bankingPanel.add(modify_phone, gbc);
        gbc.gridx=0;gbc.gridy = 6;
        bankingPanel.add(deleteClient, gbc);
        gbc.gridx = 1;
        bankingPanel.add(modify_date, gbc);
        gbc.gridx=0;gbc.gridy = 8;
        bankingPanel.add(deleteAccount, gbc);
        gbc.gridx = 1;
        bankingPanel.add(exitButton, gbc);
        //--------------------------------------------------------------------------------------------------(adding panels to mainPanel)-----------------------------------------------------------------------------------------------------------------------
        mainPanel.add(loginPanel, "login");
        mainPanel.add(signUpPanel, "signUp");
        mainPanel.add(bankingPanel, "banking");
        //--------------------------------------------------------------------------------------(ActionListeners :  login, sign-up / modify_infos )--------------------------------------------------------------------------------------------------------------
        exitButton.addActionListener(_ -> frame.dispose());

        switchToSignUpButton.addActionListener(_ -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "signUp");
        });
        switchToLoginButton.addActionListener(_ -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "login");
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
                    if (validAdmin(cin, password)) {
                        JOptionPane.showMessageDialog(frame, "Login successful!");
                        CardLayout cl = (CardLayout) mainPanel.getLayout();
                        cl.show(mainPanel, "banking");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid CIN or password.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid CIN.");
                }
            }
        });
        signUpButton.addActionListener(_ -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String password = new String(signUpPasswordField.getPassword());
            String phone = phoneNumberField.getText();
            String cinText = cinSignUpField.getText();
            String birthDayText = birthDayField.getText();
            String birthMonthText = birthMonthField.getText();
            String birthYearText = birthYearField.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() ||
                    phone.isEmpty() || cinText.isEmpty() || birthDayText.isEmpty() ||
                    birthMonthText.isEmpty() || birthYearText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                return;
            }
            try {
                int cin = Integer.parseInt(cinText);
                int birthDay = Integer.parseInt(birthDayText);
                int birthMonth = Integer.parseInt(birthMonthText);
                int birthYear = Integer.parseInt(birthYearText);
                Date birthDate = new Date(birthDay, birthMonth, birthYear);

                int phoneNumber = Integer.parseInt(phone);

                Admin admin=new Admin(firstName,lastName,cin,phoneNumber,birthDate);
                if (!password.equals(get_secret_key())){
                    JOptionPane.showMessageDialog(frame, "You do not have permission to create an admin account.");
                    return;
                }
                if (!adminExists(admin)) {
                    addAdmin(admin,password);
                    JOptionPane.showMessageDialog(frame, "Sign-up successful! You can now log in.");
                    CardLayout cl = (CardLayout) mainPanel.getLayout();
                    cl.show(mainPanel, "login");
                } else {
                    JOptionPane.showMessageDialog(frame, "Admin already exists!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Please ensure that the CIN, birth date, phone number values are valid and numeric.");
            }
        });

        deleteAccount.addActionListener(_ -> {
            try {
                String id = JOptionPane.showInputDialog("Enter target's ID :");
                if (id == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }
                int ib = Integer.parseInt(id);
                if (deleteAccount(ib)){
                    JOptionPane.showMessageDialog(frame, "Account successfully deleted!");
                }else {
                    JOptionPane.showMessageDialog(frame, "Account not found!");
                }
            } catch (Exception _) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });
        deleteClient.addActionListener(_ -> {
            try {
                String cin = JOptionPane.showInputDialog("Enter target's CIN :");
                if (cin == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }
                int ci= Integer.parseInt(cin);
                if (deleteClient(ci)){
                    JOptionPane.showMessageDialog(frame, "Client successfully deleted!");
                }else {
                    JOptionPane.showMessageDialog(frame, "Client not found!");
                }
            }catch (Exception _) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });
        modify_first_name.addActionListener(_ -> {
            try {
                String cinInput = JOptionPane.showInputDialog("Enter target's cin :");
                if (cinInput == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }
                int cin = Integer.parseInt(cinInput);

                String newFirstName = JOptionPane.showInputDialog("Enter new first name :");
                if (newFirstName == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }

                if (modify_f_name(cin, newFirstName)) {
                    JOptionPane.showMessageDialog(frame, "First name changed successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Could not change first name!");
                }
            } catch (Exception _) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });
        modify_last_name.addActionListener(_ -> {
            try {
                String cinInput = JOptionPane.showInputDialog("Enter target's cin :");
                if (cinInput == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }
                int cin = Integer.parseInt(cinInput);

                String newLastName = JOptionPane.showInputDialog("Enter new last name :");
                if (newLastName == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }

                if (modify_l_name(cin, newLastName)) {
                    JOptionPane.showMessageDialog(frame, "Last name changed successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Could not change last name!");
                }
            } catch (Exception _) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });
        modify_address.addActionListener(_ -> {
            try {
                String cinInput = JOptionPane.showInputDialog("Enter target's cin :");
                if (cinInput == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }
                int cin = Integer.parseInt(cinInput);

                String newAddress = JOptionPane.showInputDialog("Enter new address :");
                if (newAddress == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }

                if (modify_address(cin, newAddress)) {
                    JOptionPane.showMessageDialog(frame, "Address changed successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Could not change address!");
                }
            } catch (Exception _) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });
        modify_mail.addActionListener(_ -> {
            try {
                String cinInput = JOptionPane.showInputDialog("Enter target's cin :");
                if (cinInput == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }
                int cin = Integer.parseInt(cinInput);

                String newEmail = JOptionPane.showInputDialog("Enter new e-mail :");
                if (newEmail == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }

                if (modify_mail(cin, newEmail)) {
                    JOptionPane.showMessageDialog(frame, "E-mail address changed successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Could not change the e-mail address!");
                }
            } catch (Exception _) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });
        modify_password.addActionListener(_ -> {
            try {
                String cinInput = JOptionPane.showInputDialog("Enter target's cin :");
                if (cinInput == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }
                int cin = Integer.parseInt(cinInput);

                String newPassword = JOptionPane.showInputDialog("Enter new password :");
                if (newPassword == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }

                if (modify_password(cin, newPassword)) {
                    JOptionPane.showMessageDialog(frame, "Password changed successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Could not change password!");
                }
            } catch (Exception _) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });
        modify_phone.addActionListener(_ -> {
            try {
                String cinInput = JOptionPane.showInputDialog("Enter target's cin :");
                if (cinInput == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }
                int cin = Integer.parseInt(cinInput);

                String phoneInput = JOptionPane.showInputDialog("Enter new phone number :");
                if (phoneInput == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }
                int newPhone = Integer.parseInt(phoneInput);

                if (modify_phone(cin, newPhone)) {
                    JOptionPane.showMessageDialog(frame, "Phone number changed successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Could not change phone number!");
                }
            } catch (Exception _) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });
        modify_date.addActionListener(_ -> {
            try {
                String cinInput = JOptionPane.showInputDialog("Enter target's cin :");
                if (cinInput == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }
                int cin = Integer.parseInt(cinInput);

                String dayInput = JOptionPane.showInputDialog("Enter new date :\nDAY");
                if (dayInput == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }
                int day = Integer.parseInt(dayInput);

                String monthInput = JOptionPane.showInputDialog("Enter new date :\nMONTH");
                if (monthInput == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }
                int month = Integer.parseInt(monthInput);

                String yearInput = JOptionPane.showInputDialog("Enter new date :\nYEAR");
                if (yearInput == null) {
                    JOptionPane.showMessageDialog(frame, "Operation canceled.");
                    return;
                }
                int year = Integer.parseInt(yearInput);

                Date newBirthdate = new Date(day, month, year);

                if (modify_date(cin, newBirthdate)) {
                    JOptionPane.showMessageDialog(frame, "Birthdate changed successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Could not change birthdate!");
                }
            } catch (Exception _) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });
        //-----------------------------------------------------------------------------------------(Creating the main panel / setting it to be visible)--------------------------------------------------------------------------------------------------------
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    //---------------------------------------------------------------------------------------------------------(Stylers for UI)-----------------------------------------------------------------------------------------------------------------------------------
    private static void styler(JTextField field) {
        // Set initial background and text properties
        field.setBackground(new Color(91, 0, 0)); // Dark red background
        field.setForeground(Color.WHITE); // White text for contrast
        field.setCaretColor(Color.WHITE); // White caret (cursor)
        field.setFont(new Font("Arial", Font.PLAIN, 18)); // Font style

        // Initial border with dark red color
        field.setBorder(BorderFactory.createLineBorder(new Color(211, 0, 0)));

        // Add mouse listener for hover effect
        field.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Change the border color when mouse enters
                field.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 2)); // Brighter red
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Revert the border color when mouse exits
                field.setBorder(BorderFactory.createLineBorder(new Color(211, 0, 0))); // Default dark red
            }
        });
    }
    static JPanel createStyledPanel(Color backgroundColor) {
        // Create the panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout()) {
            private final Image backgroundImage = new ImageIcon("C:/Users/BL9/Desktop/learning/Java Project/adminbg.png").getImage();

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
                g2.setColor(Color.RED);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

                g2.dispose();
            }
        };
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 24));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Darken the button color on hover with smooth transition
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
                // Return the button to the original color with smooth transition
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

        // Set background and text color with a more modern color scheme
        passwordField.setBackground(new Color(91, 0, 0)); // Dark gray background for modern look
        passwordField.setForeground(Color.WHITE); // White text
        passwordField.setCaretColor(Color.WHITE); // White caret (cursor)
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18)); // Font style

        // Set preferred size
        passwordField.setPreferredSize(new Dimension(250, 40));

        // Set border to a sleek color (light blue border)
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(211, 0, 0), 1));

        // Focus effect: Change the border to a brighter blue when focused
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(new Color(211, 0, 0), 2));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(new Color(211, 0, 0), 1));
            }
        });

        // Hover effect: Change border color when mouse enters and exits
        passwordField.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 2)); // Brighter red on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                passwordField.setBorder(BorderFactory.createLineBorder(new Color(211, 0, 0), 1)); // Default red border when not hovered
            }
        });

        return passwordField;
    }
    private static JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);

        // Set background and text color with a modern color scheme
        textField.setBackground(new Color(91, 0, 0)); // Dark gray background for a sleek look
        textField.setForeground(Color.WHITE); // White text
        textField.setCaretColor(Color.WHITE); // White caret (cursor)
        textField.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font style

        // Set preferred size
        textField.setPreferredSize(new Dimension(250, 40));

        // Set border to a sleek color (light blue border)
        textField.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 1)); // Light blue border

        // Focus effect: Change the border to a brighter blue when focused
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 2)); // Brighter red on focus
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 1)); // Default red when focus is lost
            }
        });

        // Hover effect: Change border color when mouse enters and exits
        textField.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 2)); // Brighter red on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 1)); // Default red border when not hovered
            }
        });

        return textField;
    }
}