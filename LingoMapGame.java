import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Random;

public class LingoMapGame extends Canvas implements Runnable, KeyListener {
    private Frame frame;
    private String currentWord;
    private String userAnswer = "";
    private int score = 0;
    private int lives = 3;
    private boolean running = true;
    
    private HashMap<String, String> wordMap;
    private Random random;

    public LingoMapGame() {
        frame = new Frame("LingoMap Game");
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.add(this);
        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        this.setSize(600, 400);
        this.addKeyListener(this);
        this.setFocusable(true);

        initializeWords();
        currentWord = getRandomWord();
    }

    private void initializeWords() {
        wordMap = new HashMap<>();
        wordMap.put("Hello", "Hola");
        wordMap.put("Thank you", "Gracias");
        wordMap.put("Goodbye", "Adios");
        wordMap.put("Please", "Por favor");
        wordMap.put("Yes", "Si");
        wordMap.put("No", "No");
        random = new Random();
    }

    private String getRandomWord() {
        Object[] keys = wordMap.keySet().toArray();
        return (String) keys[random.nextInt(keys.length)];
    }

    public synchronized void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (running && lives > 0) {
            render();
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        renderGameOver();
    }

    private void render() {
        Graphics g = this.getGraphics();
        g.clearRect(0, 0, 600, 400);

        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.BLACK);
        g.drawString("Translate: " + currentWord, 50, 100);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.BLUE);
        g.drawString("Your Answer: " + userAnswer, 50, 150);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.GREEN);
        g.drawString("Score: " + score, 50, 200);
        g.setColor(Color.RED);
        g.drawString("Lives: " + lives, 50, 230);

        g.dispose();
    }

    private void renderGameOver() {
        Graphics g = this.getGraphics();
        g.clearRect(0, 0, 600, 400);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.setColor(Color.RED);
        g.drawString("Game Over!", 200, 180);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.setColor(Color.BLACK);
        g.drawString("Final Score: " + score, 200, 220);
        g.dispose();
    }

    private void checkAnswer() {
        if (wordMap.get(currentWord).equalsIgnoreCase(userAnswer.trim())) {
            score++;
            userAnswer = ""; 
            currentWord = getRandomWord();  
        } else {
            lives--;
            userAnswer = ""; 
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            checkAnswer(); 
        } else {
            userAnswer += e.getKeyChar(); 
        }
    }

    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }

    public static void main(String[] args) {
        LingoMapGame game = new LingoMapGame();
        game.start();
    }
}
