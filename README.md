# Gamestore
___
## О проекте
REST-сервис магазина игр.<br>
Предоставляет простой функционал CRUD операций для игр, разработчиков игр и игровых платформ

Перед использованием необходимо:
1. установленная Java 17 или выше;
2. установленный Tomcat;
3. установленная PostgreSQL;
4. наличие самого проекта;
5. установить имя пользователя, пароль, и путь к БД если они отличаются от настроек по-умолчанию в application.yml.
6. Запустить Tomcat с собранным проектом.

## API

1. **Игры**:

`GET /api/games` — получение всех игр.  
`GET /api/games/{id}` — получение игры с указанным id.  

`POST /api/games` — добавление новой игры (из json).  
Пример:
`{  
"name": "New Game",  
    "developer": {  
    "id": {developer_id}  
    }  
}` 

`POST /api/games/{platform_id}` — добавление новой игры (из json), но с указанием поддерживаемой игровой платформы по platform_id

`POST /api/games/platformSupport/{game_id}/{platform_id}` - добавление поддержки игровой платформы к игре по `game_id` и `platform_id`

`PUT /api/games/{id}` — обновление игры по id.  
Если нужно обновить только название игры:
`{  
"name": "New Name"  
}`  
если нужно изменить разработчика игры:
`{  
"developer": {  
"id": {developer_id}  
}
}`  
или вот так если нужно изменить всё:
`{
"name": "New Name",
"developer": {
"id": {developer_id}
}
}`

`DELETE /api/games/{id}` — удаление игры по-указанному id.  
`DELETE /api/games/platformSupport/{game_id}/{platform_id}` - удаление поддержки игровой платформы от игры по `game_id` и `platform_id`
___
2. **Разработчики**:

`GET /api/developers` — получение всех разработчиков игр.  
`GET /api/developers/{id}` — получение разработчика по-указанному id.

`POST /api/developers` — добавление нового разработчика (из json).  
Пример:
`{
    "developerName": "New Developer"
}`

`PUT /api/developers/{id}` — обновление имени разработчика по id.

`DELETE /api/developers/{id}` — удаление разработчика по-указанному id.
___
3. **Игровые платформы**:

`GET /api/platforms` — получение всех игровых платформ.  
`GET /api/platforms/{id}` — получение игровой платформы по-указанному id.

`POST /api/platforms` — добавление новой платформы (из json).  
Пример:
`{
    "name": "New Platform"
}`

`PUT /api/platforms/{id}` — обновление наименования платформы по id.

`DELETE /api/platforms/{id}` — удаление игровой платформы по-указанному id.
