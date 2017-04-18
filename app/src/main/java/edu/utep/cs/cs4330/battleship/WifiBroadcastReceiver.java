/*
 * @created Mahdokht Afravi on 04/16 U
 */
package edu.utep.cs.cs4330.battleship;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;

public class WifiBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager manager;
    private Channel channel;
    private MainActivity activity;

    public WifiBroadcastReceiver(WifiP2pManager m, Channel c, MainActivity a) {
        super();
        manager = m;
        channel = c;
        activity = a;
    }

    @Override
    public void onReceive(Context c, Intent i) {
        String action = i.getAction();
        WifiP2pManager.PeerListListener peerListListener;
        peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                
            }
        };

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            int state = i.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
                activity.wifiDirectEnabled();
            else activity.wifiDirectDisabled();
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            if ( manager!=null ) {
                manager.requestPeers(channel,peerListListener);
            }
            manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    //todo
                }
                @Override
                public void onFailure(int reasonCode) {
                    //todo
                }
            });
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        }
    }
}