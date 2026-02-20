package com.mycompany.projectjava2.model;

public class MockChatService implements ChatService {

    @Override
    public String getResponse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Please say something!";
        }

        String lowerInput = input.toLowerCase();

        if (lowerInput.contains("hello") || lowerInput.contains("hi")) {
            return "Hello! How can I help you today?";
        } else if (lowerInput.contains("help")) {
            return "I can help you explicitly manage books or answer questions about this library system.";
        } else if (lowerInput.contains("book")) {
            return "You can manage books using the form on the left.";
        } else if (lowerInput.contains("bye")) {
            return "Goodbye! Have a nice day.";
        } else {
            return "I'm just a simple bot. I don't understand '" + input + "' yet.";
        }
    }
}
