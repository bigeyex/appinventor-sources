package com.makeblock.appinventor.brandnew;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 17/4/7.
 */

public class ActionExecutor implements Action.ActionLifeListener {

    private List<Action> mActionList = new ArrayList<Action>();
    private ActionStateListener actionStateListener;

    public void setActionStateListener(ActionStateListener actionStateListener) {
        this.actionStateListener = actionStateListener;
    }

    /**
     * 保存action
     *
     * @param action
     */
    public void addAction(Action action) {
        action.addActionLifeListener(this);
        mActionList.add(action);
    }

    /**
     * 移除冲突action
     *
     * @param action
     */
    public void cancelConflictActions(Action action) {
        //Action cancel后会被remove，拷贝List避免 ConcurrentModificationException
        List<Action> copiedList = new ArrayList<Action>(mActionList);
        for (Action currentAction : copiedList) {
            if (RJ25ActionFactory.isActionConflict(action, currentAction)) {
                currentAction.cancel();
            }
        }
    }

    /**
     * 1.解决冲突
     * 2.执行action
     *
     * @param action
     */
    public void executeAction(Action action) {
        cancelConflictActions(action);
        addAction(action);
        action.execute();
    }

    @VisibleForTesting
    public List<Action> getActionList() {
        return mActionList;
    }

    @Override
    public void onActionExecute(Action action) {
        if (actionStateListener != null) {
            actionStateListener.onActionExecute();
        }
    }

    @Override
    public void onActionCancel(Action action) {
        mActionList.remove(action);
        if (actionStateListener != null) {
            actionStateListener.onActionCancel();
        }
    }

    public void cancelAllActions() {
        //Action cancel后会被remove，拷贝List避免 ConcurrentModificationException
        List<Action> copiedList = new ArrayList<Action>(mActionList);
        for (Action action : copiedList) {
            action.cancel();
        }
    }

    @Override
    public void onActionFinish(Action action) {
        if (action.getDuration() != Action.DURATION_INFINITE) {
            mActionList.remove(action);
        }
        if (actionStateListener != null) {
            actionStateListener.onActionFinish();
        }
    }

    public interface ActionStateListener {
        void onActionExecute();
        void onActionCancel();
        void onActionFinish();
    }
}
