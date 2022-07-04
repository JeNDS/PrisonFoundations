package Me.JeNDS.PlayerGUI.MinesGUI.MineOptions.MineBlockTypes;

import Me.JeNDS.Main.PF;
import Me.JeNDS.Objects.MineObjects.BlockType;
import Me.JeNDS.Objects.MineObjects.Mine;
import Me.JeNDS.PlayerGUI.PFGUI;
import Me.JeNDS.Static.Catch;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MineResetTypeOptionsMenu extends PFGUI {

    private final MineResetTypeMenu lastMenu;
    private final Mine mine;
    private  final BlockType blockType;

    public MineResetTypeOptionsMenu(MineResetTypeMenu lastMenu, Mine mine,BlockType blockType){
        this.lastMenu = lastMenu;
        this.mine = mine;
        this.blockType = blockType;

        addItems(Material.EMERALD_BLOCK, name + "Add 5%", null, 12, this);
        addItems(Material.EMERALD_BLOCK, name + "Add 10%", null, 21, this);
        addItems(Material.EMERALD_BLOCK, name + "Add 25%", null, 30, this);
        addItems(Material.EMERALD_BLOCK, name + "Add 50%", null, 39, this);
        addItems(Material.REDSTONE_BLOCK, name + "Remove 5%", null, 14, this);
        addItems(Material.REDSTONE_BLOCK, name + "Remove 10%", null, 23, this);
        addItems(Material.REDSTONE_BLOCK, name + "Remove 25%", null, 32, this);
        addItems(Material.REDSTONE_BLOCK, name + "Remove 50%", null, 41, this);

        addItems(Material.REDSTONE_BLOCK,name+"Delete",null,49,this);
        addItems(Material.REDSTONE_BLOCK,name+"Back",null,45,this);
        setMenuAndInterface(title1 + mine.getName() + " Reset Type Menu "+blockType.getMaterial().name()+" "+blockType.getPercentage()+ "%", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
    }


    @Override
    protected boolean rightClickEvents(ItemStack itemStack, Player player) {
        return false;
    }

    @Override
    protected boolean leftClickEvents(ItemStack itemStack, Player player) {
        if(itemAndSlot.get(45).isSimilar(itemStack)){
            MineResetTypeMenu mineResetTypeMenu = new MineResetTypeMenu(lastMenu.getLastMenu(),mine);
            player.openInventory(mineResetTypeMenu.getMenu());
            return true;
        }
        if(itemAndSlot.get(49).isSimilar(itemStack)){
            ArrayList<BlockType> blockTypes = new ArrayList<>(mine.getBlockTypes());
            blockTypes.remove(blockType);
            mine.updateBlockTypes(blockTypes);
            MineResetTypeMenu mineResetTypeMenu1 = new MineResetTypeMenu(lastMenu.getLastMenu(),mine);
            player.openInventory(mineResetTypeMenu1.getMenu());
        }
        if (itemAndSlot.get(12).isSimilar(itemStack)) {
            modifyMenuPercentage(player, +5);
        }
        if (itemAndSlot.get(21).isSimilar(itemStack)) {
            modifyMenuPercentage(player, +10);
        }
        if (itemAndSlot.get(30).isSimilar(itemStack)) {
            modifyMenuPercentage(player, +25);
        }
        if (itemAndSlot.get(39).isSimilar(itemStack)) {
            modifyMenuPercentage(player, +50);
        }
        if (itemAndSlot.get(14).isSimilar(itemStack)) {
            modifyMenuPercentage(player, -5);
        }
        if (itemAndSlot.get(23).isSimilar(itemStack)) {
            modifyMenuPercentage(player, -10);
        }
        if (itemAndSlot.get(32).isSimilar(itemStack)) {
            modifyMenuPercentage(player, -25);
        }
        if (itemAndSlot.get(41).isSimilar(itemStack)) {
            modifyMenuPercentage(player, -50);
        }
        return false;
    }
    private void modifyMenuPercentage(Player player, int percentage) {
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine m : Catch.RunningMines) {
                if (m.getName().contains(m.getName())) {
                    if (blockType.getPercentage() + percentage <= 95 &&blockType.getPercentage() + percentage >= 5) {
                        resetPercentage(blockType.getPercentage() + percentage, mine, player);
                    } else {
                        if (blockType.getPercentage()+ percentage > 95) {
                            resetPercentage(95, mine, player);
                        }
                        if (blockType.getPercentage() + percentage < 5) {
                            resetPercentage(5, mine, player);
                        }
                    }
                }
            }
        }
    }
    private void resetPercentage(int percentage, Mine mine, Player player) {
        ArrayList<BlockType> blockTypes = mine.getBlockTypes();
        blockTypes.remove(blockType);
        blockType.setPercentage(percentage);
        blockTypes.add(blockType);
        mine.updateBlockTypes(blockTypes);
        MineResetTypeOptionsMenu mineResetTypeOptionsMenu = new MineResetTypeOptionsMenu(lastMenu,mine,blockType);
        player.openInventory(mineResetTypeOptionsMenu.getMenu());
    }

    public Mine getMine() {
        return mine;
    }
}
