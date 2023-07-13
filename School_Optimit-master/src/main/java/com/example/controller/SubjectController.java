package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.SubjectRequest;
import com.example.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subject/")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("save")
    public ApiResponse save(@RequestBody SubjectRequest subjectRequest){
        return subjectService.create(subjectRequest);
    }

    @PostMapping("saveTopic")
    public ApiResponse saveTopic(@RequestBody SubjectRequest subjectRequest){
        return subjectService.addTopic(subjectRequest);
    }

    @GetMapping("getById/{id}")
    public ApiResponse getById(@PathVariable Integer id){
        return subjectService.getById(id);
    }

    @GetMapping("getTopicList/{subjectId}")
    public ApiResponse getTopicList(@PathVariable Integer subjectId){
        return subjectService.getTopicList(subjectId);
    }

    @PutMapping("update")
    public ApiResponse update(@RequestBody SubjectRequest subjectRequest){
        return subjectService.update(subjectRequest);
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse delete(@PathVariable Integer id){
        return subjectService.delete(id);
    }

    @DeleteMapping("deleteTopic/{subjectId}/{topicId}")
    public ApiResponse deleteTopic(@PathVariable Integer subjectId,
                                   @PathVariable Integer topicId){
        return subjectService.deleteTopic(subjectId,topicId);
    }
}
