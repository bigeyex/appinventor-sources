package com.makeblock.appinventor.brandnew;

/**
 * Created by hupihuai on 17/3/31.
 * 指令包装类
 */

public class InstructionWrap {

    private Instruction mInstruction;
    private long duration;

    public InstructionWrap(Instruction instruction, long duration) {
        mInstruction = instruction;
        this.duration = duration;
    }

    public Instruction getInstruction() {
        return mInstruction;
    }

    public void setInstruction(Instruction instruction) {
        mInstruction = instruction;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        InstructionWrap targetInstruction = (InstructionWrap) obj;
        return this.mInstruction.equals(targetInstruction.mInstruction) && this.duration == targetInstruction.duration;
    }
}
