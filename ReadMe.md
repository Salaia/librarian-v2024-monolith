# Librarian 📚

Проект платформы для обмена мнениями о книгах с возможностью получить рекомендации для дальнейшего чтения.

Монолитная версия, до того как мне взбрели в голову микросервисы. Тут еще адекватны API и тесты.

## Функционал (кроме базового)
* Выборка самых популярных книг
* Получение индивидуальной рекомендации "что почитать". Рекомендация основана на сравнении лайков юзера и его друзей. Выбираются друзья со схожими лайками, выбираются книги, которые лайкали друзья и не читал пользователь.

<div>
<img width="1024" alt="Friendly robot-cat at a library" src="assets/librarian_robo_cat_orig.jpg">
</div>

## API (Swagger)
Три пути посмотреть API:
* прямая ссылка на SwaggerHub(на 2024.09.05 открывается без VPN) https://app.swaggerhub.com/apis-docs/lessera/Librarian/v1#/

ИЛИ
* Перейти на сайт https://editor-next.swagger.io
* импортировать файл documentation/SwaggerAPI.json
* Он же, но ссылка на GitHub: https://github.com/Salaia/librarian/blob/master/documentation/SwaggerAPI.json

ИЛИ
* Во время работы проекта API доступно по ссылке:
http://localhost:8182/swagger-ui/index.html

## Инструкция по развёртыванию ▶️
1) Склонируйте репозиторий: https://github.com/Salaia/librarian-v2024-monolith.git
2) Откройте программу Docker
3) В терминале или командной строке перейдите в папку проекта (где лежит файл docker-compose.yml) и выполните команду: docker-compose up
4) В программе Docker должны появиться 2 контейнера
5) Программа доступна по ниже описанному API по адресу: http://localhost:8182
6) Можно импортировать и гонять тесты Postman :)

## 🛠 Tech & Tools

<div>
      <img src="https://github.com/Salaia/icons/blob/main/green/Java.png?raw=true" title="Java" alt="Java" height="40"/>
      <img src="https://github.com/Salaia/icons/blob/main/green/SPRING%20boot.png?raw=true" title="Spring Boot" alt="Spring Boot" height="40"/>
      <img src="https://github.com/Salaia/icons/blob/main/green/Maven.png?raw=true" title="Apache Maven" alt="Apache Maven" height="40"/>
    <img src="https://github.com/Salaia/icons/blob/main/green/JDBC.png?raw=true" title="JDBC" alt="JDBC" height="40"/>
<img src="https://github.com/Salaia/icons/blob/main/green/PostgreSQL.png?raw=true" title="PostgreSQL" alt="PostgreSQL" height="40"/>
<img src="https://github.com/Salaia/icons/blob/main/green/Docker.png?raw=true" title="Docker" alt="Docker" height="40"/>
<img src="https://github.com/Salaia/icons/blob/main/green/Postman.png?raw=true" title="Postman" alt="Postman" height="40"/>
<img src="https://github.com/Salaia/icons/blob/main/green/Swagger.png?raw=true" title="Swagger" alt="Swagger" height="40"/>
</div>

## Testing
Я тестирую через Постман (тесты в папке postman). Гонять коллекцией, они не изолированы!

## Развитие проекта

В соседнем репозитории:
https://github.com/Salaia/librarian.git

Там начинаются песни-пляски с микросервисами и Кафкой...