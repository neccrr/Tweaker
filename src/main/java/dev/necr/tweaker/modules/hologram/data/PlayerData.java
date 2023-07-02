package dev.necr.tweaker.modules.hologram.data;

import java.util.List;

public class PlayerData {
    private String playerName;
    private String playerUUID;
    private List<String> holograms;

    public PlayerData(String playerName, String playerUUID, List<String> holograms) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.holograms = holograms;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public List<String> getHolograms() {
        return holograms;
    }

    public void setHolograms(List<String> holograms) {
        this.holograms = holograms;
    }
}

