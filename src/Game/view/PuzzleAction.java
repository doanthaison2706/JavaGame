package Game.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class PuzzleAction implements ActionListener {

	private final Puzzle pz;

	public PuzzleAction(Puzzle pz) {
		this.pz = pz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == pz.getControlPanel().getAiSolveButton()) {
			System.out.println("AI Q-Learning button clicked.");
			pz.c.AiSolve(); // gọi hàm AI Q-learning giải
			return;
		}
		if (src == pz.getControlPanel().getAiSupportButton()) {
			System.out.println("AI button clicked.");
			pz.c.AiSupport(); // Gọi hàm AI
			return;
		}
		if (src == pz.getControlPanel().getNewGameButton()) {
			System.out.println("New Game button clicked.");
			pz.c.NewGame();
			pz.c.StartGame();
			return;
		}
		if (src == pz.getControlPanel().getOutGameButton()) {
			System.out.println("Out Game button clicked.");

			// Setup phong cách pastel XANH cute
			javax.swing.UIManager.put("OptionPane.messageFont", new java.awt.Font("Comic Sans MS", java.awt.Font.PLAIN, 18));
			javax.swing.UIManager.put("OptionPane.buttonFont", new java.awt.Font("Comic Sans MS", java.awt.Font.BOLD, 16));
			javax.swing.UIManager.put("OptionPane.background", new java.awt.Color(230, 255, 250)); // Xanh mint pastel
			javax.swing.UIManager.put("Panel.background", new java.awt.Color(230, 255, 250));
			javax.swing.UIManager.put("Button.background", new java.awt.Color(204, 255, 229)); // Xanh ngọc nhạt
			javax.swing.UIManager.put("Button.foreground", new java.awt.Color(0, 102, 102)); // Chữ xanh đậm pastel dễ đọc

			int confirmed = JOptionPane.showOptionDialog(
					null,
					"🌿 Are you sure you want to return to Main Menu? 🌿",
					"Exit Game?",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, // Không hiện icon cốc Java nữa
					null, // Không icon
					null,
					null
			);

			if (confirmed == JOptionPane.YES_OPTION) {
				pz.setVisible(false);
				new MainMenu();
				MainMenu menu = new MainMenu();
				menu.setVisible(true);
			}
			return;
		}
		if (src == pz.getControlPanel().getStopAIButton()) {
			System.out.println("Stop AI button clicked.");
			pz.c.StopAI(); // Gọi hàm stop AI ở Control
			return;
		}
	}
}