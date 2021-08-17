package com.invitae.lab.platform.variant.api.controller;

import com.invitae.lab.platform.variant.api.service.VariantService;
import com.invitae.lab.platform.variant.entity.CreateVariantRequest;
import com.invitae.lab.platform.variant.entity.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@RequestMapping("/api/v1/variants")
public class VariantController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VariantController.class);

    private final VariantService service;

    @Autowired
    public VariantController(final VariantService service) {
        this.service = service;
    }

    @GetMapping(value = "/{variantId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Variant> getVariant(@PathVariable final UUID variantId) {
        return service.getVariant(variantId)
                .map(value -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(value))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Void> postVariant(@RequestBody final CreateVariantRequest request) {
        service.createVariant(request);
        return ResponseEntity.accepted().build();
    }

}
