# Новый функционал:

1. Переписанный проект web library с использованием Hibernate и Spring Data JPA. Реализованы
сущности @Entity Книга и Человек, репозитории и сервисы. PersonDAO и
BookDAO пустые и не используются, вся работа с БД через сервисы.

2. Добавлена пагинация для книг: книг может быть много и они могут не помещаться на одной странице в
браузере. Чтобы решить эту проблему, метод контроллера умеет
выдавать не только все книги разом, но и разбивать выдачу на страницы. Метод контроллера принимает в адресной строке два
ключа: page и books_per_page. Первый ключ сообщает, какую страницу мы
запрашиваем, а второй ключ сообщает, сколько книг должно быть на одной странице.
Нумерация страниц стартует с 0. Если в адресной строке не передаются эти ключи, то
возвращаются как обычно все книги.

3. Добавлена сортировка книг по году. Метод контроллера умеет
выдавать книги в отсортированном порядке. Метод контроллера принимает в адресной строке ключ
sort_by_year. Если он имеет значение true, то выдача должна быть отсортирована
по году. Если в адресной строке не передается этот ключ, то книги возвращаются в
обычном порядке.

> ***Пагинация и сортировка могут
работать одновременно (если
передаются сразу три параметра в
запросе)***

4. Создана страница поиска книг. При вводе в поле на странице начальных букв
названия книги, получаем полное название книги и имя автора. Также, если
книга сейчас находится у кого-то, получаем имя этого человека.

5. Добавлена автоматическая проверка на то, что человек просрочил возврат
книги
