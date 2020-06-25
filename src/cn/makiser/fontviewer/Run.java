package cn.makiser.fontviewer;

import cn.makiser.fontviewer.exception.ProgramArgumentException;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

public class Run {
    private static Font mainFont = null;

    public static void main(String[] args) throws ProgramArgumentException {
        //检测字体文件并加载
        Font font = findCustomFont("custom_font", 14);
        if (font == null) {
            mainFont = new Font("Default", Font.PLAIN, 14);
        } else {
            System.out.println("发现自定义字体文件, 准备应用...");
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                new ExceptionDialog(null, null, e).show_();
//            }
            mainFont = font;
            initFont(font);
        }
        //初始化窗口
        System.out.println("初始化窗口...");
        MainFrame frame = new MainFrame();
        File file;
        if (args.length == 0) {
            frame.init();
        } else {
            if ((file = new File(args[0])).exists()) {
                frame.init(file);
            } else {
                throw new ProgramArgumentException("参数传递的字体文件不存在");
            }
        }
        frame.setSize(600, 360);
        frame.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
        frame.updateFont();
        frame.setTitle("Makiser FontViewer");
        System.out.println("启动窗口...");
        frame.show_();
    }

    //获取全局字体
    public static Font getMainFont(int size) {
        return mainFont.deriveFont((float) size);
    }

    public static Font findCustomFont(String filename, int size) {
        File fontFile;
        Font font = null;
        File gf_otf = new File(filename + ".otf"),
                gf_ttf = new File(filename + ".ttf"),
                gf_ttc = new File(filename + ".ttc");
        if ((fontFile = gf_otf).exists());
        else if ((fontFile = gf_ttf).exists());
        else if ((fontFile = gf_ttc).exists());
        else fontFile = null;
        if (fontFile != null) {
            try {
                Font f = getFont(fontFile).deriveFont((float) size);
                font = f;
                System.out.println(f.getSize());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return font;
    }

    //某大神写的方法,可以把全局字体改掉 xD
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

    //从文件加载字体
    public static Font getFont(File file) throws IOException, FontFormatException {
        Font font;
        if(file == null) {
            throw new NullPointerException("文件对象为空");
        }
        font = Font.createFont(Font.TRUETYPE_FONT, file);
        return font;
    }
}
