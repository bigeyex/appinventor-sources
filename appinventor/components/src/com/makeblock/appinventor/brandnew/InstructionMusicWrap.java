package com.makeblock.appinventor.brandnew;

import com.makeblock.appinventor.brandnew.Instruction;
import com.makeblock.appinventor.brandnew.InstructionWrap;

/**
 * Created by hupihuai on 17/4/1.
 */

public class InstructionMusicWrap extends InstructionWrap {
    public InstructionMusicWrap(Instruction instruction, long duration) {
        super(instruction, duration);
    }

    @Override
    public long getDuration() {
        return super.getDuration() + 100;
    }
}
