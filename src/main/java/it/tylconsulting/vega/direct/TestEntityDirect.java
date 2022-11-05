package it.tylconsulting.vega.direct;

import it.tylconsulting.vega.service.TestEntityQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@CrossOrigin(origins = "http://localhost:2129", maxAge = 3600)
public class TestEntityDirect {

    @Autowired
    TestEntityQueryService testEntityQueryService;

//    @ExtDirectMethod(ExtDirectMethodType.STORE_READ)
//    public ExtDirectStoreResult<TestEntityDTO> loadAllTestEntities(ExtDirectStoreReadRequest request) {
//
//        TestEntityCriteria testEntityCriteria=new TestEntityCriteria();
//        TylExtCriteriaMapper.mapCriterias(request, testEntityCriteria, TestEntity.class);
//        TylRequestParams trp = TylExtCriteriaMapper.transformRequest(request);
//
//        // Esecuzione della findByCriteria
//        Pageable pageable = PageRequest.of(trp.getPage(), trp.getSize(), trp.getSort());
//        Page<TestEntityDTO> resultPage=testEntityQueryService.findByCriteria( testEntityCriteria,pageable);
//        return new ExtDirectStoreResult<>(resultPage.getTotalElements(),resultPage.getContent());
//    }
}
