package cn.makiser.fontviewer.ic;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * 字体选择对话框。
 *
 * @author lucky star
 *
 */
public class FontDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JComboBox<String> fontNameBox = null;
    private JComboBox<String> fontStyleBox = null;
    private JComboBox<String> fontSizeBox = null;
    private JTextArea txtrHereIs = null;
    private final JButton okButton = new JButton("\u786E\u5B9A"),
            cancelButton = new JButton("\u53D6\u6D88");

    private String fontName;
    private String fontStyle;
    private String fontSize;
    private int fontSty;
    private int fontSiz;

    /*
     * Launch the application.

    public static void main(String[] args) {
        try {
            FontDialog dialog = new FontDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     */

    // Create the dialog.
    public FontDialog(Window owner, Font font) {
        //初始化窗口
        super(owner);
        setTitle("\u5B57\u4F53");
        int x = owner.getX() + owner.getWidth()/2 - 245;
        int y = owner.getY() + owner.getHeight()/2 - 120;
        setBounds(x, y, 490, 240);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JLabel lblf = new JLabel("\u5B57\u4F53(F):");
            lblf.setBounds(5, 10, 54, 15);
            contentPanel.add(lblf);
        }
        {
            JLabel lbly = new JLabel("\u5B57\u5F62(Y):");
            lbly.setBounds(212, 10, 54, 15);
            contentPanel.add(lbly);
        }
        {
            JLabel lbls = new JLabel("\u5927\u5C0F(S):");
            lbls.setBounds(350, 10, 54, 15);
            contentPanel.add(lbls);
        }
        {
            JLabel label = new JLabel("\u663E\u793A\u6548\u679C:");
            label.setBounds(34, 82, 64, 15);
            contentPanel.add(label);
        }

        //预览
        Panel panel = new Panel();
        panel.setBounds(120, 35, 300, 140);
        contentPanel.add(panel);
        panel.setLayout(null);
        {
            txtrHereIs = new JTextArea();
            txtrHereIs.setBounds(5, 5, 295, 120);
            txtrHereIs
                    .setText("\u8FD9\u91CC\u663E\u793A\u9884\u89C8\r\nHere is the preview");
            panel.add(txtrHereIs);
        }
        //字体
        {
            fontNameBox = new JComboBox<String>();
            fontNameBox.setBounds(58, 7, 143, 21);
            contentPanel.add(fontNameBox);
            fontNameBox.addItemListener(itemevent -> {
                fontName = (String) itemevent.getItem();
                // change preview
                Font f = new Font(fontName, fontSty, fontSiz);
                txtrHereIs.setFont(f);
            });
        }
        //字型
        {
            fontStyleBox = new JComboBox<>();
            fontStyleBox.setBounds(266, 7, 73, 21);
            contentPanel.add(fontStyleBox);
            fontStyleBox.addItemListener(itemevent -> {
                fontStyle = (String) itemevent.getItem();
                fontSty = getFontStyleByCnName(fontStyle);
                // change preview
                Font f = new Font(fontName, fontSty, fontSiz);
                txtrHereIs.setFont(f);
            });
        }
        //字号
        {
            fontSizeBox = new JComboBox<String>();
            fontSizeBox.setBounds(404, 7, 78, 21);
            contentPanel.add(fontSizeBox);
            fontSizeBox.addItemListener(itemevent -> {
                fontSize = (String) itemevent.getItem();
                fontSiz = Integer.parseInt(fontSize);

                // change preview
                Font f = new Font(fontName, fontSty, fontSiz);
                txtrHereIs.setFont(f);
            });
        }
        //按钮
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                okButton.addActionListener(event -> {
                    int fontSty = getFontStyleByCnName(fontStyle);
                    int fontSiz = Integer.parseInt(fontSize);
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
                cancelButton.addActionListener(event -> FontDialog.this.dispose());
            }
        }

        // 初始化字体名称
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
//        fontNameBox.setModel(new DefaultComboBoxModel(fontNames));
        String fontName_ = font.getName();
        for(int i = 0; i < fontNames.length; i++) {
            fontNameBox.addItem(fontNames[i]);
            if(fontName_.equals(fontNames[i])) {
                fontNameBox.setSelectedIndex(i);
            }
        }
        // 初始化字体样式
        String[] fontStyles = { "常规", "粗体", "斜体", "粗斜体" };
        fontStyleBox.setModel(new DefaultComboBoxModel<>(fontStyles));
        fontStyleBox.setSelectedIndex(font.getStyle());
        // 初始化字体大小
        String[] fontSizes = { "8", "9", "10", "11", "12", "14", "16", "18",
                "20", "22", "24", "26", "28", "36", "48", "72" };
        fontSizeBox.setModel(new DefaultComboBoxModel<>(fontSizes));

        fontSizeBox.setSelectedIndex(7);
        fontStyle = (String) fontStyleBox.getSelectedItem();
        fontSize = (String) fontSizeBox.getSelectedItem();
        fontSty = getFontStyleByCnName(fontStyle);
        fontSiz = Integer.parseInt(fontSize);
    }

    public static int getFontStyleByCnName(String fontStyle) {
        if (fontStyle.equals("常规")) {
            return Font.PLAIN;
        }
        if (fontStyle.equals("斜体")) {
            return Font.ITALIC;
        }
        if (fontStyle.equals("粗体")) {
            return Font.BOLD;
        }
        if (fontStyle.equals("粗斜体")) {
            return Font.BOLD + Font.ITALIC;
        }
        return -1;
    }

    //get&set
    public void addOKButtonListener(ActionListener listener) {
        okButton.addActionListener(listener);
    }
    public Font getChooseFont() {
        return new Font(fontName, fontSty, fontSiz);
    }
    public int getFontSize() {
        return fontSiz;
    }
    public int getFontStyle() {
        return fontSty;
    }
}
/*

————————————————
这个类是从CSDN搜集来的,版权协议规定声明如下:
        版权声明：本文为CSDN博主「luckystar2008」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        原文链接：https://blog.csdn.net/qincidong/java/article/details/8515087
部分内容修改：为此类添加面向对象使用的功能
 */
