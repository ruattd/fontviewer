package cn.makiser.fontviewer;

import cn.makiser.fontviewer.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FontSizeDialog extends JDialog {
    private final MainFrame frame; //父窗口
    private final SpinnerModel model1 = new SpinnerNumberModel();
    private final JSpinner spinner1 = new JSpinner(model1);

    public FontSizeDialog(int size, MainFrame frame) {
        super(frame, true);
        this.frame = frame;
        Container c = getContentPane();
        c.setLayout(new GridLayout(2, 1, 15, 0));
        JLabel label1 = new JLabel(" 字号: ");
        JPanel panel1 = new JPanel(new GridLayout(1, 2));
        panel1.add(label1);
        panel1.add(spinner1);
        JPanel panel2 = new JPanel(new FlowLayout());
        JButton b1 = new JButton("确定");
        b1.addActionListener(new Action());
        panel2.add(b1);
        c.add(panel1); c.add(panel2);
        spinner1.setValue(size);
        int x = frame.getX() + frame.getWidth()/2 - 90;
        int y = frame.getY() + frame.getHeight()/2 - 50;
        setBounds(x, y, 180, 100);
    }

    class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = (int) spinner1.getValue();
            setVisible(false);
            frame.setFontSize(i);
        }
    }
}
