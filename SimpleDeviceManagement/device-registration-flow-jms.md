# Device-Registrierung per JMS

## Uebersicht
Dieses Dokument beschreibt den JMS-Ablauf fuer die Device-Registrierung. JMS erlaubt bestehende und neue User.

## Fachlicher Ablauf JMS (bestehender oder neuer User)

1) Eingang: `RegisterDeviceCommand`
- User-Daten: `userId` (UUID, optional), `userSignature` (fuer die Verifikation)
- User-Felder: `firstName`, `lastName`, `email` (Pflicht, wenn `userId` fehlt)
- Device-Daten: Device-Felder + Device-Public-Zertifikat

2) Fall A: `userId` vorhanden
- User-Authentizitaet ueber Signatur pruefen (`UserIdentityService.verifySignature`).
- Device fuer bestehenden User registrieren (`DeviceRegistrationService.registerForExistingUser`).

3) Fall B: `userId` fehlt
- User-Erstellung anstossen (`UserRegistrationService.requestUserCreation`).
- Bestaetigungslink per E-Mail senden.
- Registrierungsauftrag mit Status `PENDING_CONFIRMATION` persistieren.
- Nach Bestaetigung `userId` erzeugen und Auftrag erneut laden.
- Signatur pruefen und Device-Registrierung abschliessen.

4) Ergebnis
- Device ist gespeichert und mit User verknuepft.
- Device-Public-Zertifikat ist gespeichert.
- Registrierungsauftrag wird auf `COMPLETED` gesetzt.
