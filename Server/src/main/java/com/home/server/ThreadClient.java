package com.home.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class ThreadClient implements Runnable {
// добавление логов
    private static final Logger log = Logger.getLogger(ThreadClient.class);
// создание перемнной даты и ее формат
    public static Date date;
    public static SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

// создание переменной очереди для подключения к серверу
    private static Socket clientDialog;
    public ThreadClient(Socket client) {
        ThreadClient.clientDialog = client;
    }

    @Override
    public void run() {

        try {
// канал записи в сокет
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());

// канал чтения из сокета
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());

// создание переменной для возвращения имени сервера
            InetAddress ip = InetAddress.getLocalHost();
// отправка имени сервера
            out.writeUTF(ip.getHostName());
            out.flush();
// начинаем диалог с подключенным клиентом в цикле, пока сокет не
// закрыт клиентом
            while (!clientDialog.isClosed()) {
// сервер ждёт в канале чтения (inputstream) получения данных клиента
                String entry = in.readUTF();
// инициализация проверки условия продолжения работы с клиентом по этому сокету по кодовому слову       - xml
                if(entry.contains("xml")){
                    //Вывод в консоль тела сообщения
                    System.out.println(entry);
// парсинг xml
                    Date date = new Date();
                    String nameClient = StringUtils.substringBetween(entry, "<name>", "</name>");
                    String messageClient = StringUtils.substringBetween(entry, "<message>", "</message>");
// запись данных в логи
                    log.info("Пользователь " + nameClient +" зарегистрировался, Принято сообщение " + messageClient);

// фомирование и отправка ответа
                    out.writeUTF("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<response>\n" +
                            "    <answer>Добрый день, " + nameClient + " .Ваше сообщение успешно обработано!</answer>\n" +
                            "    <message>" + messageClient + "</message>\n" +
                            "    <date>"+formatForDateNow.format(date)+ "</date>\n" +
                            "</response>\n" );
                    out.flush();
                }
            }
// закрываем сначала каналы сокета !
            in.close();
            out.close();
// потом закрываем сокет общения с клиентом в нити моносервера
        }
        catch (IOException e) {
        }
    }
}
