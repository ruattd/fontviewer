package cn.makiser.tools.exception;

import cn.makiser.fontviewer.MainFrame;
import cn.makiser.fontviewer.Run;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionDialog extends JDialog {
    private final Exception exception;
    private final Frame owner;
    private final JLabel label = new JLabel(); //提示标签
    private JTextPane text = new JTextPane(); //错误信息区
    private final JButton b_ok = new JButton("确定"),
            b_copy = new JButton("复制"); //按钮
    private final String info;
    private static Font f;

    static {
        try {
            f = Run.getFont(new File(ClassLoader.getSystemResource(
                        "lib/fonts/NotoMono-Regular.ttf").toURI())).deriveFont(13f);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public ExceptionDialog(Frame owner, String title, Exception e) {
        super(owner, true);
        this.owner = owner;
        exception = e;
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
        text.setFont(f);
        JPanel p1 = new JPanel();
        p1.add(text, BorderLayout.CENTER);
        JScrollPane sp = new JScrollPane(p1);
        JPanel p2 = new JPanel(new FlowLayout());
        b_ok.addActionListener(e1 -> {
            setVisible(false);
        });
        b_copy.addActionListener(e1 -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable trans = new StringSelection(info);
            clipboard.setContents(trans, null);
        });
        //添加组件
        MainFrame.add_(p2, b_ok, b_copy);
        c.add(label, BorderLayout.NORTH);
        c.add(sp, BorderLayout.CENTER);
        c.add(p2, BorderLayout.SOUTH);
    }

    public void setHintText(String text) {
        label.setText(text);
    }
    public void setHTMLHintText(String text) {
        label.setText("<html>" + text + "</html>");
    }
    public void show_() {
        text.setText(info);
        pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int dw = (int) (d.width/1.9), dh = (int) (d.height/1.9);
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

    //事件监听器
    public void addOKButtonListener(ActionListener listener) {
        b_ok.addActionListener(listener);
    }
    public void addCopyButtonListener(ActionListener listener) {
        b_copy.addActionListener(listener);
    }

    //get
    public Exception getException() {
        return exception;
    }
}
