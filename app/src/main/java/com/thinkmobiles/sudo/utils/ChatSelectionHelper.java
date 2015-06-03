package com.thinkmobiles.sudo.utils;

import com.thinkmobiles.sudo.models.chat.LastChatsModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 21.05.15.
 */
abstract public class ChatSelectionHelper extends BaseSelectionHelper {

    private List<MessageModel> mainList;
    private List<MessageModel> remainList;
    private List<MessageModel> deleteList;

    @Override
    abstract public void StopParentSelectionMode();


    @Override
    public <T> void addToMainList(List<T> list) {
        mainList.addAll((List<MessageModel>) list);
    }

    public ChatSelectionHelper() {
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
        this.mainList = (List<MessageModel>) mainList;

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
    public void addToSelection(int position) {
        selection[selection.length - 1 - position] = !selection[selection.length - 1 - position];
    }

    @Override
    public List<MessageModel> getRemainList() {
        return remainList;
    }

    public void addNewMessage(MessageModel message) {
        mainList.add(0, message);
        growSelectioOffset();
    }


    private void growSelectioOffset() {
        if (selection != null) {


            boolean[] temp = new boolean[selection.length + 1];
            if (temp.length > 1) {
                temp[0] = false;
                for (int i = 0; i < selection.length; i++) {
                    temp[i + 1] = selection[i];
                }
            }
        }
    }
}