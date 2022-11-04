package it.tylconsulting.vega.service.generator;

import com.github.javafaker.Faker;
import it.tylconsulting.vega.domain.TestEntity;
import it.tylconsulting.vega.domain.enumeration.QuestionnaireType;
import it.tylconsulting.vega.repository.QuestionnaireRepository;
import it.tylconsulting.vega.repository.TestEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TestEntityGenerator {

    @Bean
    public CommandLineRunner loadTestEntityData(TestEntityRepository testEntityRepository) {


        return args->{
            final long NUMBEROFRECORDSTOGENERATE=10000;
            Logger logger = LoggerFactory.getLogger(getClass());

            if (testEntityRepository.count() != 0L) {
                logger.info("TestEntity already loaded");
                return;
            }

            logger.info("Generating demo data for TestEntity");

            logger.info("... generating 10000 TestEntity entities...");
            Faker faker=new Faker();

            List<TestEntity> testEntityList= new ArrayList<>();
            for (int i = 0; i < NUMBEROFRECORDSTOGENERATE; i++) {
                TestEntity testEntity=new TestEntity();
                testEntity.setIntegerField(faker.number().randomDigitNotZero());
                testEntity.setLongField(faker.number().randomNumber());
                testEntity.setFloatField((float) faker.number().randomNumber());
                testEntity.setDoubleField(faker.number().randomDouble(2,1,500000));
                testEntity.setBigDecimalField(BigDecimal.valueOf(faker.number().randomNumber()));
                testEntity.setBooleanField(faker.random().nextBoolean());
                testEntity.setStringField(faker.dog().name());
                Date date = faker.date().birthday();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                testEntity.setLocalDateField(localDate);
                testEntity.setDurationField(Long.valueOf(faker.number().numberBetween(10000L,100000L)));
                int index=faker.number().numberBetween(0,3);
                testEntity.setQuestionnaireType(QuestionnaireType.values()[index]);
                testEntityList.add(testEntity);

            }
            testEntityRepository.saveAll(testEntityList);
            logger.info("generated demo data for TestEntity");
        };
    }
}
