package it.tylconsulting.vega.util.extjshelpers.protocol;

import net.jodah.typetools.TypeResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface ExtResponseUtil {

    static <X> ExtLoadResponse<X> wrapOrNotFound(Optional<X> maybeResponse, String entityName) {
        if (maybeResponse.isPresent())
            return new ExtLoadResponse<X>(true, maybeResponse.get(), "");
        else
            return new ExtLoadResponse<X>(false, null, "Entity " + entityName + " not found");
    }
}

