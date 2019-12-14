package gradient;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class RightPanel extends JPanel implements ChangeListener {
    protected static String colorCode;
    protected static Color currentColor;
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private StringSelection stringSelection;

    JSlider sliderRed, sliderGreen, sliderBlue;
    private JSlider [] jSliders = {
            sliderRed = new JSlider(0,255,125),
            sliderGreen = new JSlider(0,255,125),
            sliderBlue = new JSlider(0,255,125)
    };
    public RightPanel() {
        sliderRed.setName("Red");
        JLabel labelRed = new JLabel("Red:");

        sliderGreen.setName("Green");
        JLabel labelGreen = new JLabel("Green:");

        sliderBlue.setName("Blue");
        JLabel labelBlue = new JLabel("Blue:");

        for (JSlider slider: jSliders) {
            slider.setMajorTickSpacing(125);
            slider.setMinorTickSpacing(5);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.addChangeListener(this);
        }
        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);
        add(labelRed);
        add(sliderRed);
        add(labelGreen);
        add(sliderGreen);
        add(labelBlue);
        add(sliderBlue);
        setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        colorCode = "#";
        String strValue;
        for (JSlider slider: jSliders) {
            int value = slider.getValue();
            strValue = Integer.toHexString(value);
            strValue = strValue.length()==2?strValue:"0"+strValue;
            colorCode += strValue;
            slider.addChangeListener(GradientWindow.gradientWindow);
        }
        currentColor = new Color(sliderRed.getValue(), sliderGreen.getValue(), sliderBlue.getValue());
        stringSelection = new StringSelection(RightPanel.colorCode);
        clipboard.setContents(stringSelection, stringSelection);
    }
}