import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        String letters = "RLRFR";
        int routeLength = 100;
        int routsAmount = 1000;
        for (int i = 0; i < routsAmount; i++) {
            new Thread(() -> {
                String route = generateRoute(letters, routeLength);
                int quantity = symbolCounting(route);
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(quantity)) {
                        sizeToFreq.put(quantity, sizeToFreq.get(quantity) + 1);
                    } else {
                        sizeToFreq.put(quantity, 1);
                    }
                }
            }).start();
        }

        Integer maxKey = null;
        for (Integer key : sizeToFreq.keySet()) {
            if (maxKey == null || sizeToFreq.get(key) > sizeToFreq.get(maxKey)) {
                maxKey = key;
            }
        }
        System.out.println("����� ������ ���������� ���������� " + maxKey + " (����������� " + sizeToFreq.get(maxKey) + " ���)");
        sizeToFreq.remove(maxKey);
        System.out.println("������ �������:");
        sizeToFreq.keySet()
                .stream()
                .forEach(key -> System.out.println("- " + key + " (" + sizeToFreq.get(key) + " ���)"));
    }// main

    public static String generateRoute(String letters, int routeLength) {
        Random random = new Random();
        StringBuilder sbRoute = new StringBuilder();
        for (int i = 0; i < routeLength; i++) {
            sbRoute.append(letters.charAt(random.nextInt(letters.length())));
        }
        return sbRoute.toString();
    }

    public static int symbolCounting(String text) {
        int quantity = 0;
        for (char letter : text.toCharArray()) {
            if (letter == 'R') {
                quantity++;
            }
        }
        return quantity;
    }
}// class
