package Game.view;

import Game.Controller.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;

public class Puzzle extends JFrame implements ItemListener {
	private static final long serialVersionUID = 1L;
	private final TitlePanel titlePanel;
	private final InfoPanel infoPanel;
	private final ControlPanel controlPanel;
	private final GameBoardPanel gameBoardPanel;
	/** Bộ điều khiển trò chơi */
	Control c;

	public Puzzle() {
		setTitle("Puzzle Game");
		setSize(850, 850);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);

		titlePanel = new TitlePanel();
		infoPanel = new InfoPanel();
		controlPanel = new ControlPanel();
		gameBoardPanel = new GameBoardPanel();

		// Main panel with background image
		JPanel mainPanel = new JPanel(new BorderLayout()) {
			private Image backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Pic/image.png"))).getImage(); // Đường dẫn tài nguyên ảnh

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};

		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(gameBoardPanel, BorderLayout.CENTER);

		// Transparent panels
		titlePanel.setOpaque(false);
//		infoPanel.setOpaque(false);
//		controlPanel.setOpaque(false);
		gameBoardPanel.setOpaque(false);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		bottomPanel.add(infoPanel);
		bottomPanel.add(controlPanel);

		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		setContentPane(mainPanel);
		initializeListeners();
	}

	private void initializeListeners() {
		c = new Control(this);
		PuzzleAction p = new PuzzleAction(this);

		// Gán ActionListener cho các nút
		getControlPanel().getNewGameButton().addActionListener(p);
		getControlPanel().getOutGameButton().addActionListener(p);
		getControlPanel().getAiSupportButton().addActionListener(p);
		getControlPanel().getAiSolveButton().addActionListener(p);
		getControlPanel().getStopAIButton().addActionListener(p);

		// Gán ItemListener cho ComboBox
		getInfoPanel().getLevelComboBox().addItemListener(this);
	}

	// Phương thức itemStateChanged để xử lý sự kiện ComboBox
	@Override
	public void itemStateChanged(ItemEvent e) {
		// Kiểm tra nếu ComboBox thay đổi
		if (e.getStateChange() == ItemEvent.SELECTED) {
			// Xử lý khi item mới được chọn
			c.ResetGame();
			// Có thể thêm âm thanh hoặc các logic khác ở đây
		}
	}

	public TitlePanel getTitlePanel() {
		return titlePanel;
	}

	public InfoPanel getInfoPanel() {
		return infoPanel;
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public GameBoardPanel getGameBoardPanel() {
		return gameBoardPanel;
	}
}