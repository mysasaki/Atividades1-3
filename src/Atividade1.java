import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Mylla on 08/03/2017.
 */
public class Atividade1
{
    public static void run() throws IOException
    {
        String PATH = "C:\\Users\\Mylla\\Pictures";
        BufferedImage img = ImageIO.read(new File(PATH, "kitty.jpg"));
        BufferedImage egaImg = rbgToEga(img);

        ImageIO.write(egaImg, "png", new File("kittyEga.png"));
        System.out.println("ok!");
    }

    public static BufferedImage rbgToEga(BufferedImage img)
    {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                Color cor = new Color(img.getRGB(x, y));

                int r = change(cor.getRed());
                int g = change(cor.getGreen());
                int b = change(cor.getBlue());

                Color outColor = new Color(r, g, b);
                out.setRGB(x, y, outColor.getRGB());
            }
        }

        return out;
    }
    /*
    public static BufferedImage dithering(BufferedImage img)
    {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                Color cor = new Color(img.getRGB(x, y));
                if(cor < 0.5f)
            }
        }
        return out;
    }*/

    public static int change(int n)
    {
        if(n <= 51)
            return 0;
        else if(n > 51 && n <= 102)
            return 63;
        else if(n > 102 && n <= 153)
            return 127;
        return 255;
    }

    public static void main(String[] arg) throws IOException {
        new Atividade1().run();
    }
}