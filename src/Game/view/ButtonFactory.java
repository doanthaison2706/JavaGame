package Game.view;

import javax.swing.*;
import java.awt.*;

/**
 * Lớp này tạo ra các nút với kiểu dáng gradient.
 */
public class ButtonFactory {

    /**
     * Lớp này kế thừa từ JButton để vẽ gradient lên nền nút.
     * Nền nút sẽ có gradient mặc định.
     */
    public static class GradientButton extends JButton {

        /**
         * Hàm dựng của GradientButton.
         *
         * @param text văn bản sẽ hiển thị trên nút.
         */
        public GradientButton(String text) {
            super(text);
        }

        /**
         * Ghi đè phương thức paintComponent để vẽ gradient lên nền nút.
         *
         * @param g đối tượng Graphics để vẽ các thành phần giao diện.
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            // Tạo gradient cho nền nút
            GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 182, 193), 0, getHeight(), new Color(173, 216, 230));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight()); // Vẽ nền gradient

            super.paintComponent(g); // Vẽ các thành phần mặc định của JButton (như văn bản)
        }
    }

    /**
     * Tạo một JButton với nền gradient và các thuộc tính tùy chỉnh.
     *
     * @param text văn bản sẽ hiển thị trên nút.
     * @return JButton đã được tạo với kiểu dáng gradient.
     */
    public static JButton createStyledButton(String text) {
        GradientButton button = new GradientButton(text); // Sử dụng GradientButton
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());  // Loại UI mặc định
        button.setForeground(new Color(255, 255, 255)); // Màu chữ (cam)
        button.setFont(new Font("Arial", Font.PLAIN, 18));  // Phông chữ
        button.setFocusPainted(false);  // Tắt viền focus
        button.setFocusable(false);  // Tắt khả năng focus
        button.setBorderPainted(false);  // Tắt viền của nút
        button.setOpaque(true);  // Bật chế độ opaque
        button.setContentAreaFilled(false);  // Tắt tô nền mặc định
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Viền nút (không có viền)

        return button;  // Trả về nút đã được tùy chỉnh
    }
}