package me.github.lilyvxv.examplequests;

import me.github.lilyvxv.examplequests.impl.ExampleBreakingQuest;
import me.github.lilyvxv.examplequests.impl.ExampleCraftingQuest;
import me.github.lilyvxv.quests.Quests;
import me.github.lilyvxv.quests.api.QuestsAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ExampleQuests extends JavaPlugin {

    public static JavaPlugin plugin;
    public static Logger logger;
    public static QuestsAPI questsAPI;
    public static MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        logger = plugin.getLogger();

        questsAPI = Quests.QUESTS_API;
        questsAPI.addQuest(new ExampleBreakingQuest());
        questsAPI.addQuest(new ExampleCraftingQuest());

        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new ExampleBreakingQuest(), plugin);
        pluginManager.registerEvents(new ExampleCraftingQuest(), plugin);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
