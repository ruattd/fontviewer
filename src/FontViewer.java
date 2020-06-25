import cn.makiser.fontviewer.ExceptionDialog;
import cn.makiser.fontviewer.Run;
import cn.makiser.fontviewer.exception.ProgramArgumentException;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class FontViewer {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Run.main(args);
        } catch (ProgramArgumentException e) {
            e.printStackTrace();
            new ExceptionDialog(null, "参数错误", e);
            System.exit(0x1f);
        } catch (Exception e) {
            e.printStackTrace();
            new ExceptionDialog(null, null, e);
        }
    }
}
