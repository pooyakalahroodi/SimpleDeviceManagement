Beschreibung von Projekt

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
