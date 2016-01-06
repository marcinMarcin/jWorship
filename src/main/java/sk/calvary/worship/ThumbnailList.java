/*
 * Created on 28.10.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package sk.calvary.worship;

import sk.calvary.misc.ui.ObjectListModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ThumbnailList extends JList {
    private static final long serialVersionUID = -3877050415800945471L;
    public final ObjectListModel files = new ObjectListModel();
    private final App app;
    public Bookmarks<?> selectedBookmarks;
    final MyListener listener = new MyListener();
    private Object selector;

    public ThumbnailList() {
        this(null);
    }

    public ThumbnailList(App app) {
        this.app = app;
        setModel(files);
        setCellRenderer(new ImageListCellRenderer(app));
        setVisibleRowCount(0);
        setLayoutOrientation(JList.HORIZONTAL_WRAP);
        if (app != null)
            setPrototypeCellValue(app.getDirPictures());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void updateContent() {
        selectedBookmarks = null;
        if (selector instanceof DirBrowser) {
            files.setObjects(((DirBrowser) selector).getSelectedFiles());
            return;
        }
        if (selector instanceof JList) {
            Object o = ((JList) selector).getSelectedValue();
            if (o instanceof Bookmarks) {
                Bookmarks<?> b = (Bookmarks<?>) o;
                files.setObjects(b.getBookmarks());
                selectedBookmarks = b;
                return;
            }
        }
        files.setObjects(new Object[0]);
    }

    public void setSelector(Object s) {
        if (s == selector)
            return;
        if (selector instanceof DirBrowser) {
            DirBrowser old = (DirBrowser) selector;
            old.removePropertyChangeListener(listener);
        }
        if (selector instanceof JList) {
            JList old = (JList) selector;
            old.removeListSelectionListener(listener);
        }
        selector = null;
        if (s instanceof DirBrowser) {
            DirBrowser n = (DirBrowser) s;
            n.addPropertyChangeListener(listener);
            selector = s;
            updateContent();
            return;
        }
        if (s instanceof JList) {
            JList n = (JList) s;
            n.addListSelectionListener(listener);
            selector = s;
            updateContent();
            return;
        }
        if (s == null) {
            selector = s;
            updateContent();
            return;
        }
        throw new IllegalArgumentException();
    }

    public String getSelectedMedia() {
        return ImageListCellRenderer.getMedia(getSelectedValue());
    }

    public boolean setSelectedMedia(String m) {
        if (m == null) {
            clearSelection();
            return true;
        }
        if (m.equals(getSelectedMedia()))
            return true;
        ListModel d = getModel();
        for (int i = 0; i < d.getSize(); i++) {
            Object v = d.getElementAt(i);
            if (m.equals(ImageListCellRenderer.getMedia(v))) {
                setSelectedValue(v, true);
                return true;
            }
        }
        return false;
    }

    private final class MyListener implements PropertyChangeListener,
            ListSelectionListener {
        public void propertyChange(PropertyChangeEvent evt) {
            if (selector instanceof DirBrowser)
                if (evt.getPropertyName().equals("selectedFiles")) {
                    updateContent();
                }
        }

        public void valueChanged(ListSelectionEvent e) {
            updateContent();
        }
    }
}
