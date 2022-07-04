package Me.JeNDS.Utilities;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class AutoEXP {

    public AutoEXP(Player player, BlockBreakEvent event){
        if (event.getExpToDrop() > 0) {
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        player.giveExp(event.getExpToDrop());
        event.setExpToDrop(0);
        }
    }
}
