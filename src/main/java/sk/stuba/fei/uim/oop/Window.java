package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window() throws HeadlessException {
        super();
        this.setVisible(true);
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
