package cn.makiser.fontviewer;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionDialog extends JDialog {
    private Exception exception;
    private final Window owner;
    private final JLabel label = new JLabel();
    JTextPane text = new JTextPane();
    private final String info;

    public ExceptionDialog(Window owner, String title, Exception e) {
        super(owner);
        this.owner = owner;
        //设置标题
        if (title == null) {
            setTitle("出错啦");
            setHintText(e.toString());
        } else {
            setTitle(title);
        }
        //处理错误信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        info = sw.toString();
        //布局
        Container c = getContentPane();
        text.setEditable(false);
        JPanel p1 = new JPanel();
        p1.add(text, BorderLayout.CENTER);
        JScrollPane sp = new JScrollPane(p1);
        //按钮
        JButton b1 = new JButton("确定"), b2 = new JButton("复制");
        JPanel p2 = new JPanel(new FlowLayout());
        b1.addActionListener(e1 -> {
            setVisible(false);
        });
        b2.addActionListener(e1 -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable trans = new StringSelection(info);
            clipboard.setContents(trans, null);
        });
        //添加组件
        MainFrame.add_(p2, b1, b2);
        c.add(label, BorderLayout.NORTH);
        c.add(sp, BorderLayout.CENTER);
        c.add(p2, BorderLayout.SOUTH);
    }

    public void setHintText(String text) {
        label.setText(text);
    }
    public void show_() {
        text.setText(info);
        pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int dw = d.width/2, dh = d.height/2;
        int w = getWidth(), h = getHeight();
        if (w > dw) w = dw; if (h > dh) h = dh;
        int x, y;
        if(owner == null) {
            x = d.width - w/2;
            y = d.height - h/2;
        } else {
            x = owner.getX() + owner.getWidth()/2 - w/2;
            y = owner.getY() + owner.getHeight()/2 - h/2;
        }
        setBounds(x, y, w, h);
        setVisible(true);
    }
}
