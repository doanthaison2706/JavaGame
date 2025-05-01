package Game.ai;

/**
 * Lớp Trainer thực hiện quá trình huấn luyện Q-learning agent
 * để giải bài toán n-puzzle.
 * Trainer chịu trách nhiệm:
 * Khởi tạo môi trường và agent.
 * Tiến hành vòng lặp huấn luyện.
 * Lưu và nạp Q-Table.
 */
public class Trainer {
    /**
     * Hàm main thực hiện huấn luyện agent với Q-learning.
     * @param args tham số dòng lệnh (không sử dụng)
     * @throws Exception nếu có lỗi IO khi lưu/nạp Q-Table
     */
    public static void main(String[] args) throws Exception {
        // Khởi tạo môi trường puzzle 3x3 và agent Q-learning
        PuzzleEnv env = new PuzzleEnv(3);
        RLAgent agent = new RLAgent();
        // Đường dẫn file lưu Q-Table
        String qTablePath = "/Users/doanthaison/Code Everything/N-Puzzle-main/res/data/qtable.dat";
        // Nếu có Q-Table từ trước thì load, không thì bắt đầu mới
        try {
            agent.loadQTable(qTablePath);
            System.out.println("Loaded existing Q-Table.");
        } catch (Exception e) {
            System.out.println("No existing Q-Table, starting fresh.");
        }
        // Thiết lập các tham số huấn luyện
        double epsilon = 1.0;
        double epsilonDecay = 0.995;
        double epsilonMin = 0.05;
        int totalReward = 0;
        // Vòng lặp huấn luyện cho 20,000 episodes
        for (int episode = 0; episode < 20000; episode++) {
            env.reset();
            String state = env.getState();
            int steps = 0;
            // Cập nhật epsilon cho agent
            agent.epsilon = epsilon;
            while (true) {
                // Agent chọn action theo epsilon-greedy
                String action = agent.chooseAction(state);
                // Thực hiện action, nhận reward và next state
                int reward = env.step(action);
                String nextState = env.getState();
                // Cập nhật Q-Table
                agent.update(state, action, reward, nextState);
                // Chuyển sang trạng thái tiếp theo
                state = nextState;
                steps++;
                // Nếu giải được hoặc quá số bước cho phép thì dừng
                if (reward == 100 || steps > 300) {
                    break;
                }
            }
            // Giảm epsilon theo từng episode
            if (epsilon > epsilonMin) epsilon *= epsilonDecay;
            // Đếm số lần giải được
            if (env.isSolved()) totalReward++;
            // Log kết quả mỗi 500 episodes
            if (episode % 500 == 0 && episode != 0) {
                System.out.println("Episode " + episode + " | Solved: " + totalReward + " | Epsilon: " + epsilon);
                totalReward = 0;
            }
        }
        // Lưu lại Q-Table sau huấn luyện
        agent.saveQTable(qTablePath);
        System.out.println("Q-Table saved!");
        System.out.println("Training session done!");
    }
}