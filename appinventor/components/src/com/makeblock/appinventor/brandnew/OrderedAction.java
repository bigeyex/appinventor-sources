package com.makeblock.appinventor.brandnew;

import com.google.common.annotations.VisibleForTesting;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xuexin on 2017/3/27.
 */

public class OrderedAction extends Action {

    private final Instruction[] finishInstructionList;
    private final Instruction[] cancelInstructionList;
    private int executeIndex;
    private List<InstructionWrap> sendInstructionPairList;
    private boolean needPause = false;

    public OrderedAction(int index,
                         List<InstructionWrap> sendInstructionPairList,
                         Instruction[] finishInstructionList,
                         Instruction[] cancelInstructionList) {
        super(index, Action.DURATION_UNKNOWN);
        if (sendInstructionPairList == null) {
            throw new RuntimeException("数据不对,初始化失败");
        }
        this.sendInstructionPairList = sendInstructionPairList;
        this.finishInstructionList = finishInstructionList;
        this.cancelInstructionList = cancelInstructionList;
        executeIndex = 0;
    }


    @Override
    public void execute() {
        if (needPause) {
            return;
        }
        super.execute();
        if (executeIndex < sendInstructionPairList.size()) {
            BleManager.getInstance().sendInstruction(
                    sendInstructionPairList.get(executeIndex++).getInstruction());
        } else {
            executeFinish();
        }

    }

    @Override
    public void cancel() {
        super.cancel();
        if (cancelInstructionList != null) {
            for (Instruction instruction : cancelInstructionList) {
                BleManager.getInstance().sendInstruction(instruction);
            }
        }
    }

    @Override
    public void executeFinish() {
        if (executeIndex < sendInstructionPairList.size()) {
            execute();
        } else {
            if (finishInstructionList != null) {
                for (Instruction instruction : finishInstructionList) {
                    BleManager.getInstance().sendInstruction(instruction);
                }
            }
            super.executeFinish();
        }
    }

    @Override
    public long getExecuteDuration() {
        return sendInstructionPairList.get(executeIndex).getDuration();
    }

    @Override
    public void pause() {
        needPause = true;
    }

    @Override
    public void resume() {
        if (needPause) {
            needPause = false;
            execute();
        } else {
            throw new RuntimeException("不要在运行状态调用这个方法");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        OrderedAction targetAction = (OrderedAction) obj;
        boolean executeEquals = this.sendInstructionPairList.equals(targetAction.sendInstructionPairList);
        boolean cancelEquals = this.cancelInstructionList == null ? null == targetAction.cancelInstructionList : Arrays.equals(this.cancelInstructionList, targetAction.cancelInstructionList);
        boolean finishEquals = this.finishInstructionList == null ? null == targetAction.finishInstructionList : Arrays.equals(this.finishInstructionList, targetAction.finishInstructionList);
        return executeEquals && cancelEquals && finishEquals;
    }

    @VisibleForTesting
    public List<InstructionWrap> getSendInstructionPairList() {
        return sendInstructionPairList;
    }

}
