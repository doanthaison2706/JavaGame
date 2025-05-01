package Game.sound;

import java.io.File;
import javax.sound.sampled.*;

/**
 * Lớp quản lý hiệu ứng âm thanh cho game.
 * Hỗ trợ phát, lặp, dừng và đóng file âm thanh.
 *
 * Yêu cầu file định dạng .wav.
 */
public class SoundEffect {

	private Clip clip;

	/**
	 * Tải file âm thanh từ đường dẫn.
	 *
	 * @param soundFile Đường dẫn tới file âm thanh (.wav)
	 * @return true nếu tải thành công, false nếu có lỗi
	 */
	public boolean setFile(String soundFile) {
		try {
			File file = new File(soundFile);
			if (!file.exists()) {
				System.err.println("Không tìm thấy file âm thanh: " + soundFile);
				return false;
			}

			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			return true;
		} catch (Exception e) {
			System.err.println("Lỗi khi tải file âm thanh: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Phát âm thanh một lần từ đầu.
	 */
	public void play() {
		if (clip != null) {
			if (clip.isRunning()) clip.stop(); // Dừng nếu đang chạy
			clip.setFramePosition(0);
			clip.start();
		} else {
			System.err.println("Clip chưa được khởi tạo. Hãy gọi setFile()");
		}
	}

	/**
	 * Phát âm thanh lặp lại liên tục.
	 */
	public void loop() {
		if (clip != null) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} else {
			System.err.println("Clip chưa được khởi tạo. Hãy gọi setFile() trước.");
		}
	}

	/**
	 * Dừng âm thanh nếu đang phát.
	 */
	public void stopSound() {
		if (clip != null && clip.isRunning()) {
			clip.stop();
			clip.flush();
		}
	}

	/**
	 * Đóng clip và giải phóng tài nguyên.
	 */
	public void close() {
		if (clip != null) {
			clip.close();
		}
	}

	/**
	 * Trả về đối tượng Clip để xử lý nâng cao (nếu cần).
	 *
	 * @return Clip hiện tại hoặc null nếu chưa khởi tạo.
	 */
	public Clip getClip() {
		return clip;
	}
}