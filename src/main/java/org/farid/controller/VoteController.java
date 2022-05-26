package org.farid.controller;

import org.farid.model.domain.VoteNewRequest;
import org.farid.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("v1/votes")
public class VoteController {

    final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?>  newProvider(@Valid @RequestBody VoteNewRequest voteNewRequest){
        return new ResponseEntity<>(voteService.newVote(voteNewRequest), HttpStatus.CREATED);
    }

}
