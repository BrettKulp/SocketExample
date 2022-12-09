import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DisplayFrame extends JFrame
{
    private int backRed, backGreen, backBlue;
    JPanel colorPanel;
    Color backColor;
    public DisplayFrame() {

        colorPanel = new JPanel();
        add(colorPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setVisible(true);
        backRed = 100;
        backGreen = 100;
        backBlue = 100;
        backColor = new Color(backRed,backGreen,backBlue);
        colorPanel.setBackground(backColor);

        //create client socket
        try
        {
            Socket s = new Socket("localhost", 5006);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(),true);
            String line;

            while((line = in.readLine()) != null)
            {
                //System.out.println(line);
                String[] lineArray = line.split(" ",2);
                if(String.valueOf(lineArray[0]).equals("w"))
                {
                    setSize(Integer.parseInt(lineArray[1]) * 10,getHeight());
                }
                if(String.valueOf(lineArray[0]).equals("h"))
                {
                    setSize(getWidth(),Integer.parseInt(lineArray[1]) * 10);
                }
                if(String.valueOf(lineArray[0]).equals("r"))
                {
                    backRed = Integer.parseInt(lineArray[1]);
                    backColor = new Color(backRed,backGreen,backBlue);
                    colorPanel.setBackground(backColor);
                }
                if(String.valueOf(lineArray[0]).equals("g"))
                {
                    backGreen = Integer.parseInt(lineArray[1]);
                    backColor = new Color(backRed,backGreen,backBlue);
                    colorPanel.setBackground(backColor);
                }
                if(String.valueOf(lineArray[0]).equals("b"))
                {
                    backBlue = Integer.parseInt(lineArray[1]);
                    backColor = new Color(backRed,backGreen,backBlue);
                    colorPanel.setBackground(backColor);
                }
            }
            out.close();
            s.close();
        }catch(IOException ioe)
        {
            System.out.println(ioe);
        }
    }

    public static void main(String[] args) {
        new DisplayFrame();
    }
}