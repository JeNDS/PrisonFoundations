package Me.JeNDS.Objects.MineObjects;

import Me.JeNDS.Main.PF;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class DropParty {
    protected ArrayList<ItemStack> items = new ArrayList<>();
    protected ArrayList<Location> dropLocation = new ArrayList<>();
    protected int percentageToDrop = 0;
    protected int redropTime = 0;
    protected int dropTime = 0;
    protected int taskID = 0;


    public void startCounter() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(PF.getPlugin(PF.class), new Runnable() {
            int time = dropTime;
            int repeat = redropTime;

            @Override
            public void run() {
                if (repeat == 0) {
                    if (time != 0) {
                        dropItems();
                    } else {
                        repeat = redropTime;
                        time = dropTime;
                        return;
                    }
                    time--;
                }
                repeat--;

            }
        }, 0L, 20L);
    }

    public void createParty() {
        startCounter();

    }

    public void loadParty() {
        startCounter();

    }


    public void stopParty() {

    }

    public void removeParty() {

    }

    public void dropItems() {
        if (!items.isEmpty() && !dropLocation.isEmpty()) {
            int randomItem = (int) Math.random() * items.size();
            int randomLocation = (int) Math.random() * dropLocation.size();
            int randomDropAmount = (int) Math.random() * 10 + 1;

            for (int i = 0; i < randomDropAmount; i++) {
                Bukkit.getWorld(dropLocation.get(randomLocation).getWorld().getName()).dropItemNaturally(dropLocation.get(randomLocation), items.get(randomItem));
            }

        }
    }


}

class PartyFileManager {

    /*
    FileMaker partyFile = new FileMaker("Parties", true, "Party Manager");

    public void loadParties() {
        partyFile.create();
        if (partyFile.getFile().get("DropParty") != null) {
            ConfigurationSection section = partyFile.getFile().getConfigurationSection("DropParty");
            for (String partyName : section.getKeys(false)) {
                DropParty dropParty = new DropParty();
                dropParty.redropTime = partyFile.getFile().getInt("DropParty." + partyName + ".ReDrop Time");
                dropParty.dropTime = partyFile.getFile().getInt("DropParty." + partyName + ".Time Per Drop");
                dropParty.percentageToDrop = partyFile.getFile().getInt("DropParty." + partyName + ".Percentage to Drop");
                Location location1 = new Location(Bukkit.getWorld(partyFile.getFile().getString("DropParty." + partyName + ".Locations.World")),
                        partyFile.getFile().getDouble("DropParty." + partyName + ".Locations.Location1.X"),
                        partyFile.getFile().getDouble("DropParty." + partyName + ".Locations.Location1.Y"),
                        partyFile.getFile().getDouble("DropParty." + partyName + ".Locations.Location1.Z"));
                Location location2 = new Location(Bukkit.getWorld(partyFile.getFile().getString("DropParty." + partyName + ".Locations.World")),
                        partyFile.getFile().getDouble("DropParty." + partyName + ".Locations.Location1.X"),
                        partyFile.getFile().getDouble("DropParty." + partyName + ".Locations.Location1.Y"),
                        partyFile.getFile().getDouble("DropParty." + partyName + ".Locations.Location1.Z"));
                dropParty.dropLocation = RegionCreator.regionCreator(location1, location2);

            }
        }

    }

    public void createPartyFiles() {
        partyFile.create();
    }

     */

}
