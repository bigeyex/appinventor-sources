package com.makeblock.appinventor.brandnew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hupihuai on 17/3/30.
 */

public class HappyBirthdayFactory implements IActionFactory {

    private BuzzerTempo mBuzzerTempo;

    public HappyBirthdayFactory() {
        this.mBuzzerTempo = new BuzzerTempo(1000);
    }

    @Override
    public Action createAction(byte port) {
        return new OrderedAction(RJ25ActionFactory.ACTION_BUZZER, createInstructions(port), null, null);
    }

    private List<InstructionWrap> createInstructions(byte port) {
        List<InstructionWrap> instructions = new ArrayList<InstructionWrap>();
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6
                , mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.B5,
                mBuzzerTempo.ONE), (long) mBuzzerTempo.ONE));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                mBuzzerTempo.ONE), (long) mBuzzerTempo.ONE));

        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G6,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E6,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.B5,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));

        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.F6,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.F6,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E6,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                mBuzzerTempo.ONE), (long) mBuzzerTempo.ONE));
        return instructions;
    }
}
