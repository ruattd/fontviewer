package cn.makiser.fontviewer;

import cn.makiser.fontviewer.ic.JTextFieldHintListener;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MainFrame extends JFrame {
    private Container c; //主容器
    private JLabel l; //显示文本的标签
    private JTextField tf; //文本框
    private JButton b_choose, b_load, b_size, b_style;

    //查看的字体
    private Font font = new Font("Default", Font.PLAIN, 20);

    //初始化方法
    protected void init() {
        c = getContentPane();
        c.setLayout(new GridLayout(3, 1));
        l = new JLabel("字体预览", JLabel.CENTER);
        JScrollPane sp1 = new JScrollPane(l);
        tf = new JTextField();
        JScrollPane sp2 = new JScrollPane(tf);
        tf.addFocusListener(new JTextFieldHintListener(tf, "键入文字预览(支持HTML)"));
        tf.setFont(Run.getMainFont(18));
        tf.setBounds(0, 0, 200, 50);
        tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {updateText();}
            @Override
            public void removeUpdate(DocumentEvent e) {updateText();}
            @Override
            public void changedUpdate(DocumentEvent e) {updateText();}
        }); //文本改变监听
        JPanel panel = new JPanel(new FlowLayout());
        b_choose = new JButton("选择字体");
        b_load = new JButton("加载文件");
        b_load.addActionListener(e -> {
            //字体选择器
            FileDialog d = new FileDialog(MainFrame.this,
                    "选择字体文件", FileDialog.LOAD);
            d.setModal(true);
            d.setVisible(true);
            try {
                if (!(d.getFile() == null)) {
                    font = getFont(new File(d.getDirectory() + d.getFile()))
                            .deriveFont((float) font.getSize());
                    System.out.println(font.getSize());
                    updateFont();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                new ExceptionDialog(MainFrame.this, e1).setVisible(true);
            }
        });
        b_size = new JButton("字号: 20");
        b_size.addActionListener(e -> {
            //修改字号
            FontSizeDialog dialog = new FontSizeDialog(font.getSize(),MainFrame.this);
            dialog.setVisible(true);
        });
        add_(panel, b_choose, b_load, b_size);
        add_(c, sp1, sp2, panel);
    }
    //随文件初始化
    protected void init(File file) {
        init();
        Font f = null;
        try {
            f = getFont(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        font = f;
        updateFont();
    }

    //更新文本
    public void updateText() {
        String text = tf.getText();
        l.setText(text);
    }
    //更新字体
    public void updateFont() {
        l.setFont(font);
    }

    //设置字号,主要给FontSizeDialog用
    protected void setFontSize(int size) {
        font = new Font(font.getName(), font.getStyle(), size);
        b_size.setText("字号: " + size);
        updateFont();
        System.out.println(font.getSize());
    }

    //显示窗口
    public void show_() {
        setVisible(true);
        tf.setFocusable(false);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tf.setFocusable(true);
    }

    //从文件加载字体
    public static Font getFont(File file) throws IOException, FontFormatException {
        Font font;
//        FileInputStream fis = new FileInputStream(file);
        if(file == null) {
            throw new NullPointerException("文件对象为空");
        }
        font = Font.createFont(Font.TRUETYPE_FONT, file);
        return font;
    }
    //自创的Container快速添加(一个一个地执行add()简直太麻烦了qwq)
    public static void add_(Container cont, Component... comp) {
        for (Component cp : comp) {
            cont.add(cp);
        }
    }
}
