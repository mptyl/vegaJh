package it.tylconsulting.vega.util;

import tech.jhipster.service.QueryService;

public class TylQueryService<ENTITY> extends QueryService<ENTITY> {

    @Override
    protected String wrapLikeQuery(String txt) {
        return txt.toUpperCase() + '%';
    }
}
