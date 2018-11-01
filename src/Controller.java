import jaco.mp3.player.MP3Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


/**
 * A controller class, this class handles communication in the system.
 */


public class Controller {
    private Icon sendIcon = null;
    private JFileChooser jChooser;
    private String path;
    private GUIFrame guiframe;
    private MovingText movingText;
    private MP3Player mp3_player;
    private MovingPicture movingPicture;

    /**
     * Initiates and starts the user interface with components.
     * Starts a new MovingText innerclass that functions as the moving text (one of the threads).
     * A guiframe is used as parameter for the thread.
     */
    public Controller() {
        guiframe = new GUIFrame(this);
        guiframe.start();
        movingText = new MovingText(guiframe);
    }

    /**
     * Method to select a file and create a MPCPlayer object with the selected file as parameter.
     * Filter to only allow MP3-files because our JAR-file only supports MP3.
     * @throws IOException
     */
    public void openFile() throws IOException {
        String userDir = System.getProperty("user.home");
        JFileChooser fileChooser = new JFileChooser(userDir + "/Desktop");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3",
                "mp3");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            mp3_player = new MP3Player(selectedFile);
        }
    }

    /**
     * Methods to start and stop threads via innerclass and "MovinPicture" class.
     */
    public void startMovingText() {
        movingText.startThread();
    }

    public void stopMovingText() {
        movingText.stopThread();
    }

    public void startMoveT(MovingPicture movePicture) {
        movingPicture.startThread();
    }

    public void stopMoveT() {
        movingPicture.stopThread();
    }

    public void play() {
        mp3_player.play();
    }

    public void stop() {
        mp3_player.stop();
    }

    /**
     * Innerclass to handle the text-thread. Uses guiframe to appear in the interface.
     * Run-method uses a random value to make the text appear in random spots in the java-panel.
     */
    private class MovingText extends Thread {
        private GUIFrame guiframe;
        private boolean active = false;
        private Thread thread;

        public MovingText(GUIFrame guiframe) {
            this.guiframe = guiframe;
        }


        public void run() {
            Random rand = new Random();
            while (active) {
                int x = rand.nextInt(150) + 1;
                int y = rand.nextInt(150) + 1;
                guiframe.moveText(x, y);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        public void startThread() {
            active = true;
            thread = new Thread(this);
            thread.start();
        }
        public void stopThread() {
            active = false;
            thread = null;
        }
    }
}