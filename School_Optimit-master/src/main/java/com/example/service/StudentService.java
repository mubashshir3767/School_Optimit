package com.example.service;

import com.example.entity.*;
import com.example.exception.*;
import com.example.model.common.ApiResponse;
import com.example.model.request.FamilyLoginDto;
import com.example.model.request.StudentDto;
import com.example.model.response.StudentResponse;
import com.example.model.response.StudentResponseListForAdmin;
import com.example.repository.BranchRepository;
import com.example.repository.JournalRepository;
import com.example.repository.StudentClassRepository;
import com.example.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class StudentService implements BaseService<StudentDto, Integer> {

    private final StudentRepository studentRepository;
    private final AttachmentService attachmentService;
    private final StudentClassRepository studentClassRepository;
    private final BranchRepository branchRepository;
    private final JournalRepository journalRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = {FileNotFoundException.class, UserNotFoundException.class,
            FileInputException.class, OriginalFileNameNullException.class, InputException.class, RecordNotFoundException.class})
    public ApiResponse create(StudentDto studentDto) {
        if (studentRepository.existsByUsername(studentDto.getUsername())) {
            throw new UserAlreadyExistException(PHONE_NUMBER_ALREADY_REGISTERED);
        }
        Branch branch = branchRepository.findById(studentDto.getBranchId()).orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        Student save = studentRepository.save(from(studentDto, branch));
        return new ApiResponse(from(save), true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Student student = studentRepository.findById(integer).orElseThrow(() -> new UserNotFoundException(STUDENT_NOT_FOUND));
        return new ApiResponse(from(student), true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = {FileNotFoundException.class, UserNotFoundException.class,
            FileInputException.class, OriginalFileNameNullException.class, InputException.class, RecordNotFoundException.class})
    public ApiResponse update(StudentDto studentDto) {
        Student student = studentRepository.findById(studentDto.getId()).orElseThrow(() -> new UserNotFoundException(STUDENT_NOT_FOUND));
        studentRepository.save(update(studentDto, student));
        return new ApiResponse(SUCCESSFULLY, true);
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Student student = studentRepository.findById(integer).orElseThrow(() -> new UserNotFoundException(STUDENT_NOT_FOUND));
        student.setActive(false);
        studentRepository.save(student);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getList(int page, int size, int branchId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> students = studentRepository.findAllByBranchIdAndActiveTrue(pageable, branchId);
        List<StudentResponse> studentResponseList = new ArrayList<>();
        students.forEach(student -> studentResponseList.add(StudentResponse.from(student)));
        return new ApiResponse(new StudentResponseListForAdmin(studentResponseList, students.getTotalElements(), students.getTotalPages(), students.getNumber()), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getListByClassNumber(Integer classId, int branchId) {
        List<Student> students = studentRepository.findAllByStudentClassIdAndBranchIdAndActiveTrue(classId, branchId);
        List<StudentResponse> studentResponseList = new ArrayList<>();
        students.forEach(student -> studentResponseList.add(StudentResponse.from(student)));
        return new ApiResponse(studentResponseList, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllNeActiveStudents(int branchId) {
        List<Student> neActiveStudents = studentRepository.findAllByBranchIdAndActiveFalseOrderByAddedTimeAsc(branchId);
        List<StudentResponse> studentResponseList = new ArrayList<>();
        neActiveStudents.forEach(student -> studentResponseList.add(StudentResponse.from(student)));
        return new ApiResponse(studentResponseList, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse studentLogIn(FamilyLoginDto dto) {
        Student student = studentRepository.findByUsernameAndPassword(dto.getPhoneNumber(), dto.getPassword()).orElseThrow(() -> new RecordNotFoundException(STUDENT_NOT_FOUND));
        Journal journal = journalRepository.findByStudentClassIdAndActiveTrue(student.getStudentClass().getId()).orElseThrow(() -> new RecordNotFoundException(JOURNAL_NOT_FOUND));

        return new ApiResponse(journal.getSubjectList(), true);
    }

    private Student from(StudentDto student, Branch branch) {
        Student from = Student.from(student);
        from.setStudentClass(studentClassRepository.findById(student.getStudentClassId())
                .orElseThrow(() -> new RecordNotFoundException(CLASS_NOT_FOUND)));
        from.setPhoto(attachmentService.saveToSystem(student.getPhoto()));
        from.setReference(attachmentService.saveToSystem(student.getPhoto()));
        from.setMedDocPhoto(attachmentService.saveToSystem(student.getMedDocPhoto()));
        from.setDocPhoto(attachmentService.saveToSystemListFile(student.getDocPhoto()));
        from.setBranch(branch);
        return from;
    }

    private StudentResponse from(Student student) {
        StudentResponse from = StudentResponse.from(student);
        List<String> docPhotoList = new ArrayList<>();
        student.getDocPhoto().forEach(obj -> docPhotoList.add(attachmentService.getUrl(obj)));
        from.setPhoto(attachmentService.getUrl(student.getPhoto()));
        from.setReference(attachmentService.getUrl(student.getReference()));
        from.setMedDocPhoto(attachmentService.getUrl(student.getMedDocPhoto()));
        from.setDocPhoto(docPhotoList);
        return from;
    }

    private Student update(StudentDto studentDto, Student student) {

        if (!studentDto.getStudentClassId().equals(student.getStudentClass().getId())) {
            StudentClass studentClass = studentClassRepository.findById(studentDto.getStudentClassId())
                    .orElseThrow(() -> new RecordNotFoundException(CLASS_NOT_FOUND));
            student.setStudentClass(studentClass);
        }
        if (studentDto.getPhoto() != null) {
            Attachment photo = attachmentService.saveToSystem(studentDto.getPhoto());
            attachmentService.deleteNewName(student.getPhoto());
            student.setPhoto(photo);
        }
        if (studentDto.getReference() != null) {
            Attachment reference = attachmentService.saveToSystem(studentDto.getReference());
            attachmentService.deleteNewName(student.getReference());
            student.setReference(reference);
        }
        if (studentDto.getDocPhoto() != null) {
            List<Attachment> docPhotos = attachmentService.saveToSystemListFile(studentDto.getDocPhoto());
            attachmentService.deleteListFilesByNewName(student.getDocPhoto());
            student.setDocPhoto(docPhotos);
        }

        return Student.builder()
                .id(student.getId())
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .fatherName(studentDto.getFatherName())
                .birthDate(studentDto.getBirthDate())
                .docNumber(studentDto.getDocNumber())
                .docPhoto(student.getDocPhoto())
                .reference(student.getReference())
                .photo(student.getPhoto())
                .studentClass(student.getStudentClass())
                .active(studentDto.isActive())
                .build();
    }


}
