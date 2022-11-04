package it.tylconsulting.vega.direct;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.bean.SortInfo;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.StringFilter;
import it.tylconsulting.vega.domain.TestEntity;
import it.tylconsulting.vega.service.QuestionnaireQueryService;
import it.tylconsulting.vega.service.TestEntityQueryService;
import it.tylconsulting.vega.service.criteria.QuestionnaireCriteria;
import it.tylconsulting.vega.service.criteria.TestEntityCriteria;
import it.tylconsulting.vega.service.dto.QuestionnaireDTO;
import it.tylconsulting.vega.service.dto.TestEntityDTO;
import it.tylconsulting.vega.util.TylExtCriteriaMapper;
import it.tylconsulting.vega.util.TylRequestParams;
import it.tylconsulting.vega.util.TylUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
@CrossOrigin(origins = "http://localhost:2129", maxAge = 3600)
public class TestEntityDirect {

    @Autowired
    TestEntityQueryService testEntityQueryService;

    @ExtDirectMethod(ExtDirectMethodType.STORE_READ)
    public ExtDirectStoreResult<TestEntityDTO> loadAllTestEntities(ExtDirectStoreReadRequest request) {

        TestEntityCriteria testEntityCriteria=new TestEntityCriteria();
        TylExtCriteriaMapper.mapCriterias(request, testEntityCriteria, TestEntity.class);
        TylRequestParams trp = TylExtCriteriaMapper.transformRequest(request);

        // Esecuzione della findByCriteria
        Pageable pageable = PageRequest.of(trp.getPage(), trp.getSize(), trp.getSort());
        Page<TestEntityDTO> resultPage=testEntityQueryService.findByCriteria( testEntityCriteria,pageable);
        return new ExtDirectStoreResult<>(resultPage.getTotalElements(),resultPage.getContent());
    }
}
