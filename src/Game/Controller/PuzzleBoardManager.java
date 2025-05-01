package Game.Controller;

import Game.fileserver.wFile;
import Game.point.Point2D;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PuzzleBoardManager {
    private final Control control;

    public PuzzleBoardManager(Control control) {
        this.control = control;
    }

    public void addBoard() {
        String s = (String) control.pz.getInfoPanel().getLevelComboBox().getSelectedItem();
        String[] Out = s.split("x");
        control.SIZE = Integer.parseInt(Out[0]);
        control.pz.getGameBoardPanel().removeAll();
        control.pz.getGameBoardPanel().setLayout(new GridLayout(control.SIZE, control.SIZE, 4, 4));
        int panelSize = 400;
        control.pz.getGameBoardPanel().setPreferredSize(new Dimension(panelSize, panelSize));

        int tileSize = panelSize / control.SIZE;
        control.matrix = new JButton[control.SIZE][control.SIZE];

        for (int i = 0; i < control.SIZE; i++) {
            for (int j = 0; j < control.SIZE; j++) {
                int value = i * control.SIZE + j + 1;
                JButton btn = new JButton(value + "");
                btn.setFont(new Font("MV Boli", Font.BOLD, 28));
                btn.setForeground(Color.WHITE);

                if (i == control.SIZE - 1 && j == control.SIZE - 1) {
                    btn.setText("");
                    btn.setBackground(Color.WHITE);
                } else {
                    btn.setBackground(getColorForValue(value, control.SIZE));
                }

                btn.setPreferredSize(new Dimension(tileSize, tileSize));
                btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                btn.setFocusPainted(false);
                btn.setContentAreaFilled(true);
                btn.setOpaque(true);
                control.matrix[i][j] = btn;
                control.pz.getGameBoardPanel().add(btn);

                btn.addActionListener(e -> {
                    if (control.isStartGame && control.isStartGame1 != 0) {
                        if (control.checkMove(btn)) {
                            control.moveButton(btn);
                            if (control.checkWin()) {
                                control.timeController.stop();

                                // Tạo JDialog tùy chỉnh
                                JDialog dialog = new JDialog();
                                dialog.setTitle("You Won!");
                                dialog.setSize(300, 200);
                                dialog.setLocationRelativeTo(null);  // Đặt cửa sổ thông báo ở giữa màn hình

                                // Đặt background và layout
                                dialog.setLayout(new BorderLayout());
                                dialog.getContentPane().setBackground(new Color(255, 230, 230)); // Nền pastel hồng nhẹ

                                // Thêm label thông báo
                                JLabel label = new JLabel("Congratulations! You Won!", SwingConstants.CENTER);
                                label.setFont(new Font("Arial", Font.BOLD, 16));
                                label.setForeground(new Color(255, 105, 180));  // Màu hồng nhẹ nhàng

                                // Tạo nút Continue
                                JButton continueButton = new JButton("Continue");
                                continueButton.setFont(new Font("Arial", Font.PLAIN, 14));
                                continueButton.setBackground(new Color(144, 238, 144));  // Màu xanh lá nhẹ
                                continueButton.setForeground(Color.BLUE);  // Màu chữ trắng
                                continueButton.setFocusPainted(false);  // Không hiển thị viền khi nhấn

                                // Lắng nghe sự kiện của nút
                                continueButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        // Đóng cửa sổ khi nhấn nút Continue
                                        dialog.dispose();

                                        // Tiếp tục trò chơi
                                        control.isStartGame = true;  // Set lại trạng thái của game
                                        // Các hành động tiếp theo bạn muốn khi game tiếp tục
                                    }
                                });

                                // Thêm label và nút vào dialog
                                dialog.add(label, BorderLayout.CENTER);
                                dialog.add(continueButton, BorderLayout.SOUTH);

                                // Hiển thị dialog
                                dialog.setVisible(true);

                                // Dừng game và ghi dữ liệu
                                control.isStartGame = false;
                                wFile.writeFile(control.pz.getInfoPanel().getTimeLabel().getText(), control.pz.getInfoPanel().getMoveCountLabel().getText());
                            }
                        }
                    }
                });
            }
        }
        control.pz.getGameBoardPanel().revalidate();
        control.pz.getGameBoardPanel().repaint();
    }

    private Color getColorForValue(int value, int size) {
        // Tính toán tỷ lệ dựa trên kích thước của ma trận
        float ratio = (float) value / (size * size - 1); // tỷ lệ từ 0 đến 1

        // Màu sắc bắt đầu từ hồng nhạt (255, 182, 193) -> vàng (255, 223, 100) -> xanh dương nhạt (173, 216, 230)
        int baseRed = 255;     // Hồng nhạt
        int baseGreen = 182;   // Hồng nhạt
        int baseBlue = 193;    // Hồng nhạt

        // Màu vàng giữa màu hồng và xanh dương nhạt
        int middleRed = 255;   // Vàng (đỏ sáng)
        int middleGreen = 223; // Vàng (xanh lá nhạt)
        int middleBlue = 100;  // Vàng

        // Màu kết thúc là xanh dương nhạt
        int endRed = 173;      // Xanh dương nhạt
        int endGreen = 216;    // Xanh dương nhạt
        int endBlue = 230;     // Xanh dương nhạt

        // Chuyển màu từ hồng nhạt -> vàng -> xanh dương nhạt
        int red, green, blue;
        if (ratio < 0.5) {
            // Từ hồng sang vàng (tính trong nửa đầu)
            float transitionRatio = ratio * 2; // Điều chỉnh cho nửa đầu
            red = (int) (baseRed - (baseRed - middleRed) * transitionRatio);
            green = (int) (baseGreen - (baseGreen - middleGreen) * transitionRatio);
            blue = (int) (baseBlue - (baseBlue - middleBlue) * transitionRatio);
        } else {
            // Từ vàng sang xanh dương nhạt (tính trong nửa sau)
            float transitionRatio = (ratio - 0.5f) * 2; // Điều chỉnh cho nửa sau
            red = (int) (middleRed - (middleRed - endRed) * transitionRatio);
            green = (int) (middleGreen - (middleGreen - endGreen) * transitionRatio);
            blue = (int) (middleBlue - (middleBlue - endBlue) * transitionRatio);
        }

        // Đảm bảo giá trị màu không vượt quá giới hạn
        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));

        return new Color(red, green, blue);
    }

    public Point2D getEmptyPos() {
        for (int i = 0; i < control.SIZE; i++) {
            for (int j = 0; j < control.SIZE; j++) {
                if (control.matrix[i][j].getText().equals("")) {
                    return new Point2D(i, j);
                }
            }
        }
        return null;
    }

    public void mixButton() {
        for (int k = 0; k < 500; k++) {
            Point2D p = getEmptyPos();
            Random r = new Random();
            int choice = r.nextInt(4);

            switch (choice) {
                case 0:
                    if (p.getX() > 0) swap(p, p.getX() - 1, p.getY());
                    break;
                case 1:
                    if (p.getY() < control.SIZE - 1) swap(p, p.getX(), p.getY() + 1);
                    break;
                case 2:
                    if (p.getX() < control.SIZE - 1) swap(p, p.getX() + 1, p.getY());
                    break;
                case 3:
                    if (p.getY() > 0) swap(p, p.getX(), p.getY() - 1);
                    break;
            }
        }
    }

    private void swap(Point2D p, int x, int y) {
        String tempText = control.matrix[p.getX()][p.getY()].getText();
        Color tempColor = control.matrix[p.getX()][p.getY()].getBackground();

        control.matrix[p.getX()][p.getY()].setText(control.matrix[x][y].getText());
        control.matrix[p.getX()][p.getY()].setBackground(control.matrix[x][y].getBackground());

        control.matrix[x][y].setText(tempText);
        control.matrix[x][y].setBackground(tempColor);
    }
}