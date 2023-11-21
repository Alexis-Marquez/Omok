import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
class MenuGUI {
    private Color BROWN = new Color(100, 69, 19);

    public JFrame getFrame() {
        return frame;
    }
    private final Game game;
    private final JFrame frame;
    private final JPanel panel;
    private final JButton newGameButton;
    public boolean gameOngoing = false;

    public MenuGUI(Game game) {
        this.game = game;
        frame = new JFrame("Omok");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 500);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.setBackground(BROWN);

        newGameButton = createStyledButton("Start New Game", Color.BLACK);
        JButton rulesButton = createStyledButton("Rules", Color.BLACK);
        JButton quitButton = createStyledButton("Quit", Color.BLACK);
        JButton strategyButton = createStyledButton("CPU Strategy", Color.BLACK);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!gameOngoing)startNewGame();
                else continueGame();
            }
        });
        newGameButton.setMnemonic(KeyEvent.VK_N);
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayRules();
            }
        });
        rulesButton.setMnemonic(KeyEvent.VK_R);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        quitButton.setMnemonic(KeyEvent.VK_Q);

        strategyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectStrategy();
            }
        });

        panel.add(newGameButton);
        panel.add(rulesButton);
        panel.add(strategyButton);
        // panel.add(setModeButton);
        panel.add(quitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public void startNewGame() {
        Object[] options = { "1", "2" };
        int choice = JOptionPane.showOptionDialog(null, "Select Number of Players", "Number of Players",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        gameOngoing = true;
        newGameButton.setText("Continue");
        game.init(choice);
        panel.setVisible(false);
        game.gui.setVisibility(true);
        game.gui.boardDrawing.repaint();
    }
    public void continueGame(){
        panel.setVisible(false);
        game.gui.setVisibility(true);
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

    private JButton createStyledButton(String text, Color textColor) {
        JButton button = new JButton(text);
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(textColor);
        return button;
    }

    private void displayRules() {
        String rules = "Players alternate turns placing a stone of their\n" +
                "color on an empty intersection.\n" +
                "The winner is the first player to form an unbroken\n" +
                "line of five stones of their color horizontally,\n" +
                "vertically, or diagonally.";
        JOptionPane.showMessageDialog(null, rules, "Rules",
                JOptionPane.INFORMATION_MESSAGE);
    }
    public void setVisibility(boolean x){
        panel.setVisible(x);
    }
    public void setButtonText(String text){
        newGameButton.setText(text);
    }
}

