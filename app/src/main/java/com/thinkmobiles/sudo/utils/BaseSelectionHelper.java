package com.thinkmobiles.sudo.utils;

import java.util.List;

/**
 * Created by omar on 21.05.15.
 */
abstract public class BaseSelectionHelper {

      boolean[] selection;
      boolean selectionMode;


    public BaseSelectionHelper() {
        selectionMode = false;

    }

    public void addToSelection(int position) {
        selection[position] = !selection[position];

    }


    public void checkSelectionNotEmpty() {
        boolean containsSelection = false;
        if (selection != null) {
            for (int i = 0; i < selection.length - 1; i++) {
                if (selection[i]) {
                    containsSelection = true;
                }
            }
        }
        if (!containsSelection) {
            StopParentSelectionMode();

        }
    }

    public boolean isSelectionNotEmpty() {
        boolean containsSelection = false;
        if (selection != null) {
            for (int i = 0; i < selection.length - 1; i++) {
                if (selection[i]) {
                    containsSelection = true;
                }
            }
        }
         return  containsSelection;
    }




    public <T>void growSelecction(List<T> tempList) {
        addToMainList(tempList);
        int grow = tempList.size();
        int newLength = selection.length + grow;
        boolean[] newArray = new boolean[newLength];
        for (int i = 0; i < selection.length; i++) {
            newArray[i] = selection[i];
        }

    }

    public boolean[] getSelection() {
        return selection;
    }

    public boolean isSelectionMode() {
        return selectionMode;
    }




    abstract public <T> void addToMainList(List<T> list);
    abstract public void stopSelectionMode();
    abstract public boolean removeDeletedItem();
    abstract public void StopParentSelectionMode();
    abstract public <T> void startSelectionMode(List<T> mainList);
    abstract public void splitList();
    abstract public <T> T getLastDeleteItem();
    abstract public List<?> getDeleteList();
    abstract public List<?> getRemainList();



}