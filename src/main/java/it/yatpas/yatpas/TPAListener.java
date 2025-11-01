package yatpas;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class TPAListener implements Listener {

    private final TPAManager manager;

    public TPAListener(TPAManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (manager.isWarmingUp(player) &&
                manager.getPlugin().getConfig().getBoolean("cancel-on-move") &&
                (event.getFrom().getBlockX() != event.getTo().getBlockX() ||
                        event.getFrom().getBlockY() != event.getTo().getBlockY() ||
                        event.getFrom().getBlockZ() != event.getTo().getBlockZ())) {

            manager.cancelWarmup(player);
            manager.sendMessage(player, "messages.teleport-cancelled-move");
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {

            if (manager.isWarmingUp(player) &&
                    manager.getPlugin().getConfig().getBoolean("cancel-on-damage")) {

                manager.cancelWarmup(player);
                manager.sendMessage(player, "messages.teleport-cancelled-damage");
            }
        }
    }
}