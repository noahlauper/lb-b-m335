# lb-b-m335

Das ist die android App KilometerKönig. Diese App Zählt die gelaufenen Schritte und gibt diese wieder. Pro gelaufenen Schritt kriegt man Level und bei 1000 erreichten Lvl kriegt man einen Pokal. 

Die App sollte auf einem Smartphone getestet werde, da es im Emulator keinen Step Counter Sensor gibt. 


## Änderungen gegenüber der Planung
ich konnte alle Klassen Einbauen welche ich verwenden wollte, jedoch habe ich um die Datenspeicherung zu gewährleiten noch files für meine Datenbank gebraucht. Folgende Klassen habe ich noch hinzugefügt: MeasurementDao und AppDatabase. 
Zudem habe ich die Sensorlogik von der Activity in den Sensor getan um die weiterzählung der Schritte im Hintergrund zu machen.
