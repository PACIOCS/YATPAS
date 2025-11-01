package yatpas;

import org.bukkit.plugin.java.JavaPlugin;

public final class YATPAS extends JavaPlugin {

    private TPAManager tpaManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        tpaManager = new TPAManager(this);

        TPACommands commandExecutor = new TPACommands(tpaManager);
        getCommand("tpa").setExecutor(commandExecutor);
        getCommand("tpahere").setExecutor(commandExecutor);
        getCommand("tpaccept").setExecutor(commandExecutor);
        getCommand("tpdeny").setExecutor(commandExecutor);
        getCommand("tpacancel").setExecutor(commandExecutor);

        getServer().getPluginManager().registerEvents(new TPAListener(tpaManager), this);

        getLogger().info(tpaManager.getMessage("messages.prefix") + " YATPAS attivato!");
    }

    @Override
    public void onDisable() {
        getLogger().info("YATPAS disattivato.");
    }

    public TPAManager getTPAManager() {
        return tpaManager;
    }
}