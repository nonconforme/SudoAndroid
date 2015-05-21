package com.thinkmobiles.sudo.utils;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 21.05.15.
 */
abstract public class SelectionHelper {
    private List<Class> mainList;
    private List<Class> remainList;
    private List<Class> deleteList;
    private boolean[] selection;
    private boolean selectionMode;


    private BaseAdapter adapter;

    public SelectionHelper(BaseAdapter adapter) {
        mainList = new ArrayList<>();
        remainList = new ArrayList<>();
        deleteList = new ArrayList<>();
        selectionMode = false;
        this.adapter = adapter;
    }

    abstract public void updateAdapterSelection(boolean selectionMode, boolean [] selection);

    abstract public void updateAdapter(List<Class> remain);

    public boolean isSelectionMode() {
        return selectionMode;
    }

    public void setMode(boolean mode, boolean[] selection) {
        this.selectionMode = mode;
        this.selection = selection;
    }

    public void splitList() {
        for (int i = 0; i < selection.length; i++) {
            if (selection[i]) deleteList.add(mainList.get(i));
            else remainList.add(mainList.get(i));
        }
    }


    public List<Class> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(List<Class> deleteList) {
        this.deleteList = deleteList;
    }

    public List<Class> getMainList() {
        return mainList;
    }

    public void setMainList(List<Class> mainList) {
        this.mainList = mainList;
        selection = new boolean[mainList.size()];
    }

    public List<Class> getRemainList() {
        return remainList;
    }

    public void setRemainList(List<Class> remainList) {
        this.remainList = remainList;
    }

    public boolean[] getSelection() {
        return selection;
    }

    public void setSelection(boolean[] selection) {
        this.selection = selection;
    }


}