package it.tylconsulting.vega.tylextension;

import ch.ralscha.extdirectspring.filter.*;
import it.tylconsulting.vega.service.criteria.QuestionnaireCriteria;
import it.tylconsulting.vega.util.TylExtCriteriaMapper;
import it.tylconsulting.vega.util.TylUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestExtJsMapper {

    @Test
    public void testMapping() throws  NoSuchFieldException {
        QuestionnaireCriteria qc = new QuestionnaireCriteria();
        StringFilter stringFilter = new StringFilter("name","Pippo",null,null);
        NumericFilter numericFilter = new NumericFilter("id",4,null, Comparison.LESS_THAN_OR_EQUAL);
        List<Filter> filters = new ArrayList<>();
        filters.add(stringFilter);
        filters.add(numericFilter);


        QuestionnaireCriteria qc2 = (QuestionnaireCriteria) TylExtCriteriaMapper.mapCriterias(filters,qc);
        System.out.println(qc2.getName());
        System.out.println(qc2.getId());

    }
}
