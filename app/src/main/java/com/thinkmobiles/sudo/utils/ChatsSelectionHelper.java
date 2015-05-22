package com.thinkmobiles.sudo.utils;

import android.widget.BaseAdapter;
import com.thinkmobiles.sudo.models.chat.LastChatsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 21.05.15.
 */
abstract public class ChatsSelectionHelper {
    private List<LastChatsModel> mainList;
    private List<LastChatsModel> remainList;
    private List<LastChatsModel> deleteList;
    private boolean[] selection;
    private boolean selectionMode;




    public ChatsSelectionHelper() {
        mainList = new ArrayList<>();
        remainList = new ArrayList<>();
        deleteList = new ArrayList<>();
        selectionMode = false;

    }

    public void addToSelection(int position) {
        selection[position] = !selection[position];

    }

    public boolean removeDeletedItem(){
        if(deleteList == null || deleteList.size()<1)
            return false;
        deleteList.remove(deleteList.size()-1);
        if(deleteList.size()<1)
            return false;
        return true;

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
            stopParentSelectionMode();

        }
    }

    abstract public void stopParentSelectionMode();


    public void growSelecction(int grow) {

        int newLength = selection.length + grow;
        boolean[] newArray = new boolean[newLength];
        for (int i = 0; i < selection.length; i++) {
            newArray[i] = selection[i];
        }

    }


    public void startSelectionMode(List<LastChatsModel> mainList){
        selection = new boolean [mainList.size()];
        selectionMode = true;
        this.mainList = mainList;
    }

    public void stopSelectionMode(){
        selectionMode = false;
        selection = null;
        deleteList = new ArrayList<>();
        remainList = new ArrayList<>();
    }


    public boolean isSelectionMode() {
        return selectionMode;
    }


    public void splitList() {
        for (int i = 0; i < selection.length; i++) {
            if (selection[i]) deleteList.add(mainList.get(i));
            else remainList.add( mainList.get(i));
        }
    }

    public <T> T getLastDeleteItem(){
        return (T)deleteList.get(deleteList.size()-1);

    }


    public List<LastChatsModel> getDeleteList() {
        return deleteList;
    }

    public boolean[] getSelection() {
        return selection;
    }




}