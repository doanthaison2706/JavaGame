package Game.sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsMenu extends JFrame implements ActionListener {

    private JButton playMusicButton;
    private JButton stopMusicButton;
    private SoundEffect sound;

    public OptionsMenu(SoundEffect sound) {
        this.sound = sound;

        setTitle("Options Menu 🎵");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng Option thì chỉ tắt nó, không tắt cả game

        // Set layout đẹp hơn
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(230, 240, 255)); // Màu nền nhẹ nhàng

        // Tạo nút Play Music
        playMusicButton = new JButton("▶ Play Music");
        styleButton(playMusicButton, new Color(102, 204, 255));

        // Tạo nút Stop Music
        stopMusicButton = new JButton("■ Stop Music");
        styleButton(stopMusicButton, new Color(255, 102, 102));

        // Add các nút vào Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20); // Padding giữa các nút
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(playMusicButton, gbc);

        gbc.gridy = 1;
        add(stopMusicButton, gbc);

        // Gán sự kiện
        playMusicButton.addActionListener(this);
        stopMusicButton.addActionListener(this);
        playMusicButton.setOpaque(true);
        stopMusicButton.setOpaque(true);
        setVisible(true);
    }

    // Hàm giúp set style cho nút
    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true)); // Bo tròn viền
        button.setPreferredSize(new Dimension(200, 50)); // Kích thước nút
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playMusicButton) {
            System.out.println("Play Music Button Pressed");
            sound.play();
            sound.loop();
        } else if (e.getSource() == stopMusicButton) {
            System.out.println("Stop Music Button Pressed");
            sound.stopSound();
        }
    }

}
