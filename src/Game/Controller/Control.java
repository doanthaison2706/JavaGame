package Game.Controller;

import Game.sound.SoundEffect;
import Game.point.Point2D;
import Game.view.Puzzle;

import javax.swing.*;
import java.awt.*;

/**
 * Lớp Control đóng vai trò là trung tâm điều phối logic trong trò chơi N-Puzzle.
 * Quản lý tương tác giữa giao diện người dùng, xử lý nút, AI, âm thanh và kiểm tra trạng thái trò chơi.
 */
public class Control {
	final SoundEffect Sound = new SoundEffect();
	public final Puzzle pz;
	public int SIZE;
	public JButton[][] matrix;
	public int move1 = 0;
	public boolean started = false;
	public boolean isStartGame = true;
	public int isStartGame1 = 0;

	public final TimeController timeController;
	public final PuzzleBoardManager puzzleBoardManager;
	public final GameManager gameManager;
	public final AIController aiController;

	private final String soundMove = "/Users/doanthaison/Code Everything/N-Puzzle-main/res/SoundEffect/move.wav";

	/**
	 * Khởi tạo lớp Control với tham chiếu đến giao diện Puzzle.
	 * Khởi tạo các bộ điều khiển con và thiết lập bảng ban đầu.
	 *
	 * @param pz Giao diện chính của trò chơi (Puzzle).
	 */
	public Control(Puzzle pz) {
		this.pz = pz;
		timeController = new TimeController(this);
		puzzleBoardManager = new PuzzleBoardManager(this);
		gameManager = new GameManager(this);
		aiController = new AIController(this);
		puzzleBoardManager.addBoard();
	}

	/**
	 * Kiểm tra người chơi đã chiến thắng trò chơi chưa.
	 *
	 * @return true nếu trò chơi đã được giải; false nếu chưa.
	 */
	public boolean checkWin() {
		if (matrix[SIZE - 1][SIZE - 1].getText().equals("")) {
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					if (i == SIZE - 1 && j == SIZE - 1) {
						return true;
					}
					if (!matrix[i][j].getText().equals(i * 3 + j + 1 + "")) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Thực hiện di chuyển một nút nếu hợp lệ, đồng thời cập nhật giao diện và phát âm thanh.
	 *
	 * @param button Nút cần di chuyển.
	 */
	public void moveButton(JButton button) {
		Point2D p = puzzleBoardManager.getEmptyPos();
		String buttonText = button.getText();
		Color buttonColor = button.getBackground();

		matrix[p.getX()][p.getY()].setText(buttonText);
		matrix[p.getX()][p.getY()].setBackground(buttonColor);

		button.setText("");
		button.setBackground(Color.WHITE);

		move1++;
		pz.getInfoPanel().getMoveCountLabel().setText(move1 + "");

		Sound.setFile(soundMove);
		Sound.play();
	}

	/**
	 * Kiểm tra xem nút được nhấn có thể di chuyển đến vị trí trống không.
	 *
	 * @param button Nút cần kiểm tra.
	 * @return true nếu có thể di chuyển; false nếu không.
	 */
	public boolean checkMove(JButton button) {
		if (button.getText().equals("")) {
			return false;
		}

		Point2D p = puzzleBoardManager.getEmptyPos();
		Point2D clickedPoint = null;

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (matrix[i][j].getText().equals(button.getText())) {
					clickedPoint = new Point2D(i, j);
				}
			}
		}

		if (p.getX() == clickedPoint.getX() && Math.abs(p.getY() - clickedPoint.getY()) == 1) {
			return true;
		}

		if (p.getY() == clickedPoint.getY() && Math.abs(p.getX() - clickedPoint.getX()) == 1) {
			return true;
		}

		return false;
	}

	/**
	 * Di chuyển ô trống theo hướng chỉ định.
	 *
	 * @param dir Hướng di chuyển: "UP", "DOWN", "LEFT", "RIGHT".
	 */
	public void moveByDirection(String dir) {
		Point2D p = puzzleBoardManager.getEmptyPos();
		int x = p.getX();
		int y = p.getY();

		switch (dir) {
			case "UP":
				if (x < SIZE - 1) moveButton(matrix[x + 1][y]);
				break;
			case "DOWN":
				if (x > 0) moveButton(matrix[x - 1][y]);
				break;
			case "LEFT":
				if (y < SIZE - 1) moveButton(matrix[x][y + 1]);
				break;
			case "RIGHT":
				if (y > 0) moveButton(matrix[x][y - 1]);
				break;
		}
	}

	/**
	 * Đặt lại trò chơi về trạng thái ban đầu.
	 */
	public void ResetGame() {
		gameManager.resetGame();
	}

	/**
	 * Bắt đầu trò chơi hiện tại.
	 */
	public void StartGame() {
		gameManager.startGame();
	}

	/**
	 * Tạo một trò chơi mới.
	 */
	public void NewGame() {
		gameManager.newGame();
	}

	/**
	 * Yêu cầu AI hỗ trợ bằng cách gợi ý một bước đi tiếp theo.
	 */
	public void AiSupport() {
		aiController.showHint();
	}

	/**
	 * Kích hoạt AI để tự động giải toàn bộ trò chơi.
	 */
	public void AiSolve() {
		aiController.solveWithRL();
	}

	/**
	 * Dừng quá trình giải của AI nếu đang chạy.
	 */
	public void StopAI() {
		aiController.stopAI();
	}
}