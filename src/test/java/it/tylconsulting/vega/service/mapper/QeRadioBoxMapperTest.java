package it.tylconsulting.vega.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QeRadioBoxMapperTest {

    private QeRadioBoxMapper qeRadioBoxMapper;

    @BeforeEach
    public void setUp() {
        qeRadioBoxMapper = new QeRadioBoxMapperImpl();
    }
}
