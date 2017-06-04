# Surf

### Einleitung
Surf ist einer der ältesten Spielmodi des GommeHD.net Netzwerkes und basiert auf den Modus “Surf” von CS:GO. Dieser Spielmodus ist anders als andere PvP Spielmodi und unterscheidet sich in wichtigen Faktoren.

Während es bei anderen Spielmodi ein klar definiertes Runden-Ende gibt und das Spiel in Phasen unterteilt ist (Lobbyphase, Schutz & Kampfphase), ist der Spielmodus Surf ein Lobbymodus, bei welchem es alle 5 Minuten ein Mapwechsel & Kitwechsel gibt.
Man spielt auf einem Server, welcher ungefähr alle 25 Minuten neustartet. Ein Surf-Server umfasst maximal 30 Spieler. Jeder dieser Spieler besitzt dasselbe Kit und spielt auf derselben Map.

Dieser Spielmodus wird durch ein Levelsystem, durch Killstreaks, Specialitems und Überraschungen abwechslungsreich gestaltet.
* Um so höher das Level, um so mehr Rechte und Features erhält / besitzt man.
Jeder neue Spieler fängt bei 0 an und hat die Chance sich bis auf Level 100 zu töten.
Töten ist in diesem das richtige Wort, da man eine bestimmte Kill Anzahl benötigt, für einen Level aufstieg.
* Eine Killstreak erhält man, wenn man sehr viele Kills nacheinander macht, ohne dazwischen zu sterben.
Bei einigen Killstreaks erhält man bestimmte Effekte, entweder nur für sich selbst oder für alle.
* Desweiteren gibt es Specialitems die man nur bei den Überraschungen erhält.
Die Überraschungen erhält man sobald man über einen Bedrockblock läuft.

<details>
<summary>Commands ohne Permissions</summary>
* **/level** oder **/lvl** zeigt dir dein aktuelles Level, den Fortschritt bis zum nächsten Level und weiteres an.
* **/stats** oder **/stats <Player>** zeigt dir deine Stats oder die Stats eines anderen Spielers an.
</details>

<details>
<summary>Commands mit Permissions</summary>
* **/nextround** verkürzt die Wartezeit bis zu einer neuen Runde auf 10 Sekunden. `Permission: surf.nextround`
* **/setup** startet oder stoppt den Setupmode `Permission: surf.setup`
* **/setup goto \<map>** teleportiert den Spieler zu der ausgewählten Map `Permission: surf.setup`
* **/setspawn** setzt die Koordinaten des Spawns und schaltet die Map im Spiel frei. `Permission: surf.setup`
* **/setkit** speichert die Items im Inventar in der Datenbank als gültiges Kit. `Permission: surf.setup`
</details>

###  Installation
Für die Nutzung dieses Plugins ist ein Spigot 1.11.2 Server und eine MySQL Datenbank erforderlich. Zuerst kopiert man das Plugin in das zugehörige Verzeichnis, kopiert 5 Maps in den WorldContainer und lässt die 2 MySQL Queries laufen. Danach startet man den Server, der ohne gültige Maps im Setupmode startet, und konfiguriert mit den Commands (/setup \<goto> und /setspawn) die Maps. Am Ende muss man nur noch die Kits mit dem /setkit Command konfigurieren und den Server restarten. Falls etwas noch nicht konfiguriert worden ist, startet der Server im Setupmode.


