# Übung 1

## Aufgabe 1

### Teilaufgabe 1

**Frage:** Wie kann diese Kommunikationsverbindung nun dennoch mit Hilfe einer zusätzlichen „Fabrik“-Klasse, welche die dazu notwendige Objekt-Erzeugung übernimmt, aufgebaut werden?
In welchem Package sollte diese zusätzliche Klasse liegen?
Bitte beachten Sie dabei auch die Hinweise bzw. Anforderungen aus den Kommentaren der Klassen, Methoden und des Interfaces.
So sollte z.B. auch das Datum der Erzeugung eines Translator-Objekts gesetzt werden.

**Antwort:** Die Kommunikation zwischen 'Client' und 'GermanTranslator' kann dadurch gewährleistet werden, dass die neue Fabrik-Klasse im Package 'control' die Erzeugung der benötigten 'Translator'-Objekte übernimmt.

---

**Frage:** Welches Entwurfsmuster (engl.: design pattern) könnte für die Problematik der Objekt-Erzeugung verwendet werden?
Was ist der software-technische Nutzen bei der Verwendung des Entwurfsmusters?
Gratistipp: Hinweise für das korrekte Pattern finden sie bei unten angegeben Video-Tutorien ;-)
    
**Antwort:** Hierzu kann das sogenannte 'Factory method pattern' genutzt werden.
Dadurch wird die Flexibilität erhöht, indem der Client-Code von der Objekterzeugung entkoppelt wird, jedoch die Methoden dieser Objekte darin (spezifisch nach Bedarf) weiterhin nutzbar bleiben.

---

**Frage:** Wie muss man den Source Code des Interface ggf. anpassen, um mögliche auftretende Kompilierfehler zu beseitigen?

**Antwort:** Das Interface 'Translator' sollte auf 'public' gesetzt werden, damit auch in der Klasse 'Client' im Package 'view' streng gegen dieses Interface implementiert werden kann.

---

### Teilaufgabe 3

**Frage:** Was ist der Vorteil einer separaten Test-Klasse?

**Antwort:** Die Trennung von Produktions- und Testcode, sowie bessere Organisation und Wartbarkeit. Außerdem entsteht so die Möglichkeit, die Tests unabhängig vom Produktionscode auszuführen.

---

**Frage:** Was ist bei einem Blackbox-Test der Sinn von Äquivalenzklassen?

**Antwort:** Die Anzahl der möglichen Testfälle wird reduziert, ohne dabei jedoch relevante Szenarien unberücksichtigt zu lassen, seien es Grenzwerte, oder Eingaben im typischen Wertebereich.

---

**Frage:** Warum ist ein Blackbox-Test mit JUnit auf der Klasse Client nicht unmittelbar durchführbar?

**Antwort:** Die Methode 'display(int)' in der Klasse 'Client' besitzt keinen Rückgabewert und die Ausgabe erfolgt direkt auf der Konsole, dies erschwert das Testen mit JUnit-Assertions.
Außerdem ist das separate Testen von Objekten wie 'GermanTranslator' in Klassen wie 'Client' nicht möglich, da hier die zu testenden Methoden wiederum in eigenen (hier: 'Client'-) Methoden eingebunden werden.
