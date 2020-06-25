package cn.makiser.fontviewer;

import java.awt.*;
import java.io.File;
import javax.swing.*;

public class MainFrame extends JFrame {
    private Container c;
    private JLabel l;
    private JTextField tf;
    private JButton b_choose, b_load, b_size, b_style;

    //初始化方法
    protected void init() {
        c = getContentPane();
        c.setLayout(new GridLayout(3, 1));
        l = new JLabel("");
        tf = new JTextField();
        tf.setFont(new Font("Default", Font.PLAIN, 18));
        tf.setBounds(0, 0, 200, 50);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        b_choose = new JButton("选择字体");
        b_load = new JButton("加载文件");
        add_(panel, b_choose, b_load);
        add_(c, l, tf, panel);
    }
    //随文件初始化
    protected void init(File file) {

    }

    //自创的Container快速添加(一个一个地执行add()简直太麻烦了qwq)
    public static void add_(Container cont, Component... comp) {
        for (Component cp : comp) {
            cont.add(cp);
        }
    }
}
