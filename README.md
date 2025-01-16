# REST API для учета носков на складе магазина

## Используемые технологии:
* Java 17
* Spring Boot 2.7
* Gradle
* Lombok
* Hibernate
* PostgreSQL
* H2
* Flyway
* Swagger
* Docker

## Функционал:
### Регистрация прихода носков:
POST /api/socks/income

Параметры: цвет носков, процентное содержание хлопка, количество.
Увеличивает количество носков на складе.

### Регистрация отпуска носков:
POST /api/socks/outcome

Параметры: цвет носков, процентное содержание хлопка, количество.
Уменьшает количество носков на складе, если их хватает.

### Получение общего количества носков с фильтрацией:
GET /api/socks

Фильтры:
Цвет носков.
Оператор сравнения (moreThan, lessThan, equal).
Процент содержания хлопка.
Возвращает количество носков, соответствующих критериям.


### Обновление данных носков:
PUT /api/socks/{id}

Позволяет изменить параметры носков (цвет, процент хлопка, количество).

### Загрузка партий носков из CSV файла:
POST /api/socks/batch

Принимает Excel или CSV (один формат на выбор) файл с партиями носков, содержащими цвет, процентное содержание хлопка и количество.

### Open api:
Интерфейс swagger доступен по адресу: http://localhost:8080/swagger-ui/index.html

### Запуск:
Приложение использует порт 8080.
Для работы приложения необходимо наличие на машине PostgreSQL либо докер. Из докера PostgreSQL запускается командой:
```Bash
docker-compose up
```
