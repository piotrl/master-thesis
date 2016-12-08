## Wstęp do pracy

>“Without deviation from the norm, progress is not possible.” 
>― Frank Zappa

Obecny styl życia i pracy idzie w kierunku coraz większej dynamiki. 
Nadmiar ogólnodostępnej informacji sprzyja coraz krótszemu *attention span<sub>[1]</sub>*.
Powszechnie uważa się, że obecnie jest on na poziomie 8 sekund.

Często na problem z utrzymaniem dłuższego skupienia szuka się pomocy w muzyce. 
Świadczą o tym liczne publikacje, proponujące gatunki muzyki lub konkretne playlisty
mające sprzyjać produktywności i skupieniu.

Na ich podstawie można zwrócić uwagę na potencjalnie sprzyjające produktywności gatunki muzyczne:
- Muzyka klasyczna, Elektro.

Oraz na te, które przeciwnie, sprzyjające rozkojarzeniu:
 - Zawierające wokal lub instrumenty brzmiące jak ludzki głos (wiolonczela, saksofon), elektroniczna gitara.

Aplikacja internetowa, którą stworzyłem, pozwala na weryfikację tych założeń.
Użytkownik może zobaczyć wizualizację danych o odtwarzanych utworach oraz własnych aktywnościach wykonywanych na komputerze
na przestrzeni miesięcy.

Z uwagi na to, że w dużej mierze opieram działanie aplikacji o zewnętrzne źródła danych,  
w tej pracy opisuje wiele problemów, 
na jakie można się natknąć przy budowaniu produktu bazującego na danych z API.

Ostatecznie przedstawię wyniki działania na własnych danych z dwóch środowisk — pracy oraz domu.

#### Żródła danych
Dane muzyczne agregowane będą z API serwisu [last.fm](http://www.last.fm/), natomiast aktywności wykonywane na komputerze będą pobierane z serwisu [RescueTime](https://www.rescuetime.com/).
Do przeprowadzenia badania, wymagane jest zainstalowanie programów do obsługi wyżej wymienionych serwisów.

#### Przypisy
[1] *attention span* - moment w którym jesteśmy maksymalnie skoncentrowani, często nazywane: *flow*
