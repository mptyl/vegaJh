package it.tylconsulting.vega.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QeCheckGroupMapperTest {

    private QeCheckGroupMapper qeCheckGroupMapper;

    @BeforeEach
    public void setUp() {
        qeCheckGroupMapper = new QeCheckGroupMapperImpl();
    }
}
