package com.example.cs393backend.RepositoryTests;

import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByUsername_thenReturnUser() {
        // given
        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setEmail("test@example.com");
        entityManager.persist(user);
        entityManager.flush();

        // when
        UserEntity found = userRepository.findByUsername(user.getUsername());

        // then
        assertThat(found.getUsername())
                .isEqualTo(user.getUsername());
    }
    @Test
    public void whenFindByEmail_thenReturnUser() {
        // given
        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setEmail("test@example.com");
        entityManager.persist(user);
        entityManager.flush();

        // when
        UserEntity found = userRepository.findByEmail(user.getEmail());

        // then
        assertThat(found.getEmail())
                .isEqualTo(user.getEmail());
    }
    @Test
    public void whenUpdateUser_thenUserShouldBeUpdated() {
        // given
        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setEmail("test@example.com");
        entityManager.persist(user);
        entityManager.flush();

        // when
        user.setUsername("updated");
        user.setEmail("updated@example.com");
        userRepository.save(user);

        // then
        assertThat(userRepository.findByUsername(user.getUsername()).getEmail())
                .isEqualTo(user.getEmail());
    }
    @Test
    public void whenSaveUser_thenUserShouldBeSaved() {
        // given
        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setEmail("test@example.com");

        // when
        userRepository.save(user);

        // then
        assertThat(userRepository.findByUsername(user.getUsername())).isNotNull();
    }

    @Test
    public void whenDeleteUser_thenUserShouldBeDeleted() {
        // given
        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setEmail("test@example.com");
        entityManager.persist(user);
        entityManager.flush();

        // when
        userRepository.delete(user);

        // then
        assertThat(userRepository.findByUsername(user.getUsername())).isNull();
    }

}
