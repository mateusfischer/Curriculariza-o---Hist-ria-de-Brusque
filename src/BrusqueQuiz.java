import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BrusqueQuiz extends JFrame {
    private JLabel displayLabel;
    private JPanel buttonPanel;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private boolean isShowingError = false;

    public BrusqueQuiz() {
        setTitle("Brusque Quiz - Jornada Histórica");
        setSize(800, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setupQuestions();
        
        displayLabel = new JLabel();
        displayLabel.setHorizontalAlignment(JLabel.CENTER);
        add(displayLabel, BorderLayout.CENTER);

        // Painel de botões para as opções (A, B, C)
        buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        setupButtons();
        add(buttonPanel, BorderLayout.SOUTH);

        updateScreen();
    }

    private void setupQuestions() {
        questions = new ArrayList<>();
        // Mapeamento baseado nos seus assets
        // Exemplo: Pergunta 1, Resposta Certa (digamos 2), Imagem de Erro 21.jpg
        questions.add(new Question(1, 2, 21)); 
        questions.add(new Question(2, 1, 22));
        questions.add(new Question(3, 3, 23));
        questions.add(new Question(4, 2, 19)); // Exemplo de uso da 19.jpg
        // ... Adicione as 15 questões aqui conforme sua lógica
    }

    private void setupButtons() {
        for (int i = 1; i <= 3; i++) {
            final int option = i;
            JButton btn = new JButton("Opção " + (i == 1 ? "A" : i == 2 ? "B" : "C"));
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.addActionListener(e -> handleChoice(option));
            buttonPanel.add(btn);
        }
    }

    private void handleChoice(int choice) {
        if (isShowingError) {
            // Se estiver na tela de erro, qualquer clique no "Tentar Novamente" volta à pergunta
            isShowingError = false;
            updateScreen();
            return;
        }

        Question q = questions.get(currentQuestionIndex);
        if (choice == q.correctOption) {
            currentQuestionIndex++;
            if (currentQuestionIndex >= questions.size()) {
                showVictory();
            } else {
                updateScreen();
            }
        } else {
            showError(q.errorImageId);
        }
    }

    private void updateScreen() {
        String path = "assets/" + questions.get(currentQuestionIndex).id + ".jpg";
        displayLabel.setIcon(new ImageIcon(path));
        toggleButtons(true);
    }

    private void showError(int errorId) {
        isShowingError = true;
        displayLabel.setIcon(new ImageIcon("assets/" + errorId + ".jpg"));
        // Muda o texto dos botões para indicar "Tentar Novamente"
        toggleButtons(false);
    }

    private void showVictory() {
        displayLabel.setIcon(new ImageIcon("assets/31.jpg")); // Tela final
        buttonPanel.removeAll();
        JButton btnSair = new JButton("Sair do Jogo");
        btnSair.addActionListener(e -> System.exit(0));
        buttonPanel.add(btnSair);
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private void toggleButtons(boolean isQuiz) {
        for (Component c : buttonPanel.getComponents()) {
            JButton b = (JButton) c;
            b.setText(isQuiz ? b.getText() : "Tentar Novamente");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BrusqueQuiz().setVisible(true));
    }
}
