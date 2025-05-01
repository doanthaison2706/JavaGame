package Game.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Game.fileserver.wFile;

/**
 * InfoPanel là một JPanel hiển thị các thông tin về thời gian, số bước đi và cấp độ (level) của trò chơi.
 * Nó bao gồm các thành phần JLabel và một GradientComboBox để người chơi lựa chọn level.
 */
public class InfoPanel extends JPanel {
    private final JLabel timeLabel;
    private final JLabel moveCountLabel;
    private final GradientComboBox levelComboBox;

    /**
     * Khởi tạo một InfoPanel và cấu hình giao diện người dùng,
     * bao gồm nhãn thời gian, số bước đi, và danh sách lựa chọn cấp độ.
     * Dữ liệu cấp độ được đọc từ file thông qua lớp wFile.
     */
    public InfoPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        setBackground(Color.WHITE);
        timeLabel = new JLabel("00:00:00");
        moveCountLabel = new JLabel("0");
        levelComboBox = new GradientComboBox();

        JLabel timeTitle = new JLabel("Time:");
        JLabel levelTitle = new JLabel("Level:");
        JLabel moveTitle = new JLabel("Moves:");

        Font font = new Font("Arial", Font.PLAIN, 18);
        timeLabel.setFont(font);
        moveCountLabel.setFont(font);
        timeTitle.setFont(font);
        levelTitle.setFont(font);
        moveTitle.setFont(font);

        // Đọc dữ liệu level từ file và thêm vào levelComboBox
        ArrayList<String> levels = wFile.readFile();
        for (String level : levels) {
            levelComboBox.addItem(level);
        }

        // Thêm các thành phần vào panel
        add(timeTitle);
        add(timeLabel);
        add(levelTitle);
        add(levelComboBox);
        add(moveTitle);
        add(moveCountLabel);
    }

    /**
     * Trả về JLabel hiển thị thời gian chơi hiện tại.
     *
     * @return JLabel biểu diễn thời gian.
     */
    public JLabel getTimeLabel() {
        return timeLabel;
    }

    /**
     * Trả về JLabel hiển thị số bước đi của người chơi.
     *
     * @return JLabel biểu diễn số bước đi.
     */
    public JLabel getMoveCountLabel() {
        return moveCountLabel;
    }

    /**
     * Trả về GradientComboBox cho phép người chơi chọn level.
     *
     * @return JComboBox<String> biểu diễn danh sách cấp độ.
     */
    public JComboBox<String> getLevelComboBox() {
        return levelComboBox;
    }
}