# HomeProject
Клиент-серверное приложение, состоящее из клиентской и серверной частей

h1 Сервер
=====================
1. Запускаем сервер, ждем подключения клиента
2. При подключении к серверу, сервер возвращяет сое имя и ожидает сообщения от пользователя
3. После получении, обрабатывает xml из сообщения и выводит его в консоль
4. Сервер исрользует логирование log4j

h1 Клиент
=====================
1. При запуске клиентского приложения происходит считывание параметров из файла конфигурации config.properties:
server.host = localhost
server.port = 3334
user.name = Admin
user.pass = Admin

2. Далее подключамся к серверу. Выводим имя сервера при удачном подключении.
3. После вывода наименования сервера пользователю предлагается ввести имя и сообщение
4. Затем на основе введённых данных формируется xml и осуществляется запрос:
<?xml version="1.0" encoding="UTF-8"?>
<root>
    <user>
        <name>Иван</name>
        <message>Привет Мир!</message>
        <date>01.01.2018 12:00:00</date>
    </user>
</root>
5. Ответ, полученный с сервера выводится консоль.
