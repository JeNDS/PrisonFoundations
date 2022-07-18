package JeNDS.JPlugins.PlayerGUI.MinesGUI;

import JeNDS.JPlugins.PlayerGUI.PFGUI;
import JeNDS.JPlugins.Files.MineFile;
import JeNDS.JPlugins.Main.PF;
import JeNDS.JPlugins.Objects.MineObjects.Mine;
import JeNDS.JPlugins.PlayerGUI.MinesGUI.MineOptions.MineOptionsGUI;
import JeNDS.JPlugins.Static.Catch;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

import static JeNDS.JPlugins.Static.Presets.*;

public class MGUI_1 extends PFGUI {


    public MGUI_1() {
        addMinesRunning();
        setMenuAndInterface(SecondaryColor + "Mines Menu", 54, InventoryType.CHEST, true, fillItem(), PF.getInstance());
    }

    private void addMinesRunning() {
        if (Catch.RunningMines.size() <= 53) {
            for (int i = 0; i < Catch.RunningMines.size(); i++) {
                Mine mine = Catch.RunningMines.get(i);
                if (!mine.getBlockTypes().isEmpty()) {
                    Material material = null;
                    MineFile mineFile = new MineFile(mine.getName());
                    HashMap<Material, Integer> materialIntegerHashMap = mineFile.getBlockTypes();
                    Integer lastPercentage = 0;
                    for (Material material1 : materialIntegerHashMap.keySet()) {
                        if (materialIntegerHashMap.get(material1) > lastPercentage) {
                            material = material1;
                        }
                    }
                    assert material != null;
                    ItemStack itemStack = new ItemStack(material);
                    ItemMeta meta = itemStack.getItemMeta();
                    meta.setDisplayName(MainColor + mine.getName());
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ThirdColor + "Click here to");
                    lore.add(ThirdColor + "Open Menu");
                    meta.setLore(lore);
                    itemStack.setItemMeta(meta);
                    addItem(itemStack, i);
                }
            }
        } else {

        }
    }

    @Override
    protected boolean rightClickEvents(ItemStack itemStack, Player player) {
        return false;
    }

    @Override
    protected boolean leftClickEvents(ItemStack itemStack, Player player) {
        for(Mine mine : Catch.RunningMines){
            if(hasSameName(itemStack,mine.getName())){
                MineOptionsGUI mineOptionsGUI = new MineOptionsGUI(this,mine);
                player.openInventory(mineOptionsGUI.getMenu());
            }
        }
        return false;
    }
}
