package com.example.service;

import com.example.entity.Topic;
import com.example.enums.Constants;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.TopicRequest;
import com.example.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService implements BaseService<TopicRequest, Integer> {

    private final TopicRepository topicRepository;

    @Override
    public ApiResponse create(TopicRequest topicRequest) {
        Topic topic = Topic.toTopic(topicRequest);
        return new ApiResponse(topicRepository.save(topic), true);
    }

    @Override
    public ApiResponse getById(Integer id) {
        return new ApiResponse(checkById(id), true);
    }

    @Override
    public ApiResponse update(TopicRequest topicRequest) {
        checkById(topicRequest.getId());
        Topic setTopic = Topic.toTopic(topicRequest);
        setTopic.setId(topicRequest.getId());
        return new ApiResponse(topicRepository.save(setTopic), true);
    }

    @Override
    public ApiResponse delete(Integer id) {
        Topic topic = checkById(id);
        topicRepository.delete(topic);
        return new ApiResponse(Constants.DELETED, true, topic);
    }

    public ApiResponse deleteALL(List<Topic> topics) {
        topicRepository.deleteAll(topics);
        return new ApiResponse(Constants.DELETED, true);
    }


    public List<Topic> checkAllById(List<Integer> id) {
        return topicRepository.findAllById(id);
    }

    public Topic checkById(Integer id) {
        return topicRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(Constants.TOPIC_NOT_FOUND));
    }

    public List<Topic> toAllEntity(List<TopicRequest> topicList) {
        List<Topic> topics = new ArrayList<>();
        topicList.forEach(topicRequest -> {
            topics.add(Topic.toTopic(topicRequest));
        });
        return topics;
    }
}
