package me.github.lilyvxv.examplequests.impl;

import me.github.lilyvxv.quests.api.Quest;
import me.github.lilyvxv.quests.structs.QuestInfo;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

import static me.github.lilyvxv.examplequests.ExampleQuests.*;

public class ExampleBreakingQuest implements Quest, Listener {

    private final QuestInfo questInfo;

    public ExampleBreakingQuest() {
        List<Component> loreLines = new ArrayList<>();
        loreLines.add(miniMessage.deserialize("<color:#9c63ff>This quest requires you to break <color:#bfbfbf>100 blocks</color>!</color>"));
        loreLines.add(miniMessage.deserialize("<color:#bfbfbf>Reward:</color> <color:#00ff51>$100</color>"));
        loreLines.add(miniMessage.deserialize(""));

        this.questInfo = new QuestInfo(
                "example_breaking_quest",
                "Example Breaking Quest",
                0.0,
                miniMessage.deserialize("<color:#f266ff>Example Breaking Quest</color>"),
                Material.DIAMOND_PICKAXE,
                loreLines,
                this
        );

    }

    @Override
    public void register() {
        questsAPI.registerQuest(this.questInfo);
    }

    @Override
    public void issueReward(Player player) {
        questsAPI.sendPlayerMessage(player, miniMessage.deserialize("<color:#4255ff>You have completed the Example Breaking Quest!</color>"));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        boolean playerHasQuestEnabled = questsAPI.playerHasQuestEnabled(player, this.questInfo);
        if (playerHasQuestEnabled) {
            questsAPI.updateQuestProgress(player, this.questInfo, 1);

        }
    }
}
