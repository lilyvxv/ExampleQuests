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
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static me.github.lilyvxv.examplequests.ExampleQuests.miniMessage;
import static me.github.lilyvxv.examplequests.ExampleQuests.questsAPI;

public class ExampleCraftingQuest implements Quest, Listener {

    private final QuestInfo questInfo;

    public ExampleCraftingQuest() {
        List<Component> loreLines = new ArrayList<>();
        loreLines.add(miniMessage.deserialize("<color:#9c63ff>This quest requires you to craft <color:#bfbfbf>200 items</color>!</color>"));
        loreLines.add(miniMessage.deserialize("<color:#bfbfbf>Reward:</color> <color:#00ff51>$100</color>"));
        loreLines.add(miniMessage.deserialize(""));

        this.questInfo = new QuestInfo(
                "example_crafting_quest",
                "Example Crafting Quest",
                0.0,
                miniMessage.deserialize("<color:#f266ff>Example Crafting Quest</color>"),
                Material.CRAFTING_TABLE,
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
        questsAPI.sendPlayerMessage(player, miniMessage.deserialize("<color:#4255ff>You have completed the Example Crafting Quest!</color>"));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerCraft(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();

        boolean playerHasQuestEnabled = questsAPI.playerHasQuestEnabled(player, this.questInfo);
        if (playerHasQuestEnabled) {
            // https://www.spigotmc.org/threads/craftitemevent-shift-clicking.52463/#post-1710833
            int itemsChecked = 0;
            int possibleCreations = 1;

            if (event.isShiftClick()) {
                for (ItemStack item : event.getInventory().getMatrix()) {
                    if (item != null && !item.getType().equals(Material.AIR)) {
                        if (itemsChecked == 0) {
                            possibleCreations = item.getAmount();
                        } else {
                            possibleCreations = Math.min(possibleCreations, item.getAmount());
                        }
                        itemsChecked++;
                    }
                }
            }

            int amountOfItems = event.getRecipe().getResult().getAmount() * possibleCreations;
            questsAPI.updateQuestProgress(player, this.questInfo, 0.5 * amountOfItems);
        }
    }
}
