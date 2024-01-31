package org.springeel.oneclickrecipe.sample.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springeel.oneclickrecipe.domain.user.entity.User;
import org.springeel.oneclickrecipe.sample.dto.service.TestCreateServiceRequestDto;
import org.springeel.oneclickrecipe.sample.dto.service.TestReadResponseDto;
import org.springeel.oneclickrecipe.sample.entity.Test;
import org.springeel.oneclickrecipe.sample.exception.NotFoundTestException;
import org.springeel.oneclickrecipe.sample.exception.TestErrorCode;
import org.springeel.oneclickrecipe.sample.mapper.entity.TestEntityMapper;
import org.springeel.oneclickrecipe.sample.repository.TestRepository;
import org.springeel.oneclickrecipe.sample.service.TestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TestEntityMapper testEntityMapper;

    // TestCreateServiceRequestDto -> Test
    @Override
    public void create(final TestCreateServiceRequestDto testRequestDto, User user) {
        // Mapper로 만들기
        Test test = testEntityMapper.toTest(testRequestDto);

        /* Builder로 만들기
            Test test = Test.builder()
            .name(testRequestDto.name())
            .age(testRequestDto.age())
            .build();
         */

        testRepository.save(test);
    }

    // Test -> TestCreateServiceRequestDto
    @Override
    public TestReadResponseDto get(Long id) {
        Test test = testRepository.findById(id)
            .orElseThrow(() -> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST));
        return testEntityMapper.toTestReadResponseDto(test);
    }

    // List<Test> -> List<TestCreateServiceRequestDto>
    @Override
    public List<TestReadResponseDto> getAll(int pageNumber) {
        Slice<Test> tests = testRepository.findAllBy(PageRequest.of(pageNumber, 9));
        return testEntityMapper.toTestReadResponseDtos(tests.getContent());
    }
}
