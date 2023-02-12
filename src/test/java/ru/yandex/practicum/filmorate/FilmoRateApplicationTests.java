package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.impl.dbDTO.UserDbStorage;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

    @SpringBootTest
    @AutoConfigureTestDatabase
    @RequiredArgsConstructor(onConstructor_ = @Autowired)
    class FilmoRateApplicationTests {
        private final UserDbStorage userStorage;

        @Test
         void beforeAll() {
            User user = User.builder()
                    .email("mail@mail.ru")
                    .login("dolore")
                    .birthday(LocalDate.parse("1946-08-20"))
                    .build();
            user.setId(1);
            userStorage.saveUser(user);
        }

        @Test
        public void testFindUserById() {
        Optional<User> userOptional = Optional.of(userStorage.getUser(1));

        assertThat(userOptional)
				.isPresent()
				.hasValueSatisfying(user ->
        assertThat(user).hasFieldOrPropertyWithValue("id", 1)

				);
    }
}