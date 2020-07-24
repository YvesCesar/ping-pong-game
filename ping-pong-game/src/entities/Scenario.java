package entities;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Scenario extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	JLabel player1;
	JLabel ball;
	int movementSpeed = 15;
	
	public void config() {
		setSize(1024, 700); // Tamanho
		setDefaultCloseOperation(EXIT_ON_CLOSE); //Ao fechar a janela, encerra a aplicação
		setResizable( false ); // tira o maximizar
		
		setLocationRelativeTo(null);//centraliza a janela
		
		setTitle("Ping-Pong");
		setLayout(null); //Sem gerenciador de Layout
		
		init();
		setVisible(true); //Deixa a janela visível(Por último, garantindo a visibilidade)
	}
	
	public void init() {
		player1 = new JLabel();
		player1.setSize(40, 200);
		player1.setLocation(50, 230);
		player1.setBackground(Color.BLACK);
		player1.setOpaque(true);
		add(player1);
		
		ball = new JLabel();
		ball.setSize(25, 25);
		ball.setOpaque(true);
		ball.setBackground(Color.RED);
		ball.setLocation(200, 400);
		add(ball);
		
		ballMovement();
		addKeyListener( this );
	}
	
	

	@Override
	public void keyPressed(KeyEvent evt) {
		//Key Codes:
		//w = 87
		//s = 83
		//seta pra cima = 38
		//seta pra baixo = 40
		
		if (evt.getKeyCode() == 38 || evt.getKeyCode() == 87) {
			
			if(player1.getY() < 0) {
				player1.setLocation( player1.getX(), 0 );
			}
			else {
				player1.setLocation( player1.getX(), player1.getY() - movementSpeed );
			}
			
		}
		else if ( evt.getKeyCode() == 83 || evt.getKeyCode() == 40 ) {
			//getHeight = altura do formulario
			if (!((player1.getY() + player1.getHeight()) > getHeight())) {
				player1.setLocation( player1.getX(), player1.getY() + movementSpeed );
			}
		}
	}
	
	boolean right = true;
	public void ballMovement() {
		int ballSpeed = 5;
		
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(25);
					}
					catch ( Exception e ) {
						System.out.println("Error when moving the ball.");
					}
					if ( (ball.getX() + ball.getWidth()) > getWidth() ) {
						right = false;
					}
					if ( ball.getX() < 0 ) {
						right = true;
					}
					if ( right ) {
						ball.setLocation(ball.getX() + ballSpeed, ball.getY());
					}
					else {
						ball.setLocation(ball.getX() - ballSpeed, ball.getY());
					}
					
				}
				
			}
		}).start();
		
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
