package Game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

import Game.ai.*;
//import Game.database.Util;
import Game.fileserver.wFile;
import Game.point.Point2D;
import Game.sound.SoundEffect;

public class Control {
	private final Color ColorButton = new Color(253, 89, 65); // Màu đỏ cam nổi bật
	private final SoundEffect Sound = new SoundEffect();
	private final Puzzle pz;
	public int SIZE;
	private JButton[][] matrix;
	private int move1 = 0;
	// Util U;
	public boolean started = false;
	private boolean isStartGame = true;
	private int isStartGame1 = 0;
	/** Biến đếm thời gian */
	private int elapsedTime = 0;
	private int seconds = 0;
	private int minutes = 0;
	private int hours = 0;
	private Timer time;

	/** Định dạng chuỗi thời gian */
	private String seconds_string = String.format("%02d", seconds);
	private String minutes_string = String.format("%02d", minutes);
	private String hours_string = String.format("%02d", hours);

	/** Đường dẫn file âm thanh khi di chuyển */
	private final String soundMove = "/Users/doanthaison/Code Everything/N-Puzzle-main/move.wav";
	public Control(Puzzle pz) {
		this.pz = pz;
		add();
	}

	public void StartGame() {

		time = new javax.swing.Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				elapsedTime = elapsedTime + 1000;
				hours = (elapsedTime / 3600000);
				minutes = (elapsedTime / 60000) % 60;
				seconds = (elapsedTime / 1000) % 60;
				seconds_string = String.format("%02d", seconds);
				minutes_string = String.format("%02d", minutes);
				hours_string = String.format("%02d", hours);
				pz.timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
			}
		});
		if (started == false) {
			started = true;
			pz.ButtonNew.setText("Reset");
			start();
		} else {
			started = false;
			// pz.ButtonNew.setText("Start");
			// reset();
		}
		this.isStartGame1 = 1;
		isStartGame = true;
		move1 = 0;
		pz.JTextCount.setText("0");
	}
	// reset game
	public void ResetGame() {
		add();
		reset();
		move1 = 0;
		pz.JTextCount.setText("0");
		pz.ButtonNew.setText("NewGame");
		this.isStartGame1 = 0;
		started = false;
	}
	//game mới
	public void NewGame() {
		add();
		mixButton();
		pz.ButtonNew.setText("Reset");

		this.isStartGame1 = 0;
	}

	void start() {
		time.start();
	}

	void stop() {
		if (elapsedTime != 0) {
			time.stop();

		}
	}

	void reset() {
		stop();
		elapsedTime = 0;
		seconds = 0;
		minutes = 0;
		hours = 0;
		seconds_string = String.format("%02d", seconds);
		minutes_string = String.format("%02d", minutes);
		hours_string = String.format("%02d", hours);
		pz.timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
	}

	public void add() {
		String s = (String) pz.ComboBox.getSelectedItem(); // Lấy dữ liệu trong combobox
		String[] Out = s.split("x"); // Tách chuỗi
		SIZE = Integer.parseInt(Out[0]); // Lấy kích thước
		pz.jPanel1.removeAll(); // Xóa các button cũ

		// Set GridLayout có khoảng cách rõ ràng giữa các ô
		pz.jPanel1.setLayout(new GridLayout(SIZE, SIZE, 4, 4)); // khoảng cách giữa các ô (hgap, vgap)

		// Cố định kích thước panel chứa nút (có thể thay đổi nếu cần responsive)
		int panelSize = 400;
		pz.jPanel1.setPreferredSize(new Dimension(panelSize, panelSize));

		int tileSize = panelSize / SIZE; // Tính kích thước button cho vừa

		matrix = new JButton[SIZE][SIZE];

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				JButton btn = new JButton(i * SIZE + j + 1 + "");
				btn.setFont(new Font("MV Boli", Font.BOLD, 28));
				btn.setForeground(Color.WHITE);
				btn.setBackground(ColorButton);

				btn.setPreferredSize(new Dimension(tileSize, tileSize)); // Kích thước động
				btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Thêm viền rõ ràng
				btn.setFocusPainted(false);
				btn.setContentAreaFilled(true); // Đảm bảo background hiển thị
				btn.setOpaque(true);
				matrix[i][j] = btn;
				pz.jPanel1.add(btn);

				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (isStartGame && isStartGame1 != 0) {
							if (checkMove(btn)) {
								moveButton(btn);
								if (checkWin()) {
									stop();
									JOptionPane.showMessageDialog(null, "You Won");
									isStartGame = false;
									writeFile();
								}
							}
						}
					}

					private void writeFile() {
						wFile.writeFile(pz.timeLabel.getText(), pz.JTextCount.getText());
					}
				});
			}
		}
		matrix[SIZE - 1][SIZE - 1].setText(""); // Button cuối để trống
		pz.jPanel1.revalidate(); // Cập nhật lại layout
		pz.jPanel1.repaint();
	}
	// lấy toạ độ ô trống
	public Point2D getEmptyPos() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (matrix[i][j].getText().equals("")) {
					return new Point2D(i, j);
				}
			}
		}
		return null;
	}
	// trộn button
	public void mixButton() {
		for (int k = 0; k < 500; k++) {
			Point2D p = getEmptyPos();
			Random r = new Random();
			int choice = r.nextInt(4);
			switch (choice) {
			case 0:
				if (p.getX() > 0) {
					matrix[p.getX()][p.getY()].setText(matrix[p.getX() - 1][p.getY()].getText());
					matrix[p.getX() - 1][p.getY()].setText("");
				}
				break;
			case 1:
				if (p.getY() < SIZE - 1) {
					matrix[p.getX()][p.getY()].setText(matrix[p.getX()][p.getY() + 1].getText());
					matrix[p.getX()][p.getY() + 1].setText("");
				}
				break;
			case 2:
				if (p.getX() < SIZE - 1) {
					matrix[p.getX()][p.getY()].setText(matrix[p.getX() + 1][p.getY()].getText());
					matrix[p.getX() + 1][p.getY()].setText("");
				}
				break;
			case 3:
				if (p.getY() > 0) {
					matrix[p.getX()][p.getY()].setText(matrix[p.getX()][p.getY() - 1].getText());
					matrix[p.getX()][p.getY() - 1].setText("");
				}
				break;
			}
		}
	}
	public boolean checkWin() {
		// Kiểm tra ô cuối có phải trống (giá trị 0)
		if (!matrix[SIZE - 1][SIZE - 1].getText().equals("0")) return false;

		// Duyệt qua tất cả các ô, kiểm tra giá trị từng ô
		for (int i = 0; i < SIZE - 1; i++) { // Không cần kiểm tra ô cuối cùng nữa
			for (int j = 0; j < SIZE - 1; j++) {
				// Tính giá trị mong muốn tại vị trí i, j
				int expectedValue = i * SIZE + j + 1;
				// Kiểm tra giá trị ô hiện tại
				if (!matrix[i][j].getText().equals(String.valueOf(expectedValue))) {
					return false;
				}
			}
		}
		return true; // Nếu không có ô nào sai thì đã chiến thắng
	}
	// di chuyển button
	public void moveButton(JButton button) {
		Point2D p = getEmptyPos();
		matrix[p.getX()][p.getY()].setText(button.getText());
		button.setText("");
		move1++;
		pz.JTextCount.setText(move1 + "");
		Sound.setFile(soundMove);
		Sound.play();
	}
	// check buuton có thể di chuyển được không
	public boolean checkMove(JButton button) {
		if (button.getText().equals("")) {
			return false;
		}
		Point2D p = getEmptyPos();
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
	 * Gợi ý nước đi tốt nhất hiện tại cho người chơi bằng cách sử dụng PuzzleHintSolver.
	 */
	public void showHint() {
		PuzzleHintSolver hintSolver = new PuzzleHintSolver(matrix, SIZE);
		String bestMove = hintSolver.getHint();

		if (bestMove == null) {
			JOptionPane.showMessageDialog(null, "Không tìm được nước đi gợi ý!");
			return;
		}
		moveByDirection(bestMove);
	}
	/**
	 * Tự động giải puzzle hiện tại bằng AI Q-Learning.
	 * AI sẽ thực hiện các nước đi dựa trên Q-Table đã huấn luyện.
	 */
	public void solveWithRL() {
		RLAgent agent = new RLAgent();
		agent.loadQTable("data/qtable.dat"); // load Q-Table đã train
		PuzzleEnv env = new PuzzleEnv(SIZE);
		env.loadCurrentBoard(matrix); // chuyển từ matrix GUI vào env
		Timer aiTimer = new Timer(300, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (env.isSolved()) {
					((Timer)e.getSource()).stop();
					JOptionPane.showMessageDialog(null, "AI Q-Learning đã giải xong!");
					return;
				}
				String state = env.getState();
				String action = agent.chooseAction(state);
				env.applyActionToBoard(matrix, action); // hàm applyActionToBoard() để di chuyển trên GUI theo action
			}
		});
		aiTimer.start();
	}
	public void moveByDirection(String dir) {
		Point2D p = getEmptyPos();
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
}
