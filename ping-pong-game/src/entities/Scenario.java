package entities;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Scenario extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	JLabel player1;
	JLabel ball;
	int movementSpeed = 20;
	int direction = 0;
	int weightY = 0;
	int ballSpeed = 1;
	
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
		
		collision();
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
	
	public boolean isCollided( JLabel obj1, JLabel obj2 ) {
		//Variáveis de apoio:
		int a = obj1.getX();
		int b = obj1.getX() + obj1.getWidth();
		int c = obj2.getX();
		int d = obj2.getX() + obj2.getWidth();
		
		if ( b >= c && d >= a ) {
			
			//Variáveis de apoio:
			int yP = obj1.getY();
			int yLP = obj1.getY() + obj1.getHeight();
			int yQ = obj2.getY();
			int yLQ = obj2.getY() + obj2.getHeight();
			
			if ( yP <= yQ && yLQ <= yLP ) {
				return true;
			}
		}
		return false;
	}
	
	public void collision() {
		
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				while( true ) {
					pause(25);
					
					if ( isCollided( player1, ball ) ) {
						
						right = !right;
						changeRandomDirection();
						
					}
				}
			}
			
		}).start();
		
	}
	
	private void pause( long time ) {
		try {
			Thread.sleep(time);
		}
		catch ( Exception e ) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	private void changeRandomDirection() {
		
		Random random = new Random();
		int n = random.nextInt( 3 ); 
		
		n -= 1;
		direction = n;
		
		//ballSpeed = random.nextInt(25) + 5;
	}
	
	public void ballMovement() {
		
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					
					pause(2);
					if ( (ball.getX() + ball.getWidth()) > getWidth() ) {
						right = false;
					}
					if ( ball.getX() < 0 ) {
						right = true;
					}
					if ( ball.getY() <= 0 ) {
						direction = -1;
					}
					if ( ball.getY() + ball.getHeight()+25 >= getHeight() ) {
						direction = 1;
					}
					
					
					if ( direction == 0 ) {
						weightY = 0;
					}
					else if ( direction == 1 ) {
						weightY = ballSpeed * -1;
					}
					else {
						weightY = ballSpeed;
					}
					
					if ( right ) {
						ball.setLocation(ball.getX() + ballSpeed, ball.getY() + weightY);
					}
					else {
						ball.setLocation(ball.getX() - ballSpeed, ball.getY() + weightY);
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
