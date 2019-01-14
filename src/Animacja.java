// @Author Szymon Sakowicz 235249

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Animacja {
	int sunX = 1, sunY = 1, moonX = -500, moonY = -500, cloudX = 550, carX = 520, carLX = -20, appleY = 350;
	int dx = 3, dy = 7; //szybkosc animacji
	int starOpacity = 0;
	int darker = 0;
	
	//losowe polozenie gwiazd
	int[] randomY = new Random().ints(300, -1100, 400).toArray();
	int[] randomX = new Random().ints(300, -1100, 500).toArray();

	
	int skyR = 135, skyG = 206, skyB = 250; //kolor nieba
	private BufferedImage moon, cloud, car, carL, tree, apple; //obrazy

	public static void main(String[] args) {
		new Animacja();
	}

	public Animacja() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
					ex.printStackTrace();
				}

				JFrame frame = new JFrame("Animacja");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(new TimerInc());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setResizable(false);
			}
		});
	}

	public class TimerInc extends JPanel {
		int count;
		public TimerInc() {
			Timer timer = new Timer(60, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (count != 15*24) { //zatrzymanie sie po 24 sekundach
						animate();
						repaint();
						count++;
					}
				}
			});
			timer.start();

		}

		protected void animate() {
			// poruszenie obiektow
			sunX += dx;
			sunY += dx;
			moonX += dx;
			moonY += dx;
			cloudX -= dy;
			carX -= dx;
			carLX += dx;
			if ((count > 50) && (appleY != 410))
			appleY += 5;
			
			//zmiana koloru nieba
			if (skyR != 25)
				skyR--;
			if (skyG != 25)
				skyG--;
			if (skyB != 50)
				skyB--;
			//rozjasnianie gwiazd
			if (starOpacity != 100)
				starOpacity += 1;
			if ((sunX > 300) && (darker != 100)) {
				darker += 2;
			}
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(500, 500); //rozmiary JPanelu
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			//wczytywanie grafik
			try {
				moon = ImageIO.read(new File("src/images/moon.png"));
			} catch (IOException e) {
			}

			try {
				cloud = ImageIO.read(new File("src/images/cloud.png"));
			} catch (IOException e) {
			}
			try {
				car = ImageIO.read(new File("src/images/car.png"));
			} catch (IOException e) {
			}
			try {
				carL = ImageIO.read(new File("src/images/carL.png"));
			} catch (IOException e) {
			}
			try {
				tree = ImageIO.read(new File("src/images/tree.png"));
			} catch (IOException e) {
			}
			try {
				apple = ImageIO.read(new File("src/images/apple.png"));
			} catch (IOException e) {
			}
			Graphics2D g2 = (Graphics2D) g;

			setBackground(new Color(skyR, skyG, skyB));
			g2.setColor(Color.YELLOW);
			g2.fillOval(sunX, sunY, 100, 100);
			g2.setColor(new Color(255, 255, 0, starOpacity));
			
			//losowe rozmieszczanie gwiazd
			for (int k = 0; k < 150; k++) {
				g2.fillRect(randomX[k] + moonX, randomY[k] + moonY, 1, 1);
				int j = k + 150;
				g2.fillRect(randomX[j] + moonX, randomY[j] + moonY, 2, 2);
			}
			g2.drawImage(moon, moonX, moonY, null);
			g2.drawImage(cloud, cloudX, 20, null);
			g2.drawImage(cloud, cloudX - 400, 60, null);
			g2.drawImage(cloud, cloudX - 200, 100, null);
			g2.drawImage(cloud, cloudX + 200, 130, null);
			g2.drawImage(cloud, cloudX + 400, 70, null);
			
			//sciemnianie trawy
			g2.setColor(new Color(124 - darker, 252 - darker, 0));
			g2.fillRect(0, 400, 550, 120);
			g2.setColor(Color.GRAY);
			g2.fillRect(0, 425, 550, 50);
			g2.setColor(Color.white);
			
			//rozmieszczenie linii na drodze
			for (int l = 0; l < 500; l += 40) {
				g2.fillRect(l, 447, 20, 5);
			}
			g2.drawImage(tree, 100, 300, null);
			g2.drawImage(apple, 120, appleY, null);
			g2.drawImage(car, carX, 420, null);
			g2.drawImage(car, carX+200, 420, null);
			g2.drawImage(car, carX+500, 420, null);
			g2.drawImage(carL, carLX, 450, null);
			g2.drawImage(carL, carLX-200, 450, null);
			g2.drawImage(carL, carLX-500, 450, null);
		}

	}

}