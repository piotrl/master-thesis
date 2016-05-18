## Wstęp do pracy

>“Without deviation from the norm, progress is not possible.” 
>― Frank Zappa

Muzyka ma bardzo duży wpływ na to, jak się czujemy, potrafi wprowadzić w nastrój i pobudzić. Zdarza się też, że nie pozwala się skupić i wprowadza w stan rozkojarzenia.

Celem pracy jest stworzenie aplikacji analizującej dane z różnych źródeł, by odpowiedzieć na pytanie: 
*Czy rodzaj słuchanej muzyki wpływa na czynności wykonywane przy komputerze?*

### Zawartość pracy
Praca będzie składała się z aplikacji internetowej podzielonej na trzy części:
- **back-end**

    Jedna aplikacja zbiera dane, łączy je i analizuje.
    Muzykę na kilka predefiniowanych kategorii,
    które będą wykorzystane w badaniu i przydzielanie im ilości spędzonego produktywnie czasu.

- **API**

    Służące bezpośrednio do komunikacji z front-endem, przetwarza już przeanalizowane dane przez back-end.
    Dzieli dane na konkretnych użytkowników systemu.

- **front-end**:

    Druga aplikacja zapewni wizualizację danych w postaci statystyk,
    pomagając badaczowi szukanie wzorców.

### Zakończenie badania
Sukcesem można nazwać wynik, po którym można stwierdzić,
jaka muzyka koreluje z tzw. *attention span<sub>[1]</sub>*.

Praca nie ma na celu udowodnienia wyniku, tym bardziej sprawdzać go na dużej populacji ludzi.
Praca ma zaoferować narzędzie pozwalające mierzyć jeden z wielu współczynników mogących mieć wpływ na produktywność.

## Dodatkowe informacje
#### Podział muzyki na kategorie
Na podstawie prezentacji [TED](https://www.youtube.com/watch?v=BBCjijl105I)
stwierdziłem kilka potencjalnych gatunków muzyki, na które warto zwrócić uwagę w badaniu.

- Potencjalnie złe
  - Zawierające wokal
  - Instrumenty brzmiące jak ludzki głos
    - wiolonczela
    - saksofon
  - Elektryczna gitara
- Potencjalnie dobre
  - Elektro
  - Muzyka klasyczna

#### Start projektu
Praca magisterska jest kontynuacją prowadzonego przeze mnie hobbystycznego projektu.
Obecnie aplikacja jest w stanie agregować dane
z [ResecueTime](https://www.rescuetime.com/) i [Last.fm](http://www.last.fm/)
oraz stwierdzać jaka muzyka była grana podczas danej czynności w czasie.
Aplikacja nie jest w stanie stwierdzić, jaki moment w czasie to *attention span<sub>[1]</sub>*

#### Przypisy
[1] *attention span* - moment w którym jesteśmy maksymalnie skoncentrowani, często nazywane: *flow*
