import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Mylla on 02/04/2017.
 */
public class Atividade3 {

    public static void run() throws IOException {
        String PATH = "C:\\Users\\Mylla\\Pictures";
        BufferedImage img = ImageIO.read(new File(PATH,"car.png"));
        BufferedImage himg = equalize(img);

        BufferedImage img2 = ImageIO.read(new File(PATH,"crowd.png"));
        BufferedImage himg2 = equalize(img2);

        BufferedImage img3 = ImageIO.read(new File(PATH,"montanha.jpg"));
        BufferedImage himg3 = equalize(img3);

        BufferedImage img4 = ImageIO.read(new File(PATH,"university.png"));
        BufferedImage himg4 = equalize(img4);

        ImageIO.write(himg, "png", new File("histogramCar.png"));
        ImageIO.write(himg2, "png", new File("histogramCrowd.png"));
        ImageIO.write(himg3, "png", new File("histogramMountain.png"));
        ImageIO.write(himg4, "png", new File("histogramUniversity.png"));
    }

    static int histogramAcumulado (int value, int[]v)
    {
        int sum = 0;

        for(int i = 0; i < value; i++)
        {
            sum += v[i];
        }
        return sum;
    }

    public static BufferedImage equalize(BufferedImage img)
    {
        int[] h = new int[256];
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < 255; i++){
            h[i] = 0;
        }

        //Histograma
        for(int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int cor = img.getRGB(x, y);

                Color pixel = new Color(cor);

                int r = pixel.getRed();
                h[r]++;
            }
        }
        //Histograma acomulado
        int[] ha = new int[256];
        int hmin = 0;
        ha[0] = h[0];

        for(int i = 1; i < 256; i++){
            ha[i] = ha[i -1] + h[i];

        }

        //Descobre minimo valor
        for(int i = 0; i < 255; i++){
            if(h[i] != 0)
            {
                hmin = h[i];
                break;
            }

        }

        int[] hv= new int[256];
        for(int i = 0; i < 256; i++){

            hv[i] = (int) (Math.round(((ha[i] - hmin) / ((float)img.getHeight() * img.getWidth()))* (256 - 1)) );

            if(hv[i] > 255){
                hv[i] = 255;
            }
            if (hv[i] < 0){
                hv[i] = 0;
            }
        }


        for(int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int cor = img.getRGB(x, y);

                Color newPixel = new Color(cor);


                newPixel = new Color(hv[newPixel.getRed()],hv[newPixel.getRed()],hv[newPixel.getRed()]);

                out.setRGB(x,y,newPixel.getRGB());
            }
        }

        return out;
    }

    public static void main(String[] args) throws IOException {
        Atividade3 at3 = new Atividade3();
        at3.run();

        System.out.println("ok!");
    }
}
