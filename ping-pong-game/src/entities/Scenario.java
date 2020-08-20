package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class Scenario extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;

	JLabel player1;
	JLabel player2;
	JLabel ball;
	JLabel scores;
	JLabel background;
	int movementSpeed = 20;
	int direction = 0;
	int weightY = 0;
	int ballSpeed = 1;
	int pointsPlayer1 = 0;
	int pointsPlayer2 = 0;

	public void config() {
		setSize(1024, 700); // Tamanho
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Ao fechar a janela, encerra a aplicação
		setResizable(false); // tira o maximizar
		

		setLocationRelativeTo(null);// centraliza a janela

		setTitle("Ping-Pong");
		setLayout(null); // Sem gerenciador de Layout

		init();

		setVisible(true); // Deixa a janela visível(Por último, garantindo a visibilidade)
	}

	public void init() {
		
		
		
		player1 = new JLabel();
		player1.setSize(40, 200);
		player1.setLocation(50, 230);
		player1.setBackground(Color.BLACK);
		player1.setOpaque(true);
		player1.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.green));
		add(player1);

		player2 = new JLabel();
		player2.setSize(40, 200);
		player2.setLocation(getWidth() - 103, 230);
		player2.setBackground(Color.BLUE);
		player2.setOpaque(true);
		player2.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.yellow));
		add(player2);

		ball = new JLabel();
		ball.setSize(25, 25);
		ball.setLocation(200, 400);
		Image img = new ImageIcon("ballwhite.png").getImage().getScaledInstance(ball.getWidth(), ball.getHeight(),
				Image.SCALE_FAST);
		ball.setIcon(new ImageIcon(img));
		add(ball);

		scores = new JLabel();
		scores.setBounds(0, 20, getWidth(), 50);
		scores.setText("0 X 0");
		scores.setFont(new Font("Century Gothic", 1, 50));
		scores.setForeground(Color.white);
		scores.setHorizontalAlignment(SwingConstants.CENTER);
		add(scores);

		background = new JLabel();
		background.setSize(getWidth(), getHeight());
		background.setLocation(0, 0);
		Image bg = new ImageIcon("bg.jpg").getImage().getScaledInstance(getWidth(), getHeight(),
				Image.SCALE_FAST);
		background.setIcon(new ImageIcon(bg));
		add(background);
		
		collision();
		ballMovement();
		addKeyListener(this);

		initBall();
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		// Key Codes:
		// w = 87
		// s = 83
		// seta pra cima = 38
		// seta pra baixo = 40

		if (evt.getKeyCode() == 87) {

			if (player1.getY() < 0) {
				player1.setLocation(player1.getX(), 0);
			} else {
				player1.setLocation(player1.getX(), player1.getY() - movementSpeed);
			}

		} else if (evt.getKeyCode() == 83) {
			// getHeight = altura do formulario
			if (!((player1.getY() + player1.getHeight()) > getHeight())) {
				player1.setLocation(player1.getX(), player1.getY() + movementSpeed);
			}
		}

		if (evt.getKeyCode() == 38) {

			if (player2.getY() < 0) {
				player2.setLocation(player2.getX(), 0);
			} else {
				player2.setLocation(player2.getX(), player2.getY() - movementSpeed);
			}

		} else if (evt.getKeyCode() == 40) {
			// getHeight = altura do formulario
			if (!((player2.getY() + player2.getHeight()) > getHeight())) {
				player2.setLocation(player2.getX(), player2.getY() + movementSpeed);
			}
		}
	}

	boolean right = true;

	public boolean isCollided(JLabel obj1, JLabel obj2) {
		// Variáveis de apoio:
		int a = obj1.getX();
		int b = obj1.getX() + obj1.getWidth();
		int c = obj2.getX();
		int d = obj2.getX() + obj2.getWidth();

		if (b >= c && d >= a) {

			// Variáveis de apoio:
			int yP = obj1.getY();
			int yLP = obj1.getY() + obj1.getHeight();
			int yQ = obj2.getY();
			int yLQ = obj2.getY() + obj2.getHeight();

			if (yP <= yQ && yLQ <= yLP) {
				return true;
			}
		}
		return false;
	}

	public void collision() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					pause(25);

					if (isCollided(player1, ball) || isCollided(player2, ball)) {

						right = !right;
						changeRandomDirection();

					}
				}
			}

		}).start();

	}

	private void pause(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void changeRandomDirection() {

		Random random = new Random();
		int n = random.nextInt(3);

		n -= 1;
		direction = n;

		// ballSpeed = random.nextInt(25) + 5;
	}

	private void initBall() {
		ball.setLocation(getWidth() / 2, getHeight() / 2);
		direction = 0;
		Random random = new Random();

		if (random.nextInt() == 0) {
			right = true;
		} else {
			right = false;
		}
	}

	public void ballMovement() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					pause(2);
					if ((ball.getX() + ball.getWidth()) > getWidth()) {
						pointsPlayer1++;
						initBall();
						scores.setText(pointsPlayer1 + " X " + pointsPlayer2);
					}
					if (ball.getX() < 0) {
						pointsPlayer2++;
						initBall();
						scores.setText(pointsPlayer1 + " X " + pointsPlayer2);
					}
					if (ball.getY() <= 0) {
						direction = -1;
					}
					if (ball.getY() + ball.getHeight() + 25 >= getHeight()) {
						direction = 1;
					}
					if (pointsPlayer1 > 2) {
						JOptionPane.showMessageDialog(null, "Player 1 wins");
						System.exit(0);
					} else if (pointsPlayer2 > 2) {
						JOptionPane.showMessageDialog(null, "Player 2 wins");
						System.exit(0);
					}

					if (direction == 0) {
						weightY = 0;
					} else if (direction == 1) {
						weightY = ballSpeed * -1;
					} else {
						weightY = ballSpeed;
					}

					if (right) {
						ball.setLocation(ball.getX() + ballSpeed, ball.getY() + weightY);
					} else {
						ball.setLocation(ball.getX() - ballSpeed, ball.getY() + weightY);
					}

				}

			}
		}).start();

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
