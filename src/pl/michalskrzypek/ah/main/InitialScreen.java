package pl.michalskrzypek.ah.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Class responsible for displaying main game screen
 */
public class InitialScreen extends JFrame {

	JButton startGameButton, exitGameButton, instructionButton, levelButton;
	Image background;
	JDialog instructionDialog;
	private String informationString;
	private static String level = "Easy";
	Clip clip = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new InitialScreen();
	}

	public InitialScreen() {

		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			background = ImageIO.read(new File("./images/menu.jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setSize(315, 428);
		setResizable(false);
		setLocation(300, 100);
		setLayout(null);
		setContentPane(new Background());
		startGameButton = new JButton("Start game");
		exitGameButton = new JButton("Exit game");
		startGameButton.setBounds(95, 120, 125, 30);
		startGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Utilities.playSound("./sounds/click.wav");
				String name = JOptionPane.showInputDialog(InitialScreen.this, "Please enter your name", null,
						JOptionPane.QUESTION_MESSAGE);
				
				if(name != null) {
				name = name.trim();
				}
				
				if (name != null && name.length()>=3){
					InitialScreen.this.dispose();
					Utilities.stopBackgroundMusic(clip);
					new AsteroidGameBoard(name);
				}
				
				if(name!= null && name.length()<3) {
					JOptionPane.showMessageDialog(InitialScreen.this, "Name is too short! (min 3 letters)", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		informationString = "GAME CONTROLS:\nw  -  move forward\ns  -  move backward\nd  -  rotate ship clockwise\na  -  rotate ship counter-clockwise\nENTER  -  shoot a bullet\np - pause the game\n\nINSTRUCTION:\nTry to destroy every asteroid!\nPress ENTER to strike an asteroid.\nCollect green power-ups to freeze asteroids for 5 seconds!\nYou can collide with an asteroid up to 5 times, \nso be careful!\n\nAsteroid Hunter� by Michal Skrzypek\nmskrzypek97@gmail.com";
		instructionButton = new JButton("Instruction");
		instructionButton.setBounds(95, 180, 125, 30);
		instructionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Utilities.playSound("./sounds/click.wav");
				JOptionPane.showMessageDialog(InitialScreen.this, new String(informationString), "Instruction",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		this.add(instructionButton);

		levelButton = new JButton("Level: Easy");
		String[] options = { "Easy", "Medium", "Hard" };
		levelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Utilities.playSound("./sounds/click.wav");
				
				int option = JOptionPane.showOptionDialog(InitialScreen.this, new String("Select level:"), "",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (option == 0) {
					level = "Easy";
				}
				if (option == 1) {
					level = "Medium";
				}
				if (option == 2) {
					level = "Hard";
				}
				levelButton.setText("Level: " + level);
			}
		});
		levelButton.setBounds(95, 240, 125, 30);
		this.add(levelButton);

		exitGameButton.setBounds(95, 300, 125, 30);
		exitGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Utilities.playSound("./sounds/click.wav");
				System.exit(0);
			}
		});

		this.add(startGameButton);
		this.add(exitGameButton);

		Utilities.playBackgroundMusic(clip, "./sounds/intro.wav");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static String getLevel() {
		return level;
	}
	
	public static void setLevel(String lvl) {
		level = lvl;
	}

	class Background extends JComponent {

		public Background() {
			setVisible(true);
			setSize(315, 435);
		}

		public void paintComponent(Graphics g) {
			g.drawImage(background, 0, 0, 315, 428, null);
		}

	}

}
