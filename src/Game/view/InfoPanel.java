package Game.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Game.fileserver.wFile;

public class InfoPanel extends JPanel {
    private JLabel timeLabel;
    private JLabel moveCountLabel;
    private JComboBox<String> levelComboBox;

    public InfoPanel() {
        setBackground(new Color(0, 251, 217));
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        timeLabel = new JLabel("00:00:00");
        moveCountLabel = new JLabel("0");
        levelComboBox = new JComboBox<>();

        JLabel timeTitle = new JLabel("Time:");
        JLabel levelTitle = new JLabel("Level:");
        JLabel moveTitle = new JLabel("Moves:");

        Font font = new Font("Arial", Font.PLAIN, 18);
        timeLabel.setFont(font);
        moveCountLabel.setFont(font);
        timeTitle.setFont(font);
        levelTitle.setFont(font);
        moveTitle.setFont(font);

        levelComboBox.setBackground(new Color(40, 54, 85));
        levelComboBox.setForeground(new Color(255, 98, 0));

        ArrayList<String> levels = wFile.readFile();
        for (String level : levels) {
            levelComboBox.addItem(level);
        }

        add(timeTitle);
        add(timeLabel);
        add(levelTitle);
        add(levelComboBox);
        add(moveTitle);
        add(moveCountLabel);
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public JLabel getMoveCountLabel() {
        return moveCountLabel;
    }

    public JComboBox<String> getLevelComboBox() {
        return levelComboBox;
    }
}
