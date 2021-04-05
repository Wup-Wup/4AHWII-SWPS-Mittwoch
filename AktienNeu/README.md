Das Programm selbst liest mit einer API Aktien Dateb ab, speichert diese in eine Datenbank und gibt sie als Chart aus. Diesen Chart wird als PNG abgespeichert.

Um das Programm starten zu können muss man folgende Libraries über die Links runterladen und einbinden:

  https://www.jfree.org/jfreechart/
  
  https://mvnrepository.com/artifact/mysql/mysql-connector-java
  
  https://mvnrepository.com/artifact/org.json/json
  
  https://commons.apache.org/proper/commons-io/
 
 
Im Programm findet man drei Textdateien in welche man folgende Sachen einfügen muss:

  In die "Key.txt" Datei muss man seinen API-Key einfügen. Diesen erhält man auf https://www.alphavantage.co.
  
  In die "Path.txt" Datei muss man den Pfad wo die Ordner mit den Bildern der Aktienkurse hingespeichert werden soll.
  
  In die "Akrienkurse.txt" Datei muss man reinschreiben von welchen Aktienkurse man die Daten haben möchte. Dazu muss man die Abkürzung der Aktie reinschreiben. 1 Aktie pro Zeile.
  

Hat man alles richtig gemacht erhält man das das Bild von dem jeweiligen Aktienkurs den man angegeben hat.

Ist der letzte Vlose Wert höher wie der 200Schnitt vom letzten Tag, ist der Hintergrund der Aktie Grün.
![2021-04-05](https://user-images.githubusercontent.com/55537968/113581289-40cd0100-9627-11eb-89cf-e57bf405fdec.png)

Ist dies nicht der Fall und der letzte Close Wert ist kleiner wie der 200Schnitt ist der Hintergrund rot.
![2021-04-04](https://user-images.githubusercontent.com/55537968/113581404-622ded00-9627-11eb-9c8e-16997275c961.png)
