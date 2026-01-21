## Hva jeg trenger (domene-messig):
- Kunde
- Produkt
- Bestilling

## Tanker:
- Én-til-mange kobling mellom kunde og bestilling.
- I en bestilling vil jeg lagre info om alle produktene som er i den bestillingen, dvs mange-til-mange kobling mellom bestilling og produkt. En bestilling kan ha flere produkter og et produkt kan være involvert i flere bestillinger.
- Den mest dynamiske tabellen vil være bestilling-tabellen. Kunde og produkt vil være mer "oppslags-tabeller"
- Jeg velger at DB oppretter egne PKer for tabellene, men at jeg bruker mobilnr som ID for bruker i domenet, og bruker produktNr for produkt
- Tenker å starte med et simpelt Controller, Service, Repo pattern
- Usikker per nå om jeg trenger DTO mtp hva jeg viser ut til bruker av info om produktet