package it.tylconsulting.vega.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QeQuestionMapperTest {

    private QeQuestionMapper qeQuestionMapper;

    @BeforeEach
    public void setUp() {
        qeQuestionMapper = new QeQuestionMapperImpl();
    }
}
