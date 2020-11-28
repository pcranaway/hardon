package top.retarders.hardon.event.warzone.handler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.function.Consumer;

public class DeathHandler implements Consumer<PlayerDeathEvent> {

    @Override
    public void accept(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Player killer = (Player) ((EntityDamageByEntityEvent) (player.getLastDamageCause())).getDamager();

        if(killer != null) {
            event.setDeathMessage(player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.WHITE + killer.getName());
        } else {
            event.setDeathMessage(player.getName() + ChatColor.GRAY + " died");
        }


        event.setDroppedExp(0);
        event.getDrops().clear();
    }

}
