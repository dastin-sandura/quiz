package com.sandura.quiz.controller;

import com.sandura.quiz.data.StarterDatasetImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Main controller, created to override default behaviour of opening the main page of the application.
 * Main purpose is to automate the process of populating database with starter dataset.
 */
@Controller
public class MainController {

    @Autowired
    StarterDatasetImporter startedDatasetImporter;

    private boolean initializationPerformed;

    @GetMapping
    public String mainPage() {
        if (initializationPerformed == false) {
            startedDatasetImporter.populateDatabaseWithStarterDataset();
            initializationPerformed = true;
        }
        return "index";
    }
}
