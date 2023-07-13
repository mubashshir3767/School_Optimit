package com.example.service;

import com.example.entity.Subject;
import com.example.entity.Topic;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.SubjectRequest;
import com.example.model.request.TopicRequest;
import com.example.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService implements BaseService<SubjectRequest, Integer> {

    private final SubjectRepository subjectRepository;
    private final TopicService topicService;

    @Override
    public ApiResponse create(SubjectRequest subjectRequest) {
        checkIfExist(subjectRequest);
        Subject subject = Subject.toSubject(subjectRequest);
        subject.setTopicList(topicService.toAllEntity(subjectRequest.getTopicList()));
        return new ApiResponse(subjectRepository.save(subject), true);
    }

    public ApiResponse addTopic(SubjectRequest subjectRequest) {
        Subject subject = checkById(subjectRequest.getId());
        subject.setTopicList(doAddTopic(subjectRequest.getTopicList(), subject));
        return new ApiResponse(subjectRepository.save(subject), true);
    }

    @Override
    public ApiResponse getById(Integer id) {
        return new ApiResponse(checkById(id), true);
    }

    public ApiResponse getTopicList(Integer subjectId) {
        return new ApiResponse(checkById(subjectId).getTopicList(), true);
    }

    @Override
    public ApiResponse update(SubjectRequest subjectRequest) {
        checkById(subjectRequest.getId());
        Subject subject = Subject.toSubject(subjectRequest);
        subject.setId(subjectRequest.getId());
        subject.setTopicList(topicService.toAllEntity(subjectRequest.getTopicList()));
        return new ApiResponse(subjectRepository.save(subject), true);
    }

    @Override
    public ApiResponse delete(Integer id) {
        Subject subject = checkById(id);
        topicService.deleteALL(subject.getTopicList());
        subjectRepository.deleteById(id);
        return new ApiResponse(Constants.DELETED, true, subject);
    }


    public ApiResponse deleteTopic(Integer subjectId, Integer topicId) {
        Subject subject = checkById(subjectId);
        subject.setTopicList(topicDelete(topicId, subject));
        subjectRepository.save(subject);
        topicService.delete(topicId);
        return new ApiResponse(Constants.DELETED, true);
    }

    private List<Topic> topicDelete(Integer topicId, Subject subject) {
        List<Topic> topicList = new ArrayList<>();
        isDelete(topicId, subject, topicList);
        isEquals(subject, topicList);
        return topicList;
    }

    private  void isDelete(Integer topicId, Subject subject, List<Topic> topicList) {
        subject.getTopicList().forEach(topic -> {
            if (!topic.getId().equals(topicId)) {
                topicList.add(topic);
            }
        });
    }

    private static void isEquals(Subject subject, List<Topic> topicList) {
        if (subject.getTopicList().size() == topicList.size()) {
            throw new RecordNotFoundException(Constants.TOPIC_NOT_FOUND);
        }
    }

    private List<Topic> doAddTopic(List<TopicRequest> topics, Subject subject) {
        List<Topic> topicList = new ArrayList<>();
        for (TopicRequest topicDto : topics) {
            isExist(subject, topicDto.getName());
            ApiResponse apiResponse = topicService.create(topicDto);
            topicList.add((Topic) apiResponse.getData());
        }
        topicList.addAll(subject.getTopicList());
        return topicList;
    }

    private void isExist(Subject subject, String topicName) {
        subject.getTopicList().forEach(topic -> {
            if (topic.getName().equals(topicName)) {
                throw new RecordAlreadyExistException(Constants.TOPIC_ALREADY_EXIST + "   name: " + topicName);
            }
        });
    }

    private void checkIfExist(SubjectRequest subjectRequest) {
        boolean present = subjectRepository.findByName(subjectRequest.getName()).isPresent();
        boolean level = subjectRepository.findByLevel(subjectRequest.getLevel()).isPresent();
        if (present && level) {
            throw new RecordAlreadyExistException(Constants.SUBJECT_ALREADY_EXIST);
        }
    }

    public Subject checkById(Integer id) {
        return subjectRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(Constants.SUBJECT_NOT_FOUND));
    }

    public List<Subject> checkAllById(List<Integer> subjects) {
        return subjectRepository.findAllById(subjects);
    }
}
