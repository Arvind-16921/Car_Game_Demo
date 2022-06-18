import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main extends MouseAdapter implements ActionListener,KeyListener {
	static Main mainApp;
	JFrame jf;
	int WIDTH = 600;
	int HEIGHT = 600;
	Renderer panel;
	Rectangle car;
	int y;
	ArrayList<Rectangle> cars;
	Random rand;
	int flag = 0;
	int score;
	boolean started, gameover;
	Main() {
		jf = new JFrame("Demo Game");
		Timer timer = new Timer(20, this);
		rand = new Random();
		panel = new Renderer();
		car = new Rectangle(WIDTH / 2 - 25, HEIGHT - 150 - y, 50, 80);
		cars = new ArrayList<Rectangle>();

		jf.add(panel);
		jf.addKeyListener(this);
		jf.addMouseListener(this);
		jf.setSize(WIDTH, HEIGHT);
		jf.setVisible(true);

		timer.start();
	}

	public void addCars(boolean b) {
		int xi = rand.nextInt(100);
		int width = 50;
		int height = 80;
		if (b) {
			cars.add(new Rectangle(WIDTH / 4 + xi,  - cars.size() * 150, width, height));
			cars.add(new Rectangle(WIDTH / 4 + xi + 120, - (cars.size() - 1) * 150, width, height));
		} else {
			cars.add(new Rectangle(WIDTH / 4 + xi,  -(cars.size() - 1) * 150 -150 , width, height));
			cars.add(new Rectangle(WIDTH / 4 + xi + 120, - (cars.size() - 2) * 150 -150, width, height));
		}
	}

	public static void main(String[] args) {
		mainApp = new Main();
	}

	public void repaint(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.GREEN.darker());
		g.fillRect(0, 0, WIDTH / 4, HEIGHT);
		g.fillRect((WIDTH / 4) * 3, 0, WIDTH / 4, HEIGHT);

		g.setColor(Color.red.darker().darker());
		g.fillRect(WIDTH / 4 - 20, 0, 20, HEIGHT);
		g.fillRect((WIDTH / 4) * 3, 0, 20, HEIGHT);

		g.setColor(Color.red.brighter());
		g.fillRect(car.x, car.y, car.width, car.height);

		for (Rectangle rect : cars)
			paintCars(g, rect);
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial",1,30));
		if(!started) {
			g.drawString("Click here to start!", 170, 100);
		}
		if(gameover) {
			
			g.drawString("Game over! Your score is : "+score, 80, 200);
			started = false;
			cars.clear();
			
		}
		if(started && !gameover) {
			g.setFont(new Font("Arial",1,20));
		g.drawString("Score : "+score, 10, 300);
	}}

	public void paintCars(Graphics g, Rectangle rect) {
		g.setColor(Color.BLUE.darker());
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int speed = 5;
		if(started) {
		for (Rectangle rect : cars) {
			rect.y += speed;
		}
		for (int i = 0; i < cars.size(); i++) {
			Rectangle rect = cars.get(i);
			if (rect.y + 150 > car.y + 150) {
				cars.remove(i);
				flag++;
				if ((flag % 2) == 0)
					addCars(false);
			}
		}}
		for(Rectangle rect : cars) {
			if(rect.y > car.y)
				score++;
			if(rect.intersects(car))
				gameover = true;
		}
		panel.repaint();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		started = true;
		gameover = false;
		score = 0;
		addCars(true);
		addCars(true);
		addCars(true);

	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		started = true;
		gameover = false;
		score = 0;
		addCars(true);
		addCars(true);
		addCars(true);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
			{
			if(car.x > 150)
				car.x -= 7;
			}
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			{
			if(car.x < 400)
				car.x += 7;
			
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
