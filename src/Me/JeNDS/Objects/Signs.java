package Me.JeNDS.Objects;

import org.bukkit.block.Sign;

import javax.xml.stream.Location;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Signs {
    private Sign Sign;
    private Location location;


    public Sign getSign() {
        return Sign;
    }

    public void setSign(Sign sign) {
        Sign = sign;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
