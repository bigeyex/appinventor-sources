package com.makeblock.appinventor.brandnew;

/**
 * Created by xuexin on 2017/3/27.
 */

public class SingleAction extends Action {
    private Instruction executeInstruction;
    private Instruction finishInstruction;
    private Instruction cancelInstruction;

    public SingleAction(int index, long duration,
                        Instruction executeInstruction,
                        Instruction finishInstruction,
                        Instruction cancelInstruction) {
        super(index, duration);
        if (executeInstruction == null) {
            throw new RuntimeException("发送的指令不能为空");
        }
        this.executeInstruction = executeInstruction;
        this.cancelInstruction = cancelInstruction;
        this.finishInstruction = finishInstruction;
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
        super.executeFinish();
        BleManager.getInstance().sendInstruction(finishInstruction);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() != obj.getClass()) {
            return false;
        }
        SingleAction targetAction = (SingleAction) obj;
        if (targetAction == null) {
            return false;
        }
        boolean executeEquals = executeInstruction.equals(targetAction.executeInstruction);
        boolean cancelEquals = cancelInstruction == null ? null == targetAction.cancelInstruction : cancelInstruction.equals(targetAction.cancelInstruction);
        boolean finishEquals = finishInstruction == null ? null == targetAction.finishInstruction : finishInstruction.equals(targetAction.finishInstruction);
        return executeEquals && cancelEquals && finishEquals;
    }
}
