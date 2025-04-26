package Game.view;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private JButton newGameButton;
    private JButton outGameButton;
    private JButton aiSupportButton;
    private JButton aiSolveButton;

    public ControlPanel() {
        setBackground(new Color(0, 251, 217));
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        newGameButton = ButtonFactory.createStyledButton("New Game");
        outGameButton = ButtonFactory.createStyledButton("Out Game");
        aiSupportButton = ButtonFactory.createStyledButton("AI hỗ trợ");
        aiSolveButton = ButtonFactory.createStyledButton("AI Giải Game");

        add(newGameButton);
        add(aiSupportButton);
        add(aiSolveButton);
        add(outGameButton);
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getOutGameButton() {
        return outGameButton;
    }

    public JButton getAiSupportButton() {
        return aiSupportButton;
    }

    public JButton getAiSolveButton() {
        return aiSolveButton;
    }
}
