package it.tylconsulting.vega.direct;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.bean.SortInfo;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.StringFilter;
import it.tylconsulting.vega.service.QuestionnaireQueryService;
import it.tylconsulting.vega.service.criteria.QuestionnaireCriteria;
import it.tylconsulting.vega.service.dto.QuestionnaireDTO;
import it.tylconsulting.vega.util.TylUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@CrossOrigin(origins = "http://localhost:2129", maxAge = 3600)
public class QuestionnaireDirect {

    @Autowired
    QuestionnaireQueryService questionnaireQueryService;

    @ExtDirectMethod(ExtDirectMethodType.STORE_READ)
    public ExtDirectStoreResult<QuestionnaireDTO> loadAllQuestionnaires(ExtDirectStoreReadRequest request) {

        // Input da request
        List<Filter> filters=request.getFilters();
        List<SortInfo> sortInfos=request.getSorters();

        // Conversione
        Integer size=request.getLimit();
        Integer page=request.getPage()-1;
        Sort sort= TylUtil.createSort(sortInfos);

        // Conversione dei filtri
        tech.jhipster.service.filter.StringFilter jhNameFilter = new tech.jhipster.service.filter.StringFilter();
        QuestionnaireCriteria questionnaireCriteria= new QuestionnaireCriteria();
        for (Filter filter:filters) {
            if (filter.getField().equals("name")){
                String value= ((StringFilter) filter).getValue();
                jhNameFilter.setContains(value);
                questionnaireCriteria.setName(jhNameFilter);
            }
        }

        // Esecuzione della findByCriteria
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<QuestionnaireDTO> resultPage=questionnaireQueryService.findByCriteria( questionnaireCriteria,pageable);
        return new ExtDirectStoreResult<QuestionnaireDTO>(resultPage.getTotalElements(),resultPage.getContent());
    }
}
