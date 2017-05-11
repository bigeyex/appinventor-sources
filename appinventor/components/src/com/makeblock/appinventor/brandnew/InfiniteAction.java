package com.makeblock.appinventor.brandnew;

/**
 * Created by xuexin on 2017/3/27.
 */

public class InfiniteAction extends Action {
    private Instruction executeInstruction;
    private Instruction cancelInstruction;

    public InfiniteAction(int index, long duration,
                          Instruction executeInstruction,
                          Instruction cancelInstruction) {
        super(index, duration);
        if (executeInstruction == null) {
            throw new RuntimeException("发送的指令不能为空");
        }
        this.executeInstruction = executeInstruction;
        this.cancelInstruction = cancelInstruction;
    }

    @Override
    public void execute() {
        super.execute();
        BleManager.getInstance().sendInstruction(executeInstruction);
    }

    @Override
    public void cancel() {
        super.cancel();
        BleManager.getInstance().sendInstruction(cancelInstruction);
    }

    @Override
    public void executeFinish() {
        execute();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() != obj.getClass()) {
            return false;
        }
        InfiniteAction targetAction = (InfiniteAction) obj;
        if (targetAction == null) {
            return false;
        }
        boolean indexEquals = targetAction.getIndex() == getIndex();
        boolean durationEquals = targetAction.getDuration() == getDuration();
        boolean executeEquals = executeInstruction.equals(targetAction.executeInstruction);
        boolean cancelEquals = cancelInstruction == null ? null == targetAction.cancelInstruction : cancelInstruction.equals(targetAction.cancelInstruction);
        return indexEquals && durationEquals && executeEquals && cancelEquals;
    }
}
