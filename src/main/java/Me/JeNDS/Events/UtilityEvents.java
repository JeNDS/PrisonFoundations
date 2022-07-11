package Me.JeNDS.Events;


import JeNDS.Plugins.PluginAPI.Other.JDItem;
import Me.JeNDS.Objects.Rank;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import Me.JeNDS.Utilities.AutoBlock;
import Me.JeNDS.Utilities.AutoSmelt;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class UtilityEvents implements Listener {

    @EventHandler
    private void placeEvent(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(event.getItemInHand().isSimilar(JDItem.CustomItemStack(Material.FURNACE,Presets.DefaultColor+"Auto Smelter",null))){
            if(!Catch.autoSmelter.contains(event.getBlockPlaced().getLocation())){
                Catch.autoSmelter.add(event.getBlockPlaced().getLocation());
            }
            player.sendMessage(Presets.DefaultColor + "You have placed an auto furnace");
        }
        if(event.getItemInHand().isSimilar(JDItem.CustomItemStack(Material.CRAFTING_TABLE,Presets.DefaultColor+"Auto Blocker",null))){
            if(!Catch.autoBlocking.contains(event.getBlockPlaced().getLocation())){
                Catch.autoBlocking.add(event.getBlockPlaced().getLocation());
            }
            player.sendMessage(Presets.DefaultColor + "You have placed an auto blocker");
        }
    }

    @EventHandler
    private void brakeEvent(BlockBreakEvent event){

    }

    @EventHandler
    private void interactEvent(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(Catch.autoBlocking.contains(event.getClickedBlock().getLocation())){
                if(event.getPlayer().hasPermission("PF.AutoBlocking")){
                    new AutoBlock(event.getPlayer());
                    event.setCancelled(true);
                }
            }
            if(Catch.autoSmelter.contains(event.getClickedBlock().getLocation())){
                if(event.getPlayer().hasPermission("PF.AutoSmelter")){
                    new AutoSmelt(event.getPlayer());
                    event.setCancelled(true);
                }
            }
        }
    }
}
