package com.home.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    static ExecutorService executeIt = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

// стартуем сервер на порту 3345 и инициализируем переменную для обработки консольных команд с самого сервера
        try (ServerSocket server = new ServerSocket(3334);)
 {
// стартуем цикл при условии что серверный сокет не закрыт
            while (!server.isClosed()) {
                Socket client = server.accept();

                // после получения запроса на подключение сервер создаёт сокет
                // для общения с клиентом и отправляет его в отдельную нить
                // в Runnable
                // монопоточную нить = сервер - ThreadClient и тот
                // продолжает общение от лица сервера
                executeIt.execute(new ThreadClient(client));
            }
// закрытие пула нитей после завершения работы всех нитей
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
