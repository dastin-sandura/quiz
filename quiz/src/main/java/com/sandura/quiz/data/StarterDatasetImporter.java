package com.sandura.quiz.data;

import org.springframework.stereotype.Component;

@Component
public interface StarterDatasetImporter {

    void populateDatabaseWithStarterDataset();
}
