package Me.JeNDS.PlayerMenus.Listeners;

import Me.JeNDS.Files.MineFile;
import Me.JeNDS.Objects.Mine;
import Me.JeNDS.PlayerMenus.Inventories.MinesMenu;
import Me.JeNDS.Static.Catch;
import Me.JeNDS.Static.Presets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class MinesMenuListener implements Listener {


    @EventHandler
    public void onClickEvent(InventoryClickEvent event) {
        boolean found = false;
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem() != null) {
                if (event.getCurrentItem().getType() != null) {
                    if (event.getCurrentItem().getType() == Material.AIR) {
                        return;
                    }
                }
            }
            if (!Catch.RunningMines.isEmpty()) {
                for (Mine mine : Catch.RunningMines) {
                    if (player.getOpenInventory().getTitle().contains(mine.getName()) ||
                            player.getOpenInventory().getTitle().contains("Mines List")) {
                        if (ifPercentAddReset(player, mine.getName(), event.getCurrentItem())) {
                            found = true;
                        }
                        if (!found)
                            if (ifPercentAddBlock(player, mine.getName(), event.getCurrentItem())) {
                                found = true;
                            }
                        if (!found)
                            if (ifTimeAddReset(player, mine.getName(), event.getCurrentItem())) {
                                found = true;
                            }
                        if (!found)
                            if (minesMenu(player, event.getCurrentItem())) {
                                found = true;
                            }
                        if (!found)
                            if (ifResetManager(player, mine, event.getCurrentItem())) {
                                found = true;
                            }
                        if (!found)
                            if (ifBlockTypeClickMaterial(player, mine, event.getCurrentItem())) {
                                found = true;
                            }
                        if (!found)
                            if (mineMenuOptions(player, event.getCurrentItem(), mine)) {
                            }
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }


    public boolean minesMenu(Player player, ItemStack clickItem) {
        if (player.getOpenInventory() != null) {
            if (player.getOpenInventory().getTitle().contains("Mines List")) {
                if (sameItem(clickItem, Presets.BackButton)) {
                    if (Catch.lastPlayerMenu.containsKey(player)) {
                        player.openInventory(Catch.lastPlayerMenu.get(player));
                        Catch.nextPlayerMenu.put(player, findNextPage(MinesMenu.GetMainMenuPages(), player.getOpenInventory().getTopInventory()));
                        return true;
                    }
                }
                if (!ifNull(clickItem, "Name")) {
                    if (!Catch.RunningMines.isEmpty()) {
                        for (Mine mine : Catch.RunningMines) {
                            if (clickItem.getItemMeta().getDisplayName().contains(mine.getName())) {
                                Catch.lastPlayerMenu.put(player, player.getOpenInventory().getTopInventory());
                                player.openInventory(MinesMenu.MainMenuPreset(mine.getName()));
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;

    }

    public boolean mineMenuOptions(Player player, ItemStack clickItem, Mine mine) {
        if (player.getOpenInventory().getTitle().contains(mine.getName())) {
            if (inventoryCheckIfSame(player.getOpenInventory().getTopInventory().getContents(), MinesMenu.MainMenuPreset(mine.getName()).getContents())) {
                if (sameItem(Presets.ResetMineButton, clickItem)) {
                    player.sendMessage(Presets.DefaultColor + "Resetting Mine " + Presets.StandOutColor + mine.getName());
                    mine.ResetMine();
                    player.closeInventory();
                    return true;
                }
                if (sameItem(Presets.BlockTypesButton, clickItem)) {
                    player.openInventory(MinesMenu.BlockTypesMenuPreset(mine.getName()));
                    Catch.lastPlayerMenu.put(player, MinesMenu.MainMenuPreset(mine.getName()));
                    return true;
                }
                if (sameItem(Presets.ResetManageButton, clickItem)) {
                    player.openInventory(MinesMenu.ChangeMenu(mine.getName()));
                    Catch.lastPlayerMenu.put(player, MinesMenu.MainMenuPreset(mine.getName()));
                    return true;
                }
                if (sameItem(Presets.BackButton, clickItem)) {
                    player.openInventory(Catch.lastPlayerMenu.get(player));

                    return true;
                }
                if (sameItem(Presets.DeleteButton, clickItem)) {
                    if (Catch.RunningMines.contains(mine)) {
                        Bukkit.getScheduler().cancelTask(mine.getTimeResetTaskID());
                        Bukkit.getScheduler().cancelTask(mine.getPercentageResetTaskID());
                        Catch.RunningMines.remove(mine);
                    }
                    MineFile mineFile = new MineFile(mine.getName());
                    mineFile.deletConfig();
                    player.closeInventory();
                    player.sendMessage(Presets.DefaultColor + "You have Deleted Mine " + Presets.StandOutColor + mine.getName());
                }
            }
        }
        return false;
    }

    private boolean ifBlockTypeClickMaterial(Player player, Mine mine, ItemStack clickItem) {
        MineFile mineFile = new MineFile(mine.getName());
        if (inventoryCheckIfSame(player.getOpenInventory().getTopInventory().getContents(), MinesMenu.BlockTypesMenuPreset(mine.getName()).getContents())) {
            if (sameItem(Presets.BackButton, clickItem)) {
                player.openInventory(Catch.lastPlayerMenu.get(player));
                Catch.lastPlayerMenu.put(player, MinesMenu.GetMainMenuPages().get(0));
                if (Catch.selectingBlockType.containsKey(player)) {
                    Catch.selectingBlockType.remove(player);
                }
                return true;
            }
            if (Catch.selectingBlockType.containsKey(player)) {
                if (Catch.selectingBlockType.get(player)) {
                    for (Material material : mineFile.getBlockTypes().keySet()) {
                        if (clickItem != null && clickItem.getType() != null && clickItem.getType() == material) {
                            return true;
                        }
                    }
                    if (sameItem(Presets.AddButton, clickItem) || sameItem(Presets.BackButton, clickItem) || sameItem(Presets.NextPageButton, clickItem)) {
                        return true;
                    } else {
                        if (!ifNull(clickItem, null)) {
                            if (!player.getOpenInventory().getTopInventory().contains(clickItem)) {
                                HashMap<Material, Integer> temp = mineFile.getBlockTypes();
                                temp.put(clickItem.getType(), 20);
                                mineFile.setBlockTypes(temp);
                                for (int i = 0; i < Catch.RunningMines.size(); i++) {
                                    if (Catch.RunningMines.get(i) == mine) {
                                        Catch.RunningMines.get(i).setBlockTypes(temp);
                                    }
                                }
                                player.sendMessage(Presets.DefaultColor + " You have Added " + Presets.StandOutColor + clickItem.getType().toString() + Presets.DefaultColor + " to your Types");
                                player.openInventory(MinesMenu.BlockTypesMenuPreset(mine.getName()));
                                Catch.selectingBlockType.remove(player);
                                return true;
                            }
                        }
                    }
                }
            }
            for (Material material : mineFile.getBlockTypes().keySet()) {
                if (clickItem != null && clickItem.getType() != null && clickItem.getType() == material) {
                    if (player.getOpenInventory().getTopInventory().contains(clickItem)) {
                        player.openInventory(MinesMenu.BlockTypeMenu(mine.getName(), material.toString(), mineFile.getBlockTypes().get(material)));
                        Catch.lastPlayerMenu.put(player, MinesMenu.BlockTypesMenuPreset(mine.getName()));
                        return true;
                    }
                }


            }
            if (sameItem(Presets.AddButton, clickItem)) {
                player.sendMessage(Presets.DefaultColor + " Select a Block in your inventory to add");
                Catch.selectingBlockType.put(player, true);
                return true;
            }

        }
        return false;
    }

    private boolean ifPercentAddBlock(Player player, String mine, ItemStack clickItem) {
        String[] splitTitle = player.getOpenInventory().getTitle().split("\\s");
        Material material = null;
        Integer percentage = 0;
        for (String s : splitTitle) {
            if (Material.getMaterial(s) != null) {
                material = Material.getMaterial(s);
            }
            try {
                percentage = Integer.parseInt(s);
            } catch (NumberFormatException e) {

            }
        }
        if (material != null) {
            if (inventoryCheckIfSame(player.getOpenInventory().getTopInventory().getContents(), MinesMenu.BlockTypeMenu(mine, material.toString(), percentage).getContents())) {
                if (material != null) {
                    if (percentage > 0) {
                        if (!Catch.RunningMines.isEmpty()) {
                            for (Mine m : Catch.RunningMines) {
                                if (m.getName().equals(mine)) {
                                    MineFile file = new MineFile(mine);
                                    if (file.getBlockTypes().containsKey(material)) {
                                        if (!ifNull(clickItem, "Name")) {
                                            Integer AorD = (int) getNumberFromName(clickItem.getItemMeta().getDisplayName());
                                            for (String ad : clickItem.getItemMeta().getLore()) {
                                                if (ad.contains("Add")) {
                                                    if (AorD + percentage <= 100) {
                                                        player.openInventory(MinesMenu.BlockTypeMenu(mine, material.toString(), percentage + AorD));
                                                        return true;
                                                    } else {
                                                        percentage = 100;
                                                        player.openInventory(MinesMenu.BlockTypeMenu(mine, material.toString(), percentage));

                                                        return true;
                                                    }
                                                } else if (ad.contains("Remove")) {
                                                    if (percentage - AorD > 0) {
                                                        player.openInventory(MinesMenu.BlockTypeMenu(mine, material.toString(), percentage - AorD));

                                                        return true;
                                                    } else {
                                                        percentage = 5;
                                                        player.openInventory(MinesMenu.BlockTypeMenu(mine, material.toString(), percentage));
                                                        return true;
                                                    }
                                                }
                                            }
                                            if (clickItem.getItemMeta().getDisplayName().contains("Delete")) {
                                                HashMap<Material, Integer> materials = file.getBlockTypes();
                                                materials.remove(material);
                                                file.setBlockTypes(materials);
                                                for (int i = 0; i < Catch.RunningMines.size(); i++) {
                                                    if (Catch.RunningMines.get(i) == m) {
                                                        Catch.RunningMines.get(i).setBlockTypes(materials);
                                                    }
                                                }
                                                player.openInventory(MinesMenu.BlockTypesMenuPreset(m.getName()));
                                                Catch.lastPlayerMenu.put(player, MinesMenu.MainMenuPreset(mine));
                                                return true;
                                            } else if (clickItem.getItemMeta().getDisplayName().contains("Confirm")) {
                                                HashMap<Material, Integer> materials = file.getBlockTypes();
                                                materials.remove(material);
                                                materials.put(material, percentage);
                                                file.setBlockTypes(materials);
                                                for (int i = 0; i < Catch.RunningMines.size(); i++) {
                                                    if (Catch.RunningMines.get(i) == m) {
                                                        Catch.RunningMines.get(i).setBlockTypes(materials);
                                                    }
                                                }
                                                player.openInventory(MinesMenu.BlockTypesMenuPreset(m.getName()));
                                                Catch.lastPlayerMenu.put(player, MinesMenu.MainMenuPreset(mine));
                                                return true;
                                            } else if (clickItem.getItemMeta().getDisplayName().contains("Back")) {
                                                player.openInventory(Catch.lastPlayerMenu.get(player));
                                                Catch.lastPlayerMenu.put(player, MinesMenu.MainMenuPreset(mine));
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    private boolean ifPercentAddReset(Player player, String mine, ItemStack clickItem) {
        if (player.getOpenInventory().getTitle().contains(mine)) {
            Integer percentage = (int) getNumberFromName(player.getOpenInventory().getTitle());
            if (inventoryCheckIfSame(player.getOpenInventory().getTopInventory().getContents(), MinesMenu.ResetPercentageMenu(mine, percentage).getContents())) {
                if (percentage != null) {
                    if (!Catch.RunningMines.isEmpty()) {
                        for (Mine m : Catch.RunningMines) {
                            if (m.getName().equals(mine)) {
                                MineFile file = new MineFile(mine);
                                if (!ifNull(clickItem, "Name")) {
                                    Integer AorD = (int) getNumberFromName(clickItem.getItemMeta().getDisplayName());
                                    for (String ad : clickItem.getItemMeta().getLore()) {
                                        if (ad.contains("Add")) {
                                            if (AorD + percentage <= 100) {
                                                player.openInventory(MinesMenu.ResetPercentageMenu(mine, percentage + AorD));
                                                return true;
                                            } else {
                                                percentage = 100;
                                                player.openInventory(MinesMenu.ResetPercentageMenu(mine, percentage));
                                                return true;
                                            }
                                        } else if (ad.contains("Remove")) {
                                            if (percentage-AorD > 0) {
                                                player.openInventory(MinesMenu.ResetPercentageMenu(mine, percentage - AorD));
                                                return true;
                                            } else {
                                                percentage = 0;
                                                player.openInventory(MinesMenu.ResetPercentageMenu(mine, percentage));
                                                return true;
                                            }
                                        }
                                    }
                                    if (sameItem(clickItem, Presets.ConfimButton)) {
                                        file.setResetPercentage(percentage);
                                        for (int i = 0; i < Catch.RunningMines.size(); i++) {
                                            if (Catch.RunningMines.get(i) == m) {
                                                Catch.RunningMines.get(i).setMinePercentageReset(percentage);
                                            }
                                        }
                                        player.openInventory(Catch.lastPlayerMenu.get(player));
                                        Catch.lastPlayerMenu.put(player, MinesMenu.MainMenuPreset(mine));
                                        return true;
                                    }
                                    if (sameItem(clickItem, Presets.BackButton)) {
                                        player.openInventory(Catch.lastPlayerMenu.get(player));
                                        Catch.lastPlayerMenu.put(player, MinesMenu.MainMenuPreset(mine));
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;

    }

    private boolean ifResetManager(Player player, Mine mine, ItemStack cickItem) {
        if (inventoryCheckIfSame(player.getOpenInventory().getTopInventory().getContents(), MinesMenu.ChangeMenu(mine.getName()).getContents())) {
            if (sameItem(Presets.TimeResetButton, cickItem)) {
                player.openInventory(MinesMenu.TimeResetMenu(mine.getName(),mine.getMineResetTime()));
                Catch.lastPlayerMenu.put(player, MinesMenu.ChangeMenu(mine.getName()));
                return true;
            }
            if (sameItem(Presets.PercentResetButton, cickItem)) {
                player.openInventory(MinesMenu.ResetPercentageMenu(mine.getName(), mine.getMinePercentageReset()));
                Catch.lastPlayerMenu.put(player, MinesMenu.ChangeMenu(mine.getName()));
                return true;
            }
            if (sameItem(Presets.BackButton, cickItem)) {
                player.openInventory(Catch.lastPlayerMenu.get(player));
                Catch.lastPlayerMenu.put(player, MinesMenu.GetMainMenuPages().get(0));
                return true;
            }
        }
        return false;

    }

    private boolean ifTimeAddReset(Player player, String mine, ItemStack clickItem) {
        double time = getNumberFromName(player.getOpenInventory().getTitle());
        if (inventoryCheckIfSame(player.getOpenInventory().getTopInventory().getContents(), MinesMenu.TimeResetMenu(mine, time).getContents())) {
            if (!Catch.RunningMines.isEmpty()) {
                for (Mine m : Catch.RunningMines) {
                    if (m.getName().equals(mine)) {
                        MineFile file = new MineFile(mine);
                        if (!ifNull(clickItem, "Name")) {
                            Integer AorD = (int) getNumberFromName(clickItem.getItemMeta().getDisplayName());
                            Integer Hours = 0;
                            Integer Minutes = 0;
                            DecimalFormat format = new DecimalFormat("0.00");
                            String temp = format.format(time);
                            String converter[] = temp.split("\\.");
                            try {
                                Hours = Integer.parseInt(converter[0]);
                                Minutes = Integer.parseInt(converter[1]);
                                ;
                            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                            }
                            for (String ad : clickItem.getItemMeta().getLore()) {
                                if (ad.contains("Add")) {
                                    if (Minutes + AorD < 60) {
                                        Minutes = Minutes + AorD;
                                        String newTime = "";
                                        if(Minutes<10){
                                            newTime = Hours + ".0" + Minutes;
                                        }
                                        else {
                                            newTime = Hours + "." + Minutes;
                                        }
                                        try {
                                            player.openInventory(MinesMenu.TimeResetMenu(mine, Double.parseDouble(newTime)));
                                        } catch (NumberFormatException e) {

                                        }
                                        return true;
                                    } else {
                                        Hours++;
                                        Minutes = (Minutes + AorD)-60;
                                        String newTime = Hours + "." + Minutes;
                                        try {
                                            player.openInventory(MinesMenu.TimeResetMenu(mine, Double.parseDouble(newTime)));
                                        } catch (NumberFormatException e) {

                                        }
                                        return true;
                                    }
                                } else if (ad.contains("Remove")) {
                                    if (Minutes > 0 || Hours > 0) {
                                        if (Minutes - AorD > 0) {
                                            Minutes = Minutes - AorD;
                                            String newTime = "";
                                            if(Minutes<10){
                                                newTime = Hours + ".0" + Minutes;
                                            }
                                            else {
                                               newTime = Hours + "." + Minutes;
                                            }
                                            try {
                                                player.openInventory(MinesMenu.TimeResetMenu(mine, Double.parseDouble(newTime)));
                                            } catch (NumberFormatException e) {

                                            }
                                            return true;
                                        } else {
                                            if (Hours > 0) {
                                                Hours--;
                                                Minutes = 60-(AorD-Minutes);
                                                String newTime = Hours + "." + Minutes;
                                                try {
                                                    player.openInventory(MinesMenu.TimeResetMenu(mine, Double.parseDouble(newTime)));

                                                } catch (NumberFormatException e) {

                                                }
                                                return true;
                                            } else {
                                                Hours = 0;
                                                Minutes = 0;
                                                String newTime = Hours + "." + Minutes;
                                                try {
                                                    player.openInventory(MinesMenu.TimeResetMenu(mine, Double.parseDouble(newTime)));

                                                } catch (NumberFormatException e) {

                                                }
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                            if (sameItem(clickItem, Presets.ConfimButton)) {
                                file.setResetTime(time);
                                for (int i = 0; i < Catch.RunningMines.size(); i++) {
                                    if (Catch.RunningMines.get(i) == m) {
                                        Catch.RunningMines.get(i).setMineResetTime(time);
                                    }
                                }
                                player.openInventory(Catch.lastPlayerMenu.get(player));
                                Catch.lastPlayerMenu.put(player, MinesMenu.MainMenuPreset(mine));
                                return true;
                            }
                            if (sameItem(clickItem, Presets.BackButton)) {
                                player.openInventory(Catch.lastPlayerMenu.get(player));
                                Catch.lastPlayerMenu.put(player, MinesMenu.MainMenuPreset(mine));
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean ifNull(ItemStack itemStack, String Type) {
        if (itemStack != null) {
            if (itemStack.getType() != null) {
                if (itemStack.getItemMeta() != null) {
                    if (Type != null) {
                        if (Type.equals("Name") || Type.equals("Lore")) {
                            if (itemStack.getItemMeta() != null) {
                                if (Type.equals("Name")) {
                                    return !itemStack.getItemMeta().hasDisplayName();
                                }
                                if (Type.equals("Lore")) {
                                    return !itemStack.getItemMeta().hasLore();
                                }
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean sameItem(ItemStack itemStack1, ItemStack itemStack2) {
        if (itemStack1 != null && itemStack2 != null) {
            if (itemStack1.getType() != null && itemStack2 != null) {
                if (itemStack1.getItemMeta() != null && itemStack2.getItemMeta() != null) {
                    if (itemStack1.getItemMeta().hasDisplayName() && itemStack2.getItemMeta().hasDisplayName()) {
                        return itemStack1.getItemMeta().getDisplayName().contains(itemStack2.getItemMeta().getDisplayName());
                    } else if (!itemStack1.getItemMeta().hasDisplayName() && !itemStack2.getItemMeta().hasDisplayName()) {
                        return true;
                    } else if (itemStack1.getItemMeta().hasLore() && itemStack2.getItemMeta().hasLore()) {
                        return itemStack1.getItemMeta().getLore().contains(itemStack2.getItemMeta().getLore());
                    } else return !itemStack1.getItemMeta().hasLore() && !itemStack2.getItemMeta().hasLore();
                }
            }
        }
        return false;
    }

    private double getNumberFromName(String string) {
        double number = 0;
        Scanner scanner = new Scanner(string);
        scanner.useLocale(Locale.US);
        while (scanner.hasNext()) {

            try {
                number = Double.parseDouble(scanner.next());
                return number;
            } catch (NumberFormatException e) {

            }
        }
        return number;

    }

    private Inventory findNextPage(ArrayList<Inventory> inventories, Inventory current) {
        Inventory inventory = null;
        int cu = 0;
        for (int i = 0; i < inventories.size(); i++) {
            if (inventories.get(i).equals(current)) {
                cu = 0;
            }
        }
        if (cu + 1 < inventories.size()) {
            inventory = inventories.get(cu + 1);
        }
        return inventory;
    }

    private boolean versionTitleCheck(Player player,String string){
        if(Bukkit.getVersion().contains("1.4.4")){
            if(player.getOpenInventory().getTitle().contains(string)){
                return true;
            }

        }
        else {
            if(player.getOpenInventory().getTitle().contains(string)){
                return true;
            }
        }
        return false;
    }

    private String versionTitleString(Player player){
        String v = "";
        if(Bukkit.getVersion().contains("1.4.4")){
            v = player.getOpenInventory().getTitle();
        }
        else {
            player.getOpenInventory().getTitle();

        }


        return v;
    }

    private Mine findMine(String Name) {
        if (!Catch.RunningMines.isEmpty()) {
            for (Mine mine : Catch.RunningMines) {
                if (mine.getName() == Name) {
                    return mine;
                }
            }
        }
        return null;
    }

    private void setMine(Mine mine) {
        if (!Catch.RunningMines.isEmpty()) {
            if (Catch.RunningMines.contains(mine)) {
                Catch.RunningMines.remove(mine);
                Catch.RunningMines.add(mine);
                return;
            }
        }
    }

    private void removemine(Mine mine) {
        if (!Catch.RunningMines.isEmpty()) {
            if (Catch.RunningMines.contains(mine)) {
                Catch.RunningMines.remove(mine);
                return;
            }
        }
    }

    private boolean inventoryCheckIfSame(ArrayList<ItemStack> first, ArrayList<ItemStack> second) {
        boolean same = false;
        for (ItemStack im : first) {
            if (!ifNull(im, null)) {
                if (second.contains(im)) {
                    same = true;
                } else {
                    return false;
                }
            }
        }
        for (ItemStack im : second) {
            if (!ifNull(im, null)) {
                if (first.contains(im)) {
                    same = true;
                } else {
                    return false;
                }
            }
        }

        return same;
    }

    private boolean inventoryCheckIfSame(ItemStack[] first, ItemStack[] second) {
        boolean same = false;
        ArrayList<ItemStack> tem1 = new ArrayList<>();
        ArrayList<ItemStack> tem2 = new ArrayList<>();
        for (ItemStack im : first) {
            tem1.add(im);
        }
        for (ItemStack im : second) {
            tem2.add(im);
        }
        for (ItemStack im : tem1) {
            if (!ifNull(im, null)) {
                if (tem2.contains(im)) {
                    same = true;
                } else {
                    return false;
                }
            }
        }
        for (ItemStack im : tem2) {
            if (!ifNull(im, null)) {
                if (tem1.contains(im)) {
                    same = true;
                } else {
                    return false;
                }
            }
        }

        return same;
    }
}
