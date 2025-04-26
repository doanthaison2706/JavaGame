package Game.ai;

import javax.swing.JButton;

/**
 * PuzzleHintSolver — A* heuristic rút gọn dùng để gợi ý nước đi tiếp theo cho trò chơi n-puzzle.
 * Chỉ tính toán heuristic Manhattan để xác định nước đi tốt nhất ở trạng thái hiện tại.
 */
public class PuzzleHintSolver {
    private int[][] board;
    private int size;

    /**
     * Khởi tạo PuzzleHintSolver từ ma trận JButton của GUI.
     * @param matrix Ma trận JButton thể hiện trạng thái board hiện tại.
     * @param size   Kích thước board (ví dụ: 3 cho 3x3 puzzle).
     */
    public PuzzleHintSolver(JButton[][] matrix, int size) {
        this.size = size;
        board = new int[size][size];
        convertMatrix(matrix);
    }

    /**
     * Chuyển dữ liệu từ JButton GUI về mảng số nguyên.
     * @param matrix Ma trận JButton từ GUI.
     */
    private void convertMatrix(JButton[][] matrix) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                String text = matrix[i][j].getText();
                board[i][j] = text.equals("") ? 0 : Integer.parseInt(text);
            }
    }

    /**
     * Tìm nước đi tốt nhất tiếp theo dựa trên heuristic Manhattan.
     * @return Tên nước đi ("UP", "DOWN", "LEFT", "RIGHT") hoặc null nếu không có nước đi hợp lệ.
     */
    public String getHint() {
        int[] zeroPos = findZero(board);
        int zx = zeroPos[0], zy = zeroPos[1];
        Direction[] dirs = {
                new Direction(-1, 0, "UP"),
                new Direction(1, 0, "DOWN"),
                new Direction(0, -1, "LEFT"),
                new Direction(0, 1, "RIGHT")
        };

        String bestMove = null;
        int minHeuristic = Integer.MAX_VALUE;
        for (Direction dir : dirs) {
            int nx = zx + dir.dx;
            int ny = zy + dir.dy;
            if (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                int[][] newBoard = deepCopy(board);
                newBoard[zx][zy] = newBoard[nx][ny];
                newBoard[nx][ny] = 0;
                int h = heuristic(newBoard);
                if (h < minHeuristic) {
                    minHeuristic = h;
                    bestMove = dir.name;
                }
            }
        }
        return bestMove;
    }

    /**
     * Tính tổng khoảng cách Manhattan cho board hiện tại.
     * @param board Ma trận trạng thái cần tính heuristic.
     * @return Tổng khoảng cách Manhattan.
     */
    private int heuristic(int[][] board) {
        int manhattan = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                int val = board[i][j];
                if (val != 0) {
                    int targetX = (val - 1) / size;
                    int targetY = (val - 1) % size;
                    manhattan += Math.abs(i - targetX) + Math.abs(j - targetY);
                }
            }
        return manhattan;
    }

    /**
     * Tìm vị trí của ô trống (giá trị 0) trong board.
     * @param board Ma trận trạng thái.
     * @return Mảng gồm 2 phần tử: vị trí x và y của ô trống.
     */
    private int[] findZero(int[][] board) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j] == 0)
                    return new int[]{i, j};
        return null;
    }

    /**
     * Tạo bản sao mới cho ma trận board.
     * @param board Ma trận gốc.
     * @return Ma trận mới đã copy giá trị.
     */
    private int[][] deepCopy(int[][] board) {
        int[][] newBoard = new int[size][size];
        for (int i = 0; i < size; i++)
            newBoard[i] = board[i].clone();
        return newBoard;
    }

    /**
     * Lớp nội bộ biểu diễn hướng di chuyển.
     */
    private static class Direction {
        int dx, dy;
        String name;
        /**
         * Khởi tạo một hướng di chuyển.
         * @param dx   Thay đổi vị trí theo trục X.
         * @param dy   Thay đổi vị trí theo trục Y.
         * @param name Tên hướng di chuyển.
         */
        Direction(int dx, int dy, String name) {
            this.dx = dx;
            this.dy = dy;
            this.name = name;
        }
    }
}