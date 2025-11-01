package yatpas;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class TPARequest {
    public UUID sender;
    public UUID target;
    public long expirationTime;
    public boolean isTpaHere;

    public TPARequest(UUID sender, UUID target, long expirationTime, boolean isTpaHere) {
        this.sender = sender;
        this.target = target;
        this.expirationTime = expirationTime;
        this.isTpaHere = isTpaHere;
    }
}

public class TPAManager {

    private final YATPAS plugin;
    private final Map<UUID, TPARequest> pendingRequests = new HashMap<>();
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final Map<UUID, BukkitTask> warmupTasks = new HashMap<>();

    public TPAManager(YATPAS plugin) {
        this.plugin = plugin;
        startTimeoutChecker();
    }

    public String getMessage(String path) {
        String msg = plugin.getConfig().getString(path, "§cERRORE: Messaggio non trovato (" + path + ")");
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void sendMessage(Player player, String messageKey, String... replacements) {
        String prefix = getMessage("messages.prefix");
        String message = getMessage(messageKey);

        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 < replacements.length) {
                message = message.replace(replacements[i], replacements[i+1]);
            }
        }

        player.sendMessage(prefix + message);
    }

    public boolean isOnCooldown(Player player) {
        long lastRequest = cooldowns.getOrDefault(player.getUniqueId(), 0L);
        long cooldownTime = plugin.getConfig().getLong("tpa-cooldown-seconds") * 1000;
        return (System.currentTimeMillis() - lastRequest) < cooldownTime;
    }

    public void setCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public void addRequest(Player sender, Player target, boolean isTpaHere) {
        long timeout = plugin.getConfig().getLong("request-timeout-seconds");
        long expirationTime = System.currentTimeMillis() + (timeout * 1000);

        TPARequest request = new TPARequest(sender.getUniqueId(), target.getUniqueId(), expirationTime, isTpaHere);
        pendingRequests.put(target.getUniqueId(), request);

        setCooldown(sender);
        sendClickableRequest(sender, target, timeout, isTpaHere);
    }

    public TPARequest getPendingRequest(Player target) {
        return pendingRequests.get(target.getUniqueId());
    }

    public void removeRequest(UUID targetId) {
        pendingRequests.remove(targetId);
    }

    public void startWarmup(Player playerToTeleport, Player destination) {
        int warmup = plugin.getConfig().getInt("teleport-warmup-seconds");
        if (warmup <= 0) {
            teleportPlayer(playerToTeleport, destination);
            return;
        }

        playerToTeleport.sendMessage(getMessage("messages.prefix") + getMessage("messages.teleport-starting").replace("%seconds%", String.valueOf(warmup)));

        BukkitTask task = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (warmupTasks.containsKey(playerToTeleport.getUniqueId())) {
                teleportPlayer(playerToTeleport, destination);
                warmupTasks.remove(playerToTeleport.getUniqueId());
            }
        }, warmup * 20L);

        warmupTasks.put(playerToTeleport.getUniqueId(), task);
    }

    private void teleportPlayer(Player player, Player destination) {
        if (!player.isOnline() || !destination.isOnline()) return;

        player.teleport(destination.getLocation());
        sendMessage(player, "messages.teleport-success");
    }

    public void cancelWarmup(Player player) {
        BukkitTask task = warmupTasks.remove(player.getUniqueId());
        if (task != null) {
            task.cancel();
        }
    }

    public boolean isWarmingUp(Player player) {
        return warmupTasks.containsKey(player.getUniqueId());
    }

    private void sendClickableRequest(Player sender, Player target, long timeout, boolean isTpaHere) {
        String prefix = getMessage("messages.prefix");
        String requestMsgPath = isTpaHere ? "messages.request-tpahere-received" : "messages.request-received";

        sendMessage(target, requestMsgPath, "%sender_name%", sender.getName());

        TextComponent clickComponent = new TextComponent(getMessage("messages.accept-click"));
        clickComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));

        TextComponent finalComponent = new TextComponent(prefix);
        finalComponent.addExtra(clickComponent);

        target.spigot().sendMessage(finalComponent);

        String denyInstructions = getMessage("messages.deny-instructions").replace("%seconds%", String.valueOf(timeout));
        target.sendMessage(prefix + denyInstructions);

        sendMessage(sender, "messages.request-sent", "%target_name%", target.getName());
    }

    public YATPAS getPlugin() {
        return plugin;
    }

    public Map<UUID, TPARequest> getPendingRequests() {
        return pendingRequests;
    }

    private void startTimeoutChecker() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            long now = System.currentTimeMillis();
            pendingRequests.entrySet().removeIf(entry -> {
                if (entry.getValue().expirationTime < now) {
                    Player target = Bukkit.getPlayer(entry.getKey());
                    Player sender = Bukkit.getPlayer(entry.getValue().sender);

                    if (target != null) {
                        sendMessage(target, "messages.error-request-expired");
                    }
                    if (sender != null) {
                        sendMessage(sender, "messages.error-request-expired");
                    }
                    return true;
                }
                return false;
            });
        }, 20L, 20L);
    }
}