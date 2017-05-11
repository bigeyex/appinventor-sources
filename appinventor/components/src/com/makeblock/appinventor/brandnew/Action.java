package com.makeblock.appinventor.brandnew;

import android.os.Handler;
import android.os.Message;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 目前只允许在主线程初始化,其他的线程直接挂掉吧,暂不处理
 * Created by xuexin on 2017/3/24.
 */

public abstract class Action {

    public static final long DURATION_INFINITE = -1;
    public static final long DURATION_IMMEDIATELY = 0;
    public static final long DURATION_UNKNOWN = -2;
    protected Handler handler;
    private long duration;
    private int index;

    private CopyOnWriteArrayList<ActionLifeListener> actionLifeListeners;

    public void execute() {
        handler.sendEmptyMessageDelayed(ActionHandler.FINISH, getExecuteDuration());
        executeNotify();
    }

    public void cancel() {
        handler.removeMessages(ActionHandler.FINISH);
        cancelNotify();
    }


    public void executeFinish() {
        finishNotify();
    }

    public int getIndex() {
        return index;
    }


    public long getExecuteDuration() {
        return duration;
    }

    public long getDuration() {
        return duration;
    }

    public void pause() {
        throw new RuntimeException("你不说,我怎么知道怎么做~~");
    }

    public void resume() {
        throw new RuntimeException("你不说,我怎么知道怎么做~~");
    }

    // TODO: lyh 2017/3/27
    public void onReceiveData() {

    }

    public Action(int index, long duration) {
        this.index = index;
        this.duration = duration;
        handler = new ActionHandler();
    }

    private class ActionHandler extends Handler {
        private static final int FINISH = 1;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FINISH:
                    executeFinish();
                    break;
            }
        }
    }


    private void executeNotify() {
        if (actionLifeListeners != null) {
            for (ActionLifeListener actionLifeListener : actionLifeListeners) {
                actionLifeListener.onActionExecute(this);
            }
        }
    }

    private void cancelNotify() {
        if (actionLifeListeners != null) {
            for (ActionLifeListener actionLifeListener : actionLifeListeners) {
                actionLifeListener.onActionCancel(this);
            }
        }
    }

    private void finishNotify() {
        if (actionLifeListeners != null) {
            for (ActionLifeListener actionLifeListener : actionLifeListeners) {
                actionLifeListener.onActionFinish(this);
            }
        }
    }

    public void addActionLifeListener(ActionLifeListener lifeListener) {
        if (actionLifeListeners == null) {
            actionLifeListeners = new CopyOnWriteArrayList<ActionLifeListener>();
        }
        actionLifeListeners.add(lifeListener);
    }


    /**
     * action 的执行生命周期回调
     */
    public interface ActionLifeListener {
        void onActionExecute(Action action);

        void onActionCancel(Action action);

        void onActionFinish(Action action);

    }
}
