package ch.batthomas.surf.constant;

/**
 *
 * @author batthomas
 */
public class ConfigConstant extends Constant<String> {

    @Override
    public void initializeContent() {
        this.getContent().put("MySQL.username", "username");
        this.getContent().put("MySQL.password", "password");
        this.getContent().put("MySQL.database", "database");
        this.getContent().put("MySQL.host", "localhost");
        this.getContent().put("MySQL.port", "3306");
    }

}
