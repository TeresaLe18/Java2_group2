package com.mycompany.projectjava2.controller;

import com.mycompany.projectjava2.model.ChatService;
import com.mycompany.projectjava2.model.GeminiChatService;
import com.mycompany.projectjava2.model.MockChatService;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

/**
 * FXML Controller class for Chatbox
 */
public class ChatboxController implements Initializable {

    @FXML
    private TextArea chatArea;
    @FXML
    private TextField chatInput;

    private ChatService chatService;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Try to get API Key from environment / system property
        String apiKey = "AIzaSyDL3Tzq2cJyLKGFoyBeZTt3rRd7u9PPR8c";

        if (apiKey == null || apiKey.isEmpty()) {
            // Prompt user for API Key
            // Note: This blocks the UI thread until input; simplified for this project
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Gemini API Key");
            dialog.setHeaderText("API Key Required");
            dialog.setContentText("Please enter your Gemini API Key:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().trim().isEmpty()) {
                apiKey = result.get().trim();
            }
        }

        if (apiKey != null && !apiKey.isEmpty()) {
            chatService = new GeminiChatService(apiKey);
            chatArea.appendText("System: Connected to Gemini AI.\n\n");
        } else {
            chatService = new MockChatService();
            chatArea.appendText("System: No API Key provided. Using Mock Chat (Offline).\n\n");
        }
    }

    @FXML
    private void handleSendAI(ActionEvent event) {
        String input = chatInput.getText();
        if (input != null && !input.trim().isEmpty()) {
            chatArea.appendText("You: " + input + "\n");

            String response = chatService.getResponse(input);
            chatArea.appendText("Bot: " + response + "\n\n");

            chatInput.clear();
        }
    }
}
