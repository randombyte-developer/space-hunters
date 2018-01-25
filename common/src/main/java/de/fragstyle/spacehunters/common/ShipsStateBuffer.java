package de.fragstyle.spacehunters.common;

import de.fragstyle.spacehunters.common.packets.server.ShipPacketList;

import java.util.HashMap;
import java.util.Map;

public class ShipsStateBuffer {

    private final Map<Integer, ShipPacketList> states = new HashMap<>();
    private int currentState = 1;

    public ShipsStateBuffer() {
    }

    public void addState(ShipPacketList shipsStates) {
        states.put(getNextState(), shipsStates);
    }

    public int getCurrentStateIndex() {
        return currentState;
    }

    public ShipPacketList getCurrentState() {
        return states.get(getCurrentStateIndex());
    }

    private int getNextState() {
        if (currentState >= 10) {
            currentState = 0;
        } else {
            currentState++;
        }

        return currentState;
    }
}
