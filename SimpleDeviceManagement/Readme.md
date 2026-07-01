Beschreibung von Projekt

**Architektur (Core vs. Services):**<br/>
Das Projekt trennt die technische Basis von der Fachlogik. `SdmCore` ist die technische
Schicht (z. B. Persistence/Mapper/technische Utilities). `SdmServices` enthaelt die
fachlichen Use-Case-Services und kapselt die fachliche Logik. Die Fachlogik nutzt den
Core und bildet stabile Use-Cases, damit spaeter mehrere Adapter (REST, GraphQL,
Batch, Messaging) dieselben Fachoperationen verwenden koennen.

**Architektur (Controller/Permissions):**<br/>
Die Controller werden nach Zielgruppe getrennt. `SdmAdminController` enthaelt die
administrativen Endpunkte (z. B. Systempflege, globale Abfragen) und ist strenger
abgesichert. `SdmAppController` stellt die fachlichen Operationen fuer Nutzer bereit
(z. B. "registriere Device", "gib mir meine Devices", "zeig mir mein Profil", "zeig mir diese bestimmte device von mir") und hat eigene Berechtigungen.
Zu beachtenn ist, dass die Berechtigungen Rollenbasiert aber auch vielleicht feiner sein sollen, denn ein user, darf nicht devices von andere user sehen/editieren
Damit sind API-Schnittstellen, Fachlogik und Security klar getrennt und erweiterbar.

**Architektur (Asynchrone API / JMS):**<br/>
Die JMS-Auftragbearbeitung wird in ein eigenes Modul ausgelagert (z. B. `SdmServicesJms`).
Dieses Modul enthaelt nur Adapter-Code (Listener, Mapping, Retry/Dead-Letter) und
ruft die Fachlogik aus `SdmServices` auf. Die fachliche Registrierung nutzt ein
gemeinsames Command-Objekt (z. B. `RegisterDeviceCommand`), das sowohl von REST
als auch JMS verwendet wird. Unterschiede in der Fachlogik werden ueber einen
Kontext gesteuert (z. B. `allowNewUser` fuer JMS). Beispiel: REST verlangt einen
existierenden User, JMS darf User anlegen und eine Bestaetigung per E-Mail ausloesen,
bevor die eigentliche Device-Registrierung abgeschlossen wird.

**Architektur (JMS-Registrierung mit UUID und Zertifikaten):**<br/>
User werden ueber `userid` (UUID) referenziert, um auch externe User-Quellen wie
Keycloak zu unterstuetzen. In der User-Tabelle kann bei Bedarf das Public-Zertifikat
gespeichert werden; das Private-Zertifikat bleibt im Identity-System. Devices
erhalten ebenfalls eine UUID sowie ein Zertifikat, wobei das Private-Zertifikat
auf dem Device verbleibt und nur das Public-Zertifikat bei uns gespeichert wird.

Ein Registrierungsauftrag enthaelt User- und Device-Daten. Wenn eine `userid`
angegeben ist, wird die Existenz geprueft und die Authentizitaet des Users ueber
eine Signatur (aus seinem Zertifikat) validiert. Wenn keine `userid` existiert,
wird der User als neu betrachtet: Pflichtfelder sind Vorname, Nachname und E-Mail.
Der User erhaelt einen Bestaetigungslink per E-Mail, legt sich damit selbst an und
ergaenzt weitere Daten (z. B. Department, Adresse). Danach wird der urspruengliche
Auftrag erneut verarbeitet (aus Queue oder Persistenz), nun mit `userid` und
Signatur, und die Device-Registrierung wird finalisiert.

~~**TODO 1:**<br/>~~
Die Persistence API und Model soll noch ein isolierte Module werden, 
welche aus Core benutze werden darf aber die API nicht bekannt sein soll. 

~~**TODO 1.1:**<br/>~~
maven dependency ist noch da per exclusion oder provided die Sache lösen!

**TODO 1.2:**
Liquibase oder equivalentes Tool für Datenbankmigrationen verwenden.

**TODO 1.3:**
Mehr Configuration für JPA und co... 

**TODO 2:**<br/>
Wie Rest werden wir in API auch ein GraphQL anbieten, welche die gleiche Services 
in Core nutzt.

**TODO 3:**<br/>
Auch eine Asynchrone API mit JMS oder Kafka bieten wir an

**TODO 4:**<br/>
Eine Batch bearbeitung mit JMS oder Kafka bietet wir auch an.
Für Batch und JMS sollen wir ein eigene Module erstellen: Auftrag Bearbeitung.

**TODO 5:**<br/>
Wir bieten auch eine Web UI an.

**TODO 6:**<br/>
Refactoring von Spring Security und Anbindung von Keycloak und vielleicht OpenFGA.
