## Analiza wpływu nawyków muzycznych na aktywności wykonywane przy komputerze

#### Piotr Lewandowski

### Słowa Kluczowe:
API, Aggregation, Data Integration, Data Visualisation, Attention span, PostgreSQL, JavaScript, Java

### Streszczenie
Istnieją badania, które starają się wskazać wpływ muzyki na aktywność naszego mózgu. Wskazują one konkretne cechy utworów wpływające na nasz nastrój. Wiele serwisów internetowych na podstawie tych badań proponuje tematyczne playlisty — na relaksujący wieczór z herbatą, pozytywny poranek, pozwalające się mocniej skupić.

Motyw przewodni aplikacji łączy się z ostatnią wymienioną grupą utworów — Muzyka wpływająca na naszą produktywność. Na podstawie historii odtwarzanych utworów oraz aktywności na komputerze, wizualizuję produktywne okresy naszych działań oraz powiązane z nimi gatunki muzyczne.

Celem pracy jest przedstawienie oraz uzasadnienie rozwiązań problemów, których trzeba być świadomym podczas budowania serwisów z dużą ilością danych, które w dużej mierze pochodzą z zewnętrznych API. Między innymi zadanie agregacji oraz przetworzenia danych w celu ich wizualizacji.

Jako przykład działania aplikacji, przedstawiam przykładowe badanie własnych aktywności, podzielone na czas spędzony w domu oraz w pracy.

### Spis treści
1. Wstęp
2. Rozdział I — Analiza problemu
  1. Przedstawienie problemu
  2. Opis źródeł danych
3. Rozdział II — Opis proponowanego rozwiązania
  1. Opis użytych technologii
  2. Diagram architektury aplikacji
  3. Problem łączenia danych w czasie
4. Rozdział III — Eksperymenty na podstawie zebranych danych
  1. Zastosowanie prostych mechanizmów statystycznych
  2. Zastosowanie algorytmów klasyfikacji machine learning
5. Rozdział IV — Prezentacja efektów
  1. Prezentacja aplikacji
  2. Prezentacja zgromadzonych wykresów
6. Zakończenie

### Wstęp do pracy
- [Wstęp do pracy](docs/01_PREFACE.md)

### Literatura

**Books**:
Not yet. 

**API documentations**:
- [Last.fm](http://www.last.fm/api) - Music agregation service
- [RescueTime](https://www.rescuetime.com/anapi/setup/documentation) - Follows computer activities and rates their productivity score

**Libraries**:
- [D3.js](https://d3js.org/) - JavaScript data visualisation library
- [Vue.js](http://vuejs.org/) - Component based JavaScript framework
- [Spring Framework](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/) - Java web framework
- [PostgreSQL](https://www.postgresql.org/docs/) - Object-relational database system
