package JeNDS.Plugins.PrisonFundations.Mines.MinesGUI.MineOptions.MineBlockTypes;

import JeNDS.Plugins.PrisonFundations.Main;
import JeNDS.Plugins.PrisonFundations.Managers.PFGUI;
import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.BlockType;
import JeNDS.Plugins.PrisonFundations.Mines.MineObjects.Mine;
import JeNDS.Plugins.PrisonFundations.Static.Catch;
import JeNDS.Plugins.PrisonFundations.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MineResetTypeMenu extends PFGUI {

    private final PFGUI lastMenu;
    private final Mine mine;

    public MineResetTypeMenu(PFGUI lastMenu, Mine mine) {
        this.lastMenu = lastMenu;
        this.mine = mine;
        addBlockTypes();
        addItems(Material.EMERALD_BLOCK, Presets.MainColor + "Add", null, 49, this);
        addItems(Material.REDSTONE_BLOCK, Presets.MainColor + "Back", null, 45, this);
        setMenuAndInterface(Presets.SecondaryColor + mine.getName() + " Reset Type Menu " + mine.getTotalPercentage() + "%", 54, InventoryType.CHEST, true, fillItem(), Main.getInstance());
    }


    @Override
    protected boolean rightClickEvents(ItemStack itemStack, Player player) {
        return false;
    }

    @Override
    protected boolean leftClickEvents(ItemStack itemStack, Player player) {
        if (itemAndSlot.get(45).isSimilar(itemStack)) {
            player.openInventory(lastMenu.getMenu());
            return true;
        }
        if (itemAndSlot.get(49).isSimilar(itemStack)) {
            if (mine.getBlockTypes().size() < 45) {
                player.sendMessage(Presets.SecondaryColor + " Select a Block in your inventory to add");
                Catch.selectingBlockType.put(player, this);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        Catch.selectingBlockType.remove(player);
                    }
                }, 200L);
                return true;
            }
        }
        for (ItemStack itemStack1 : itemAndSlot.values()) {
            if (itemStack1.isSimilar(itemStack)) {
                for (BlockType blockType : mine.getBlockTypes()) {
                    if (itemStack1.getType().equals(blockType.getMaterial())) {
                        MineResetTypeOptionsMenu mineResetTypeOptionsMenu = new MineResetTypeOptionsMenu(this, mine, blockType);
                        player.openInventory(mineResetTypeOptionsMenu.getMenu());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void addBlockTypes() {
        int i = 1;
        for (BlockType blockType : mine.getBlockTypes()) {
            if (i < 45) {
                ArrayList<String> lore = new ArrayList<>();
                lore.add(Presets.ThirdColor + "Click to");
                lore.add(Presets.ThirdColor + "Modify");
                lore.add(Presets.ThirdColor + blockType.getPercentage() + "%");
                addItems(blockType.getMaterial(), Presets.MainColor + blockType.getMaterial().name(), lore, i - 1, this);
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
