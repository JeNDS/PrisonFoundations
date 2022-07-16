package JeNDS.JPlugins.PlayerGUI.MinesGUI.MineOptions.MineBlockTypes;

import JeNDS.JPlugins.Main.PF;
import JeNDS.JPlugins.Objects.MineObjects.BlockType;
import JeNDS.JPlugins.Objects.MineObjects.Mine;
import JeNDS.JPlugins.PlayerGUI.PFGUI;
import JeNDS.JPlugins.Static.Catch;
import JeNDS.JPlugins.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MineResetTypeMenu extends PFGUI {

    private final PFGUI lastMenu;
    private final Mine mine;

    public MineResetTypeMenu(PFGUI lastMenu, Mine mine){
        this.lastMenu = lastMenu;
        this.mine = mine;
        addBlockTypes();
        addItems(Material.EMERALD_BLOCK, Presets.DefaultColor +"Add",null,49,this);
        addItems(Material.REDSTONE_BLOCK, Presets.DefaultColor +"Back",null,45,this);
        setMenuAndInterface(Presets.StandOutColor + mine.getName() + " Reset Type Menu "+mine.getTotalPercentage()+ "%", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
    }


    @Override
    protected boolean rightClickEvents(ItemStack itemStack, Player player) {
        return false;
    }

    @Override
    protected boolean leftClickEvents(ItemStack itemStack, Player player) {
        if(itemAndSlot.get(45).isSimilar(itemStack)){
            player.openInventory(lastMenu.getMenu());
            return true;
        }
        if(itemAndSlot.get(49).isSimilar(itemStack)){
            if(mine.getBlockTypes().size()<45){
                player.sendMessage(Presets.StandOutColor + " Select a Block in your inventory to add");
                Catch.selectingBlockType.put(player, this);
                Bukkit.getScheduler().scheduleSyncDelayedTask(PF.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        Catch.selectingBlockType.remove(player);
                    }
                },200L);
                return true;
            }
        }
        for(ItemStack itemStack1 : itemAndSlot.values()){
            if(itemStack1.isSimilar(itemStack)){
                for(BlockType blockType : mine.getBlockTypes()){
                    if(itemStack1.getType().equals(blockType.getMaterial())){
                        MineResetTypeOptionsMenu mineResetTypeOptionsMenu = new MineResetTypeOptionsMenu(this,mine,blockType);
                        player.openInventory(mineResetTypeOptionsMenu.getMenu());
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void addBlockTypes(){
        int i = 1;
        for(BlockType blockType : mine.getBlockTypes()){
            if(i<45){
                ArrayList<String> lore = new ArrayList<>();
                lore.add(Presets.StandOutColor2 +"Click to");
                lore.add(Presets.StandOutColor2 +"Modify");
                lore.add(Presets.StandOutColor2 + ""+ blockType.getPercentage()+"%");
                addItems(blockType.getMaterial(), Presets.DefaultColor +blockType.getMaterial().name(),lore,i-1,this);
            }
            i++;
        }
    }

    public PFGUI getLastMenu() {
        return lastMenu;
    }

    public Mine getMine() {
        return mine;
    }
}