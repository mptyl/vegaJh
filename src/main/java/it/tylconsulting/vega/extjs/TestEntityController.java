package it.tylconsulting.vega.extjs;

import ch.ralscha.extdirectspring.bean.SortInfo;
import it.tylconsulting.vega.domain.TestEntity;
import it.tylconsulting.vega.util.extjsHelpers.ExtResponse;
import it.tylconsulting.vega.util.extjsHelpers.SortAdapter;
import it.tylconsulting.vega.util.extjsHelpers.filters.Filter;
import it.tylconsulting.vega.service.TestEntityQueryService;
import it.tylconsulting.vega.service.TestEntityService;
import it.tylconsulting.vega.service.criteria.TestEntityCriteria;
import it.tylconsulting.vega.service.dto.TestEntityDTO;
import it.tylconsulting.vega.util.extjsHelpers.TylExtCriteriaMapper;
import it.tylconsulting.vega.util.TylUtil;
import it.tylconsulting.vega.web.rest.QuestionnaireProfileResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/extjs")
public class TestEntityController {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireProfileResource.class);

    @Autowired
    TestEntityQueryService testEntityQueryService;

    @Autowired
    TestEntityService testEntityService;

    @GetMapping("/testentities")
    @ResponseBody
    public ExtResponse getAllQuestionnaires(@RequestParam(name="limit") Integer size,
                                            @RequestParam Integer page,
                                            @RequestParam Optional<List<SortInfo>> sort,
                                            @RequestParam Optional<List<Filter>> filter) {

        TestEntityCriteria testEntityCriteria = new TestEntityCriteria();

        Pageable pageable;
        Page<TestEntityDTO> resultPage;
        if (sort.isPresent()) {
            log.debug("sort is present");
            Sort springSort = SortAdapter.createSort(sort.get());
            pageable = PageRequest.of(--page, size, springSort);
        } else
            pageable = PageRequest.of(--page, size);
        if(filter.isPresent()) {
            log.debug("filter is present");
            TylExtCriteriaMapper.mapCriterias(filter.get(), testEntityCriteria, TestEntity.class);
        }
        resultPage = testEntityQueryService.findByCriteria(testEntityCriteria, pageable);
        return new ExtResponse(resultPage.getTotalElements(), resultPage.getContent());

    }
}

