package Game.ai;

import javax.swing.*;
import java.util.*;

/**
 * PuzzleEnv — Mô phỏng môi trường trò chơi n-puzzle.
 * Cho phép di chuyển, kiểm tra trạng thái và tính toán phần thưởng cho agent.
 */
public class PuzzleEnv {
    private int size;
    private int[][] board;

    /**
     * Khởi tạo môi trường puzzle với kích thước cho trước.
     * @param size Kích thước (số hàng/cột) của puzzle.
     */
    public PuzzleEnv(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    /**
     * Tải trạng thái board hiện tại từ ma trận các JButton của GUI vào môi trường.
     * @param matrix Ma trận JButton đại diện cho giao diện người dùng.
     */
    public void loadCurrentBoard(JButton[][] matrix) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                String text = matrix[i][j].getText();
                board[i][j] = text.equals("") ? 0 : Integer.parseInt(text);
            }
        }
    }

    /**
     * Lấy trạng thái hiện tại của board dưới dạng chuỗi.
     * @return Chuỗi biểu diễn trạng thái hiện tại (các số cách nhau bởi dấu phẩy).
     */
    public String getState() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int val : row) {
                sb.append(val).append(",");
            }
        }
        return sb.toString();
    }

    /**
     * Kiểm tra board đã được giải xong chưa.
     * @return true nếu puzzle đã đúng thứ tự; false nếu chưa.
     */
    public boolean isSolved() {
        int count = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    if (board[i][j] != 0) return false;
                } else {
                    if (board[i][j] != count++) return false;
                }
            }
        }
        return true;
    }

    /**
     * Thực hiện một hành động di chuyển trong GUI và cập nhật board nội bộ.
     * @param matrix Ma trận JButton hiện tại.
     * @param action Hành động di chuyển ("UP", "DOWN", "LEFT", "RIGHT").
     */
    public void applyActionToBoard(JButton[][] matrix, String action) {
        int x = -1, y = -1;
        // Tìm ô trống
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }
        switch (action) {
            case "UP":
                if (x < size - 1) swap(matrix, x, y, x + 1, y);
                break;
            case "DOWN":
                if (x > 0) swap(matrix, x, y, x - 1, y);
                break;
            case "LEFT":
                if (y < size - 1) swap(matrix, x, y, x, y + 1);
                break;
            case "RIGHT":
                if (y > 0) swap(matrix, x, y, x, y - 1);
                break;
        }
        loadCurrentBoard(matrix);
    }

    /**
     * Đánh đổi hai nút trên GUI.
     * @param matrix Ma trận JButton.
     * @param x1    Tọa độ dòng ô thứ nhất.
     * @param y1    Tọa độ cột ô thứ nhất.
     * @param x2    Tọa độ dòng ô thứ hai.
     * @param y2    Tọa độ cột ô thứ hai.
     */
    private void swap(JButton[][] matrix, int x1, int y1, int x2, int y2) {
        String temp = matrix[x1][y1].getText();
        matrix[x1][y1].setText(matrix[x2][y2].getText());
        matrix[x2][y2].setText(temp);
    }

    /**
     * Thực hiện một hành động di chuyển trên board nội bộ và tính phần thưởng.
     * @param action Hành động di chuyển ("UP", "DOWN", "LEFT", "RIGHT").
     * @return Phần thưởng nhận được: 100 nếu giải xong, -1 nếu hợp lệ, -5 nếu không hợp lệ.
     */
    public int step(String action) {
        int x = -1, y = -1;
        // Tìm ô trống
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }

        boolean valid = false;
        switch (action) {
            case "UP":
                if (x < size - 1) {
                    swap(x, y, x + 1, y);
                    valid = true;
                }
                break;
            case "DOWN":
                if (x > 0) {
                    swap(x, y, x - 1, y);
                    valid = true;
                }
                break;
            case "LEFT":
                if (y < size - 1) {
                    swap(x, y, x, y + 1);
                    valid = true;
                }
                break;
            case "RIGHT":
                if (y > 0) {
                    swap(x, y, x, y - 1);
                    valid = true;
                }
                break;
        }

        if (!valid) return -5;
        return isSolved() ? 100 : -1;
    }

    /**
     * Hoán đổi hai ô trên board nội bộ (cho training, không liên quan GUI).
     * @param x1 Tọa độ dòng ô thứ nhất.
     * @param y1 Tọa độ cột ô thứ nhất.
     * @param x2 Tọa độ dòng ô thứ hai.
     * @param y2 Tọa độ cột ô thứ hai.
     */
    private void swap(int x1, int y1, int x2, int y2) {
        int temp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = temp;
    }

    /**
     * Đặt lại board về trạng thái ngẫu nhiên hợp lệ.
     */
    public void reset() {
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < size * size; i++) nums.add(i);
        Collections.shuffle(nums);
        for (int i = 0, k = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = nums.get(k++);
    }
}