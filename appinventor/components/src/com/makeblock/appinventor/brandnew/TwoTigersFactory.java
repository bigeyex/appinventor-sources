package com.makeblock.appinventor.brandnew;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by hupihuai on 17/3/31.
 */

public class TwoTigersFactory implements IActionFactory {
    private BuzzerTempo mBuzzerTempo;

    public TwoTigersFactory() {
        this.mBuzzerTempo = new BuzzerTempo(1600);
    }

    @Override
    public Action createAction(byte port) {

        return new OrderedAction(RJ25ActionFactory.ACTION_BUZZER, createInstructions(port), null, null);
    }

    private List<InstructionWrap> createInstructions(byte port) {
        List<InstructionWrap> instructions = new ArrayList<InstructionWrap>();
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.F5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));


        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.F5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_EIGHT), (long) mBuzzerTempo.ONE_OF_EIGHT));

        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                mBuzzerTempo.ONE_OF_EIGHT), (long) mBuzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_EIGHT), (long) mBuzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.F5,
                mBuzzerTempo.ONE_OF_EIGHT), (long) mBuzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_EIGHT), (long) mBuzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                mBuzzerTempo.ONE_OF_EIGHT), (long) mBuzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                mBuzzerTempo.ONE_OF_EIGHT), (long) mBuzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.F5,
                mBuzzerTempo.ONE_OF_EIGHT), (long) mBuzzerTempo.ONE_OF_EIGHT));


        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G4,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C5,
                mBuzzerTempo.ONE_OF_TWO), (long) mBuzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G4,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C5,
                mBuzzerTempo.ONE_OF_FOUR), (long) mBuzzerTempo.ONE_OF_FOUR));

        return instructions;
    }
}
