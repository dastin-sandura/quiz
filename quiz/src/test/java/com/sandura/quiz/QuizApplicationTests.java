package com.sandura.quiz;

import com.sandura.quiz.repository.CrudQuizRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class QuizApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CrudQuizRepository crudQuizRepository;

    private final Logger log = LoggerFactory.getLogger(QuizApplicationTests.class);

    @Test
    void contextLoads() {
        log.info("Context loads test");
    }

    @Test
    public void getMainPage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void createQuizViaRestEndpointTest() throws Exception {
        //Make sure there are not Quizzes
        crudQuizRepository.deleteAll();

        String uniqueTestQuizName = "Test quiz" + new Date();
        mvc.perform(MockMvcRequestBuilders.post("/quiz")
                .param("questionCategories", "Java")
                .param("quizName", uniqueTestQuizName)
                .param("questionCount", "2")
        ).andExpect(status().isOk());

        //Cleanup
        crudQuizRepository.deleteAll();
        //Reset sequence, because it used by every table. Without the reset, import of starter data set will fail.
        jdbcTemplate.execute("ALTER SEQUENCE HIBERNATE_SEQUENCE restart with 1");
    }

}
