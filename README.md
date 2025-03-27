# VerwaltungWerkstatt  
### Was macht die Anwendung?

WerkstattVerwaltung ist eine Software zur effizienten Verwaltung von Kunden, Fahrzeugen und Werkstattservices. Mit ihr können Werkstätten Kunden- und Fahrzeugdaten erfassen, Serviceaufträge erstellen und den Bearbeitungsstatus verfolgen. Die Anwendung erleichtert die Organisation von Wartungen, Reparaturen und Rechnungen, um den Arbeitsablauf zu optimieren.

Zum Bedienen entweder einer der Optionen angeben (die Zahlen die links stehen zur Option) oder falls aufgefordert Zahlen und Wörter.

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
   > cd VerwaltungWerkstatt  
   > git checkout submission  
   > mvn clean package
   
3. Anwendung ausführen
   
   **Windows:**
   ```
   java -jar .\target\verwaltung-werkstatt-1.0-SNAPSHOT.jar
   ```

   **MacOS:**
   ```
   java -jar ./target/verwaltung-werkstatt-1.0-SNAPSHOT.jar
   ```

4. Nun sollte eine Abfrage von Daten beginnen. Wenn keine Informationen über die Werkstatt bekannt ist, wird diese erst einmal abgefragt. Wenn du die Daten eingegeben hast oder sie bereits existieren, sollte sich das Hauptmenü öffnen. Nun kannst du über die Kommandozeile die Zahl eigeben, dessen Option du auswählen möchtest.

## Anwendungbeispiele  
Wenn du die Option auswählst die WerkstattInformationen einzusehen, solltest du immer ein Ergebnis bekommen, da im Falle einer leeren Datenbank du bereits vor dem Hauptmenü aufgefordert wirst diese Daten anzugeben.  

Wenn du einen Kunden anlegst solltest du eine ID als Antwort erhalten mit der du nach ihm suchen kannst.

**Einfaches Anwendungsbeispiel um einen Service (Wenn noch kein Kunde und Auto angelegt wurde) anzulegen:**
1. Einen Kunden anlegen:
 
   ![image](https://github.com/user-attachments/assets/a76a4dbc-e432-4a3c-8c3c-ef0c1e65c6ff)
   
2. Nachdem man die Nummer 6 in den CLI eingetippt hat kommt folgendes Menü:
   
   ![image](https://github.com/user-attachments/assets/774a5abc-31d9-4f80-9e03-e2f61ebc1c4e)
   
3. Es wird nun eine neuer Kunde hinzugefügt:
 
   ![image](https://github.com/user-attachments/assets/e06364b1-f510-4bd0-bf27-d90afb6331bd)
   
4. Jetzt wird die gewünschte Kunden-ID/Name/Adresse/Telefonnummer abgefragt, die der Kunde erhalten soll:
   
   ![image](https://github.com/user-attachments/assets/b3dd998a-8eee-4a47-ad5f-cbc893c92362)

5. Nun wurde ein Kunde angelegt, jedoch noch ohne Auto. Daher legen wir nun ein Auto für den Kunden an. Dabei kann der Kunde selbstverständlich mehrere Autos besitzen:

![image](https://github.com/user-attachments/assets/0a3ce38a-b4db-48bf-9b6d-9d36cf53a20d)

6. Nun gelangt man in das sogenannte CarMenu:
   
![image](https://github.com/user-attachments/assets/a4aae0f1-2688-49ea-8bd2-343e90315933)

7. Hier wählt man einfach die Option 3 aus:

![image](https://github.com/user-attachments/assets/f10b068c-3005-4324-a0f6-7e017d62462e)

8. Nun wird natürlich gefragt, für welchen Kunden das Auto hinzugefügt werden soll. Da wir den Kunden mit der ID 1 erstellt haben, wählen wir diesen aus:

![image](https://github.com/user-attachments/assets/9ea53bed-8b40-4065-b154-30a9d3ee2f7f)

9. Nun werden hier die Auto-ID, das Modell, die Marke und das Kennzeichen abgefragt:

![image](https://github.com/user-attachments/assets/555cc6bd-be25-4b81-8a76-1a8b5daee7e7)

10. Da wir nun einen Kunden mit einem Auto hinzugefügt haben, können wir nun einen Service für das Auto anlegen:

![image](https://github.com/user-attachments/assets/9fb3d4ef-8bba-4d6e-8a91-ebe8a2f50ca0)

11. Hier wählt man die Option 1, um einen neuen Service für das Auto hinzuzufügen. Zudem legt man fest, für welches Auto der Service erstellt werden soll:

![image](https://github.com/user-attachments/assets/2ac81c7b-4a16-4c12-9781-c7855a0da9fe)

12. Nun wählen wir die Option 2, um den Audi des Kunden auszuwählen:
    
![image](https://github.com/user-attachments/assets/5fb8644a-29d3-404e-ae48-57376dc5a8de)

13. Hier gibt man nun die neue Service-ID an und wählt den Service-Typen „Reifenwechsel“ aus:

![image](https://github.com/user-attachments/assets/f1c04247-010d-42d7-88c1-4eece979f08d)

14. Nun kann man auch eine Beschreibung eingeben, falls nötig. Nachdem der Service erfolgreich hinzugefügt wurde, befindet man sich wieder im Hauptmenü:

![image](https://github.com/user-attachments/assets/a7dced85-db58-40b9-a5de-1846780af5f4)

## Bekannte Fehler / Einschränkungen  
Eine Einschränkung besteht darin, dass die ID von manchen Komponenten nicht automatisch inkrementiert wird. Daher muss in diesen Fällen die ID manuell angegeben werden.
