package cn.makiser.fontviewer;

import cn.makiser.fontviewer.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
        label1.setFont(Run.getMainFont(18));
        JPanel panel1 = new JPanel(new GridLayout(1, 2));
        panel1.add(label1);
        panel1.add(spinner1);
        JPanel panel2 = new JPanel(new GridLayout());
        JButton b1 = new JButton("确定");
        b1.addActionListener(e -> ok());
        panel2.add(b1);
        c.add(panel1); c.add(panel2);
        spinner1.setValue(size);
        spinner1.setFont(Run.getMainFont(21));
        spinner1.getEditor().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                System.out.println(e.getKeyChar() + " ?= " + KeyEvent.VK_ENTER);
                if(e.getKeyChar() == KeyEvent.VK_ENTER) {
                    ok();
                }
            }
        });
        int x = frame.getX() + frame.getWidth()/2 - 90;
        int y = frame.getY() + frame.getHeight()/2 - 60;
        setBounds(x, y, 180, 120);
    }

    private void ok() {
        int i = (int) spinner1.getValue();
        setVisible(false);
        frame.setFontSize(i);
    }
}
