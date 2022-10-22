package it.tylconsulting.vega.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QeGroupMapperTest {

    private QeGroupMapper qeGroupMapper;

    @BeforeEach
    public void setUp() {
        qeGroupMapper = new QeGroupMapperImpl();
    }
}
