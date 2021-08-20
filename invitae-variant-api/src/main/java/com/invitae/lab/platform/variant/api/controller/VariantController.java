package com.invitae.lab.platform.variant.api.controller;

import com.invitae.lab.platform.variant.api.service.VariantService;
import com.invitae.lab.platform.variant.entity.CreateVariantRequest;
import com.invitae.lab.platform.variant.entity.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Spring Boot {@link RestController} which exposes endpoints for Variant API
 *
 * @author dgoodman
 */
@RestController
@RequestMapping("/api/v1/variants")
public class VariantController {
    private final VariantService service;

    @Autowired
    public VariantController(final VariantService service) {
        this.service = service;
    }

    /**
     * Endpoint definition for GETing a variant by an ID
     *
     * @param variantId UUID defined in the path
     * @return {@link ResponseEntity} containing the {@link Variant} if it exists
     */
    @GetMapping(value = "/{variantId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Variant> getVariant(@PathVariable final UUID variantId) {
        return service.getVariant(variantId)
                .map(value -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(value))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint definition for POSTing a variant
     *
     * @param request {@link CreateVariantRequest} deserialized from the request body
     * @return {@link ResponseEntity} containing the {@link Variant} if it was created
     */
    @PostMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Variant> postVariant(@RequestBody final CreateVariantRequest request) {
        return service.createVariant(request)
                .map(value -> ResponseEntity.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(value))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
    }
}
