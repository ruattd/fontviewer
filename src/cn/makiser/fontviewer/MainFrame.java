package cn.makiser.fontviewer;

import cn.makiser.fontviewer.ic.FontDialog;
import cn.makiser.fontviewer.ic.JTextFieldHintListener;
import cn.makiser.tools.exception.ExceptionDialog;

import java.awt.*;
import java.io.File;
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
        GridBagLayout layout = new GridBagLayout();
        c.setLayout(layout);
        l = new JLabel("字体预览", JLabel.CENTER);
        l.setFont(font);
        JScrollPane sp1 = new JScrollPane(l,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
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
        tf.addActionListener(e -> JOptionPane.showMessageDialog(MainFrame.this,
                "由于Swing组件限制, 如需换行, 请在文本开头和结尾分别添" +
                        "加\"<html>\"和\"</html>\", \n并在需要换行处添加HTML的\"<br>\"标签" +
                        "来实现换行.",
                "提示", JOptionPane.INFORMATION_MESSAGE)); //提示使用换行标签
        JPanel p3 = new JPanel(new GridLayout(1, 3));
        b_choose = new JButton("选择字体");
        b_choose.addActionListener(e -> {
            FontDialog dialog = new FontDialog(MainFrame.this, font);
            System.out.println(font.getName());
            dialog.setModal(true);
            dialog.addOKButtonListener(e1 -> {
                dialog.dispose();
                font = dialog.getChooseFont();
                setFontSize(dialog.getFontSize());
            });
            dialog.setVisible(true);
        });
        b_load = new JButton("加载文件");
        b_load.addActionListener(e -> {
            //字体选择器
            FileDialog d = new FileDialog(MainFrame.this,
                    "选择字体文件", FileDialog.LOAD);
            d.setModal(true);
            d.setVisible(true);
            try {
                if (!(d.getFile() == null)) {
                    font = Run.getFont(new File(d.getDirectory() + d.getFile()))
                            .deriveFont((float) font.getSize());
                    updateFont();
                }
            } catch (FontFormatException e1) {
                e1.printStackTrace();
                ExceptionDialog dialog = new ExceptionDialog(
                        MainFrame.this, "字体格式出错", e1);
                dialog.setHTMLHintText("<div style=\"font-size:1.02em\">" +
                        "<b>这可能是由于选择的字体文件格式不受支持或不是字体文件, 请" +
                        "选择正确的文件格式.</b></div><p>目前支持格式: .otf | " +
                        ".ttf | .ttc</p>");

                dialog.show_();
            } catch (Exception e1) {
                e1.printStackTrace();
                new ExceptionDialog(MainFrame.this, null, e1).show_();
            }
        });
        JLabel l = new JLabel();
        b_size = new JButton("字号: 20");
        b_size.addActionListener(e -> {
            //修改字号
            FontSizeDialog dialog = new FontSizeDialog(font.getSize(),MainFrame.this);
            dialog.setVisible(true);
        });
        add_(p3, b_choose, b_load, l, b_size);
        add_(c, sp1, sp2, p3);
        //安排布局
        sp2.setPreferredSize(new Dimension(0, 60));
        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth = 3; s.gridheight = 1; s.gridx = 0; s.gridy = 0;
        s.weightx = 1; s.weighty = 0.9;
        layout.setConstraints(sp1, s);
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth = 3; s.gridheight = 1; s.gridx = 0; s.gridy = 1;
        s.weightx = 1; s.weighty = 0;
        layout.setConstraints(sp2, s);
        s.fill = GridBagConstraints.HORIZONTAL;
        s.gridwidth = 3; s.gridheight = 1; s.gridx = 0; s.gridy = 2;
        s.weightx = 1; s.weighty = 0;
        layout.setConstraints(p3, s);
    }
    //随文件初始化
    protected void init(File file) {
        init();
        Font f = null;
        try {
            f = Run.getFont(file);
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
        String n = font.getName();
        l.setFont(font);
        if(!n.equals("Default")) {
            setTitle(n + " - FontViewer");
        }
    }


    //设置字号,主要给FontSizeDialog用
    protected void setFontSize(int size) {
        font = font.deriveFont((float) size);
        b_size.setText("字号: " + size);
        updateFont();
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

    //自创的Container快速添加(一个一个地执行add()简直太麻烦了qwq)
    public static void add_(Container cont, Component... comp) {
        for (Component cp : comp) {
            cont.add(cp);
        }
    }
}
