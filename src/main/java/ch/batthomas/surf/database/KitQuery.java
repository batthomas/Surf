package ch.batthomas.surf.database;

import ch.batthomas.surf.util.Kit;
import com.google.gson.Gson;
import java.sql.PreparedStatement;
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
        String query = "INSERT INTO surf_kits (name, slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, helmet, chestplate, leggings, boots) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = mysql.getConnection().prepareStatement(query);
        statement.setString(1, kit.getName());
        for (int i = 1; i < 10; i++) {
            statement.setString(i + 1, serializeItem(kit.getItems().get("slot" + i)));
        }
        statement.setString(11, serializeItem(kit.getItems().get("helmet")));
        statement.setString(12, serializeItem(kit.getItems().get("chestplate")));
        statement.setString(13, serializeItem(kit.getItems().get("leggings")));
        statement.setString(14, serializeItem(kit.getItems().get("boots")));
        mysql.executeUpdate(statement);
    }

    public Kit getKit(String name) throws SQLException {
        String query = "SELECT * FROM surf_kits WHERE name=?";
        PreparedStatement statement = mysql.getConnection().prepareStatement(query);
        statement.setString(1, name);
        ResultSet rs = mysql.executeQuery(statement);

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
        String query = "SELECT * FROM surf_kits";
        PreparedStatement statement = mysql.getConnection().prepareStatement(query);
        ResultSet rs = mysql.executeQuery(statement);
        
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
