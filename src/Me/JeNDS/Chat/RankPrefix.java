package Me.JeNDS.Chat;


import Me.JeNDS.Files.RankFile;
import Me.JeNDS.Static.Presets;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class RankPrefix implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void changeChat(AsyncPlayerChatEvent event) {
        if (RankFile.FindPlayerRank(event.getPlayer()) != null) {
            if(Presets.ColorReplacer(RankFile.getPrefix(RankFile.FindPlayerRank(event.getPlayer()))).length()>=1){
                event.setFormat(Presets.ColorReplacer(RankFile.getPrefix(RankFile.FindPlayerRank(event.getPlayer()))) + ChatColor.RESET + " " + event.getFormat());
            }
        }
    }
}
