package it.tylconsulting.vega.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QeRadioGroupMapperTest {

    private QeRadioGroupMapper qeRadioGroupMapper;

    @BeforeEach
    public void setUp() {
        qeRadioGroupMapper = new QeRadioGroupMapperImpl();
    }
}
