package com.translation.Translation.mainclass.controller;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
public class TranslationClass {

    @Value("${freetts.voices}")
    private String freettsVoices;

    @GetMapping(value = "/getvoice", produces = "text/plain")
    public ResponseEntity<String> getVoice(@RequestParam String text) {


        // Initialize the FreeTTS VoiceManager
        VoiceManager voiceManager = VoiceManager.getInstance();
        System.setProperty("freetts.voices", freettsVoices);
        // Select a voice from available voices
        Voice voice = voiceManager.getVoice("kevin16");

        VoiceManager voiceManager1 = VoiceManager.getInstance();
        Voice[] voices = voiceManager1.getVoices();

        if (voice == null) {
            System.err.println("Voice not found.");
            System.exit(1);
        }

        // Allocate the selected voice
        voice.allocate();

        // Text to be converted to speech
        String aa="success";
        String jsonResponse = "{\"message\": \"" + aa + "\"}";

        // Speak the text
        voice.speak(text);

        // Deallocate the voice
        voice.deallocate();
        return new ResponseEntity<>(jsonResponse,HttpStatus.OK);
    }



    @PostMapping("/upload-excel")
    public List<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        List<String> result = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    String cellValue = cell.toString();
                    result.add(cellValue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any exceptions here
        }

        System.out.println(result);

        return result;
    }
}
