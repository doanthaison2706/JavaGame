package Game.view;

import javax.swing.*;
import java.awt.*;

/**
 * ControlPanel là một JPanel tùy chỉnh chứa các nút điều khiển cho trò chơi N-Puzzle.
 * Bao gồm các nút: Bắt đầu trò chơi mới, thoát trò chơi, hỗ trợ AI, giải bằng AI và dừng AI.
 */
public class ControlPanel extends JPanel {
    private final JButton newGameButton;
    private final JButton outGameButton;
    private final JButton aiSupportButton;
    private final JButton aiSolveButton;
    private final JButton stopAIButton;

    /**
     * Khởi tạo ControlPanel với các nút điều khiển được bố trí theo FlowLayout.
     */
    public ControlPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        setBackground(Color.WHITE);
        newGameButton = ButtonFactory.createStyledButton("New Game");
        outGameButton = ButtonFactory.createStyledButton("Out Game");
        aiSupportButton = ButtonFactory.createStyledButton("AI hỗ trợ");
        aiSolveButton = ButtonFactory.createStyledButton("AI Giải Game");
        stopAIButton = ButtonFactory.createStyledButton("Dừng AI");

        add(newGameButton);
        add(aiSupportButton);
        add(aiSolveButton);
        add(outGameButton);
        add(stopAIButton);
    }

    /**
     * Trả về nút "New Game".
     *
     * @return JButton đại diện cho nút bắt đầu trò chơi mới.
     */
    public JButton getNewGameButton() {
        return newGameButton;
    }

    /**
     * Trả về nút "Out Game".
     *
     * @return JButton đại diện cho nút thoát khỏi trò chơi.
     */
    public JButton getOutGameButton() {
        return outGameButton;
    }

    /**
     * Trả về nút "AI hỗ trợ".
     *
     * @return JButton đại diện cho nút AI hỗ trợ người chơi.
     */
    public JButton getAiSupportButton() {
        return aiSupportButton;
    }

    /**
     * Trả về nút "AI Giải Game".
     *
     * @return JButton đại diện cho nút AI tự động giải trò chơi.
     */
    public JButton getAiSolveButton() {
        return aiSolveButton;
    }

    /**
     * Trả về nút "Dừng AI".
     *
     * @return JButton đại diện cho nút dừng quá trình AI.
     */
    public JButton getStopAIButton() {
        return stopAIButton;
    }
}