package Me.JeNDS.Events;

import Me.JeNDS.Objects.MineObjects.Mine;
import Me.JeNDS.Objects.MineObjects.SingType;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class SignEvents extends EventManager {

    @EventHandler
    public void placeSigns(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getState() instanceof Sign) {
            if (event.getItemInHand().hasItemMeta()) {
                if (event.getItemInHand().getItemMeta().hasDisplayName()) {
                    placeMineSign(event, "Mine Percentage", true);
                    placeMineSign(event, "Time Until Reset", false);
                }
            }
        }
    }
    @EventHandler
    public void singBrake(BlockBreakEvent event) {
        if(event.getBlock().getState() instanceof Sign){
            if(event.getPlayer().isOp()){
                for(Mine mine : Catch.RunningMines){
                    if(mine.deleteMineSing(event.getBlock().getLocation())){
                        return;
                    }
                }
            }
        }
    }

    private void placeMineSign(BlockPlaceEvent event, String name, boolean percentage) {
        ItemStack itemStack = event.getItemInHand();
        for (Mine mine : Catch.RunningMines) {
            if (itemStack.getItemMeta().getDisplayName().contains(name) && itemStack.getItemMeta().getDisplayName().contains(mine.getName())) {
                Sign sign = (Sign) event.getBlockPlaced().getState();
                sign.setLine(0, Presets.DefaultColor+""+ mine.getName());
                sign.setLine(1, Presets.StandOutColor+""+name);
                sign.setLine(2, "");
                SingType singType = null;
                if (percentage) {
                    sign.setLine(3,Presets.StandOutColor2+""+ mine.getMinePercentage() + "%");
                    singType = SingType.MinePercentage;
                } else {
                    sign.setLine(3,Presets.StandOutColor2+""+ mine.getTimeBeforeReset() + "M");
                    singType = SingType.MineTimeReset;
                }
                sign.setEditable(false);
                sign.update(true);
                event.getPlayer().getOpenInventory().close();
                event.getPlayer().getInventory().removeItem(event.getItemInHand());
                mine.createMineSign(event.getBlockPlaced().getLocation(),singType);

            }
        }
    }

}
