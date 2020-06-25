package cn.makiser.fontviewer;

import javax.swing.*;
import java.awt.*;

public class ExceptionDialog extends JDialog {
    private Exception exception;

    public ExceptionDialog(Window owner, Exception e) {
        super(owner);
    }
}
