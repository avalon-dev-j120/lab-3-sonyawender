package gradient;

import javax.swing.*;

public class ColorBox extends JPanel {

    public ColorBox(){
        setBackground(RightPanel.currentColor);
        setToolTipText(RightPanel.colorCode);
    }
}