package Game.Controller;

import Game.ai.PuzzleHintSolver;
import Game.ai.RLAgent;
import Game.ai.PuzzleEnv;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AIController {
    private final Control control;
    private Timer aiTimer;

    public AIController(Control control) {
        this.control = control;
    }

    public void showHint() {
        PuzzleHintSolver hintSolver = new PuzzleHintSolver(control.matrix, control.SIZE);
        String bestMove = hintSolver.getHint();

        if (bestMove == null) {
            JOptionPane.showMessageDialog(null, "Không tìm được nước đi gợi ý!");
            return;
        }
        control.moveByDirection(bestMove);
    }

    public void solveWithRL() {
        RLAgent agent = new RLAgent();
        agent.loadQTable("/Users/doanthaison/Code Everything/N-Puzzle-main/res/data");
        PuzzleEnv env = new PuzzleEnv(control.SIZE);
        env.loadCurrentBoard(control.matrix);

        aiTimer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (env.isSolved()) {
                    aiTimer.stop();
                    JOptionPane.showMessageDialog(null, "AI Q-Learning đã giải xong!");
                    return;
                }
                String state = env.getState();
                String action = agent.chooseAction(state);
                env.applyActionToBoard(control.matrix, action);
            }
        });
        aiTimer.start();
    }

    public void stopAI() {
        if (aiTimer != null && aiTimer.isRunning()) {
            aiTimer.stop();
            System.out.println("AI stopped manually.");
        }
    }
}