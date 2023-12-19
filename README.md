# easyPermissionsPORA

## Kaj je to?
Easy permissions je android knjižnica namenjena za hitro in enostavno spremljanje in obveščanje uporabnikom glede potrebnih dovoljenj, 
ki jih Vaša aplikacija nujno potrebuje za delovanje. 

## Zakaj je dobra ta knjižnica?
Glavna prednost te knjižnice je enostavnost uporabe. Ta knjižnica sama po sebi ne vsebuje veliko funkcionalnosti, vendar pa tiste, ki jih omogoča so 
enostavne za implementirati in zelo koristne v splošnem za vsako malo kompleksnejšo aplikacijo. 

## Funkcionalnosti, ki jih knjižnica omogoča
- Preverja rezultate  dovoljenj, ki jih je izbral uporabnik (prekrije metodo onRequestPermissionsResult)
- implementirano ima metodo, ki preveri, če je uporabnik dovolil navedena dovoljenja
- implementirano ima metodo, ki preveri, če katero izmed navedenih dovoljenj ni bilo dovoljeno, in izvede neko kodo v tem primeru
- uporabnika lahko preusmeri na mesto znotraj nastavitev, kjer ima ta aplikacija podatke oziroma dovoljenja
- omogočeno je prilaganje izgleda z dovoljenji

## Glavne prednosti
- Enostavnost uporabe
- Uporabniku lahko prikaže vsa dovoljenja, ki jih želimo naenkrat
- Enostavno lahko preverimo, če so dovoljenja omogočena
- Izpiše lahko obvestilo, če katero izmed dovoljenj, ki so nujno potrebna za delovanje, ni bilo omogočeno
- uporabnika lahko preusmeri na stran aplikacije znotraj nastavitev
- hkrati lahko preverjamo za več dovoljenj
- prilagodimo si lahko izgled sporočila
- uvoziti je potrebno samo eno knjižnico
- verzija knjižnice tako za programski jezik Java, kot tudi za Kotlin

## Glavne slabosti
- Uporablja se lahko le za android OS, ki so večji ali enaki verziji M (Marshmallow) - Android 6
- ne more točno razlikovati med "dovoljenja onemogočena za vedno" in "dovoljenja onemogočena", dokler najprej niso bila onemogočena in šele nato onemogočena za vedno
  (omejen s strani Androida)
- nekaj let že ni bila posodobljena

## Statistika podaktov iz repozitorija
- 2 verziji aplikacije: Klasična (namenjena za Javo) in Kotlin (ktx knjižnica)
- V lasti s strani Googla
- Skupaj ima 26 ljudi, ki je prispevalo k izdelavi te knjižnice
- 12 release verzij za Javo (trenutno v3.0.0) in 1 verzija za Kotlin (trenutno v1.0.0)
- Java knjižnica ima zadnji commit 26.4.2021 in zadnji release iz 23.1.2019
- Kotlin knjižnica ima zadnji commit 18.1.2022 in zadnji release iz 4.5.2021
- Java verzija knjižnica implementirana v Javi, Kotlin verzija pa v Kotlinu
- Java verzija je 185. na Android Weekly, Kotlin verzija pa 446.
- Java verzija ima 184 commitov, Kotlin pa 192 (pri čemer izvira iz Java knjižnice)
- Vsega skupaj je 17 vej in ima 9.8 zvezdic na Github-u


