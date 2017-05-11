package com.makeblock.appinventor.brandnew;

/**
 * Created by xuexin on 2017/3/27.
 */

public class InfiniteRunnableAction extends Action {

    private Runnable runnable;

    public InfiniteRunnableAction(int index, long duration, Runnable runnable) {
        super(index, duration);
        if (runnable == null) {
            throw new RuntimeException("runnable不能为空");
        }
        this.runnable = runnable;
    }

    @Override
    public void execute() {
        super.execute();
        runnable.run();
    }

    @Override
    public void cancel() {
        super.cancel();
        runnable = null;
    }

    @Override
    public void executeFinish() {
        execute();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public String toString() {
        return "自动执行runnable的永续action";
    }
}
