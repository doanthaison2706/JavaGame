package Game.view;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Game.fileserver.wFile;

/**
 * Lớp giao diện chính cho game Puzzle.
 * Hiển thị tiêu đề, bộ đếm thời gian, số bước đi, cấp độ chơi và các nút chức năng.
 * Tích hợp điều khiển thông qua { Control} và {PuzzleAction}.
 * Đọc dữ liệu cấp độ từ file thông qua { wFile}.

 */
public class Puzzle extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;

    /** Màu nút */
	private static final Color ColorButton = new Color(40, 54, 85);
	/** Màu chữ */
	private static final Color ColorText = new Color(255, 98, 0);

	/** Font cho text thông thường */
	private static final Font FontText = new Font("Arial", Font.PLAIN, 18);
	/** Font cho tiêu đề */
	private static final Font FontTitle = new Font("MV Boli", Font.BOLD, 25);

	/** Tiêu đề game */
	private static final JLabel title = new JLabel("PUZZLE GAME SIMPLE");
	/** Nhãn hiển thị thời gian */
	public JLabel timeLabel = new JLabel("00:00:00");
	/** Nhãn hiển thị số bước đi */
	static final JLabel JTextCount = new JLabel("0");

	// Các panel bố cục
	JPanel jPanel1 = new JPanel();
	JPanel s2Panel = new JPanel();
	JPanel SPanel = new JPanel();
	JPanel mPanel = new JPanel();
	JPanel sPanel = new JPanel();
	JPanel s1Panel = new JPanel();

	// Các nút chức năng
	JButton ButtonOut = new JButton("OutGame");
	JButton ButtonNew = new JButton("NewGame");
	JButton ButtonAI = new JButton("AI hỗ trợ");
	JButton ButtonSolveRL = new JButton("AI Giải Game");

	/** Danh sách cấp độ (đọc từ file) */
	JComboBox<String> ComboBox = new JComboBox<>();

	/** Bộ điều khiển trò chơi */
	Control c;

	/**
	 * Tạo giao diện và khởi tạo các thành phần của game Puzzle.
	 */
	public Puzzle() {
		this.setTitle("Puzzle Game");
		this.setSize(700, 850);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setIconImage(new ImageIcon("/Users/doanthaison/Code Everything/N-Puzzle-main/Pic/logo.png").getImage());

		JLabel fJLabel1 = new JLabel("Moves");
		JLabel fJLabel2 = new JLabel("Level");
		JLabel fJLabel3 = new JLabel("Time:");

		// Set font
		title.setFont(FontTitle);
		timeLabel.setFont(FontText);
		JTextCount.setFont(FontText);
		fJLabel1.setFont(FontText);
		fJLabel2.setFont(FontText);
		fJLabel3.setFont(FontText);

		// Style các nút
		styleButton(ButtonNew, ColorButton, ColorText, FontText);
		styleButton(ButtonOut, ColorButton, ColorText, FontText);
		styleButton(ButtonAI, ColorButton, ColorText, FontText);
		styleButton(ButtonSolveRL, ColorButton, ColorText, FontText);

		// Layout các panel
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 30, 10);
		sPanel.setLayout(flowLayout);
		sPanel.add(fJLabel3);
		sPanel.add(timeLabel);

		s1Panel.setLayout(flowLayout);
		s1Panel.add(fJLabel2);
		s1Panel.add(ComboBox);
		s1Panel.add(fJLabel1);
		s1Panel.add(JTextCount);

		s2Panel.setLayout(flowLayout);
		s2Panel.add(ButtonNew);
		s2Panel.add(ButtonAI);
		s2Panel.add(ButtonSolveRL);
		s2Panel.add(ButtonOut);

		BoxLayout boxlayout = new BoxLayout(SPanel, BoxLayout.Y_AXIS);
		SPanel.setLayout(boxlayout);
		SPanel.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
		SPanel.add(sPanel);
		SPanel.add(s1Panel);
		SPanel.add(s2Panel);

		// Canh giữa tiêu đề
		title.setHorizontalAlignment(JTextField.CENTER);
		timeLabel.setHorizontalAlignment(JTextField.CENTER);
		title.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));

		// Đặt màu nền
        /** Màu nền */
        Color colorBac = new Color(0, 251, 217);
        sPanel.setBackground(colorBac);
		s1Panel.setBackground(colorBac);
		s2Panel.setBackground(colorBac);
		SPanel.setBackground(colorBac);
		mPanel.setBackground(colorBac);

		ComboBox.setBackground(ColorButton);
		ComboBox.setForeground(ColorText);

		// Load level từ file
		ArrayList<String> s = wFile.readFile();
		for (String string : s) {
			ComboBox.addItem(string);
		}

		jPanel1.setBorder(BorderFactory.createEtchedBorder());

		mPanel.setLayout(new BorderLayout());
		mPanel.add(title, BorderLayout.NORTH);
		mPanel.add(jPanel1, BorderLayout.CENTER);
		mPanel.add(SPanel, BorderLayout.SOUTH);
		mPanel.setBorder(null);
		this.setContentPane(mPanel);

		// Khởi tạo điều khiển và hành động
		c = new Control(this);
		PuzzleAction p = new PuzzleAction(this);

		ButtonNew.addActionListener(p);
		ButtonOut.addActionListener(p);
		ButtonAI.addActionListener(p);
		ButtonSolveRL.addActionListener(p);
		ComboBox.addItemListener(this);
	}

	/**
	 * Thiết lập giao diện cho các nút.
	 *
	 * @param btn nút cần thiết lập
	 * @param bg màu nền
	 * @param fg màu chữ
	 * @param font font chữ
	 */
	private void styleButton(JButton btn, Color bg, Color fg, Font font) {
		btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
		btn.setBackground(bg);
		btn.setForeground(fg);
		btn.setFont(font);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setOpaque(true);
		btn.setContentAreaFilled(true);
		btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	}

	/**
	 * Xử lý sự kiện khi chọn cấp độ mới từ combo box.
	 *
	 * @param e sự kiện thay đổi item
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		c.ResetGame();
	}
}