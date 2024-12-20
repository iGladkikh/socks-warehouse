package com.igladkikh.warehouse.service;

import com.igladkikh.warehouse.dto.SockDto;
import com.igladkikh.warehouse.dto.SockQueryFilter;
import com.igladkikh.warehouse.exception.BadRequestException;
import com.igladkikh.warehouse.exception.DataConflictException;
import com.igladkikh.warehouse.exception.DataNotFoundException;
import com.igladkikh.warehouse.mapper.SockMapper;
import com.igladkikh.warehouse.model.Sock;
import com.igladkikh.warehouse.model.SockColor;
import com.igladkikh.warehouse.repository.SockRepository;
import com.igladkikh.warehouse.util.FileUtil;
import com.igladkikh.warehouse.util.SockDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SockServiceImpl implements SockService {
    private final SockRepository repository;
    private final SockDtoUtil dtoValidator;

    public List<SockDto> findWithFilter(SockQueryFilter filter) {

        Specification<Sock> spec = Specification
                .where(repository.hasColor(filter.getColors()))
                .and(repository.cottonEquals(filter.getCotton()))
                .and(repository.cottonLessThan(filter.getCottonMax()))
                .and(repository.cottonGreaterThan(filter.getCottonMin()));

        Sort sort = Sort.by(filter.getSortField().toString().toLowerCase());
        sort = filter.getSortDirection() == Sort.Direction.ASC ? sort.ascending() : sort.descending();

        return SockMapper.toDto(repository.findAll(spec, sort));
    }

    @Override
    @Transactional
    public SockDto plus(SockDto dto) {
        return update(dto, Operation.PLUS);
    }

    @Override
    @Transactional
    public SockDto minus(SockDto dto) {
        return update(dto, Operation.MINUS);
    }

    private SockDto update(SockDto dto, Operation operation) {
        Sock sock;
        Optional<Sock> optionalSock = repository.findByColorAndCotton(dto.getColor(), dto.getCotton());
        if (optionalSock.isPresent()) {
            sock = optionalSock.get();
            int resultQuantity = operation == Operation.MINUS
                    ? sock.getQuantity() - dto.getQuantity()
                    : sock.getQuantity() + dto.getQuantity();

            if (resultQuantity < 0) {
                throw new BadRequestException("Недостаточное количество товара для отгрузки");
            }

            sock.setQuantity(resultQuantity);
        } else {
            sock = repository.save(SockMapper.toEntity(dto));
        }
        return SockMapper.toDto(sock);
    }

    @Override
    @Transactional
    public SockDto update(long id, SockDto dto) {
        Optional<Sock> optionalSock = repository.findById(id);
        if (optionalSock.isEmpty()) {
            throw new DataNotFoundException("Товар с id=" + id + " не найден");
        }

        Sock oldSock = optionalSock.get();
        if (oldSock.getId() != id) {
            throw new DataConflictException("Товар с цветом=" + dto.getColor() +
                    " с содержанием хлопка " + dto.getCotton() + " имеет другой id");
        }
        Sock newSock = SockMapper.toEntity(dto);
        newSock.setId(oldSock.getId());
        return SockMapper.toDto(repository.save(newSock));
    }

    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public List<SockDto> uploadFromFile(MultipartFile file) {
        try (Scanner scanner = new Scanner(file.getInputStream())) {
            // Сохранение копии файла
            FileUtil.createTempFile(file);

            // Обработка содержимого файла
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(",");

                SockDto sockDto = new SockDto();
                sockDto.setColor(SockColor.valueOf(split[0]));
                sockDto.setCotton(Integer.parseInt(split[1]));
                sockDto.setQuantity(Integer.parseInt(split[2]));

                update(sockDto);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return SockMapper.toDto(repository.findAll());
    }

    private void update(SockDto dto) {
        // Валидация
        dtoValidator.validate(dto);

        Optional<Sock> optionalSock = repository.findByColorAndCotton(dto.getColor(), dto.getCotton());
        if (optionalSock.isEmpty()) {
            repository.save(SockMapper.toEntity(dto));
        } else {
            Sock oldSock = optionalSock.get();
            oldSock.setQuantity(dto.getQuantity());
            repository.save(oldSock);
        }
    }

    private enum Operation {
        PLUS,
        MINUS
    }
}
