package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.TopicRequest;
import com.example.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic/")
public class TopicController {

    private final TopicService topicService;

    @PostMapping("save")
    public ApiResponse save(@RequestBody TopicRequest topicRequest){
        return topicService.create(topicRequest);
    }

    @GetMapping("getById/{id}")
    public ApiResponse getById(@PathVariable Integer id){
        return topicService.getById(id);
    }

    @PutMapping("update")
    public ApiResponse update(@RequestBody TopicRequest topicRequest){
        return topicService.update(topicRequest);
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse delete(@PathVariable Integer id){
        return topicService.delete(id);
    }
}
