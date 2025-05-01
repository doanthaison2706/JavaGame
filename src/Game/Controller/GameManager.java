package Game.Controller;

/**
 * Quản lý các trạng thái chính của trò chơi N-Puzzle như bắt đầu, đặt lại và tạo ván mới.
 */
public class GameManager {
    private final Control control;

    /**
     * Khởi tạo đối tượng GameManager với liên kết tới lớp điều khiển trung tâm.
     *
     * @param control Đối tượng điều khiển chính quản lý các thành phần trò chơi.
     */
    public GameManager(Control control) {
        this.control = control;
    }

    /**
     * Bắt đầu trò chơi nếu chưa được khởi động.
     * Thiết lập lại các thông số ban đầu và khởi động đồng hồ đếm thời gian.
     */
    public void startGame() {
        if (!control.started) {
            control.started = true;
            control.pz.getControlPanel().getNewGameButton().setText("Reset");
            control.timeController.start();
        } else {
            control.started = false;
        }
        control.isStartGame1 = 1;
        control.isStartGame = true;
        control.move1 = 0;
        control.pz.getInfoPanel().getMoveCountLabel().setText("0");
    }

    /**
     * Đặt lại trò chơi về trạng thái ban đầu.
     * Làm mới bảng, đặt lại thời gian và số bước đi.
     */
    public void resetGame() {
        control.puzzleBoardManager.addBoard();
        control.timeController.reset();
        control.move1 = 0;
        control.pz.getInfoPanel().getMoveCountLabel().setText("0");
        control.pz.getControlPanel().getNewGameButton().setText("NewGame");
        control.isStartGame1 = 0;
        control.started = false;
    }

    /**
     * Tạo một ván chơi mới bằng cách sinh bảng ngẫu nhiên và thiết lập lại các trạng thái liên quan.
     */
    public void newGame() {
        control.puzzleBoardManager.addBoard();
        control.puzzleBoardManager.mixButton();
        control.pz.getControlPanel().getNewGameButton().setText("Reset");
        control.isStartGame1 = 0;
    }
}