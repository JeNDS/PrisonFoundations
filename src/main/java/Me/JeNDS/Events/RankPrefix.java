package Me.JeNDS.Events;


import Me.JeNDS.Objects.Rank;
import Me.JeNDS.Static.Presets;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class RankPrefix implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void changeChat(AsyncPlayerChatEvent event) {
        if (Rank.GetPlayerRank(event.getPlayer()) != null) {
            Rank rank = Rank.GetPlayerRank(event.getPlayer());
            if (rank.getPrefix() != null) {
                event.setFormat(Presets.ColorReplacer(rank.getPrefix()) +" "+ event.getFormat());
            }
        }
    }
}
