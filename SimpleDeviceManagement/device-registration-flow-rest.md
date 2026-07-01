# Device-Registrierung per REST

## Uebersicht
Dieses Dokument beschreibt den REST-Ablauf fuer die Device-Registrierung. REST erlaubt nur bestehende User.

## Fachlicher Ablauf REST (bestehender User)

1) Eingang: `RegisterDeviceCommand`
- User-Daten: `userId` (UUID, Pflicht), `userSignature` (fuer die Verifikation)
- Device-Daten: Device-Felder + Device-Public-Zertifikat

2) Ablauf
- User-Authentizitaet ueber Signatur pruefen (`UserIdentityService.verifySignature`).
- Device fuer bestehenden User registrieren (`DeviceRegistrationService.registerForExistingUser`).

3) Fehlerfall
- Wenn `userId` fehlt oder unbekannt ist: Fehlerantwort an den REST-Client.

4) Ergebnis
- Device ist gespeichert und mit User verknuepft.
- Device-Public-Zertifikat ist gespeichert.
