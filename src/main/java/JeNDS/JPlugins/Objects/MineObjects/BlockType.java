package JeNDS.JPlugins.Objects.MineObjects;

import org.bukkit.Material;

public class BlockType {

    private Material material;
    private int percentage;
    public BlockType(Material material,int percentage){
        this.material = material;
        this.percentage = percentage;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }


}
