package Game.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class PuzzleAction implements ActionListener {

	private Puzzle pz;

	public PuzzleAction(Puzzle pz) {
		this.pz = pz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == pz.ButtonSolveRL) {
			System.out.println("AI Q-Learning button clicked.");
			pz.c.solveWithRL(); // gọi hàm AI Q-learning giải
			return;
		}
		if (src == pz.ButtonAI) {
			System.out.println("AI button clicked.");
			pz.c.showHint(); // Gọi hàm AI
			return;
		}
		if (src == pz.ButtonNew) {
			System.out.println("New Game button clicked.");
			pz.c.NewGame();
			pz.c.StartGame();
			return;
		}
		if (src == pz.ButtonOut) {
			System.out.println("Out Game button clicked.");
			int confirmed = JOptionPane.showConfirmDialog(null, "Do you want to Exit?", "Exit",
					JOptionPane.YES_NO_OPTION);
			if (confirmed == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			return;
		}

		// Nếu cần mở rộng thêm action khác:
		System.out.println("Unknown action source: " + src);
	}
}