package com.makeblock.appinventor.brandnew;

/**
 * Created by xuexin on 2017/4/1.
 */

public enum BuzzerTone {
    C2(65),
    D2(73),
    E2(82),
    F2(87),
    G2(98),
    A2(110),
    B2(123),
    C3(131),
    D3(147),
    E3(165),
    F3(175),
    G3(196),
    A3(220),
    B3(247),
    C4(262),
    D4(294),
    E4(330),
    F4(349),
    G4(392),
    A4(440),
    B4(494),
    C5(523),
    D5(587),
    E5(658),
    F5(698),
    G5(784),
    A5(880),
    B5(988),
    C6(1047),
    D6(1175),
    E6(1319),
    F6(1397),
    G6(1568),
    A6(1760),
    B6(1976),
    C7(2093),
    D7(2349),
    E7(2637),
    F7(2794),
    G7(3136),
    A7(3520),
    B7(3951),
    C8(4186),
    L1(555),
    L2(623),
    L3(741),
    L4(832),
    L5(934);

    public int frequency;

    BuzzerTone(int frequency) {
        this.frequency = frequency;
    }
}

