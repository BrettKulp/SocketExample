import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicMenuUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ControlFrame extends JFrame
{
    private JSlider widthSlider, heightSlider, redSlider, greenSlider, blueSlider;
    private BufferedReader in;
    private PrintWriter out;

    public ControlFrame()
     {
         ChangeHandler ch = new ChangeHandler();

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(10,1));
        add(sliderPanel);
        JLabel width = new JLabel("Width");
        sliderPanel.add(width);
        widthSlider = new JSlider();
        width.setHorizontalAlignment(0);
        sliderPanel.add(widthSlider);
        widthSlider.addChangeListener(ch);
        JLabel height = new JLabel("Height");
        height.setHorizontalAlignment(0);
        sliderPanel.add(height);
        heightSlider = new JSlider();
        heightSlider.addChangeListener(ch);
        sliderPanel.add(heightSlider);
        JLabel red = new JLabel("Red");
        red.setHorizontalAlignment(0);
        sliderPanel.add(red);
        redSlider = new JSlider();
        redSlider.addChangeListener(ch);
        sliderPanel.add(redSlider);
        JLabel green = new JLabel("Green");
        green.setHorizontalAlignment(0);
        sliderPanel.add(green);
        greenSlider = new JSlider();
        greenSlider.addChangeListener(ch);
        sliderPanel.add(greenSlider);
        JLabel blue = new JLabel("Blue");
        sliderPanel.add(blue);
        blue.setHorizontalAlignment(0);
        blueSlider = new JSlider();
        blueSlider.addChangeListener(ch);
        sliderPanel.add(blueSlider);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 400);
        setVisible(true);

        //create server socket
        try
        {
            ServerSocket ss = new ServerSocket(5006);
            while(true)
            {
                Socket sock = ss.accept();
                System.out.println("Client Connected");
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                out = new PrintWriter(sock.getOutputStream(),true);
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private class ChangeHandler implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            if(e.getSource() == widthSlider)
                out.println("w " + widthSlider.getValue());
            if(e.getSource() == heightSlider)
               out.println("h "+ heightSlider.getValue());
            if(e.getSource() == redSlider)
                out.println("r " + redSlider.getValue());
            if(e.getSource() == greenSlider)
                out.println("g " + greenSlider.getValue());
            if(e.getSource() == blueSlider)
                out.println("b " + blueSlider.getValue());
        }
    }

    public static void main(String[] args) {
        new ControlFrame();
    }
}

