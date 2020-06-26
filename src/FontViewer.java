import cn.makiser.tools.exception.ExceptionDialog;
import cn.makiser.fontviewer.Run;
import cn.makiser.fontviewer.exception.ProgramArgumentException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
            ExceptionDialog dialog = new ExceptionDialog(null, "参数错误", e);
            dialog.addOKButtonListener(e1 -> System.exit(0x1f));
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0x1f);
                }
            });
            dialog.show_();
        } catch (Exception e) {
            e.printStackTrace();
            new ExceptionDialog(null, null, e);
        }
    }
}
