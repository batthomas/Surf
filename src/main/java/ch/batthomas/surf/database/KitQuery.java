package ch.batthomas.surf.database;

import ch.batthomas.surf.util.Kit;
import com.google.gson.Gson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        sb.append("INSERT INTO surf_kits (name, slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, helmet, chestplate, leggings, boots)");
        sb.append("VALUES (");
        sb.append("'").append(kit.getName()).append("'").append(", ");
        for (int i = 1; i < 10; i++) {
            sb.append("'").append(gson.toJson(kit.getItems().get("slot" + i))).append("'").append(", ");
        }
        sb.append("'").append(gson.toJson(kit.getItems().get("helmet"))).append("'").append(", ");
        sb.append("'").append(gson.toJson(kit.getItems().get("chestplate"))).append("'").append(", ");
        sb.append("'").append(gson.toJson(kit.getItems().get("leggings"))).append("'").append(", ");
        sb.append("'").append(gson.toJson(kit.getItems().get("boots"))).append("'");
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
            kit.addItem("slot" + i, gson.fromJson(rs.getString("slot" + i), ItemStack.class));
        }
        kit.addItem("helmet", gson.fromJson(rs.getString("helmet"), ItemStack.class));
        kit.addItem("helmet", gson.fromJson(rs.getString("chestplate"), ItemStack.class));
        kit.addItem("helmet", gson.fromJson(rs.getString("leggings"), ItemStack.class));
        kit.addItem("helmet", gson.fromJson(rs.getString("boots"), ItemStack.class));
        return kit;
    }
}
