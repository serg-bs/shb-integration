# Quickstart интеграция с Goodfin

1. Отредактируйте ендпоинт для обратного вызова
``` 
class InitController {
    val baseUrl = "http://192.168.4.56:8082/callback"
```
Если нет возможности выставить callback, примеры входящих сообщений можно получить из integrationLog.    
2. Запуск приложения ./mvnw compile quarkus:dev
3. Открыть в браузере http://localhost:8082/init/hello
4. Логирование запросов можно увидеть в файле trace.log
