package Me.JeNDS.Files;

import Me.JeNDS.MainFolder.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileMaker {

    private Main plugin = Main.getPlugin(Main.class);
    private FileConfiguration fileConfig;
    private File file;
    private String fileName;

    public FileMaker(String FileName, boolean folder) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        fileName = FileName;
        file = new File(plugin.getDataFolder(), fileName);
        if (folder) {
            if (!file.exists()) {
                file.mkdir();
            }
        }
        if (!folder) {
            if (file.exists()) {
                fileConfig = YamlConfiguration.loadConfiguration(file);
            }
        }
    }

    public FileMaker(String FileName, boolean customFolder, String FolderName) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        if (customFolder) {
            fileName = FileName;
            File folder = new File(plugin.getDataFolder(), FolderName);
            if (!folder.exists()) {
                folder.mkdir();
            }
            if (folder.exists()) {
                file = new File(folder, fileName);
            }
        }
        if (!customFolder) {
            file = new File(plugin.getDataFolder(), fileName);
        }
        fileName = FileName;
        if (file.exists()) {
            fileConfig = YamlConfiguration.loadConfiguration(file);
        }

    }

    public FileConfiguration getFile() {
        return fileConfig;
    }

    public File getRawFile() {
        return file;
    }

    public void create() {
        if (!file.exists()) {
            try {
                file.createNewFile();
                fileConfig = YamlConfiguration.loadConfiguration(file);
            } catch (IOException e) {
            }
        }

    }

    public void copy() {
        if (new File(plugin.getDataFolder(), fileName).exists()) {
            if (!file.exists()) {
                plugin.saveResource(fileName, true);
            }
        } else {
            plugin.saveResource(fileName, false);
        }
    }

    public void save() {
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender();
        }

    }

    public void delete() {
        file.delete();
    }
}
