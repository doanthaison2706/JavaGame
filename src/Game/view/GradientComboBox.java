package Game.view;

import javax.swing.*;
import java.awt.*;

public class GradientComboBox extends JComboBox<String> {
    public GradientComboBox() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isEnabled()) {
            Graphics2D g2d = (Graphics2D) g;
            // Tạo gradient từ hồng pastel sang xanh dương pastel
            GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 182, 193), 0, getHeight(), new Color(173, 216, 230));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight()); // Vẽ nền gradient cho combo box
        }
        super.paintComponent(g); // Vẽ các thành phần mặc định của combo box
    }
}