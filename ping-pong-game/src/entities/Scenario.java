package entities;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Scenario extends JFrame {
	private static final long serialVersionUID = 1L;
	
	JLabel player1; 
	
	public void config() {
		setSize(700, 600); // Tamanho
		setDefaultCloseOperation(EXIT_ON_CLOSE); //Ao fechar a janela, encerra a aplicação
		setResizable( false ); // tira o maximizar
		
		setLocationRelativeTo(null);//centraliza a janela
		
		setTitle("Ping-Pong");
		
		init();
		setVisible(true); //Deixa a janela visível(Por último, garantindo a visibilidade)
	}
	
	public void init() {
		player1 = new JLabel();
		player1.setText("Player");
		player1.setSize(200, 50);
		player1.setLocation(500, 400);
		add(player1);
	}
}
