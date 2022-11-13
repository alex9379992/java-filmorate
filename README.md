# java-filmorate  
Здравствуйте. Данное приложение помогает хранить информацию о фильмах и дружить пользователям. Тут имеются следующие классы:  
1) [ER-диаграмма](C:\Users\alexx\IdeaProjects\java-filmorate\Filmorate ER-diagram.png) :
    Описывает схему базы данных приложения.  
2) [FilmConroller](C:\Users\alexx\IdeaProjects\java-filmorate\src\main\java\ru\yandex\practicum\filmorate\controllers\FilmController.java) : Описывает работу API, связанную с фильмами.
3) [UserController](C:\Users\alexx\IdeaProjects\java-filmorate\src\main\java\ru\yandex\practicum\filmorate\controllers\UserController.java) :
   описывает работу API, связанную с пользователями.
4) [SearchException](C:\Users\alexx\IdeaProjects\java-filmorate\src\main\java\ru\yandex\practicum\filmorate\exeptions\SearchException.java):
   Исключение,для отлавливания ошибок, связанных с поиском.
5) [ValidationException](ru/yandex/practicum/filmorate/exeptions/ValidationException.java) :
   Исключение,для отлавливания ошибок, связанных с валидацией обьектов.
6) [FriendshipStatusesType](ru/yandex/practicum/filmorate/model/enums/FriendshipStatusesType.java) : 
    Перечисление статусов друзей.
7) [GenresType](ru/yandex/practicum/filmorate/model/enums/GenresType.java) : 
   Перечисление жанров для фильмов.
8) [RatingType](ru/yandex/practicum/filmorate/model/enums/RatingType.java) :
   Перечисление рейтингов для фильмов.
9) [Film](ru/yandex/practicum/filmorate/model/Film.java): Класс, описывающий модель фильма.
10) [User](ru/yandex/practicum/filmorate/model/User.java): Класс, описывающий модель пользователя.
11) [FilmService](ru/yandex/practicum/filmorate/service/FilmService.java): Класс, описывающий непоследственную работу приложения, связанную с фильмами.
12) [UserService](ru/yandex/practicum/filmorate/service/UserService.java): Класс, описывающий непоследственную работу приложения, связанную с пользователями.
13) [FilmStorage](src/main/java/ru/yandex/practicum/filmorate/storage/FilmStorage.java): Интерфейс, описывающий хранение фильмов.
14) [InMemoryFilmStorage](ru/yandex/practicum/filmorate/storage/InMemoryFilmStorage.java): Класс, имплементирующий интерфейс FilmStorage.
15) [UserStorage](ru/yandex/practicum/filmorate/storage/UserStorage.java): Интерфейс, описывающий хранение пользователей.
16) [InMemoryUserStorage](ru/yandex/practicum/filmorate/storage/InMemoryUserStorage.java): Класс, имплементирующий интерфейс UserStorage.
17) [FilmValidator](ru/yandex/practicum/filmorate/validators/FilmValidator.java): Класс, который производит проверку фильма по определенным параматрам.
18) [UserValidator](ru/yandex/practicum/filmorate/validators/UserValidator.java): Класс, который производит проверку пользователя по определенным параматрам.