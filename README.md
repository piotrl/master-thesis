## Analiza wpływu nawyków muzycznych na aktywności wykonywane przy komputerze

#### Piotr Lewandowski

### Słowa Kluczowe:
Attention span, Data mining, Machine learning, Visualisation, JavaScript, Python, MongoDB, last.fm, RescueTime

### Streszczenie
Istnieją serwisy, które pozwalają na odtwarzanie muzyki wspomagającej koncentrację i ogólną produktywność.
Bazują one na badaniach naukowych wskazujących sprzyjające temu gatunki muzyki, lecz playlisty są zawsze predefiniowane z określonego zbioru.

Celem pracy jest stworzenie aplikacji, która będzie w stanie wspomóc użytkownika w określeniu jego nawyków muzycznych i wskazania muzyki, która pozytywnie wpływa na produktywność wykonywanych aktywności. Aplikacja analizuje dane agregowane z zewnętrznych API.

Dodatkowo autor przeprowadzi badanie na samym sobie, celem potwierdzenia ogólnie przyjętych gatunków muzycznych jako produktywne. Badanie będzie podzielone na aktywności wykonywane na komputerze domowym i firmowym, skutkiem czego będzie stwierdzenie czy muzyka zawsze wpływa na jakość wykonywanych zadań.

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
- Applied Predictive Modeling, by Kjell Johnson and Max Kuhn

**API documentations**:
- [Last.fm](http://www.last.fm/api) - Music agregation service
- [RescueTime](https://www.rescuetime.com/anapi/setup/documentation) - Follows computer activities and rates their productivity score

**Libraries**:
- [scikit-learn](http://scikit-learn.org/stable/index.html) - data mining and data analysis in Python
- [D3.js](https://d3js.org/) - JavaScript data visualisation library
- [AngularJS](https://docs.angularjs.org/api) - JavaScript front-end framework
- [NodeJS](https://nodejs.org/) - JavaScript back-end framework
