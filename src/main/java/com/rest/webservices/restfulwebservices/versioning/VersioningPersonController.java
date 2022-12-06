package com.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

    @GetMapping("/v1/person")
    public Person getFirstVersionOfPerson() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping("/v2/person")
    public Person getSecondVersionOfPerson() {
        return new PersonV2("Bob", "Charlie");
    }

    @GetMapping(path = "/person", params = "version=1")
    public Person getFirstVersionOfPersonRequestParameter() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(path = "/person", params = "version=2")
    public Person getSecondVersionOfPersonRequestParameter() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
    public Person getFirstVersionOfPersonRequestHeader() {
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
    public Person getSecondVersionOfPersonRequestHeader() {
        return new PersonV2("Bob", "Charlie");
    }
}
