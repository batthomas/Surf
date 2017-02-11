package ch.batthomas.surf.database;

import ch.batthomas.surf.util.Kit;
import com.google.gson.Gson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author batthomas
 */
public class KitQuery {

    private final MySQLConnector mysql;
    private final Gson gson;

    public KitQuery(MySQLConnector mysql) {
        this.mysql = mysql;
        gson = new Gson();
    }

    public void addKit(Kit kit) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT IGNORE INTO surf_kits (name, slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, helmet, chestplate, leggings, boots)");
        sb.append("VALUES (");
        sb.append("'").append(kit.getName()).append("'").append(", ");
        for (int i = 1; i < 10; i++) {
            sb.append("'").append(serializeItem(kit.getItems().get("slot" + i))).append("'").append(", ");
        }
        sb.append("'").append(serializeItem(kit.getItems().get("helmet"))).append("'").append(", ");
        sb.append("'").append(serializeItem(kit.getItems().get("chestplate"))).append("'").append(", ");
        sb.append("'").append(serializeItem(kit.getItems().get("leggings"))).append("'").append(", ");
        sb.append("'").append(serializeItem(kit.getItems().get("boots"))).append("'");
        sb.append(")");
        mysql.executeUpdate(sb.toString());
    }

    public Kit getKit(String name) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM surf_kits WHERE name='").append(name).append("'");
        ResultSet rs = mysql.executeQuery(sb.toString());
        rs.first();
        Kit kit = new Kit(rs.getString("name"));
        for (int i = 1; i < 10; i++) {
            deserializeItem(kit, "slot" + i, rs.getString("slot" + i));
        }
        deserializeItem(kit, "helmet", rs.getString("helmet"));
        deserializeItem(kit, "chestplate", rs.getString("chestplate"));
        deserializeItem(kit, "leggings", rs.getString("leggings"));
        deserializeItem(kit, "boots", rs.getString("boots"));
        return kit;
    }

    public List<Kit> getKits() throws SQLException {
        ResultSet rs = mysql.executeQuery("SELECT * FROM surf_kits");
        List<Kit> kits = new ArrayList<>();
        while (rs.next()) {
            Kit kit = new Kit(rs.getString("name"));
            for (int i = 1; i < 10; i++) {
                deserializeItem(kit, "slot" + i, rs.getString("slot" + i));
            }
            deserializeItem(kit, "helmet", rs.getString("helmet"));
            deserializeItem(kit, "chestplate", rs.getString("chestplate"));
            deserializeItem(kit, "leggings", rs.getString("leggings"));
            deserializeItem(kit, "boots", rs.getString("boots"));
            kits.add(kit);
        }
        return kits;
    }

    private String serializeItem(ItemStack item) {
        return gson.toJson(item != null ? item.serialize() : null);
    }

    private void deserializeItem(Kit kit, String path, String item) {
        if (gson.fromJson(item, Map.class) != null) {
            kit.addItem(path, ItemStack.deserialize(gson.fromJson(item, Map.class)));
        }
    }
}
