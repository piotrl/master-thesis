## Analiza wpływu nawyków muzycznych na aktywności wykonywane przy komputerze

#### Piotr Lewandowski

### Słowa Kluczowe:
API, Aggregation, Data Integration, Data Visualisation, Attention span, PostgreSQL, JavaScript, Java

### Streszczenie
Istnieją badania, które starają się wskazać wpływ muzyki na aktywność naszego mózgu. Wskazują one konkretne cechy utworów wpływające na nasz nastrój. Wiele serwisów internetowych na podstawie tych badań proponuje tematyczne playlisty — na relaksujący wieczór z herbatą, pozytywny poranek, pozwalające się mocniej skupić.

Motyw przewodni tej pracy łączy się z ostatnią wymienioną grupą utworów — Muzyka wpływająca na naszą produktywność. Na podstawie historii odtwarzanych utworów oraz aktywności na komputerze, wizualizuję produktywne okresy naszych działań oraz powiązane z nimi gatunki muzyczne.

Celem pracy jest przedstawienie oraz uzasadnienie rozwiązań problemów, których trzeba być świadomym podczas budowania serwisów z dużą ilością danych, które w dużej mierze pochodzą z zewnętrznych API. Między innymi zadanie agregacji oraz przetworzenia danych w celu ich wizualizacji.

Jako przykład działania aplikacji, przedstawiam przykładowe badanie własnych aktywności, podzielone na czas spędzony w domu oraz w pracy.

Aplikację dzielę na trzy części
    
- front-endową w modelu MVC, która komunikuje się z serwerem WebAPI, która wizualizuje dane na wykresach
- WebAPI — Stworzone w Javie z użyciem lekkiego frameworka ``Spring boot``, przystosowująca dane pod wymagania prezentacji
- Aggregation service — Główny mechanizm pobierania danych, zapisywanych w relatywnej bazie danych PostgreSQL

### Spis treści
1. Wstęp
  1. Wpływu muzyki na produktywność
  2. Przepływ danych w aplikacji
2. Agregacja danych
  1. Wybór dostawców danych
  2. Cechy dobrego mechanizmu agregacji
    1. Niezależność
    2. Ciągłość
    3. Powtarzalność
  3. Powszechne problemy przy komunikacji z API
    1. Orgraniczenia
    2. Autoryzacja
    3. Braki dostępu    
3. Analiza danych
  1. Opis zebranych danych
  2. Data fusion - Łączenie danych w czasie
    1. Korekta
    2. Integracja dwóch źródeł danych
  2. Budowa raportów na podstawie metod statystycznych
4. Wizualizacja danych
  1. Jeden zbiór danych - wiele interpretacji
  2. Wybór odpowiednich metod wizualizacji danych
  3. Analiza danych z punktu widzenia użytkownika
5. Zakończenie

### Wstęp do pracy
- [Wstęp do pracy](docs/01_PREFACE.md)

### Literatura

**Książki**:
- Do uzupełnienia 

**Inne źródła**:
- [Lista stron i artykułów](./docs/00_RESOURCES.md)

**Dokumentacja API**:
- [Last.fm](http://www.last.fm/api) - Music agregation service
- [RescueTime](https://www.rescuetime.com/anapi/setup/documentation) - Follows computer activities and rates their productivity score

**Użyte biblioteki**:
- [D3.js](https://d3js.org/) - JavaScript data visualisation library
- [Vue.js](http://vuejs.org/) - Component based JavaScript framework
- [Spring Framework](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/) - Java web framework
- [PostgreSQL](https://www.postgresql.org/docs/) - Object-relational database system
