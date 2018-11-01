import jaco.mp3.player.MP3Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.*;

/**
 * The GUI
 */
public class GUIFrame implements ActionListener
{
	/**
	 * These are the components you need to handle.
	 * You have to add listeners and/or code
	 */
	private JLabel movingText;
	private Controller controller;
	private JFrame frame;		// The Main window
	private JButton btnOpen;	// Open sound file button
	private JButton btnPlay;	// Play selected file button
	private JButton btnStop;	// Stop music button
	private JButton btnDisplay;	// Start thread moving display
	private JButton btnDStop;	// Stop moving display thread
	private JButton btnTriangle;// Start moving graphics thread
	private JButton btnTStop;	// Stop moving graphics thread
	private JLabel lblPlaying;	// Hidden, shown after start of music
	private JLabel lblPlayURL;	// The sound file path
	private JPanel pnlMove;		// The panel to move display in
	private JPanel pnlRotate;	// The panel to move graphics in
	private boolean playing = false;
	private JLabel movePicture;
	private MovingPicture movingPicture;

	/**
	 * Constructor
	 */
	public GUIFrame(Controller controller){
        this.controller = controller;

    }




    /**
	 * Starts the application
	 */
	public void start()
	{
		frame = new JFrame();
		frame.setBounds(0, 0, 494, 437);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setTitle("Multiple Thread Demonstrator");
		InitializeGUI();					// Fill in components
		frame.setVisible(true);
		frame.setResizable(false);			// Prevent user from change size
		frame.setLocationRelativeTo(null);	// Start middle screen
        setController(controller);
		movingText = new JLabel("This is a MovingText class");
		pnlMove.add(movingText);
		lblPlaying.setText("");
		movingPicture = new MovingPicture(this);

	}

	/**
	 * Sets up the GUI with components
	 */
	private void InitializeGUI()
	{
		// The play panel
		JPanel pnlSound = new JPanel();
		Border b1 = BorderFactory.createTitledBorder("Music Player");
		pnlSound.setBorder(b1);
		pnlSound.setBounds(12, 12, 450, 100);
		pnlSound.setLayout(null);
		
		// Add the buttons and labels to this panel
		btnOpen = new JButton("Open");
		btnOpen.setBounds(6, 71, 75, 23);
		pnlSound.add(btnOpen);
		
		btnPlay = new JButton("Play");
		btnPlay.setBounds(88, 71, 75, 23);
		pnlSound.add(btnPlay);
		
		btnStop = new JButton("Stop");
		btnStop.setBounds(169, 71, 75, 23);
		pnlSound.add(btnStop);
		
		lblPlaying = new JLabel("Now Playing...",JLabel.CENTER);
		lblPlaying.setFont(new Font("Serif", Font.BOLD, 14));
		lblPlaying.setBounds(128, 16, 240, 30);
		pnlSound.add(lblPlaying);
		
		lblPlayURL = new JLabel("");
		lblPlayURL.setBounds(10, 44, 115, 13);
		pnlSound.add(lblPlayURL);
		// Then add this to main window
		frame.add(pnlSound);
		
		// The moving display outer panel
		JPanel pnlDisplay = new JPanel();
		Border b2 = BorderFactory.createTitledBorder("Display Thread");
		pnlDisplay.setBorder(b2);
		pnlDisplay.setBounds(12, 118, 222, 269);
		pnlDisplay.setLayout(null);
		
		// Add buttons and drawing panel to this panel
		btnDisplay = new JButton("Start Display");
		btnDisplay.setBounds(10, 226, 121, 23);
		pnlDisplay.add(btnDisplay);
		
		btnDStop = new JButton("Stop");
		btnDStop.setBounds(135, 226, 75, 23);
		pnlDisplay.add(btnDStop);
		
		pnlMove = new JPanel();
		pnlMove.setBounds(10,  19,  200,  200);
		Border b21 = BorderFactory.createLineBorder(Color.black);
		pnlMove.setBorder(b21);
		pnlDisplay.add(pnlMove);
		// Then add this to main window
		frame.add(pnlDisplay);
		
		// The moving graphics outer panel
		JPanel pnlTriangle = new JPanel();
		Border b3 = BorderFactory.createTitledBorder("Triangle Thread");
		pnlTriangle.setBorder(b3);
		pnlTriangle.setBounds(240, 118, 222, 269);
		pnlTriangle.setLayout(null);
		
		// Add buttons and drawing panel to this panel
		btnTriangle = new JButton("Start Rotate");
		btnTriangle.setBounds(10, 226, 121, 23);
		pnlTriangle.add(btnTriangle);
		
		btnTStop = new JButton("Stop");
		btnTStop.setBounds(135, 226, 75, 23);
		pnlTriangle.add(btnTStop);
		
		pnlRotate = new JPanel();
		pnlRotate.setBounds(10,  19,  200,  200);
		Border b31 = BorderFactory.createLineBorder(Color.black);
		pnlRotate.setBorder(b31);
		pnlTriangle.add(pnlRotate);
		// Add this to main window
		frame.add(pnlTriangle);
		movePicture = new JLabel(new ImageIcon(""));
		pnlRotate.add(movePicture);

        btnOpen.addActionListener(this);
		btnDisplay.addActionListener(this);
		btnDStop.addActionListener(this);
		btnPlay.addActionListener(this);
		btnTriangle.addActionListener(this);
		btnStop.addActionListener(this);
		btnTStop.addActionListener(this);
	}

