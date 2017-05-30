## Analiza wpływu nawyków muzycznych na aktywności wykonywane przy komputerze

#### Piotr Lewandowski

### Słowa Kluczowe
Aggregation, Data Integration, Visualisation, API, PostgreSQL, Java, JavaScript

### Streszczenie
W ramach pracy magisterskiej napisano aplikację internetową,
wdrożoną na chmurze Digital Ocean pod adresem http://mgr.piotrl.net:8080
z przygotowanymi danymi testowymi (user: test, password: test).

Aplikacja umożliwia import danych użytkownika z dwóch serwisów,
RescueTime — lista aktywności oraz
Last.fm — lista odsłuchiwanych utworów.
Pobrane dane są połączone na wspólnej osi czasu i wizualizowane pod różnymi względami za pomocą wykresów oraz tabel.

Mechanizm agregacji napisany jest w języku Java i frameworku Spring,
dane przechowywane są w bazie danych PostgreSQL,
a warstwa wizualna została stworzona w frameworku Material Design oraz JavaScript,
do generowania wykresów użytko biblioteki Google Charts.

Kod znajduje się w prywatnym repozytorium GIT (pod adresem http://github.com/piotrl/master-thesis),
pytania o dostęp lub o pracę można kierować na mail: mail@piotrl.net. 

### Spis treści
0. Wprowadzenie
1. Import danych
    1. Wybór dostawców danych
    2. Mechanizm importu danych
    3. Komunikacja z API    
3. Analiza danych
    1. Opis zebranych danych
    2. Korekta
    3. Integracja źródeł danych
4. Wizualizacja danych
    1. Budowa raportów na podstawie szeregów czasowych
    2. Wybór metod wizualizacji danych
4. Architektura
    1. Testy jednostkowe
    2. Stos technologiczny

### Wprowadzenie

Właśnie słucham playlisty z muzyką która pomaga mi się skupić podczas pisania tego wstępu.
Gdy programuję, zwykle słucham muzyki innej niż podczas pisania ---
w pracy, by się odciąć od zewnętrznych dźwięków, a w domu, by się odpowiednio nastroić.
Gdy mam trudny problem do rozwiązania to preferuję absolutną ciszę.

Podobnie jak ponad 36 tysięcy innych programistów odpowiadających na pytania w corocznej ankiecie StackOverflow.
W pytaniu "Ideal Auditory Environment for Coding"
więszkość badanych słucha muzyki, duży odsetek preferuje skrajną ciszę w pracy, natomiast niewielu preferuje rozpraszacze w tle:

- 59.6\% --- Turn on some music
- 24.2\% --- Keep the room absolutely quiet
- 7.1\% --- Put on some ambient sounds (e.g. whale songs, forest sounds)
- 3.5\% --- Something else
- 3.2\% --- Put on a movie or TV show
- 2.3\% --- Turn on the news or talk radio

Will Henshall na konferencji TEDx stwierdza, że m.in. muzyka klasyczna i Elektro pozytywnie sprzyjają na skupienie,
natomiast instrumenty brzmiące jak ludzki głos, wokal i elektryczna gitara rozpraszają naszą uwagę.
Wideo obejrzano ponad 400 tysięcy razy, a koncepcja została rozwinięta w projekcie
Focus@Will.

Próbuję zweryfikować te założenia przez stworzenie aplikacji, która analizuje moje własne aktywności i muzykę, którą słucham.
Jednym z mierników, którym się posługuję jest
"Attention Span".
Dla uproszczenia tego projektu, przyjmuję założenie, że "Attention Span" to czas od początku rozpoczęcia zadania do momentu zmiany aktywnego okna.

Celem pracy jest opis pełnego procesu budowy aplikacji opartej o analizę danych wraz z ich interpretacją i wizualizacją.

Opis zaczynam od mechanizmu importu danych z zewnętrznych źródeł, w którym charakteryzuję sposób komunikacji z dostawcami API
oraz definiuję problemy, którym należy stawić czoła przy budowie agregatora danych.

W drugim rozdziale przeprowadzam analizę zebranych danych, zaczynając od zapoznania specyfiki struktury danych w bazie
oraz przeprowadzenia ogólnej statystki, by zobrazować skalę, po której się poruszam.
Opisałem, w jaki sposób przeprowadzam korektę w przypadku duplikatów, niepełnych lub błędnych danych.

Rozdział trzeci zawiera przedstawienie zwizualizowanych efektów analizy w postaci wykresów i screenshotów z aplikacji.
Definiowaniuję sposób budowania zapytań SQL, by zwracały odpowiednio uszeregowane dane w czasie z wybranym interwałem.

Rozdział czwarty poświęcam architekturze projektu, użytych narzędzi oraz instrukcję uruchomienia projektu lokalnie lub na serwerze.

Rysunki i tabele są opracowania własnego, chyba że oznaczyłem inaczej.