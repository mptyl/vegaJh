package it.tylconsulting.vega.web.extjs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.tylconsulting.vega.domain.TestEntity;
import it.tylconsulting.vega.service.criteria.TestEntityCriteria;
import it.tylconsulting.vega.service.dto.TestEntityDTO;
import it.tylconsulting.vega.util.extjshelpers.ExtResponse;
import it.tylconsulting.vega.util.extjshelpers.SortAdapter;
import it.tylconsulting.vega.util.extjshelpers.TylExtCriteriaMapper;
import it.tylconsulting.vega.util.extjshelpers.filters.ExtJsFilter;
import it.tylconsulting.vega.util.extjshelpers.filters.TylFilter;
import it.tylconsulting.vega.service.TestEntityQueryService;
import it.tylconsulting.vega.service.TestEntityService;
import it.tylconsulting.vega.util.extjshelpers.sort.SortInfo;
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

@Controller
@RequestMapping("/extjs")
public class TestEntityController {

    private final Logger log = LoggerFactory.getLogger(TestEntityController.class);

    @Autowired
    TestEntityQueryService testEntityQueryService;

    @Autowired
    TestEntityService testEntityService;

    @GetMapping("/testentities")
    @ResponseBody
    public ExtResponse getAllTestentities(@RequestParam(name="limit") Integer size,
                                            @RequestParam Integer page,
                                            @RequestParam(required=false) String sort,
                                            @RequestParam(required=false) String filter) throws JsonProcessingException {

        TestEntityCriteria testEntityCriteria = new TestEntityCriteria();
        ObjectMapper objectMapper = new ObjectMapper();
        Pageable pageable;
        Page<TestEntityDTO> resultPage;
        if (sort !=null) {
            List<SortInfo> jsonSort = objectMapper.readValue(sort, new TypeReference<>(){});
            log.debug("sort is present");
            Sort springSort = SortAdapter.createSort(jsonSort);
            pageable = PageRequest.of(--page, size, springSort);
        } else
            pageable = PageRequest.of(--page, size);
        if(filter!=null) {
            log.debug("filter is present");
            List<ExtJsFilter> jsonFilter = objectMapper.readValue(filter, new TypeReference<>(){});
            List<TylFilter> tylFilters= TylExtCriteriaMapper.transformFilter(jsonFilter, TestEntity.class);
            TylExtCriteriaMapper.mapCriterias(tylFilters, testEntityCriteria, TestEntity.class);
        }
        resultPage = testEntityQueryService.findByCriteria(testEntityCriteria, pageable);
        return new ExtResponse(resultPage.getTotalElements(), resultPage.getContent());
    }
    @PostMapping("/deleteTestEntities")
    public void deleteTestEntities(@RequestBody List<TestEntity> testEntities ){
        System.out.println(testEntities);
    }

}

