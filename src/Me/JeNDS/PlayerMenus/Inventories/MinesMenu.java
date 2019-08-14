package Me.JeNDS.PlayerMenus.Inventories;

import Me.JeNDS.Files.MineFile;
import Me.JeNDS.Objects.Mine;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MinesMenu {

    public MinesMenu() {
    }

    public static ArrayList<Inventory> GetMainMenuPages() {
        ArrayList<Inventory> inventories = new ArrayList<>();
        if (!Catch.RunningMines.isEmpty()) {
            double pages = 1;
            if (pages >45) {
                 pages = Math.ceil(Catch.RunningMines.size() / 45);
            }
            ArrayList<Mine> usedMines = new ArrayList<>();
            for (int i = 0; i < pages; i++) {
                Inventory minemenu = Bukkit.createInventory(null, 54, Presets.StandOutColor + "Mines List");
                Integer slots = 0;
                for (Mine mine : Catch.RunningMines) {
                    if (!usedMines.contains(mine)) {
                        if (slots <= 54) {
                            Material material = null;
                            MineFile mineFile = new MineFile(mine.getName());
                            HashMap<Material, Integer> materialIntegerHashMap = mineFile.getBlockTypes();
                            Integer lastPercentage = 0;
                            for (Material material1 : materialIntegerHashMap.keySet()) {
                                if (materialIntegerHashMap.get(material1) > lastPercentage) {
                                    material = material1;
                                }

                            }
                            ItemStack menuItem = new ItemStack(material, 1);
                            ItemMeta meta = menuItem.getItemMeta();
                            meta.setDisplayName(Presets.DefaultColor + mine.getName());
                            List<String> lore = new ArrayList<>();
                            lore.add(Presets.DefaultColor + "Click To");
                            lore.add(Presets.DefaultColor + "Open Mine Menu");
                            meta.setLore(lore);
                            menuItem.setItemMeta(meta);
                            minemenu.addItem(menuItem);
                            slots++;
                            usedMines.add(mine);

                        }
                    }
                }
                inventories.add(FillEmptySpace(minemenu));
            }
        }
        return inventories;
    }

    public static Inventory MainMenuPreset(String Menu) {
        Inventory inventory = Bukkit.createInventory(null, 54, Presets.DefaultColor + Menu);
        ItemStack reset = Presets.ResetMineButton;
        ItemStack delete = Presets.DeleteButton;
        ItemStack blockTypes = Presets.BlockTypesButton;
        ItemStack resetType = Presets.ResetManageButton;

        inventory.setItem(28, reset);
        inventory.setItem(30, delete);
        inventory.setItem(32, blockTypes);
        inventory.setItem(34, resetType);
        inventory.setItem(45, Presets.BackButton);
        return FillEmptySpace(inventory);


    }

    public static Inventory BlockTypesMenuPreset(String Menu) {
        Inventory inventory = Bukkit.createInventory(null, 54, Presets.DefaultColor + Menu);
        inventory.setItem(45, Presets.BackButton);
        inventory.setItem(49,Presets.AddButton);
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine mine : Catch.RunningMines) {
                if (mine.getName() == Menu) {
                    MineFile mineFile = new MineFile(mine.getName());
                    if (!mineFile.getBlockTypes().isEmpty()) {
                        for (Material material : mineFile.getBlockTypes().keySet()) {
                            if (InventorySpaceLeft(inventory)) {
                                inventory.addItem(AddItemWithLore(material));
                            }
                        }
                    }

                }
            }
        }
        return FillEmptySpace(inventory);

    }

    public static Inventory BlockTypeMenu(String Menu, String Material, Integer percentage) {
        Inventory inventory = Bukkit.createInventory(null, 54, Presets.DefaultColor + Menu + " " + Material + " " + percentage);

        ItemStack add5Percent = PercentageSet(true, 5);
        inventory.setItem(3, add5Percent);
        ItemStack add10Percent = PercentageSet(true, 10);
        inventory.setItem(12, add10Percent);
        ItemStack add25Percent = PercentageSet(true, 25);
        inventory.setItem(21, add25Percent);
        ItemStack add50Percent = PercentageSet(true, 50);
        inventory.setItem(30, add50Percent);
        ItemStack remove5Percent = PercentageSet(false, 5);
        inventory.setItem(5, remove5Percent);
        ItemStack remove25Percent = PercentageSet(false, 25);
        inventory.setItem(23, remove25Percent);
        ItemStack remove10Percent = PercentageSet(false, 10);
        inventory.setItem(14, remove10Percent);
        ItemStack remove50Percent = PercentageSet(false, 50);
        inventory.setItem(32, remove50Percent);

        inventory.setItem(45, Presets.BackButton);
        inventory.setItem(53, Presets.ConfimButton);
        inventory.setItem(49, Presets.DeleteButton);
        return FillEmptySpace(inventory);
    }

    public static Inventory ResetPercentageMenu(String Menu, Integer percentage) {
        Inventory inventory = Bukkit.createInventory(null, 54, Presets.DefaultColor + Menu + " " + percentage);


        inventory.setItem(45, Presets.BackButton);
        inventory.setItem(53, Presets.ConfimButton);

        ItemStack add5Percent = PercentageSet(true, 5);
        inventory.setItem(3, add5Percent);
        ItemStack add10Percent = PercentageSet(true, 10);
        inventory.setItem(12, add10Percent);
        ItemStack add25Percent = PercentageSet(true, 25);
        inventory.setItem(21, add25Percent);
        ItemStack add50Percent = PercentageSet(true, 50);
        inventory.setItem(30, add50Percent);
        ItemStack remove5Percent = PercentageSet(false, 5);
        inventory.setItem(5, remove5Percent);
        ItemStack remove25Percent = PercentageSet(false, 25);
        inventory.setItem(23, remove25Percent);
        ItemStack remove10Percent = PercentageSet(false, 10);
        inventory.setItem(14, remove10Percent);
        ItemStack remove50Percent = PercentageSet(false, 50);
        inventory.setItem(32, remove50Percent);

        return FillEmptySpace(inventory);

    }

    public static Inventory TimeResetMenu(String Menu, double time) {
        DecimalFormat format = new DecimalFormat("0.00");
        Inventory inventory = Bukkit.createInventory(null, 54, Presets.DefaultColor +" " +Menu+ " " + format.format(time));

        inventory.setItem(45, Presets.BackButton);
        inventory.setItem(53, Presets.ConfimButton);

        ItemStack add5time = TimedSet(true, 5);
        inventory.setItem(3, add5time);
        ItemStack add10time = TimedSet(true, 10);
        inventory.setItem(12, add10time);
        ItemStack add25time = TimedSet(true, 25);
        inventory.setItem(21, add25time);
        ItemStack add50time = TimedSet(true, 50);
        inventory.setItem(30, add50time);
        ItemStack remove5time = TimedSet(false, 5);
        inventory.setItem(5, remove5time);
        ItemStack remove25time = TimedSet(false, 25);
        inventory.setItem(23, remove25time);
        ItemStack remove10time = TimedSet(false, 10);
        inventory.setItem(14, remove10time);
        ItemStack remove50time = TimedSet(false, 50);
        inventory.setItem(32, remove50time);

        return FillEmptySpace(inventory);
    }

    private static ItemStack TimedSet(boolean AorD, int time) {
        ItemStack Add = new ItemStack(Presets.AddButton.getType(),1);
        Add.setItemMeta(Presets.AddButton.getItemMeta());
        ItemStack Remove = new ItemStack(Presets.RemoveButton.getType(),1);
        Remove.setItemMeta(Presets.RemoveButton.getItemMeta());
        ItemStack itemStack;
        if (AorD) {
            itemStack = Add;
        } else {
            itemStack = Remove;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Presets.DefaultColor + " " + time + " Minutes");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private static ItemStack PercentageSet(boolean AorD, int percent) {
        ItemStack itemStack;
        ItemStack Add = new ItemStack(Presets.AddButton.getType(),1);
        Add.setItemMeta(Presets.AddButton.getItemMeta());
        ItemStack Remove = new ItemStack(Presets.RemoveButton.getType(),1);
        Remove.setItemMeta(Presets.RemoveButton.getItemMeta());
        if (AorD) {
            itemStack = Add;
        } else {
            itemStack = Remove;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Presets.DefaultColor + " " + percent + " Percent");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static Inventory ChangeMenu(String Menu) {
        Inventory inventory = Bukkit.createInventory(null, 54, Presets.DefaultColor + Menu);
        inventory.setItem(21, Presets.PercentResetButton);
        inventory.setItem(23, Presets.TimeResetButton);
        inventory.setItem(45, Presets.BackButton);
        return FillEmptySpace(inventory);

    }

    private static ItemStack AddItemWithLore(Material Material) {
        return Presets.itemMaker(Material,null,Presets.ClickTo+"Manage");

    }

    private static boolean InventorySpaceLeft(Inventory inventory) {
        for(ItemStack item : inventory.getContents()){
            if(item == null){
                return true;
            }
        }
        return false;
    }

    private static Inventory FillEmptySpace(Inventory inventory) {
        if (inventory.getContents().length > 1) {
            for (int i = 0; i < inventory.getContents().length; i++) {
                if (inventory.getItem(i) == null) {
                    inventory.setItem(i, Presets.EmtpySpace);
                }
            }
        }
        return inventory;
    }

    private void loadDefaultItems() {

    }


}
