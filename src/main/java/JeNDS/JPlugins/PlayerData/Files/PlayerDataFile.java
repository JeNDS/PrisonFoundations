package JeNDS.JPlugins.PlayerData.Files;

import JeNDS.JPlugins.PF;
import JeNDS.JPlugins.PlayerData.Multiplier;
import JeNDS.JPlugins.PlayerData.PFPlayer;
import JeNDS.JPlugins.Static.Catch;
import JeNDS.Plugins.PluginAPI.Files.YMLFile;
import JeNDS.Plugins.PluginAPI.JDAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerDataFile {



    private static YMLFile file;

    private static int id;


    public static void LoadDataFile(){
        file = JDAPI.getFileManipulation.createFile("PlayerData.yml", PF.getInstance());
        ConfigurationSection section = file.getFileConfiguration().getConfigurationSection("");
        for(String playerID : section.getKeys(false)){
            PFPlayer player = new PFPlayer(UUID.fromString(playerID));
            ConfigurationSection multipliers = file.getFileConfiguration().getConfigurationSection(playerID+".Multipliers");
            ArrayList<Multiplier> multipliersList = new ArrayList<>();
            for(String multiID : multipliers.getKeys(false)){
                double amount = file.getFileConfiguration().get(playerID+".Multipliers."+multiID+".Amount") == null ? 0.0 :file.getFileConfiguration().getDouble(playerID+".Multipliers."+multiID+".Amount");
                String time = file.getFileConfiguration().get(playerID+".Multipliers."+multiID+".Time") == null ? "0:0:0:0" :file.getFileConfiguration().getString(playerID+".Multipliers."+multiID+".Time");
                boolean permanent = file.getFileConfiguration().get(playerID + ".Multipliers." + multiID + ".Permanent") != null && file.getFileConfiguration().getBoolean(playerID + ".Multipliers." + multiID + ".Permanent");
                Multiplier multiplier = new Multiplier(playerID,multiID,amount,permanent,time);
                multipliersList.add(multiplier);
            }
            player.setMultipliers(multipliersList);
            Catch.PFPlayers.add(player);
        }

        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(PF.getInstance(), new Runnable() {
            @Override
            public void run() {
                file.save();
            }
        },1200L,1200L);

    }
    public static void UnLoadDataFile(){
        Bukkit.getScheduler().cancelTask(id);
        Catch.PFPlayers = new ArrayList<>();
        file.save();
    }

    public static void AddMultiplierToFile(Multiplier multiplier){
        if(file.getFileConfiguration().get(multiplier.getPlayerId()+".Multipliers."+multiplier.getID())==null){
            file.getFileConfiguration().set(multiplier.getPlayerId() +".Multipliers."+multiplier.getID()+".Amount",multiplier.getAmount());
            file.getFileConfiguration().set(multiplier.getPlayerId() +".Multipliers."+multiplier.getID()+".Time",multiplier.getDays()+":"+multiplier.getTime());
            file.getFileConfiguration().set(multiplier.getPlayerId() +".Multipliers."+multiplier.getID()+".Permanent",multiplier.isPermanent());
        }
    }
    public static void RemoveMultiplierToFile(Multiplier multiplier){
        if(file.getFileConfiguration().get(multiplier.getPlayerId()+".Multipliers."+multiplier.getID())!=null){
            file.getFileConfiguration().set(multiplier.getPlayerId() +".Multipliers."+multiplier.getID(),null);
        }
    }


}
