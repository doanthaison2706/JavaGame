package Game.view;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    private JLabel titleLabel;

    public TitlePanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("PUZZLE GAME SIMPLE");
        titleLabel.setFont(new Font("MV Boli", Font.BOLD, 25));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }
}
