package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.impl.dbDTO.MpaDbStorage;
import java.util.List;

@Service
@Slf4j
public class MpaService {

    private final MpaDbStorage mpaDbStorage;
    @Autowired
    public MpaService(MpaDbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }

    public Mpa getMpa(int id) {
        log.info("Получен запрос на получение MPA по id" + id);
        return mpaDbStorage.getMPA(id);
    }

    public List<Mpa> getListMpa() {
        log.info("Получен запрос на получение списка MPA");
      return mpaDbStorage.getListMpa();
    }
}
