# Quickstart интеграция с Goodfin

- Отредактируйте ендпоинт для обратного вызова. Если нет возможности выставить callback, примеры входящих сообщений можно получить из integrationLog.
``` 
class InitController {
    val baseUrl = "http://192.168.4.56:8082/callback"
```
- Запуск приложения ./mvnw compile quarkus:dev
- Открыть в браузере http://localhost:8082/init/hello
- Логирование запросов можно увидеть в файле trace.log


##  Quickstart интеграция с Goodfin через soap ui
Файл проекта soapui-project.xml