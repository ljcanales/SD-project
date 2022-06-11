package com.sd.apirest.controllers;

import com.sd.apirest.dao.ProviderDAO;
import com.sd.apirest.model.Provider;
import com.sd.apirest.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProviderController {

    @Autowired
    ProviderDAO providerDAO;

    @GetMapping("/provider/{id}")
    public HttpEntity<?> getProvider(@PathVariable(name = "id") String providerId) {
        Provider provider = providerDAO.getProvider(providerId);
        if (provider == null) {
            return new ResponseEntity<>(new ResponseMessage("Provider not found."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(getEntity(provider), HttpStatus.OK);
    }

    @PostMapping("/provider")
    public HttpEntity<?> saveProvider(@RequestBody Provider provider) {
        if (provider == null) {
            return new ResponseEntity<>(new ResponseMessage("No valid provider was provided."), HttpStatus.BAD_REQUEST);
        }
        Provider savedProvider = providerDAO.save(provider);
        return new ResponseEntity<>(getEntity(savedProvider), HttpStatus.OK);
    }

    @PutMapping("/provider/{id}")
    public HttpEntity<?> replaceProvider(@PathVariable(name = "id") String providerId, @RequestBody Provider provider) {
        if (providerId == null) {
            return new ResponseEntity<>(new ResponseMessage("No valid provider was provided."), HttpStatus.BAD_REQUEST);
        }
        if (providerId.equals(provider.getId())) {
            return new ResponseEntity<>(new ResponseMessage("IDs don't match."), HttpStatus.BAD_REQUEST);
        }
        Provider providerDB = providerDAO.getProvider(providerId);
        if (providerDB == null) {
            return new ResponseEntity<>(new ResponseMessage("Provider not found."), HttpStatus.NOT_FOUND);
        }
        Provider modifiedProvider = providerDAO.save(provider);
        return new ResponseEntity<>(getEntity(modifiedProvider), HttpStatus.OK);
    }

    @DeleteMapping("/provider/{id}")
    public HttpEntity<?> deleteProvider(@PathVariable(name = "id") String providerId) {
        Provider providerDB = providerDAO.getProvider(providerId);
        if (providerDB == null) {
            return new ResponseEntity<>(new ResponseMessage("Provider not found."), HttpStatus.NOT_FOUND);
        }
        providerDAO.deleteById(providerId);
        return new ResponseEntity<>(new ResponseMessage("Provider has been deleted."), HttpStatus.OK);
    }

    private EntityModel<Provider> getEntity(Provider provider) {
        EntityModel<Provider> providerResource = EntityModel.of(provider);
        providerResource.add(linkTo(methodOn(ProviderController.class).getProvider(provider.getId())).withSelfRel());
        return providerResource;
    }
}
