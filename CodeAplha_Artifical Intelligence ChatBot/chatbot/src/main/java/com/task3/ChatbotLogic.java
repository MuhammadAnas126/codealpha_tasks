package com.task3; // Make sure your folder structure is com/task3/

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChatbotLogic {
    
    private Map<String, String> knowledgeBase;
    private String userName = "User"; // Default name

    public ChatbotLogic() {
        knowledgeBase = new HashMap<>();

        // Removed "hello" and "hi" from here because the IF block handles them better!
        knowledgeBase.put("name", "I am CodeAlphaBot, your virtual assistant.");
        knowledgeBase.put("job", "I answer questions and help you with tasks.");
        knowledgeBase.put("bye", "Goodbye! Have a great day.");
        knowledgeBase.put("time", "I can tell you the time if you ask nicely!");
        knowledgeBase.put("help", "Sure! Ask me anything about my capabilities.");
        knowledgeBase.put("weather", "I don't have weather data, but I hope it's nice outside!");
        knowledgeBase.put("joke", "Why did the programmer quit his job? Because he didn't get arrays.");
        knowledgeBase.put("thanks", "You're welcome! Happy to help.");
    }

    public String generateResponse(String userInput) {
        String input = userInput.trim().toLowerCase();
        Random random = new Random();

        // --- Priority 1: Random Greetings ---
        if(input.contains("hello") || input.contains("hi")) {
            String[] greetings = {
                "Hello there!", 
                "Hi! How can I help?", 
                "Greetings! What's on your mind?",
                "Hey! Good to see you."
            };
            return greetings[random.nextInt(greetings.length)];
        }

        // --- Priority 2: Random Moods ---
        if(input.contains("how are you")) {
            String[] moods = {
                "I'm just a bunch of code, but thanks for asking!",
                "Doing great! Ready to assist you.",
                "I'm functioning as expected. How about you?",
                "All systems operational! How can I help?"
            };
            return moods[random.nextInt(moods.length)];
        }

        if (input.startsWith("my name is ")) {
            if (userInput.length() > 11) {
                userName = userInput.substring(11).trim();
                return "Nice to meet you, " + userName + "! I will remember your name.";
            }
        }

        // --- Priority 4: Name Recall ---
        if(input.contains("what is my name") || input.contains("who am i")) {
            return "Your name is " + userName + "!";
        }

        // --- Priority 5: Time ---
        if (input.contains("time")) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            return "The current time is " + LocalTime.now().format(dtf);
        }

        // --- Priority 6: Knowledge Base Search ---
        for (String keyword : knowledgeBase.keySet()) {
            if(input.contains(keyword)) {
                return knowledgeBase.get(keyword);
            }
        }

        // Fallback
        return "I'm sorry, I don't understand that. Try asking about my name or the time.";
    }
}