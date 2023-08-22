import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Runnable logic = () -> {
                String path = generateRoute("RLRFR", 100);
                int count = counter(path);
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(count)) {
                        sizeToFreq.replace(count, sizeToFreq.get(count) + 1);
                    } else {
                        sizeToFreq.put(count, 1);
                    }
                }
            };
            Thread thread = new Thread(logic);
            thread.start();
            thread.join();
        }

        int maxValue = Collections.max(sizeToFreq.entrySet(), Map.Entry.comparingByValue()).getKey();

        System.out.println("Самое частое количество повторений " + maxValue + " встретилось " + sizeToFreq.get(maxValue) + " раз(а).");
        System.out.println("Другие размеры: ");
        sizeToFreq.forEach((key, value) -> {
            System.out.println(key + " (" + value + ")");
        });
    }

    public static int counter (String path) {
        int count = 0;
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == 'R') {
                count++;
            }
        }
        return count;
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}