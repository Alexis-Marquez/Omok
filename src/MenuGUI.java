import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

class MenuGUI {

    public JPanel connectionFrame;

    public JFrame getFrame() {
        return frame;
    }

    private final Game game;
    private final JFrame frame;
    private final JPanel panel;
    final JButton newGameButton;

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    boolean valid = false;
    public boolean gameOngoing = false;
    private JLabel messageLabel; // Label to display messages

    public MenuGUI(Game game) {
        this.game = game;
        frame = new JFrame("Omok");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 550);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        newGameButton = createStyledButton("Start New Game");
        JButton rulesButton = createStyledButton("Rules");
        JButton quitButton = createStyledButton("Quit");
        JButton strategyButton = createStyledButton("CPU Strategy");

        newGameButton.addActionListener(e -> {
            if (!gameOngoing) {
                try {
                    startNewGame();
                } catch (UnknownHostException ex) {
                    throw new RuntimeException(ex);
                }
            } else
                continueGame();
        });
        newGameButton.setMnemonic(KeyEvent.VK_N);
        rulesButton.addActionListener(e -> displayRules());
        rulesButton.setMnemonic(KeyEvent.VK_R);
        quitButton.addActionListener(e -> System.exit(0));
        quitButton.setMnemonic(KeyEvent.VK_Q);

        strategyButton.addActionListener(e -> selectStrategy());
        // Initialize the message panel

        // Initialize the message label

        panel.add(newGameButton);
        panel.add(rulesButton);
        panel.add(strategyButton);
        panel.add(quitButton);// Add the message panel to the main panel
        frame.add(panel);
        frame.setVisible(true);
    }

    public void startNewGame() throws UnknownHostException {
        Object[] options = { "1", "2" };
        int choice = JOptionPane.showOptionDialog(null, "Select Number of Players", "Number of Players",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        boolean network = false;
        if (choice == 1) {
            Object[] gameModes = { "Local", "Network" };
            int choice2 = JOptionPane.showOptionDialog(null, "Select Your Game Mode", "Game Mode",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, gameModes, gameModes[0]);
            network = choice2 == 1;
        }
        gameOngoing = true;
        newGameButton.setText("Continue");
        panel.setVisible(false);
        if (network) {
            game.setHost(true);
            game.init(1, true);
            game.startServer();
            game.gui.setVisibility(false);
            createTwoPlayerOptionsPanel();
        } else {
            game.init(choice, false);
            game.gui.setVisibility(true);
            game.gui.boardDrawing.repaint();
        }
    }

    public void continueGame() {
        panel.setVisible(false);
        game.gui.setVisibility(true);
    }

    private void createTwoPlayerOptionsPanel() throws UnknownHostException {
        JPanel playerPanel = createPlayerPanel();
        JPanel opponentPanel = createOpponentPanel();

        // Include both player and opponent panels in a parent panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3, 1)); // Increased rows for the new message panel
        optionsPanel.add(playerPanel);
        optionsPanel.add(opponentPanel);

        // Add an empty panel for messages
        JPanel messagePanel = createMessagePanel();
        optionsPanel.add(messagePanel);

        // Show the option dialog
        connectionFrame = new JPanel();
        connectionFrame.setLayout(new GridLayout(2, 1));
        connectionFrame.add(optionsPanel);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton connect = new JButton("Connect");
        JButton cancel = new JButton("Cancel");
        JPanel connectPanel = new JPanel();
        connectPanel.add(connect);
        JPanel cancelPanel = new JPanel();
        cancelPanel.add(cancel);
        connect.setPreferredSize(new Dimension(100, 40));
        cancel.setPreferredSize(new Dimension(100, 40));
        buttonPanel.add(connectPanel);
        buttonPanel.add(cancelPanel);
        connectionFrame.add(buttonPanel);
        frame.add(connectionFrame);
//        panel.setVisible(false);
        connectionFrame.setVisible(true);

        connect.addActionListener(e -> {
            JTextField opponentHostField = (JTextField) opponentPanel.getComponent(1);
            JTextField opponentIPField = (JTextField) opponentPanel.getComponent(3);
            String opponentPort = opponentHostField.getText();
            String opponentIP = opponentIPField.getText();
            game.setHost(false);
            try {
                game.init(1, true);
            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            }
            try {
                game.startClient(opponentIP, Integer.parseInt(opponentPort));
                valid = true;
            } catch (Exception err) {
                setMessage("Please enter a valid Port Number");
                valid = false;
                game.setHost(true);
            }
            // Add logic to connect to the online game server using the provided information

            });
        cancel.addActionListener(e -> {
            gameOngoing = false;
            game.gui.goBackToMenu();
            connectionFrame.setVisible(false);
            setVisibility(true);
            newGameButton.setText("New Game");
        });
    }

    private JPanel createMessagePanel() {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new FlowLayout());
        messagePanel.setBackground(Color.WHITE);
        messageLabel = new JLabel("Please enter your opponent information in the corresponding text field");
        messagePanel.add(messageLabel);
        return messagePanel;
    }

    private JPanel createPlayerPanel() throws UnknownHostException {
        JTextField playerHostField = new JTextField(String.valueOf(game.port));
        JTextField playerIPField = new JTextField(InetAddress.getLocalHost().getHostAddress());
        playerHostField.setEditable(false);
        playerIPField.setEditable(false);
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(2, 2));
        playerPanel.setBackground(Color.WHITE);
        playerPanel.setBorder(BorderFactory.createTitledBorder("Your Config"));

        playerPanel.add(new JLabel("Port Number:"));
        playerPanel.add(playerHostField);
        playerPanel.add(new JLabel("IP:"));
        playerPanel.add(playerIPField);

        return playerPanel;
    }

    private JPanel createOpponentPanel() {
        JTextField opponentHostField = new JTextField();
        JTextField opponentIPField = new JTextField();

        JPanel opponentPanel = new JPanel();
        opponentPanel.setLayout(new GridLayout(2, 2));
        opponentPanel.setBackground(Color.WHITE);
        opponentPanel.setBorder(BorderFactory.createTitledBorder("Opponent"));

        opponentPanel.add(new JLabel("Port Number:"));
        opponentPanel.add(opponentHostField);
        opponentPanel.add(new JLabel("IP:"));
        opponentPanel.add(opponentIPField);

        return opponentPanel;
    }

    private void selectStrategy() {
        Object[] strategyOptions = { "Smart", "Random" };
        int choice = JOptionPane.showOptionDialog(null, "Select Your Strategy", "Strategy", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, strategyOptions, strategyOptions[0]);

        String strategy = (choice == 0) ? "Smart" : "Random";
        game.strategy = strategy;
        gameOngoing = false;
        JOptionPane.showMessageDialog(null, "You selected the " + strategy + " strategy.");
        newGameButton.setText("New Game");
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        return button;
    }

    private void displayRules() {
        String rules = """
                Players alternate turns placing a stone of their
                color on an empty intersection.
                The winner is the first player to form an unbroken
                line of five stones of their color horizontally,
                vertically, or diagonally.""";
        JOptionPane.showMessageDialog(null, rules, "Rules",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void setVisibility(boolean x) {
        panel.setVisible(x);
    }

    public void setButtonText(String text) {
        newGameButton.setText(text);
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }
}
