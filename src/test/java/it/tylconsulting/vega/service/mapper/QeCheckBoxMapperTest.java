package it.tylconsulting.vega.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QeCheckBoxMapperTest {

    private QeCheckBoxMapper qeCheckBoxMapper;

    @BeforeEach
    public void setUp() {
        qeCheckBoxMapper = new QeCheckBoxMapperImpl();
    }
}
