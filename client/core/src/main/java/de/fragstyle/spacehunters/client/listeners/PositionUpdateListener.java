package de.fragstyle.spacehunters.client.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.fragstyle.spacehunters.common.ShipsState;
import de.fragstyle.spacehunters.common.ShipsStateBuffer;
import de.fragstyle.spacehunters.common.packets.server.ShipPacketList;

public class PositionUpdateListener extends Listener {

    private ShipsStateBuffer shipsStateBuffer;


    public PositionUpdateListener(ShipsStateBuffer shipsStateBuffer) {
        this.shipsStateBuffer = shipsStateBuffer;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof ShipPacketList) {
            ShipPacketList shipPacketList = (ShipPacketList) object;
            shipsStateBuffer.addState(shipPacketList);
        }
    }
}
