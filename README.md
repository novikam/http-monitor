# http-monitor
Микросервис предполагает следующую схему действия: клиент передает адрес приложения, работоспособность которого требуется проверить (хост/ip-адрес, порт, путь), микросервис генерирует ответ об успешном принятии запроса, затем асинхронно пытается осуществить GET-запрос по указанному адресу и записывает результат в базу данных. Впоследствие клиент может запросить статус того или иного приложения, и микросервис должен вернуть ему результат последней проверки приложения (или 404, если таковой отсутствует). При переполнении очереди запросов сервис будет возвращать статус 503, если выполняемый запрос не может быть выполнен, запросу проставится статус 502. При запуске приложения все запросы которые не были обработаны(находящиеся в таблице task) будут автоматически подгружены в очередь на выполнение.

Пример обмена данными:
POST /api/v1/application-check HTTP/1.1

{
    "host": "192.168.0.1",
    "port": 80,
    "path": "ping"
}
->

HTTP/1.1 201 Accepted
GET /api/v1/application-status/192.168.0.1/80/ping HTTP/1.1
->

HTTP/1.1 200 OK

{
    "status": 200
}

В application.proprties можно настроить размер очереди в которую складываются запросы, также по умолчанию проект настроен на работу с Postgresql, версия 9.3.
#Для запуска проекта необходимо иметь установленный и запущенный Postgresql сервер, а также выполнить запросы описанные в файле data.sql.
