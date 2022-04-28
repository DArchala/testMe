package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.models.Answer;
import pl.archala.testme.repositories.AnswerRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepo;

    public AnswerService(AnswerRepository answerRepo) {
        this.answerRepo = answerRepo;
    }

    public void addSampleAnswers(){
        if (answerRepo.count() == 0) {
            var a1 = new Answer(1L, "Zwierzę", false);
            var a2 = new Answer(2L, "Miejscowość");
            var a3 = new Answer(3L, "Obiekt w bazie danych", true);
            var a4 = new Answer(4L, "Budynek");

            var a5 = new Answer(5L, "Owca");
            var a6 = new Answer(6L, "Instancja klasy", true);
            var a7 = new Answer(7L, "Narzędzie");
            var a8 = new Answer(8L, "Kolor");

            var a9 = new Answer(9L, "Program", true);
            var a10 = new Answer(10L, "Rodzaj roweru");
            var a11 = new Answer(11L, "Zawód");
            var a12 = new Answer(12L, "Danie");

            List<Answer> answers = new ArrayList<>(Arrays.asList(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12));
            answerRepo.saveAll(answers);
        }
    }
}