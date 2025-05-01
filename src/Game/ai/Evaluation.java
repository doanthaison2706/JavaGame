package Game.ai;

public class Evaluation {

    /**
     * Đánh giá agent sau khi huấn luyện.
     * @param agent agent Q-learning đã được huấn luyện
     * @param env môi trường (PuzzleEnv)
     * @param episodes số lượng episodes dùng để đánh giá
     */
    public static void evaluate(RLAgent agent, PuzzleEnv env, int episodes) {
        int solved = 0;
        int totalSteps = 0;

        // Đánh giá agent trên số episodes nhất định
        for (int episode = 0; episode < episodes; episode++) {
            env.reset();  // Đặt lại môi trường sau mỗi episode
            String state = env.getState();
            int steps = 0;

            while (!env.isSolved() && steps <= 500) {
                // Agent chọn hành động theo policy đã học
                String action = agent.chooseAction(state);
                // Thực hiện hành động và nhận reward
                int reward = env.step(action);
                state = env.getState();  // Cập nhật trạng thái
                steps++;  // Đếm số bước
            }

            // Kiểm tra xem puzzle đã được giải quyết chưa
            if (env.isSolved()) {
                solved++;
            }
            totalSteps += steps;  // Cộng dồn số bước
        }

        // Hiển thị kết quả đánh giá
        System.out.println("Evaluation complete.");
        System.out.println("Solved " + solved + " out of " + episodes + " episodes.");
        System.out.println("Average steps per episode: " + (totalSteps / (double) episodes));
    }

    public static void main(String[] args) throws Exception {
        // Khởi tạo môi trường puzzle 3x3 và agent Q-learning
        PuzzleEnv env = new PuzzleEnv(3);
        RLAgent agent = new RLAgent();
        // Nạp Q-Table đã huấn luyện
        String qTablePath = "/Users/doanthaison/Code Everything/N-Puzzle-main/res/data/qtable.dat";
        agent.loadQTable(qTablePath);

        // Đánh giá agent trên 1000 episodes
        evaluate(agent, env, 10000);
    }
}