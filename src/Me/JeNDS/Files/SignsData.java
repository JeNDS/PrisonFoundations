package Me.JeNDS.Files;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class SignsData {
    private FileMaker SignFile;

    public SignsData() {
        FileMaker folder = new FileMaker("Sign Data", true);
        {
            folder.create();
        }
        SignFile = new FileMaker("Signs", true, "Signs Data");
        {

            if (SignFile.getRawFile().length() == 0) {
                SignFile.create();
            } else {
                loadSignFile();
            }
        }
    }
    private void loadSignFile() {

    }

}
