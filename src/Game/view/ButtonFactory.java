package Game.view;

import javax.swing.*;
import java.awt.*;

public class ButtonFactory {
    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setBackground(new Color(40, 54, 85));
        button.setForeground(new Color(255, 98, 0));
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}
