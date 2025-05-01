package Game.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import Game.sound.OptionsMenu;
import Game.sound.SoundEffect;

public class MainMenu extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JButton startButton = new JButton("PLAY GAME");
	private JButton outButton = new JButton("EXIT");
	private JButton optionsButton = new JButton("OPTIONS");
	private boolean isMusicPlaying = false;
	private JButton imageButton; // Thêm nút ảnh đây

	private Puzzle pz;
	private SoundEffect sound = new SoundEffect();
	 // Đường dẫn tới file âm thanh click
	private String click = "/Users/doanthaison/Code Everything/N-Puzzle-main/res/SoundEffect/soundgame.wav";;

	public MainMenu() {
		this.setTitle("Puzzle Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(530, 700);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		sound.setFile(click);
		sound.play();
		sound.loop();
		isMusicPlaying = true;
		// Tạo nút ảnh
		imageButton = new JButton(new ImageIcon("/Users/doanthaison/Code Everything/N-Puzzle-main/res/Pic/anh2.png"));
		imageButton.setBounds(50, 50, 430, 430); // vị trí ảnh
		imageButton.setBorderPainted(false);
		imageButton.setContentAreaFilled(false);
		imageButton.setFocusPainted(false);
		imageButton.setFocusable(false);
		imageButton.addActionListener(this); // Nếu muốn click vào ảnh làm gì thì add action

		// Nút Start
		startButton.setFont(new Font("Arial", Font.BOLD, 20));
		startButton.setBackground(new Color(49, 169, 184));
		startButton.setFocusPainted(false);
		startButton.setBorderPainted(false);
		startButton.setBounds(181, 500, 150, 50);
		startButton.addActionListener(this);

		// Nút Options
		optionsButton.setFont(new Font("Arial", Font.BOLD, 20));
		optionsButton.setBackground(new Color(100, 149, 237));
		optionsButton.setFocusPainted(false);
		optionsButton.setBorderPainted(false);
		optionsButton.setBounds(181, 560, 150, 50);
		optionsButton.addActionListener(this);

		// Nút Exit
		outButton.setFont(new Font("Arial", Font.BOLD, 20));
		outButton.setBackground(new Color(184, 49, 49));
		outButton.setFocusPainted(false);
		outButton.setBorderPainted(false);
		outButton.setBounds(181, 620, 150, 50);
		outButton.addActionListener(this);

		// Thêm các thành phần vào cửa sổ
		this.setLayout(null);
		this.add(imageButton);
		this.add(startButton);
		this.add(optionsButton);
		this.add(outButton);
		startButton.setOpaque(true);
		optionsButton.setOpaque(true);
		outButton.setOpaque(true);
		// Background màu trơn
		this.getContentPane().setBackground(new Color(240, 240, 240)); // trắng nhẹ
		// Icon cửa sổ
		this.setIconImage(new ImageIcon("/Users/doanthaison/Code Everything/N-Puzzle-main/res/Pic/logo.png").getImage());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			pz = new Puzzle();
			pz.setVisible(true);
			this.setVisible(false);
		} else if (e.getSource() == optionsButton) {
			new OptionsMenu(sound);
		} else if (e.getSource() == outButton) {
			System.exit(0);
		} else if (e.getSource() == imageButton) {
			// Nếu muốn click vào ảnh làm gì thì thêm code ở đây
			System.out.println("Ảnh được click!");
		}
	}
}
