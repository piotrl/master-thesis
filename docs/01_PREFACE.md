## Wstęp do pracy

>“Without deviation from the norm, progress is not possible.” 
>― Frank Zappa

Muzyka ma wpływ na to jak się czujemy, potrafi wprowadzić w nastrój i pobudzić. 
Zdarza się, że nie pozwala się skupić i wprowadza w stan rozkojarzenia.

Celem pracy jest stworzenie aplikacji analizującej dane muzyczne z różnych źródeł, by odpowiedzieć na pytanie: 
*Czy rodzaj słuchanej muzyki wpływa na aktywności wykonywane przy komputerze?*

### Architektura aplikacji

Praca będzie składała się z aplikacji internetowej podzielonej na trzy części:
- **back-end**

    Jedna aplikacja która zbiera dane, łączy je i analizuje.
    Muzykę na kilka predefiniowanych kategorii,
    które będą wykorzystane w badaniu i przydzielanie im ilości spędzonego produktywnie czasu.

- **API**

    Służące bezpośrednio do komunikacji z front-endem, przetwarza już przeanalizowane dane przez back-end oraz łączy je w żądany sposób.
    Dzieli dane na konkretnych użytkowników systemu.

- **front-end**:

    Druga aplikacja zapewni wizualizację danych w postaci statystyk,
    pomagając badaczowi szukanie wzorców.

### Zakończenie badania
Sukcesem można nazwać wynik, po którym można stwierdzić, jaka muzyka koreluje z tzw. *attention span<sub>[1]</sub>*.

Praca nie ma na celu udowodnienia wyniku, tym bardziej sprawdzać go na dużej populacji ludzi.
Praca ma zaoferować narzędzie, pozwalające mierzyć jeden z wielu współczynników mogących mieć wpływ na produktywność oraz przedstawić przykładowe badanie autora na jego własnych nawykach. 

## Dodatkowe informacje
#### Podział muzyki na kategorie
Na podstawie prezentacji [TED](https://www.youtube.com/watch?v=BBCjijl105I), stwierdzam kilka potencjalnych gatunków muzyki, na które warto zwrócić uwagę przy badaniu.

- **Potencjalnie złe**
  - Zawierające wokal
  - Instrumenty brzmiące jak ludzki głos
    - wiolonczela
    - saksofon
  - Elektryczna gitara
  
- **Potencjalnie dobre**
  - Elektro
  - Muzyka klasyczna
  
#### Żródła danych
Dane muzyczne agregowane będą z API serwisu [last.fm](http://www.last.fm/), natomiast aktywności wykonywane na komputerze będą pobierane z serwisu [RescueTime](https://www.rescuetime.com/).
Do przeprowadzenia badania, wymagane jest zainstalowanie programów do obsługi wyżej wymienionych serwisów.

#### Przypisy
[1] *attention span* - moment w którym jesteśmy maksymalnie skoncentrowani, często nazywane: *flow*
