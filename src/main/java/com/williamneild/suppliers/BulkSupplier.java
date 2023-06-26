package com.williamneild.suppliers;

import com.google.gson.stream.JsonWriter;
import com.williamneild.utils.Statistics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Supplier;

public class BulkSupplier implements Supplier<String> {

    private final MinecraftServer minecraftServer;

    public BulkSupplier(MinecraftServer minecraftServer) {
        this.minecraftServer = minecraftServer;
    }

    @Override
    public String get() {
        try (StringWriter jsonString = new StringWriter();
             JsonWriter json = new JsonWriter(jsonString)) {

            json.beginObject();

            PlayerManager playerManager = this.minecraftServer.getPlayerManager();

            // list of player names
            json.name("players").beginArray();
            for(ServerPlayerEntity playerEntity: playerManager.getPlayerList()) {
                String playerName = playerEntity.getName().getString();
                UUID playerUuid = playerEntity.getUuid();
                ServerStatHandler playerStatHandler = playerEntity.getStatHandler();

                ArrayList<Statistics.GeneralStatistic> generalStatistics = Statistics.getGeneralStatistics(playerStatHandler);

                // player object
                json.beginObject();

                json.name("name").value(playerName);
                json.name("uuid").value(playerUuid.toString());

                json.name("general").beginObject();
                for (Statistics.GeneralStatistic statistic: generalStatistics) {
                    json.name(statistic.label.getString()).value(statistic.value);
                }
                json.endObject();

                json.endObject();
            }
            json.endArray();

            // number of players
            json.name("count").value(playerManager.getCurrentPlayerCount());

            json.endObject();
            json.flush();

            return jsonString.toString();
        } catch (IOException e) {
            return "Runtime Exception: " + e;
        }
    }
}
