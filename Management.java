import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.*;
import java.sql.*;
import java.util.*;

public class Management {
    protected static final String DB_URL = "jdbc:mysql://localhost:3306/Client";
    protected static final String DB_USER = "sqluser";
    protected static final String DB_PASSWORD = "password";
    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    //------------------------------------------------------------------------------------------------------(withdraw/deposit/transfer)----------------------------------------------------------------------------------------------------------------------------
    public static Account getAccount(int id) {
        Account account = null;
        String query = "SELECT id, pin, balance, op_day, op_month, op_year FROM accounts WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int pinCode = resultSet.getInt("pin");
                float balance = resultSet.getBigDecimal("balance").floatValue();
                int day = resultSet.getInt("op_day");
                int month = resultSet.getInt("op_month");
                int year = resultSet.getInt("op_year");

                Date openingDate = new Date(day, month, year);

                account = new Account(id, pinCode, openingDate, balance);
            }
        } catch (Exception _) {
        }

        return account;
    }
    public static boolean Withdraw(float money, int id) {
        String updateBalanceQuery = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
        Date date = new Date();
        Account account = new Account(id, 0, date, 0);
        if (getBalance(account) >= money) {
            try (Connection conn = getConnection();
                 PreparedStatement updateStmt = conn.prepareStatement(updateBalanceQuery)) {
                updateStmt.setFloat(1, money);
                updateStmt.setInt(2, id);
                updateStmt.executeUpdate();
                addTransaction(id,money,"withdraw");
                return true;
            } catch (SQLException _) {
                return false;
            }
        }
        return false;
    }
    public static boolean Transfer(float money, int id1, int id2) {
        Account account1=getAccount(id1);
        Account account2=getAccount(id2);
        if (accountExists(account1) && accountExists(account2) && getBalance(account1)>=money) {
            addTransaction(id1,money,"transfer");
            addTransaction(id2,money,"transfer");
            return Withdraw(money, id1) && Deposit(money, id2);
        }
        return false;
    }
    public static boolean Deposit(float money, int id) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setFloat(1, money);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            addTransaction(id,money,"deposit");
            return true;
        } catch (Exception _) {
            return false;
        }
    }
    public static float getBalance(Account account) {
        String query = "SELECT balance FROM accounts WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, account.id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("balance");
                }
            }
        } catch (Exception _) {
        }
        return -1;
    }
    public static void addTransaction(int id, float value, String transactionType) {
        String query = "INSERT INTO transaction_history (id, transaction_type, transaction_amount) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.setString(2, transactionType);
            stmt.setFloat(3, value);
            stmt.executeUpdate();
        } catch (Exception _) {
        }
    }
    //------------------------------------------------------------------------------------------------------(add/valid/existence client...)---------------------------------------------------------------------------------------------------------------------------------
    public static boolean clientExists(Client client) {
        String check = "SELECT cin FROM clients WHERE cin = " + client.cin;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(check);
            return !rs.next();
        } catch (Exception _) {
            return true;
        }
    }
    public static void addClient(Client client) {
        String sql = "INSERT INTO clients (cin, address, mail, password, first_name, last_name, birth_day, birth_month, birth_year, phone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        if (clientExists(client)) {
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, client.cin);
                stmt.setString(2, client.address);
                stmt.setString(3, client.mail);
                stmt.setString(4, hashPassword(client.password));
                stmt.setString(5, client.first_name);
                stmt.setString(6, client.last_name);
                stmt.setInt(7, client.birth_date.day);
                stmt.setInt(8, client.birth_date.month);
                stmt.setInt(9, client.birth_date.year);
                stmt.setInt(10, client.phone);
                stmt.executeUpdate();
            } catch (Exception _) {
            }
        }
    }
    public static boolean validClient(int cin, String password) {
        String sql = "SELECT * FROM clients WHERE cin = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cin);
            stmt.setString(2, hashPassword(password));
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception _) {
            return false;
        }
    }
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    public static boolean accountExists(Account account) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, account.id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception _) {
            return false;
        }
    }
    public static boolean ValidAccount(int id,int pin) {
        String sql = "SELECT * FROM accounts WHERE id = ? AND pin = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, pin);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception _) {
            return false;
        }
    }
    public static Account generateAccount(float balance) {
        Random random = new Random();
        int newid;
        int newPinCode;

        do {
            newid = random.nextInt(1_000_000_000);
            newPinCode = 1000 + random.nextInt(8999);
        } while (accountExists(new Account(newid, newPinCode, new Date(),balance)));

        return new Account(newid, newPinCode, new Date(), balance);
    }
    public static void addAccount(Account account, int cin) {
        String sql = "INSERT INTO accounts (id, cin, pin, balance, op_day, op_month, op_year) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, account.id);
            statement.setInt(2, cin);
            statement.setInt(3, account.pinCode);
            statement.setFloat(4, account.balance);
            statement.setInt(5, account.openingDate.day);
            statement.setInt(6, account.openingDate.month);
            statement.setInt(7, account.openingDate.year);
            statement.executeUpdate();
        } catch (Exception _) {
        }
    }
    //----------------------------------------------------------------------------------------------------------(ADMIN METHODS)-----------------------------------------------------------------------------------------------------------------------------------------
    static boolean modify_f_name(int cin, String new_f_name) {//
        String sql = "UPDATE clients SET first_name = '" + new_f_name + "' WHERE cin = " + cin;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(sql);
            return rowsUpdated > 0;
        } catch (Exception _) {
            return false;
        }
    }
    static boolean modify_l_name(int cin, String new_l_name) {
        String sql = "UPDATE clients SET last_name = '" + new_l_name + "' WHERE cin = " + cin;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(sql);
            return rowsUpdated > 0;
        } catch (Exception _) {
            return false;
        }
    }
    static boolean modify_address(int cin, String new_address) {
        String sql = "UPDATE clients SET address = '" + new_address + "' WHERE cin = " + cin;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(sql);
            return rowsUpdated > 0;
        } catch (Exception _) {
            return false;
        }
    }
    static boolean modify_mail(int cin, String new_mail) {
        String sql = "UPDATE clients SET mail = '" + new_mail + "' WHERE cin = " + cin;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(sql);
            return rowsUpdated > 0;
        } catch (Exception _) {
            return false;
        }
    }
    static boolean modify_password(int cin, String new_password) {
        String sql = "UPDATE clients SET password = '" + hashPassword(new_password) + "' WHERE cin = " + cin;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(sql);
            return rowsUpdated > 0;
        } catch (Exception _) {
            return false;
        }
    }
    static boolean modify_phone(int cin, int new_phone) {
        String sql = "UPDATE clients SET phone = '" + new_phone + "' WHERE cin = " + cin;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(sql);
            return rowsUpdated > 0;
        } catch (Exception _) {
            return false;
        }
    }
    static boolean modify_date(int cin, Date new_birth_date) {
        String sql = "UPDATE clients SET birth_day = '" + new_birth_date.day +
                "', birth_month = '" + new_birth_date.month +
                "', birth_year = '" + new_birth_date.year +
                "' WHERE cin = " + cin;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(sql);
            return rowsUpdated > 0;
        } catch (Exception _) {
            return false;
        }
    }
    static boolean deleteClient(int cin) {
        String sql = "DELETE FROM clients WHERE cin = " + cin;
        try (Connection con=getConnection();
        Statement stmt= con.createStatement()){
            int rowsUpdated = stmt.executeUpdate(sql);
            return rowsUpdated > 0;
        }catch(Exception _){
            return false;
        }
    }
    static boolean deleteAccount(int id) {
        String sql = "DELETE FROM accounts WHERE id = " + id;
        try (Connection con=getConnection();
            Statement stmt=con.createStatement()){
            int rowsUpdated = stmt.executeUpdate(sql);
            return rowsUpdated > 0;
        }catch (Exception _){
            return false;
        }
    }
    //-----------------------------------------------------------------------------------------------(add/valid/existence/ admin + secret key)------------------------------------------------------------------------------------------------------------------
    public static boolean validAdmin(int cin, String Password) {
        if (Password.equals(get_secret_key())) {
            String sql = "SELECT * FROM admins WHERE cin = ?";
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, cin);
                ResultSet rs = stmt.executeQuery();
                return rs.next();
            } catch (Exception _) {
                return false;
            }
        }
        return false;
    }
    public static boolean adminExists(Admin admin) {
        String sql = "SELECT phone FROM admins WHERE cin = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, admin.cin);
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (Exception _) {
            return false;
        }
    }
    public static void addAdmin(Admin admin, String key) {
        if (!key.equals(get_secret_key()) || adminExists(admin)) {
            return;
        }
        String sql = "INSERT INTO admins (cin,first_name,last_name,phone,day,month,year) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        //new admin
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, admin.cin);
            stmt.setString(2, admin.first_name);
            stmt.setString(3, admin.last_name);
            stmt.setInt(4, admin.phone);
            stmt.setInt(5, admin.birth_date.day);
            stmt.setInt(6, admin.birth_date.month);
            stmt.setInt(7, admin.birth_date.year);
            stmt.executeUpdate();
        } catch (Exception _) {
        }
    }
    protected static String get_secret_key() {
        File launchFile = new File("launch.txt");
        if (launchFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(launchFile))) {
                return reader.readLine();
            } catch (Exception _) {
                System.exit(1);
            }
        }
        return null;
    }
    //----------------------------------------------------------------------------------------------------------(Stylers for UI)-------------------------------------------------------------------------------------------------------------------------------------------
    protected static JLabel createStyledLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        return label;
    }
    protected static void makeWindowDraggable(JFrame frame, JPanel headerPanel) {
        final Point[] initialClick = new Point[1];
        headerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick[0] = e.getPoint();
            }
        });
        headerPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen() - initialClick[0].x;
                int y = e.getYOnScreen() - initialClick[0].y;
                frame.setLocation(x, y);
            }
        });
    }
}