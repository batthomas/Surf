package ch.batthomas.surf.constant;

/**
 *
 * @author batthomas
 */
public class WorldConfigConstant extends Constant<String> {

    @Override
    public void initializeContent() {
        this.getContent().put("mapinfo.name", "name");
        this.getContent().put("mapinfo.description", "description");
        this.getContent().put("spawn.x", "0");
        this.getContent().put("spawn.y", "0");
        this.getContent().put("spawn.z", "0");
        this.getContent().put("spawn.yaw", "0");
        this.getContent().put("spawn.pitch", "0");
        this.getContent().put("safezone.y", "0");
    }

}
