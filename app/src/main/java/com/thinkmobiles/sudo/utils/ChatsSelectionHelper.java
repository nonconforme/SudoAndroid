package com.thinkmobiles.sudo.utils;

import com.thinkmobiles.sudo.models.chat.LastChatsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 21.05.15.
 */
abstract public class ChatsSelectionHelper extends BaseSelectionHelper {

    private List<LastChatsModel> mainList;
    private List<LastChatsModel> remainList;
    private List<LastChatsModel> deleteList;

    @Override
    abstract public void StopParentSelectionMode();


    public ChatsSelectionHelper() {
        super();
        mainList = new ArrayList<>();
        remainList = new ArrayList<>();
        deleteList = new ArrayList<>();

    }


    @Override
    public boolean removeDeletedItem() {
        if (deleteList == null || deleteList.size() < 1) return false;
        deleteList.remove(deleteList.size() - 1);
        if (deleteList.size() < 1) return false;
        return true;

    }

    @Override
    public <T> void startSelectionMode(List<T> mainList) {
        selection = new boolean[mainList.size()];
        selectionMode = true;
        this.mainList = (List<LastChatsModel>) mainList;

    }

    @Override
    public void stopSelectionMode() {
        selectionMode = false;
        selection = null;
        deleteList = new ArrayList<>();
        remainList = new ArrayList<>();
    }

    @Override
    public void splitList() {
        for (int i = 0; i < selection.length; i++) {
            if (selection[i]) deleteList.add(mainList.get(i));
            else remainList.add(mainList.get(i));
        }
    }

    @Override
    public <T> T getLastDeleteItem() {
        return (T) deleteList.get(deleteList.size() - 1);

    }

    @Override
    public List<?> getDeleteList() {
        return deleteList;
    }

    @Override
    public <T> void addToMainList(List<T> list) {
    mainList.addAll((List<LastChatsModel>)list);
    }

    @Override
    public List<?> getRemainList() {
        return null;
    }
}