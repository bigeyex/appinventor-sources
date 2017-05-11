package com.makeblock.appinventor.brandnew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hupihuai on 17/3/30.
 * 圣诞歌 歌单
 */

public class ChristmasFactory implements IActionFactory {
    @Override
    public Action createAction(byte port) {
        return new OrderedAction(RJ25ActionFactory.ACTION_BUZZER, createInstructions(port), null, null);
    }

    private List<InstructionWrap> createInstructions(byte port) {

        BuzzerTempo buzzerTempo = new BuzzerTempo(1200);
        final List<InstructionWrap> instructions = new ArrayList<InstructionWrap>();

        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.B5,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E6,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.B5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E6,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.F6,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E6,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));

        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.B5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE_OF_TWO), (long) buzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.B5,
                buzzerTempo.ONE_OF_TWO), (long) buzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.B5,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.B5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                buzzerTempo.ONE_OF_TWO), (long) buzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));

        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.B5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                buzzerTempo.ONE_OF_TWO), (long) buzzerTempo.ONE_OF_TWO));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.E6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.G5,
                buzzerTempo.ONE_OF_EIGHT), (long) buzzerTempo.ONE_OF_EIGHT));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.A5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.D6,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.B5,
                buzzerTempo.ONE_OF_FOUR), (long) buzzerTempo.ONE_OF_FOUR));
        instructions.add(new InstructionMusicWrap(RJ25InstructionFactory.createBuzzerInstruction(port, BuzzerTone.C6,
                buzzerTempo.ONE), (long) buzzerTempo.ONE));
        return instructions;
    }
}
