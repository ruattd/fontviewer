package cn.makiser.fontviewer;

import cn.makiser.fontviewer.exception.ProgramArgumentException;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;

public class Run {
    private static Font mainFont = null;
    private static String jarPath;

    private static final int font_size = 14;

    public static void main(String[] args) throws Exception {
        //
        String s = Run.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        s = URLDecoder.decode(s, "UTF-8");
        jarPath = new File(s).getAbsolutePath();
        //检测字体文件并加载
        System.out.println("寻找自定义字体文件...");
        Font font = findCustomFont(jarPath + "custom_font", font_size);
        findFont(font);
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
    //递归:寻找字体
    private static void findFont(Font font) {
        if (font == null) {
            font = findCustomFont("custom_font", font_size);
            if(font == null) {
                mainFont = new Font("Default", Font.PLAIN, font_size);
            } else {
                findFont(font);
            }
        } else {
            System.out.println("全局字体更改: " + font.getName());
            mainFont = font;
            initFont(font);
        }
    }

    //获取全局字体
    public static Font getMainFont(int size) {
        return mainFont.deriveFont((float) size);
    }
    //获取JAR路径
    public static String getJarPath() {
        return jarPath;
    }

    //寻找字体
    public static Font findCustomFont(String filepath, int size) {
        File fontFile;
        Font font = null;
        File gf_otf = new File(filepath + ".otf"),
                gf_ttf = new File(filepath + ".ttf"),
                gf_ttc = new File(filepath + ".ttc");
        if ((fontFile = gf_otf).exists());
        else if ((fontFile = gf_ttf).exists());
        else if ((fontFile = gf_ttc).exists());
        else fontFile = null;
        if (fontFile != null) {
            try {
                System.out.println("发现自定义字体文件: " + fontFile.getCanonicalPath());
                Font f = getFont(fontFile).deriveFont((float) size);
                font = f;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return font;
    }

    //某大神写的方法,可以把全局字体改掉 xD
    public static void initFont(Font font) {
        FontUIResource fontResource = new FontUIResource(font);
        System.out.println("应用属性...");
        for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontResource);
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
