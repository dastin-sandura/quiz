package com.sandura.quiz.controller;

import com.sandura.quiz.data.StarterDatasetImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${initialization.import.starterdata}")
    private boolean shouldImportStarterDataset;

    @GetMapping
    public String mainPage() {
        if (shouldImportStarterDataset) {
            startedDatasetImporter.populateDatabaseWithStarterDataset();
            shouldImportStarterDataset = false;
        }
        return "index";
    }
}
