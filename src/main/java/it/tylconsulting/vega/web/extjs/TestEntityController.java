package it.tylconsulting.vega.web.extjs;

import it.tylconsulting.vega.domain.TestEntity;
import it.tylconsulting.vega.service.criteria.TestEntityCriteria;
import it.tylconsulting.vega.service.dto.TestEntityDTO;
import it.tylconsulting.vega.service.TestEntityQueryService;
import it.tylconsulting.vega.service.TestEntityService;
import it.tylconsulting.vega.util.TylIdentifier;
import it.tylconsulting.vega.util.extjshelpers.filters.FilterParam;
import it.tylconsulting.vega.util.extjshelpers.filters.TylExtCriteriaMapper;
import it.tylconsulting.vega.util.extjshelpers.filters.TylFilter;
import it.tylconsulting.vega.util.extjshelpers.protocol.*;
import it.tylconsulting.vega.util.extjshelpers.sort.SortAdapter;
import org.hibernate.boot.model.naming.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/extjs/testentity")
public class TestEntityController {

    private final Logger log = LoggerFactory.getLogger(TestEntityController.class);

    @Autowired
    TestEntityQueryService testEntityQueryService;

    @Autowired
    TestEntityService testEntityService;

    @PostMapping("/getall")
    @ResponseBody
    public ExtStoreResponse getAllTestentities(@RequestBody ExtStoreRequest extJsRequest) {
        TestEntityCriteria testEntityCriteria = new TestEntityCriteria();
        Pageable pageable;
        Page<TestEntityDTO> resultPage;
        Integer page = extJsRequest.getPage();
        Integer size = extJsRequest.getLimit();
        if (extJsRequest.getSort() != null) {
            log.debug("sort is present");
            Sort springSort = SortAdapter.createSort(extJsRequest.getSort());
            pageable = PageRequest.of(--page, size, springSort);
        } else
            pageable = PageRequest.of(--page, size);
        List<FilterParam> filter = extJsRequest.getFilter();
        if (filter != null) {
            log.debug("filter is present");
            List<TylFilter> tylFilters = TylExtCriteriaMapper.transformFilter(filter, TestEntity.class);
            TylExtCriteriaMapper.mapCriterias(tylFilters, testEntityCriteria, TestEntity.class);
        }
        resultPage = testEntityQueryService.findByCriteria(testEntityCriteria, pageable);
        return new ExtStoreResponse(resultPage.getTotalElements(), resultPage.getContent());
    }

    @PostMapping("/delete")
    @ResponseBody
    public ExtStoreResponse deleteTestEntities(@RequestBody ExtStoreListRequest<TestEntityDTO> entities) {
        entities.getRecords()
            .stream()
            .forEach(testEntity -> testEntityService.delete(testEntity.getId()));
        return new ExtStoreResponse();
    }

    @PostMapping("/load")
    @ResponseBody
        public ExtLoadResponse getTestEntity(@RequestParam Long id ){
        ExtLoadResponse response = new ExtLoadResponse();
        Optional<TestEntityDTO> testEntityDTO = testEntityService.findOne(id);
        response.setData(testEntityDTO.get());
        return response;
    }

    @PostMapping("/submit")
    @ResponseBody
    public ExtSubmitResponse submitTestEntity(@RequestBody TestEntityDTO testEntity){
        ExtSubmitResponse response = new ExtSubmitResponse();
        testEntityService.update(testEntity);
        return response;
    }

    @PostMapping("/replicate")
    @ResponseBody
    public ExtSubmitResponse replicateTestEntities(@RequestBody List<TylIdentifier> toReplicate){
        ExtSubmitResponse response = new ExtSubmitResponse();
        toReplicate.stream().forEach(identifier -> replicate(identifier.getId()));
        response.setMsg(String.format("Replicate %d TestEntities",toReplicate.size()));
        return response;
    }

    // TODO - gestire correttamente l'Optional
    private void replicate(Long id){
        TestEntityDTO te = testEntityService.findOne(id).get();
        te.setId(null);
        testEntityService.save(te);
    }
}

