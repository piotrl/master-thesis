## Analiza wpływu nawyków muzycznych na aktywności wykonywane przy komputerze

#### Piotr Lewandowski

### Słowa Kluczowe
API, Aggregation, Data Integration, Data Visualisation, Attention span, PostgreSQL, JavaScript, Java

### Streszczenie
W ramach pracy magisterskiej napisano aplikację internetową, wdrożoną w chmurze Digital Ocean pod adresem http://tbd.digitalocean.com/ z przygotowanymi danymi testowymi pod kontem (user: test, login: test). 

Aplikacja umożliwia agregację danych użytkownika z dwóch serwisów, RescueTime — lista aktywności oraz Last.fm — lista odsłuchiwanych utworów. Pobrane dane są połączone na wspólnej osi czasu i wizualizowane pod różnymi względami za pomocą wykresów oraz tabel.  

Mechanizm agregacji napisany jest w języku Java i frameworku Spring, dane przechowywane są w bazie danych PostgreSQL, a warstwa wizualna została stworzona w JavaScript, z użyciem biblioteki generującej wykresy — C3.js oraz frameworka Material Design.

Kod znajduje się w prywatnym repozytorium GIT (pod adresem https://github.com/piotrl/master-thesis), pytania o dostęp lub o pracę proszę kierować na mail: poczta@piotrl.net. 

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
  2. Budowa raportów na podstawie szeregów czasowych
4. Wizualizacja danych
  1. Jeden zbiór danych - wiele interpretacji
  2. Wybór odpowiednich metod wizualizacji danych
  3. Analiza danych z punktu widzenia użytkownika
5. Zakończenie

### Wstęp do pracy
- [Wstęp do pracy](docs/01_PREFACE.md)

### Źródła
- [Źródła](docs/00_RESOURCES.md)
