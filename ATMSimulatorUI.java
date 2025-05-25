import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ATMSimulatorUI extends JFrame {

    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);

    // Panels representing the sections
    JPanel mainMenuPanel = new JPanel();
    JPanel createAccountPanel = new JPanel();
    JPanel loginPanel = new JPanel();
    JPanel transactionMenuPanel = new JPanel();
    JPanel depositPanel = new JPanel();
    JPanel withdrawPanel = new JPanel();
    JPanel resetPinPanel = new JPanel();
    JPanel transactionHistoryPanel = new JPanel();
    JPanel balanceDisplayPanel = new JPanel();
    JPanel exitPanel = new JPanel();

    // Shared fonts and colors to replicate style
    Color primaryBlue = new Color(2, 136, 209);
    Color lightBlue = new Color(224, 247, 250);
    Color bgWhiteTranslucent = new Color(255, 255, 255, 220);

    Font headingFont = new Font("Segoe UI", Font.BOLD, 20);
    Font subheadingFont = new Font("Segoe UI", Font.BOLD, 18);
    Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);

    // Fields for Create Account
    JTextField createAccNoField = new JTextField();
    JPasswordField createPinField = new JPasswordField();
    JTextField createBalanceField = new JTextField();
    JLabel createMsgLabel = new JLabel(" ", SwingConstants.CENTER);

    // Fields for Login
    JTextField loginAccNoField = new JTextField();
    JPasswordField loginPinField = new JPasswordField();
    JLabel loginMsgLabel = new JLabel(" ", SwingConstants.CENTER);

    // Transaction menu
    JLabel userAccLabel = new JLabel("", SwingConstants.CENTER);

    // Deposit
    JTextField depositAmountField = new JTextField();
    JLabel depositMsgLabel = new JLabel(" ", SwingConstants.CENTER);

    // Withdraw
    JTextField withdrawAmountField = new JTextField();
    JLabel withdrawMsgLabel = new JLabel(" ", SwingConstants.CENTER);

    // Reset PIN
    JPasswordField currentPinField = new JPasswordField();
    JPasswordField newPinField = new JPasswordField();
    JLabel resetPinMsgLabel = new JLabel(" ", SwingConstants.CENTER);

    // Transaction History
    DefaultListModel<String> historyListModel = new DefaultListModel<>();
    JList<String> historyList = new JList<>(historyListModel);

    // Balance Display
    JLabel balanceAmountLabel = new JLabel("", SwingConstants.CENTER);

    // For demonstration: dummy current user info
    String currentUserAccNo = "";
    String currentUserPin = "";
    double currentUserBalance = 0.0;
    java.util.List<String> currentUserTransactions = new ArrayList<>();

    public ATMSimulatorUI() {
        setTitle("ATM Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        // Background panel with gradient-like effect
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color c1 = new Color(224, 247, 250);
                Color c2 = new Color(128, 222, 234);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, c1, w, h, c2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        bgPanel.setLayout(new BorderLayout(0, 15));
        bgPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Container panel mimics the .container class in your CSS
        JPanel containerWrapper = new JPanel(new BorderLayout());
        containerWrapper.setBackground(bgWhiteTranslucent);
        containerWrapper.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(30, 35, 40, 35),
                BorderFactory.createLineBorder(new Color(150, 150, 150), 1)
        ));
        containerWrapper.add(container, BorderLayout.CENTER);

        bgPanel.add(containerWrapper, BorderLayout.CENTER);

        // Bottom credits panel
        JPanel creditsPanel = new JPanel();
        creditsPanel.setBackground(new Color(179, 229, 252));
        creditsPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        creditsPanel.setPreferredSize(new Dimension(380, 90));
        creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.Y_AXIS));

        JLabel devLabel = new JLabel("Developed by:", SwingConstants.CENTER);
        devLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        devLabel.setForeground(new Color(1, 87, 155));
        devLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        creditsPanel.add(devLabel);

        String[] devs = {
                "Nikhil Singh (24SCSE1010124)",
                "Mustehsan Nawaz (24SCSE1010978)", // Your name included here
                "Shivam Kumar (24SCSE1010013)",
                "Sanket Bhati (24SCSE1010035)"
        };
        for (String dev : devs) {
            JLabel label = new JLabel(dev, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            label.setForeground(new Color(1, 87, 155));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            creditsPanel.add(label);
        }

        // Add all panels to container (CardLayout)
        container.add(mainMenuPanel, "mainMenu");
        container.add(createAccountPanel, "createAccount");
        container.add(loginPanel, "login");
        container.add(transactionMenuPanel, "transactionMenu");
        container.add(depositPanel, "depositSection");
        container.add(withdrawPanel, "withdrawSection");
        container.add(resetPinPanel, "resetPinSection");
        container.add(transactionHistoryPanel, "transactionHistory");
        container.add(balanceDisplayPanel, "balanceDisplay");
        container.add(exitPanel, "exitMsg");

        // Build all sections
        buildMainMenuPanel();
        buildCreateAccountPanel();
        buildLoginPanel();
        buildTransactionMenuPanel();
        buildDepositPanel();
        buildWithdrawPanel();
        buildResetPinPanel();
        buildTransactionHistoryPanel();
        buildBalanceDisplayPanel();
        buildExitPanel();

        getContentPane().add(bgPanel, BorderLayout.CENTER);
        getContentPane().add(creditsPanel, BorderLayout.SOUTH);

        cardLayout.show(container, "mainMenu");
    }

    private void buildMainMenuPanel() {
        mainMenuPanel.setBackground(bgWhiteTranslucent);
        mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome to ATM Simulator", SwingConstants.CENTER);
        welcomeLabel.setFont(headingFont);
        welcomeLabel.setForeground(primaryBlue);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton createAccBtn = styledButton("Create Account");
        createAccBtn.addActionListener(e -> showSection("createAccount"));

        JButton loginBtn = styledButton("Login");
        loginBtn.addActionListener(e -> showSection("login"));

        JButton exitBtn = styledButton("Exit");
        exitBtn.addActionListener(e -> showSection("exitMsg"));

        mainMenuPanel.add(welcomeLabel);
        mainMenuPanel.add(createAccBtn);
        mainMenuPanel.add(loginBtn);
        mainMenuPanel.add(exitBtn);
    }

    private void buildCreateAccountPanel() {
        createAccountPanel.setBackground(bgWhiteTranslucent);
        createAccountPanel.setLayout(new BoxLayout(createAccountPanel, BoxLayout.Y_AXIS));
        createAccountPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel heading = new JLabel("Create New Account", SwingConstants.CENTER);
        heading.setFont(subheadingFont);
        heading.setForeground(primaryBlue);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Account Number Field with placeholder
        createAccNoField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        createAccNoField.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccNoField.setFont(new Font("Segoe UI", Font.ITALIC, 16)); // Italic for placeholder
        createAccNoField.setForeground(Color.GRAY); // Gray for placeholder
        createAccNoField.setBorder(inputBorder());
        createAccNoField.setText("Account Number"); // Placeholder text
        createAccNoField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (createAccNoField.getText().equals("Account Number")) {
                    createAccNoField.setText("");
                    createAccNoField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                    createAccNoField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (createAccNoField.getText().isEmpty()) {
                    createAccNoField.setText("Account Number");
                    createAccNoField.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                    createAccNoField.setForeground(Color.GRAY);
                }
            }
        });

        // PIN Field with placeholder
        createPinField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        createPinField.setAlignmentX(Component.CENTER_ALIGNMENT);
        createPinField.setFont(new Font("Segoe UI", Font.ITALIC, 16)); // Italic for placeholder
        createPinField.setForeground(Color.GRAY); // Gray for placeholder
        createPinField.setBorder(inputBorder());
        createPinField.setEchoChar((char) 0); // Show placeholder text
        createPinField.setText("PIN"); // Placeholder text
        createPinField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(createPinField.getPassword()).equals("PIN")) {
                    createPinField.setText("");
                    createPinField.setEchoChar('*'); // Restore echo char
                    createPinField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                    createPinField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(createPinField.getPassword()).isEmpty()) {
                    createPinField.setEchoChar((char) 0); // Hide actual text, show placeholder
                    createPinField.setText("PIN");
                    createPinField.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                    createPinField.setForeground(Color.GRAY);
                }
            }
        });

        // Initial Balance Field with placeholder
        createBalanceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        createBalanceField.setAlignmentX(Component.CENTER_ALIGNMENT);
        createBalanceField.setFont(new Font("Segoe UI", Font.ITALIC, 16)); // Italic for placeholder
        createBalanceField.setForeground(Color.GRAY); // Gray for placeholder
        createBalanceField.setBorder(inputBorder());
        createBalanceField.setText("Initial Balance"); // Placeholder text
        createBalanceField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (createBalanceField.getText().equals("Initial Balance")) {
                    createBalanceField.setText("");
                    createBalanceField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                    createBalanceField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (createBalanceField.getText().isEmpty()) {
                    createBalanceField.setText("Initial Balance");
                    createBalanceField.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                    createBalanceField.setForeground(Color.GRAY);
                }
            }
        });

        JButton submitBtn = styledButton("Submit");
        submitBtn.addActionListener(e -> createAccount());

        JButton backBtn = styledButton("Back");
        backBtn.addActionListener(e -> {
            clearCreateAccountFields();
            showSection("mainMenu");
        });

        createMsgLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createMsgLabel.setForeground(Color.RED);
        createMsgLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        createMsgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        createAccountPanel.add(heading);
        createAccountPanel.add(createAccNoField);
        createAccountPanel.add(Box.createVerticalStrut(10));
        createAccountPanel.add(createPinField);
        createAccountPanel.add(Box.createVerticalStrut(10));
        createAccountPanel.add(createBalanceField);
        createAccountPanel.add(Box.createVerticalStrut(10));
        createAccountPanel.add(submitBtn);
        createAccountPanel.add(backBtn);
        createAccountPanel.add(createMsgLabel);
    }

    private void buildLoginPanel() {
        loginPanel.setBackground(bgWhiteTranslucent);
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel heading = new JLabel("Login", SwingConstants.CENTER);
        heading.setFont(subheadingFont);
        heading.setForeground(primaryBlue);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Account Number Field with placeholder
        loginAccNoField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        loginAccNoField.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginAccNoField.setFont(new Font("Segoe UI", Font.ITALIC, 16)); // Italic for placeholder
        loginAccNoField.setForeground(Color.GRAY); // Gray for placeholder
        loginAccNoField.setBorder(inputBorder());
        loginAccNoField.setText("Account Number"); // Placeholder
        loginAccNoField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (loginAccNoField.getText().equals("Account Number")) {
                    loginAccNoField.setText("");
                    loginAccNoField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                    loginAccNoField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (loginAccNoField.getText().isEmpty()) {
                    loginAccNoField.setText("Account Number");
                    loginAccNoField.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                    loginAccNoField.setForeground(Color.GRAY);
                }
            }
        });

        // PIN Field with placeholder
        loginPinField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        loginPinField.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPinField.setFont(new Font("Segoe UI", Font.ITALIC, 16)); // Italic for placeholder
        loginPinField.setForeground(Color.GRAY); // Gray for placeholder
        loginPinField.setBorder(inputBorder());
        loginPinField.setEchoChar((char) 0); // Show placeholder
        loginPinField.setText("PIN"); // Placeholder
        loginPinField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(loginPinField.getPassword()).equals("PIN")) {
                    loginPinField.setText("");
                    loginPinField.setEchoChar('*'); // Restore echo char
                    loginPinField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                    loginPinField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(loginPinField.getPassword()).isEmpty()) {
                    loginPinField.setEchoChar((char) 0); // Hide actual text, show placeholder
                    loginPinField.setText("PIN");
                    loginPinField.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                    loginPinField.setForeground(Color.GRAY);
                }
            }
        });

        JButton submitBtn = styledButton("Login");
        submitBtn.addActionListener(e -> login());

        JButton backBtn = styledButton("Back");
        backBtn.addActionListener(e -> {
            clearLoginFields();
            showSection("mainMenu");
        });

        loginMsgLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginMsgLabel.setForeground(Color.RED);
        loginMsgLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        loginMsgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginPanel.add(heading);
        loginPanel.add(loginAccNoField);
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(loginPinField);
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(submitBtn);
        loginPanel.add(backBtn);
        loginPanel.add(loginMsgLabel);
    }

    private void buildTransactionMenuPanel() {
        transactionMenuPanel.setBackground(bgWhiteTranslucent);
        transactionMenuPanel.setLayout(new BoxLayout(transactionMenuPanel, BoxLayout.Y_AXIS));

        userAccLabel.setFont(subheadingFont);
        userAccLabel.setForeground(primaryBlue);
        userAccLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        userAccLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton depositBtn = styledButton("Deposit");
        depositBtn.addActionListener(e -> {
            if (verifyPin("Enter PIN to Deposit")) {
                showSection("depositSection");
            }
        });

        JButton withdrawBtn = styledButton("Withdraw");
        withdrawBtn.addActionListener(e -> {
            if (verifyPin("Enter PIN to Withdraw")) {
                showSection("withdrawSection");
            }
        });

        JButton resetPinBtn = styledButton("Reset PIN");
        resetPinBtn.addActionListener(e -> showSection("resetPinSection"));

        JButton transactionHistoryBtn = styledButton("Transaction History");
        transactionHistoryBtn.addActionListener(e -> showTransactionHistory());

        JButton balanceBtn = styledButton("Check Balance");
        balanceBtn.addActionListener(e -> {
            if (verifyPin("Enter PIN to View Balance")) {
                showBalance();
            }
        });

        JButton logoutBtn = styledButton("Logout");
        logoutBtn.addActionListener(e -> {
            clearLoginFields(); // Resets login placeholders
            clearUserData();    // Clears current user data
            showSection("mainMenu");
        });

        transactionMenuPanel.add(userAccLabel);
        transactionMenuPanel.add(depositBtn);
        transactionMenuPanel.add(withdrawBtn);
        transactionMenuPanel.add(resetPinBtn);
        transactionMenuPanel.add(transactionHistoryBtn);
        transactionMenuPanel.add(balanceBtn);
        transactionMenuPanel.add(logoutBtn);
    }

    private void buildDepositPanel() {
        depositPanel.setBackground(bgWhiteTranslucent);
        depositPanel.setLayout(new BoxLayout(depositPanel, BoxLayout.Y_AXIS));
        depositPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel heading = new JLabel("Deposit Money", SwingConstants.CENTER);
        heading.setFont(subheadingFont);
        heading.setForeground(primaryBlue);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel amountLabel = new JLabel("Amount to Deposit:", SwingConstants.LEFT);
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        depositAmountField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        depositAmountField.setAlignmentX(Component.CENTER_ALIGNMENT);
        depositAmountField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        depositAmountField.setBorder(inputBorder());

        JButton submitBtn = styledButton("Deposit");
        submitBtn.addActionListener(e -> deposit());

        JButton backBtn = styledButton("Back");
        backBtn.addActionListener(e -> {
            depositAmountField.setText("");
            depositMsgLabel.setText(" ");
            showSection("transactionMenu");
        });

        depositMsgLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        depositMsgLabel.setForeground(Color.RED);
        depositMsgLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        depositMsgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        depositPanel.add(heading);
        depositPanel.add(amountLabel); // Label for amount field
        depositPanel.add(depositAmountField);
        depositPanel.add(Box.createVerticalStrut(10));
        depositPanel.add(submitBtn);
        depositPanel.add(backBtn);
        depositPanel.add(depositMsgLabel);
    }

    private void buildWithdrawPanel() {
        withdrawPanel.setBackground(bgWhiteTranslucent);
        withdrawPanel.setLayout(new BoxLayout(withdrawPanel, BoxLayout.Y_AXIS));
        withdrawPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel heading = new JLabel("Withdraw Money", SwingConstants.CENTER);
        heading.setFont(subheadingFont);
        heading.setForeground(primaryBlue);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel amountLabel = new JLabel("Amount to Withdraw:", SwingConstants.LEFT);
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        withdrawAmountField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        withdrawAmountField.setAlignmentX(Component.CENTER_ALIGNMENT);
        withdrawAmountField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        withdrawAmountField.setBorder(inputBorder());

        JButton submitBtn = styledButton("Withdraw");
        submitBtn.addActionListener(e -> withdraw());

        JButton backBtn = styledButton("Back");
        backBtn.addActionListener(e -> {
            withdrawAmountField.setText("");
            withdrawMsgLabel.setText(" ");
            showSection("transactionMenu");
        });

        withdrawMsgLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        withdrawMsgLabel.setForeground(Color.RED);
        withdrawMsgLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        withdrawMsgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        withdrawPanel.add(heading);
        withdrawPanel.add(amountLabel); // Label for amount field
        withdrawPanel.add(withdrawAmountField);
        withdrawPanel.add(Box.createVerticalStrut(10));
        withdrawPanel.add(submitBtn);
        withdrawPanel.add(backBtn);
        withdrawPanel.add(withdrawMsgLabel);
    }

    private void buildResetPinPanel() {
        resetPinPanel.setBackground(bgWhiteTranslucent);
        resetPinPanel.setLayout(new BoxLayout(resetPinPanel, BoxLayout.Y_AXIS));
        resetPinPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel heading = new JLabel("Reset PIN", SwingConstants.CENTER);
        heading.setFont(subheadingFont);
        heading.setForeground(primaryBlue);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Current PIN Label and Field
        JLabel currentPinLabel = new JLabel("Current PIN:", SwingConstants.LEFT);
        currentPinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        currentPinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentPinLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        currentPinField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        currentPinField.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentPinField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        currentPinField.setBorder(inputBorder());
        // No placeholder, label handles instruction

        // New PIN Label and Field
        JLabel newPinLabel = new JLabel("New PIN:", SwingConstants.LEFT);
        newPinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        newPinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        newPinLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Add some space above

        newPinField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        newPinField.setAlignmentX(Component.CENTER_ALIGNMENT);
        newPinField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        newPinField.setBorder(inputBorder());
        // No placeholder, label handles instruction

        JButton submitBtn = styledButton("Change PIN");
        submitBtn.addActionListener(e -> resetPin());

        JButton backBtn = styledButton("Back");
        backBtn.addActionListener(e -> {
            currentPinField.setText("");
            newPinField.setText("");
            resetPinMsgLabel.setText(" ");
            showSection("transactionMenu");
        });

        resetPinMsgLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        resetPinMsgLabel.setForeground(Color.RED);
        resetPinMsgLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        resetPinMsgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        resetPinPanel.add(heading);
        resetPinPanel.add(currentPinLabel); // Add the label for current PIN
        resetPinPanel.add(currentPinField);
        resetPinPanel.add(newPinLabel);     // Add the label for new PIN
        resetPinPanel.add(newPinField);
        resetPinPanel.add(Box.createVerticalStrut(10));
        resetPinPanel.add(submitBtn);
        resetPinPanel.add(backBtn);
        resetPinPanel.add(resetPinMsgLabel);
    }

    private void buildTransactionHistoryPanel() {
        transactionHistoryPanel.setBackground(bgWhiteTranslucent);
        transactionHistoryPanel.setLayout(new BorderLayout(10, 10));
        transactionHistoryPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel heading = new JLabel("Transaction History", SwingConstants.CENTER);
        heading.setFont(subheadingFont);
        heading.setForeground(primaryBlue);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        historyList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(historyList);

        JButton backBtn = styledButton("Back");
        backBtn.addActionListener(e -> showSection("transactionMenu"));

        transactionHistoryPanel.add(heading, BorderLayout.NORTH);
        transactionHistoryPanel.add(scrollPane, BorderLayout.CENTER);
        transactionHistoryPanel.add(backBtn, BorderLayout.SOUTH);
    }

    private void buildBalanceDisplayPanel() {
        balanceDisplayPanel.setBackground(bgWhiteTranslucent);
        balanceDisplayPanel.setLayout(new BorderLayout(10, 10));
        balanceDisplayPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel heading = new JLabel("Your Balance", SwingConstants.CENTER);
        heading.setFont(subheadingFont);
        heading.setForeground(primaryBlue);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        balanceAmountLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        balanceAmountLabel.setForeground(new Color(2, 136, 209));
        balanceAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton backBtn = styledButton("Back");
        backBtn.addActionListener(e -> showSection("transactionMenu"));

        balanceDisplayPanel.add(heading, BorderLayout.NORTH);
        balanceDisplayPanel.add(balanceAmountLabel, BorderLayout.CENTER);
        balanceDisplayPanel.add(backBtn, BorderLayout.SOUTH);
    }

    private void buildExitPanel() {
        exitPanel.setBackground(bgWhiteTranslucent);
        exitPanel.setLayout(new BorderLayout());

        JLabel exitLabel = new JLabel("Thank you for using ATM Simulator!", SwingConstants.CENTER);
        exitLabel.setFont(headingFont);
        exitLabel.setForeground(primaryBlue);

        exitPanel.add(exitLabel, BorderLayout.CENTER);
    }

    // Button style like the CSS buttons
    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setFont(buttonFont);
        btn.setForeground(Color.WHITE);
        btn.setBackground(primaryBlue);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 0, 8, 0));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(lightBlue);
                btn.setForeground(primaryBlue);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(primaryBlue);
                btn.setForeground(Color.WHITE);
            }
        });
        return btn;
    }

    private Border inputBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryBlue, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private void showSection(String name) {
        cardLayout.show(container, name);
    }

    private void clearCreateAccountFields() {
        createAccNoField.setText("Account Number");
        createAccNoField.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        createAccNoField.setForeground(Color.GRAY);

        createPinField.setEchoChar((char) 0); // Show placeholder for PIN
        createPinField.setText("PIN");
        createPinField.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        createPinField.setForeground(Color.GRAY);

        createBalanceField.setText("Initial Balance");
        createBalanceField.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        createBalanceField.setForeground(Color.GRAY);

        createMsgLabel.setText(" ");
    }

    private void clearLoginFields() {
        loginAccNoField.setText("Account Number");
        loginAccNoField.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        loginAccNoField.setForeground(Color.GRAY);

        loginPinField.setEchoChar((char) 0);
        loginPinField.setText("PIN");
        loginPinField.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        loginPinField.setForeground(Color.GRAY);

        loginMsgLabel.setText(" ");
    }

    private void clearUserData() {
        currentUserAccNo = "";
        currentUserPin = "";
        currentUserBalance = 0.0;
        currentUserTransactions.clear();
    }

    private void createAccount() {
        String accNo = createAccNoField.getText().trim();
        String pin = new String(createPinField.getPassword()).trim();
        String balanceStr = createBalanceField.getText().trim();

        // Check if placeholder text is still present
        if (accNo.equals("Account Number") || pin.equals("PIN") || balanceStr.equals("Initial Balance")) {
            createMsgLabel.setText("Please fill all fields");
            return;
        }

        if (accNo.isEmpty() || pin.isEmpty() || balanceStr.isEmpty()) {
            createMsgLabel.setText("Please fill all fields");
            return;
        }

        double balance;
        try {
            balance = Double.parseDouble(balanceStr);
            if (balance < 0) {
                createMsgLabel.setText("Balance cannot be negative");
                return;
            }
        } catch (NumberFormatException e) {
            createMsgLabel.setText("Invalid balance amount");
            return;
        }

        // For demo, just save data in current session
        currentUserAccNo = accNo;
        currentUserPin = pin;
        currentUserBalance = balance;
        currentUserTransactions.clear();
        currentUserTransactions.add("Account created with initial balance: " + String.format("$ %.2f", balance));

        createMsgLabel.setForeground(new Color(0, 128, 0));
        createMsgLabel.setText("Account created successfully! Redirecting to Login...");

        // Auto redirect to login after 1.5 seconds
        Timer timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearCreateAccountFields();
                createMsgLabel.setForeground(Color.RED); // Reset color for next message
                showSection("login");
                ((Timer)e.getSource()).stop(); // Stop the timer so it doesn't fire again
            }
        });
        timer.setRepeats(false); // Ensure it only runs once
        timer.start();
    }

    private void login() {
        String accNo = loginAccNoField.getText().trim();
        String pin = new String(loginPinField.getPassword()).trim();

        // Check if placeholder text is still present
        if (accNo.equals("Account Number") || pin.equals("PIN")) {
            loginMsgLabel.setText("Please enter account number and PIN");
            return;
        }

        if (accNo.isEmpty() || pin.isEmpty()) {
            loginMsgLabel.setText("Please enter account number and PIN");
            return;
        }

        // For demo, check only against currentUserAccNo
        if (accNo.equals(currentUserAccNo) && pin.equals(currentUserPin)) {
            loginMsgLabel.setText(" "); // Clear any previous error message
            userAccLabel.setText("Welcome, Account: " + accNo);
            showSection("transactionMenu"); // Directly go to transaction menu
        } else {
            loginMsgLabel.setText("Invalid account number or PIN");
        }
    }

    // Helper method to verify PIN via a pop-up dialog
    private boolean verifyPin(String promptMessage) {
        JPasswordField pf = new JPasswordField();
        pf.setEchoChar('*'); // Ensure it echoes asterisks
        int option = JOptionPane.showConfirmDialog(
                this,
                pf,
                promptMessage, // Use the provided message
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            String enteredPin = new String(pf.getPassword()).trim();
            if (enteredPin.equals(currentUserPin)) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect PIN.", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false; // User cancelled
    }

    private void deposit() {
        String amountStr = depositAmountField.getText().trim();

        if (amountStr.isEmpty()) {
            depositMsgLabel.setText("Please enter an amount");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                depositMsgLabel.setText("Amount must be positive");
                return;
            }
        } catch (NumberFormatException e) {
            depositMsgLabel.setText("Invalid amount");
            return;
        }

        currentUserBalance += amount;
        currentUserTransactions.add("Deposited: " + String.format("$ %.2f", amount));
        depositMsgLabel.setForeground(new Color(0, 128, 0));
        depositMsgLabel.setText("Deposit successful!");

        depositAmountField.setText("");
    }

    private void withdraw() {
        String amountStr = withdrawAmountField.getText().trim();

        if (amountStr.isEmpty()) {
            withdrawMsgLabel.setText("Please enter an amount");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                withdrawMsgLabel.setText("Amount must be positive");
                return;
            }
        } catch (NumberFormatException e) {
            withdrawMsgLabel.setText("Invalid amount");
            return;
        }

        if (amount > currentUserBalance) {
            withdrawMsgLabel.setText("Insufficient balance");
            return;
        }

        currentUserBalance -= amount;
        currentUserTransactions.add("Withdrawn: " + String.format("$ %.2f", amount));
        withdrawMsgLabel.setForeground(new Color(0, 128, 0));
        withdrawMsgLabel.setText("Withdrawal successful!");

        withdrawAmountField.setText("");
    }

    private void resetPin() {
        String currentPinInput = new String(currentPinField.getPassword()).trim();
        String newPinInput = new String(newPinField.getPassword()).trim();

        if (currentPinInput.isEmpty() || newPinInput.isEmpty()) {
            resetPinMsgLabel.setText("Please fill both fields");
            return;
        }

        if (newPinInput.length() < 4) { // Example: enforce minimum PIN length
            resetPinMsgLabel.setText("New PIN must be at least 4 characters long");
            return;
        }

        if (!currentPinInput.equals(currentUserPin)) {
            resetPinMsgLabel.setText("Current PIN is incorrect");
            return;
        }

        currentUserPin = newPinInput;
        currentUserTransactions.add("PIN changed");
        resetPinMsgLabel.setForeground(new Color(0, 128, 0));
        resetPinMsgLabel.setText("PIN changed successfully!");

        currentPinField.setText("");
        newPinField.setText("");
    }

    private void showTransactionHistory() {
        historyListModel.clear();
        if (currentUserTransactions.isEmpty()) {
            historyListModel.addElement("No transactions available");
        } else {
            // Display transactions in reverse chronological order (most recent first)
            for (int i = currentUserTransactions.size() - 1; i >= 0; i--) {
                historyListModel.addElement(currentUserTransactions.get(i));
            }
        }
        showSection("transactionHistory");
    }

    private void showBalance() {
        balanceAmountLabel.setText(String.format("$ %.2f", currentUserBalance));
        showSection("balanceDisplay");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATMSimulatorUI ui = new ATMSimulatorUI();
            ui.setVisible(true);
        });
    }
}