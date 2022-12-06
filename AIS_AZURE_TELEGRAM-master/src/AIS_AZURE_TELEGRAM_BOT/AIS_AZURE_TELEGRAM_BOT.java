package AIS_AZURE_TELEGRAM_BOT;


import java.io.File;
import static java.lang.Math.pow;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

class MyTelegramBot extends TelegramLongPollingBot {

    // Метод получения команд бота, тут ничего не трогаем
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText(doCommand(update.getMessage().getChatId(),
                    update.getMessage().getText()));
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
            }
        }
    }

    // Тут задается нужное значение имени бота
    @Override
    public String getBotUsername() {
        return "AIS_Math_bot";
    }

    // Тут задается нужное значение токена
    @Override
    public String getBotToken() {
        return "5649152146:AAF5-Za8YN0bWeFxVN69ytE3N_dgMCqiars";
    }

    // Метод обработки команд бота
    public String doCommand(long chatId, String command) {
        try {
                sendPhoto(new SendPhoto().setChatId(chatId).setNewPhoto(new File("mytasksix.png")));
            } catch (TelegramApiException e) {
            }
        
        if (command.startsWith("/solve")) {
            
            String[] param = command.split(" ");
            if (param.length > 1) {
                return "y= " + getSolve(Double.parseDouble(param[1]), Double.parseDouble(param[2]), Double.parseDouble(param[3]));
            } else {
                return "Для решения примера используйте команду /solve и введите переменные x, a, b через пробел";
            }
        }
        return "Для решения примера используйте команду /solve и введите переменные x, a, b через пробел";
    }

     private String getSolve(double x, double a, double b) {
        StringBuilder answer = new StringBuilder();
        double y;

        if (x < 7) {
            y = (pow(x, 2) + pow(a, 2) + pow(b, 2)) / (pow(x, 3) * pow(a + b, 2));
        } else {
            y = pow(x, 3) * pow(a + b, 3);
        }
        if (!Double.isNaN(y) && !Double.isInfinite(y)) { 
            answer.append(y);
        } else {
            return "Деление на ноль запрещено!";
        }

        
        return String.format("%.3f", y);
    }
}
public class AIS_AZURE_TELEGRAM_BOT {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            // ЗАПУСКАЕМ КЛАСС НАШЕГО БОТА
            botsApi.registerBot(new MyTelegramBot());
        } catch (TelegramApiException e) {
        }
    }
}
