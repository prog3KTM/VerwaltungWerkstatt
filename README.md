# VerwaltungWerkstatt  
### Was macht die Anwendung?

WerkstattVerwaltung ist eine Software zur effizienten Verwaltung von Kunden, Fahrzeugen und Werkstattservices. Mit ihr können Werkstätten Kunden- und Fahrzeugdaten erfassen, Serviceaufträge erstellen und den Bearbeitungsstatus verfolgen. Die Anwendung erleichtert die Organisation von Wartungen, Reparaturen und Rechnungen, um den Arbeitsablauf zu optimieren.

(Hier Menüeinführung einfügen)

## Liste wichtigster Features und Funktionen  

| Funktion | Beshreibung |
|-------|-----|
| Kunden verwalten | Hier kannst du einen neuen Kunden anlegen oder einen bestimmten Kunden anzeigen lassen. |
| Autos verwalten | Hier kannst du Autos hinzufügen, löschen oder suchen. |
| Werkstatt Informationen | Hier kannst du Angaben zu deiner Werkstatt machen, damit diese bei wichtigen Dokumenten angegeben werden können. |
| Services | Hier kannst du Services erstellen, die an Autos und Kunden gebunden sind. |
| Order | Hier kannst du mehrere Services zu einer Order für einen Kunden zusammenlegen. |

## Systemanforderungen  
Java: *Version 21 oder höher*  
Maven: *Version 3.8.1*

## Installation  
1. Repository clonen
   
   Over SSH
   > git clone git@github.com:prog3KTM/VerwaltungWerkstatt.git

   Over HTTP
   > git clone https://github.com/prog3KTM/VerwaltungWerkstatt.git

2. Dependencies installieren & Bauen
   > git checkout submission  
   > cd VerwaltungWerkstatt  
   > mvn clean package
   
3. Anwendung ausführen
   > java -jar .\target\verwaltung-werkstatt-1.0-SNAPSHOT.jar

4. Nun sollte eine Abfrage von Daten beginnen. Wenn keine Informationen über die Werkstatt bekannt ist, wird diese erst einmal abgefragt. Wenn du die Daten eingegeben hast oder sie bereits existieren, sollte sich das Hauptmenü öffnen. Nun kannst du über die Kommandozeile die Zahl eigeben, dessen Option du auswählen möchtest.

## Anwendungbeispiele  
Wenn du die Option auswählst die WerkstattInformationen einzusehen, solltest du immer ein Ergebnis bekommen, da im Falle einer leeren Datenbank du bereits vor dem Hauptmenü aufgefordert wirst diese Daten anzugeben.  

Wenn du einen Kunden anlegst solltest du eine ID als Antwort erhalten mit der du nach ihm suchen kannst.

Einfaches Anwendungsbeispiel um eine Service anzulegen:
....TODO!!!!!!

## Bekannte Fehler / Einschränkungen  
Fehler hier einfügen (am ende)
