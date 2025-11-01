package yatpas;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPACommands implements CommandExecutor {

    private final TPAManager manager;

    public TPACommands(TPAManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Solo i giocatori possono usare questo comando.");
            return true;
        }

        if (label.equalsIgnoreCase("tpa") || label.equalsIgnoreCase("tpahere")) {
            if (args.length == 0) {
                player.sendMessage(manager.getMessage("messages.prefix") + cmd.getUsage());
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null || !target.isOnline()) {
                manager.sendMessage(player, "messages.error-target-offline", "%target_name%", args[0]);
                return true;
            }

            if (player.equals(target)) {
                manager.sendMessage(player, "messages.error-self-tpa");
                return true;
            }

            if (manager.isOnCooldown(player)) {
                long cooldown = manager.getPlugin().getConfig().getLong("tpa-cooldown-seconds");
                manager.sendMessage(player, "messages.error-cooldown", "%seconds%", String.valueOf(cooldown));
                return true;
            }

            boolean isTpaHere = label.equalsIgnoreCase("tpahere");
            manager.addRequest(player, target, isTpaHere);
            return true;
        }

        if (label.equalsIgnoreCase("tpaccept") || label.equalsIgnoreCase("tpdeny")) {
            TPARequest request = manager.getPendingRequest(player);

            if (request == null) {
                manager.sendMessage(player, "messages.error-no-pending-request");
                return true;
            }

            Player senderPlayer = Bukkit.getPlayer(request.sender);
            if (senderPlayer == null || !senderPlayer.isOnline()) {
                manager.sendMessage(player, "messages.error-request-expired");
                manager.removeRequest(player.getUniqueId());
                return true;
            }

            if (label.equalsIgnoreCase("tpaccept")) {
                manager.sendMessage(player, "messages.accepted-target", "%sender_name%", senderPlayer.getName());
                manager.sendMessage(senderPlayer, "messages.accepted-sender", "%target_name%", player.getName());

                Player playerToTeleport;
                Player destination;

                if (request.isTpaHere) {
                    playerToTeleport = player;
                    destination = senderPlayer;
                } else {
                    playerToTeleport = senderPlayer;
                    destination = player;
                }

                manager.startWarmup(playerToTeleport, destination);
            } else {
                manager.sendMessage(player, "messages.denied-target", "%sender_name%", senderPlayer.getName());
                manager.sendMessage(senderPlayer, "messages.denied-sender", "%target_name%", player.getName());
            }

            manager.removeRequest(player.getUniqueId());
            return true;
        }

        if (label.equalsIgnoreCase("tpacancel")) {
            TPARequest requestToCancel = manager.getPendingRequests().values().stream()
                    .filter(req -> req.sender.equals(player.getUniqueId()))
                    .findFirst()
                    .orElse(null);

            if (requestToCancel == null) {
                manager.sendMessage(player, "messages.error-no-pending-request");
                return true;
            }

            manager.removeRequest(requestToCancel.target);

            Player target = Bukkit.getPlayer(requestToCancel.target);
            if (target != null) {
                manager.sendMessage(target, "messages.cancelled-target", "%sender_name%", player.getName());
            }
            manager.sendMessage(player, "messages.cancelled-success");
            return true;
        }

        return false;
    }
}