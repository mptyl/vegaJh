package it.tylconsulting.vega.web.extjs;

import it.tylconsulting.vega.domain.TestEntity;
import it.tylconsulting.vega.service.criteria.TestEntityCriteria;
import it.tylconsulting.vega.service.dto.QuestionnaireDTO;
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
        ExtStoreResponse extStoreResponse=new ExtStoreResponse<>();
        try {
            entities.getRecords()
                .stream()
                .forEach(testEntity -> testEntityService.delete(testEntity.getId()));
        } catch (Exception e){
            extStoreResponse.setSuccess(false);
            extStoreResponse.setMessage("Error in deleting TestEntity. Error:"+e.getMessage());
        }
        return extStoreResponse;
    }

    @GetMapping("/load")
    @ResponseBody
        public ExtLoadResponse getTestEntity(@RequestParam Long id ){
        ExtLoadResponse<TestEntityDTO> response = null;
        Optional<TestEntityDTO> testEntityDTO = testEntityService.findOne(id);
        try {
            response=ExtResponseUtil.wrapOrNotFound(testEntityDTO,"TestEntity");
        } catch (Exception e) {
            response = new ExtLoadResponse<>(false, null, e.getMessage());
        } finally {
            return response;
        }
    }

    @PostMapping("/submit")
    @ResponseBody
    public ExtSubmitResponse submitTestEntity(@RequestBody TestEntityDTO testEntity){
        ExtSubmitResponse response = new ExtSubmitResponse();
        try {
            testEntityService.update(testEntity);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    @PostMapping("/replicate")
    @ResponseBody
    public ExtSubmitResponse replicateTestEntities(@RequestBody List<TylIdentifier> toReplicate){

        ExtSubmitResponse response = new ExtSubmitResponse();
        try {
            toReplicate.stream().forEach(identifier -> replicate(identifier.getId()));
            response.setMsg(String.format("Replicated %d TestEntities", toReplicate.size()));
        } catch (Exception e){
            response.setSuccess(false);
            response.setMsg("Replication of TestEntities failed. Error: "+e.getMessage());
        }
        return response;
    }

    // TODO - gestire correttamente l'Optional
    private void replicate(Long id){
        TestEntityDTO te = testEntityService.findOne(id).get();
        te.setId(null);
        testEntityService.save(te);
    }
}

