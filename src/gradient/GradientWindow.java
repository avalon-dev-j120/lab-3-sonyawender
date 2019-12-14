package gradient;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class GradientWindow extends JFrame implements ChangeListener {
    public static GradientWindow gradientWindow = null;
    private static ColorBox colorBox = null;
    private static RightPanel rightPanel = null;
    private static int colorRed = 125;
    private static int colorGreen = 125;
    private static int colorBlue = 125;

    public GradientWindow() {
        gradientWindow = this;
        setTitle("Gradient");
        setSize(500, 200);
        setMaximumSize(new Dimension(500,200));
        this.setLayout(new GridLayout(1, 2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        colorBox = new ColorBox();
        Color defaultColor = new Color(colorRed, colorGreen, colorBlue);
        colorBox.setBackground(defaultColor);
        rightPanel = new RightPanel();
        add(colorBox);
        add(rightPanel);
        setVisible(true);
    }

    public static GradientWindow getInstance(){
        if(gradientWindow != null){
            return gradientWindow;
        } else {
            return new GradientWindow();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        String sliderName = slider.getName();
        switch (sliderName) {
            case "Red" : colorRed = slider.getValue();
                break;
            case "Blue" : colorBlue = slider.getValue();
                break;
            case "Green" : colorGreen = slider.getValue();
                break;
        }
        colorBox.setBackground(RightPanel.currentColor);
        colorBox.setToolTipText(RightPanel.colorCode);
    }
}