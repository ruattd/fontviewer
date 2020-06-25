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
                frame.init(file);
            } else {
                JOptionPane.showMessageDialog(null,
                        "参数传递的字体文件不存在, 请检查参数并重试",
                        "出错啦", JOptionPane.ERROR_MESSAGE);
                System.exit(0x1f);
            }
        }
        frame.setSize(600, 360);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.updateFont();
        frame.show_();
        frame.setTitle("Makiser FontViewer");
    }
}
