import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Class to represent the thread that moves a picture.
 */
public class MovingPicture implements Runnable {
    private GUIFrame guiframe;
    private boolean picture = false;
    private boolean threadActive = false;
    private Thread thread;
    private Controller controller;

    public MovingPicture(GUIFrame guiframe) {
        this.guiframe = guiframe;
    }

    /**
     * Method that serves as a thread, lets the user choose a file (filtered to jpg & png) to show jumping around in the java-panel.
     * Also uses a random-object to make the image jump around in random spots in the panel.
     */
    public void run() {
        if (!threadActive) {
            String userDir = System.getProperty("user.home");
            JFileChooser fileChooser = new JFileChooser(userDir + "/Desktop");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & PNG Images", "jpg", "png");
            fileChooser.setFileFilter(filter);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile().getAbsoluteFile();
                try {
                    BufferedImage image = ImageIO.read(selectedFile);
                    guiframe.setImage(image);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            threadActive = true;
        }
        Random rand = new Random();
        while (picture) {
            int x = rand.nextInt(100) + 1;
            int y = rand.nextInt(100) + 1;
            guiframe.movePicture(x, y);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void startThread() {
        picture = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stopThread() {
        picture = false;
        thread = null;
    }
}