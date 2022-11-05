package it.tylconsulting.vega.direct;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import it.tylconsulting.vega.service.QuestionnaireQueryService;
import it.tylconsulting.vega.service.QuestionnaireService;
import it.tylconsulting.vega.service.dto.QuestionnaireDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;

@Service
@CrossOrigin(origins = "http://localhost:2129", maxAge = 3600)
public class QuestionnaireDirect {

    @Autowired
    QuestionnaireQueryService questionnaireQueryService;

    @Autowired
    QuestionnaireService questionnaireService;

//    @ExtDirectMethod(ExtDirectMethodType.STORE_READ)
//    public ExtDirectStoreResult<QuestionnaireDTO> loadAllQuestionnaires(ExtDirectStoreReadRequest request) {
//
//        QuestionnaireCriteria questionnaireCriteria=new QuestionnaireCriteria();
//        TylExtCriteriaMapper.mapCriterias(request, questionnaireCriteria, Questionnaire.class);
//
//        // Conversione
//        Integer size=request.getLimit();
//        Integer page=request.getPage()-1;
//
//        List<SortInfo> sortInfos=request.getSorters();
//        Sort sort= TylUtil.createSort(sortInfos);
//
//        // Esecuzione della findByCriteria
//        Pageable pageable = PageRequest.of(page, size, sort);
//        Page<QuestionnaireDTO> resultPage=questionnaireQueryService.findByCriteria( questionnaireCriteria,pageable);
//        return new ExtDirectStoreResult<>(resultPage.getTotalElements(),resultPage.getContent());
//    }

    @ExtDirectMethod(ExtDirectMethodType.FORM_LOAD)
    public QuestionnaireDTO loadQuestionnaireDTO(@RequestParam(value = "loadId") long loadId) {

        QuestionnaireDTO questionnaireDTO = new QuestionnaireDTO();
        System.out.println("Richiesta load del QuestionnaireDTO");
        return questionnaireDTO;
    }

    @ExtDirectMethod(ExtDirectMethodType.FORM_POST)
    public ExtDirectFormPostResult updateQuestionnaireDTO(@Valid QuestionnaireDTO questionnaireDTO, BindingResult result) {
        if (!result.hasErrors()) {
            // Testare errore di contenuto invalido
        }

        questionnaireService.update(questionnaireDTO);
        return new ExtDirectFormPostResult(result);
    }

    @ExtDirectMethod(STORE_MODIFY)
    public void destroy(List<QuestionnaireDTO> questionari) {
        questionari
            .stream()
            .forEach(q->questionnaireService.delete(q.getId()));
    }
}
