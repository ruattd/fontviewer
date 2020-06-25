package cn.makiser.fontviewer;

import javax.swing.*;
import java.io.File;

public class Run {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainFrame frame = new MainFrame();
        File file;
        if(args.length == 0) {
            frame.init();
        } else {
            if ((file = new File(args[0])).exists()) {

            }
        }
        frame.setSize(600, 360);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
