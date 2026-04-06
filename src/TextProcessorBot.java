import config.BotConfig;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import processor.TextProcessor;


public class TextProcessorBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final TextProcessor textProcessor; // добавляем обработчик

    public TextProcessorBot() {
        telegramClient = new OkHttpTelegramClient(BotConfig.getBOT_TOKEN());
        textProcessor = new TextProcessor(); // создаем обработчик
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            // ВСЯ ЛОГИКА ВЫНЕСЕНА В TextProcessor
            String answerText = textProcessor.processMessage(userMessageText);

            SendMessage reply = SendMessage.builder()
                    .chatId(chatId)
                    .text(answerText)
                    .build();

            try {
                telegramClient.execute(reply);
            } catch (TelegramApiException e) {
                System.err.println("❌ Ошибка обработки сообщения: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}