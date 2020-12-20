import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Client {


// создание перемнной даты и ее формат
    public static SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws InterruptedException, IOException {
// чтение конфиг файла
        FileInputStream fis;
        Properties property = new Properties();

        fis = new FileInputStream("F:\\Projects\\Задание_v2\\Client\\src\\main\\java\\config.properties");
        property.load(fis);

        String host = property.getProperty("server.host");
        String port = property.getProperty("server.port");
        String name = property.getProperty("user.name");
        String pass = property.getProperty("user.pass");

// запускаем подключение сокета по известным координатам и нициализируем приём сообщений с консоли клиента
        try (Socket socket = new Socket(host, Integer.parseInt(port));
             BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
             DataInputStream ois = new DataInputStream(socket.getInputStream());) {

// создание переменной имени сервера и вывод ее
            String NameServer = ois.readUTF();
            System.out.printf(NameServer + "\n");

// проверяем живой ли канал и работаем если живой
            while (!socket.isOutputShutdown()) {
                Thread.sleep(1000);

// ввод имени и сообщения
                System.out.println("Введите имя");
                String Name = br.readLine();
                System.out.println("Введите сообщение");
                String Message = br.readLine();
                Date date = new Date();

// пишем данные с консоли в канал сокета для сервера
                oos.writeUTF("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<root>\n" +
                        "    <user>\n" +
                        "        <name>" + Name + "</name>\n" +
                        "        <message>" + Message + "</message>\n" +
                        "        <date>" + formatForDateNow.format(date) + "</date>\n" +
                        "    </user>\n" +
                        "</root>\n");
                oos.flush();

// чтение ответа от сервера и вывод его в консоль
                String response = ois.readUTF();
                System.out.println(response);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

