package cn.makiser.fontviewer;

import cn.makiser.fontviewer.MainFrame;

import javax.swing.*;
import java.awt.*;

public class FontSizeDialog extends JDialog {
    public FontSizeDialog(int size, MainFrame frame) {
        super(frame, true);
        Container c = getContentPane();
        c.setLayout(new GridLayout(2, 1, 30, 0));
        SpinnerModel model1 = new SpinnerNumberModel();
        JSpinner spinner1 = new JSpinner(model1);
        JLabel label1 = new JLabel(" 字号: ");
        JPanel panel1 = new JPanel(new GridLayout(1, 2));
        panel1.add(label1);
        panel1.add(spinner1);
        JPanel panel2 = new JPanel(new FlowLayout());
        JButton b1 = new JButton("确定");
        b1.addActionListener(e -> {
            int i = (int) spinner1.getValue();
            setVisible(false);
            frame.setFontSize(i);
        });
        panel2.add(b1);
        c.add(panel1); c.add(panel2);
//        spinner1.setBounds(0, 0, 60, 30);
        spinner1.setValue(size);
        int x = frame.getX() + frame.getWidth()/2 - 90;
        int y = frame.getY() + frame.getHeight()/2 - 60;
        setBounds(x, y, 180, 120);
    }
}
