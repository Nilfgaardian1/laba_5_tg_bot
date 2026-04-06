package processor;

public class TextProcessor
{
    public String processMessage(String userMessageText) {
        if (userMessageText.equals("/start")) {
            return "Введите часы и минуты через ':' ";
        }

        String[] parts = userMessageText.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        StringBuilder result = new StringBuilder();

        for (int i = 1; i < 7; i++) {
            hours++;
            minutes = minutes + 30;

            if (minutes >= 60) {
                minutes -= 60;
                hours++;
            }
            if (hours == 24) {
                hours = 0;
            }

            result.append("цикл ").append(i).append(": ").append(hours).append(":").append(minutes).append("\n");
        }

        return result.toString();
    }

}

