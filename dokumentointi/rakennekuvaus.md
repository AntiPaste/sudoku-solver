## Rakennekuvaus

Pääluokassa luodaan Sudoku-lauta (Board), Sudoku-ratkoja (Solver) ja käyttöliittymä (UserInterface).  
Lauta voi olla tyhjä, tai ennaltamääritelty jolloin käytetään lukijaa (Parser) joka muuntaa tekstimuotoisen laudan objektiksi.

Lauta vain pitää kirjaa ruuduista (Tile). Ruutujen muokkaaminen tapahtuu ruutuluokan metodien kautta.  
Ruudulla on koordinaatit (x, y), numero ja lista numeroista jotka ruutuun voidaan sääntöjen mukaan laittaa.  
Koordinaattien käsittelyn helpottamiseksi on luotu koordinaattiluokka (Coordinate).

Ratkojassa tapahtuu kaikki jännä. Ratkojalla on muistissaan lauta jota käsitellään.  
Ratkoja voi resetoida laudan tilanteen mikäli käyttäjä keskeyttää ratkomisen.  
Ratkojan tehtävä on päivittää ruutujen mahdollisia numeroita, sekä käyttää useita metodeja pelin ratkomiseksi.

