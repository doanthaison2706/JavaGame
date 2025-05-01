package Game.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Quản lý thời gian trong trò chơi N-Puzzle.
 * Bao gồm chức năng bắt đầu, dừng và đặt lại bộ đếm giờ.
 */
public class TimeController {
    private final Control control;
    private Timer time;
    private int elapsedTime = 0;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    /**
     * Khởi tạo bộ điều khiển thời gian với liên kết đến đối tượng Control chính.
     * @param control Đối tượng điều khiển chính của game.
     */
    public TimeController(Control control) {
        this.control = control;
    }

    /**
     * Bắt đầu đếm thời gian. Cập nhật thời gian mỗi giây
     * và hiển thị lên giao diện người dùng theo định dạng HH:mm:ss.
     */
    public void start() {
        time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime += 1000;
                hours = (elapsedTime / 3600000);
                minutes = (elapsedTime / 60000) % 60;
                seconds = (elapsedTime / 1000) % 60;
                String seconds_string = String.format("%02d", seconds);
                String minutes_string = String.format("%02d", minutes);
                String hours_string = String.format("%02d", hours);
                control.pz.getInfoPanel().getTimeLabel().setText(hours_string + ":" + minutes_string + ":" + seconds_string);
            }
        });
        time.start();
    }

    /**
     * Dừng bộ đếm thời gian nếu thời gian đã bắt đầu chạy.
     */
    public void stop() {
        if (elapsedTime != 0 && time != null) {
            time.stop();
        }
    }

    /**
     * Đặt lại toàn bộ đồng hồ về 00:00:00 và cập nhật lại trên giao diện.
     */
    public void reset() {
        stop();
        elapsedTime = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        control.pz.getInfoPanel().getTimeLabel().setText("00:00:00");
    }
}