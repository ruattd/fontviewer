package cn.makiser.fontviewer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

public class Run {
    private static Font mainFont = null;

    public static void main(String[] args) {
        //检测字体文件并加载
        File fontFile;
        File gf_otf = new File("custom_font.otf"),
                gf_ttf = new File("custom_font.ttf"),
                gf_ttc = new File("custom_font.ttc");
        if ((fontFile = gf_otf).exists());
        else if ((fontFile = gf_ttf).exists());
        else if ((fontFile = gf_ttc).exists());
        else fontFile = null;
        if (fontFile != null) {
            System.out.println("发现自定义字体文件, 准备应用...");
            try {
                Font f = MainFrame.getFont(fontFile).deriveFont(14f);
                mainFont = f;
                System.out.println(f.getSize());
                initFont(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mainFont = new Font("Default", Font.PLAIN, 14);
        }
        //初始化窗口
        MainFrame frame = new MainFrame();
        File file;
        if (args.length == 0) {
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
        frame.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
        frame.updateFont();
        frame.setTitle("Makiser FontViewer");
        frame.show_();
    }

    //获取全局字体
    public static Font getMainFont(int size) {
        return mainFont.deriveFont((float) size);
    }

    public static void initFont(Font font) {
        FontUIResource fontResource = new FontUIResource(font);
        for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontResource);
                System.out.println("属性应用于: " + key);
            }
        }
    }
    //某大神写的方法,可以把全局字体改掉 xD
}
