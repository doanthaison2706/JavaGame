package Game.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Game.sound.SoundEffect;

/**
 * MainMenu là cửa sổ khởi đầu của game Puzzle.
 * Hiển thị nút "PLAY GAME" và chạy nhạc nền khi người chơi bắt đầu.
 *
 * @author YourName
 */
public class MainMenu extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	/** Nút bắt đầu trò chơi */
	private JButton startButton = new JButton("PLAY GAME");

	/** Nút thoát (chưa dùng) */
	private JButton outButton = new JButton("EXIT");

	/** Tham chiếu đến đối tượng Puzzle game */
	private Puzzle pz;

	/** Trình phát hiệu ứng âm thanh */
	private SoundEffect sound = new SoundEffect();

	/** Đường dẫn tới file âm thanh click */
	private String click;

	/**
	 * Khởi tạo giao diện Menu chính.
	 * Gồm ảnh nền, nút PLAY GAME và cấu hình cửa sổ.
	 */
	public MainMenu() {
		this.setTitle("Puzzle Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(100, 100);
		this.setSize(530, 700);
		this.setResizable(true);
		this.setLocationRelativeTo(null);

		// Cấu hình nút Start
		startButton.setFont(new Font("Aril", Font.BOLD, 20));
		startButton.setBackground(new Color(49,169,184));
		startButton.setForeground(new Color(0, 251, 217, 255));
		startButton.setFocusPainted(false);
		startButton.setBorder(null);
		startButton.setBorderPainted(false);
		startButton.setBounds(181, 560, 150, 50);
		// Cấu hình nút Exit
		outButton = new JButton("EXIT");
		outButton.setFont(new Font("Arial", Font.BOLD, 20));
		outButton.setBackground(new Color(184, 49, 49));
		outButton.setForeground(new Color(0, 251, 217, 255));
		outButton.setFocusPainted(false);
		outButton.setBorderPainted(false);
		outButton.setBounds(181, 620, 150, 50);
		outButton.addActionListener(this);

		// Ảnh nền
		JLabel background = new JLabel(new ImageIcon("/Users/doanthaison/Code Everything/N-Puzzle-main/anh2.png"));
		background.setSize(530, 700);
		this.add(background);
		background.add(startButton);
		background.add(outButton);

		// Icon cửa sổ
		this.setIconImage(new ImageIcon("/Users/doanthaison/Code Everything/N-Puzzle-main/Pic/logo.png").getImage());

		// Gán sự kiện
		startButton.addActionListener(this);
		outButton.addActionListener(this);

		click = "/Users/doanthaison/Code Everything/N-Puzzle-main/soundgame.wav";
	}

	/**
	 * Xử lý sự kiện khi nút được nhấn.
	 * Khi nhấn PLAY, mở cửa sổ game chính và bật nhạc nền.
	 *
	 * @param e Sự kiện hành động được kích hoạt
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			pz = new Puzzle();
			this.setVisible(false);
			sound.setFile(click);
			sound.play();
			sound.loop();
		} else if (e.getSource() == outButton) {
			System.exit(0); // thoát chương trình
		}
	}
}