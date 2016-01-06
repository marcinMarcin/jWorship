/*
 * Created on 21.10.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package sk.calvary.worship;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.File;

public class ImageListCellRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = -9063091792011363300L;
    private final App app;
    final JLabel jLabelImage = new JLabel();
    final JPanel jPanel = new JPanel();
    final Font myFont = Font.decode("Arial-PLAIN-10");

    public ImageListCellRenderer(App app) {
        this.app = app;
        BorderLayout bl = new BorderLayout();
        bl.setHgap(3);
        jPanel.setLayout(bl);
        jPanel.add(jLabelImage, BorderLayout.CENTER);
        jPanel.add(this, BorderLayout.SOUTH);

        Dimension d = App.thumbnails.getMaxSize();
        jLabelImage.setPreferredSize(d);
        jLabelImage.setMinimumSize(d);
        jLabelImage.setHorizontalAlignment(SwingConstants.CENTER);

        jPanel.setBorder(new EtchedBorder());
    }

    public static String getMedia(Object value) {
        if (value instanceof File) {
            File f = (File) value;
            return f.toString();
        }
        if (value instanceof Bookmark) {
            Bookmark b = (Bookmark) value;
            return (String) b.getValue();
        }
        return null;
    }

    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected,
                cellHasFocus);

        File file = new File(getMedia(value));
        Image thumbnail = App.thumbnails.getThumbnail(file, list);
        if (thumbnail != null) {
            jLabelImage.setIcon(new ImageIcon(thumbnail));
        } else {
            jLabelImage.setIcon(null);
        }
        this.setText(file.getName());
        this.setFont(myFont);

        jPanel.setBackground(getBackground());

        return jPanel;
    }
}
