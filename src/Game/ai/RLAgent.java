package Game.ai;

import java.io.*;
import java.util.*;

/**
 * RLAgent — Tác nhân Reinforcement Learning cho bài toán n-puzzle sử dụng thuật toán Q-Learning.
 * Q-Table được lưu dưới dạng HashMap lưu trữ giá trị Q cho mỗi cặp (state, action).
 * Hỗ trợ save/load Q-Table từ file để tiếp tục training hoặc inference.
 */
public class RLAgent implements Serializable {
    /** Bảng Q lưu trữ giá trị Q cho từng state-action. */
    Map<String, Map<String, Double>> Q = new HashMap<>();
    /** Learning rate (tốc độ học). */
    double alpha = 0.1;
    /** Discount factor (hệ số chiết khấu). */
    double gamma = 0.9;
    /** Epsilon cho epsilon-greedy strategy (tỉ lệ random). */
    double epsilon = 1.0;
    /** Danh sách các action hợp lệ. */
    String[] actions = {"UP", "DOWN", "LEFT", "RIGHT"};
    /**
     * Chọn action dựa theo epsilon-greedy strategy.
     * @param state Trạng thái hiện tại dưới dạng String.
     * @return Action đã chọn ("UP", "DOWN", "LEFT", "RIGHT").
     */
    public String chooseAction(String state) {
        Random rand = new Random();
        ensureState(state);
        // Epsilon-greedy strategy: random hoặc chọn action có Q-value cao nhất
        if (rand.nextDouble() < epsilon) {
            return actions[rand.nextInt(actions.length)];
        } else {
            return Q.get(state).entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        }
    }
    /**
     * Cập nhật giá trị Q cho cặp state-action theo công thức Q-Learning.
     * @param state     Trạng thái hiện tại.
     * @param action    Action đã thực hiện.
     * @param reward    Reward nhận được sau action.
     * @param nextState Trạng thái tiếp theo sau action.
     */
    public void update(String state, String action, int reward, String nextState) {
        ensureState(state);
        ensureState(nextState);
        double predict = Q.get(state).get(action);
        double target = reward + gamma * Collections.max(Q.get(nextState).values());
        Q.get(state).put(action, predict + alpha * (target - predict));
    }

    /**
     * Kiểm tra nếu state chưa có trong Q-Table thì thêm mới với giá trị khởi tạo 0 cho tất cả action.
     * @param state Trạng thái cần kiểm tra.
     */
    private void ensureState(String state) {
        Q.putIfAbsent(state, new HashMap<>());
        for (String a : actions)
            Q.get(state).putIfAbsent(a, 0.0);
    }

    /**
     * Lưu Q-Table xuống file.
     * @param filename Đường dẫn file lưu.
     * @throws IOException Nếu có lỗi IO xảy ra.
     */
    public void saveQTable(String filename) throws IOException {
        File file = new File(filename);
        file.getParentFile().mkdirs();  // Tạo folder nếu chưa có
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(Q);
        }
    }

    /**
     * Load Q-Table từ file, nếu không có file thì khởi tạo mới.
     * @param filename Đường dẫn file Q-Table.
     */
    public void loadQTable(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Q = (Map<String, Map<String, Double>>) ois.readObject();
            System.out.println("Q-Table loaded thành công!");
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file Q-Table, bắt đầu mới!");
            Q = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}