    public void setController(Controller controller) {
        this.controller = controller;
    }

	/**
	 * Method to handle the button-listeners. Enabled and disables button on press and calls controller-methods.
	 * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnOpen) {
			{
				try {
					if (playing) {
						controller.stop();
						btnPlay.setEnabled(true);
					}

					controller.openFile();
					lblPlaying.setText("");
					if(playing == false) {
						btnPlay.setEnabled(true);
						btnStop.setEnabled(false);
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
        }

		if (e.getSource() == btnPlay) {

				controller.play();
				lblPlaying.setText("Successfuly playing MP3-File.");
				btnPlay.setEnabled(false);
				btnStop.setEnabled(true);
				playing = true;


		}

		if (e.getSource() == btnStop) {
			try {
				controller.stop();
				lblPlaying.setText("Stopped MP3-player");
				btnPlay.setEnabled(true);
				btnStop.setEnabled(false);
			}
			catch(Exception en) {

			}
		}
		if (e.getSource() == btnDisplay){
			btnDisplay.setEnabled(false);
			btnDStop.setEnabled(true);
			startMove();
		}

		if (e.getSource() == btnDStop){
			btnDisplay.setEnabled(true);
			btnDStop.setEnabled(false);
			stopMove();
		}

		if (e.getSource() == btnTriangle ) {
			btnTriangle.setEnabled(false);
			btnTStop.setEnabled(true);
			startMoveT();
		}

		if (e.getSource() == btnTStop) {
			btnTStop.setEnabled(false);
			btnTriangle.setEnabled(true);
			stopMoveT();
		}
    }

	/**
	 * Methods to control thread by starting/stopping.
	 */
	public void startMove() {
		controller.startMovingText();
	}

	public void stopMove() { controller.stopMovingText();}

	public void startMoveT() {
		movingPicture.startThread();
	}

	public void stopMoveT() {
		movingPicture.stopThread();
	}

	public void moveText(int x, int y) {
		movingText.setLocation(x, y);
	}

	public void movePicture(int x, int y) {movePicture.setLocation(x, y);}

	/**
	 * Loads the image into the panel.
	 * @param image
     */
	public void setImage(BufferedImage image) { movePicture.setIcon(new ImageIcon(image.getScaledInstance(45, 45, 0)));}
}

