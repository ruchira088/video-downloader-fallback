package com.ruchij.services.user;

import com.ruchij.daos.credentials.CredentialsRepository;
import com.ruchij.daos.credentials.models.Credentials;
import com.ruchij.daos.user.UserRepository;
import com.ruchij.daos.user.models.User;
import com.ruchij.exceptions.ResourceConflictException;
import com.ruchij.exceptions.ResourceNotFoundException;
import com.ruchij.services.generator.IdGenerator;
import com.ruchij.services.system.SystemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final SystemService systemService;
    private final IdGenerator idGenerator;

    public UserServiceImpl(
        UserRepository userRepository,
        CredentialsRepository credentialsRepository,
        PasswordEncoder passwordEncoder,
        SystemService systemService,
        IdGenerator idGenerator
    ) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
        this.systemService = systemService;
        this.idGenerator = idGenerator;
    }

    @Transactional
    @Override
    public User create(String email, String password, String firstName, String lastName) throws ResourceConflictException {
        Optional<User> existingUser = userRepository.findUserByEmail(email);

        if (existingUser.isPresent()) {
            throw new ResourceConflictException("User with email=%s already exists".formatted(email));
        }

        Instant timestamp = systemService.timestamp();

        String userId = idGenerator.generate();
        User user = userRepository.save(new User(userId, email, firstName, lastName, timestamp));

        String encodedPassword = passwordEncoder.encode(password);
        credentialsRepository.save(new Credentials(userId, encodedPassword, timestamp));

        return user;
    }

    @Override
    public User update(String userId, Optional<String> email, Optional<String> firstName, Optional<String> lastName)
        throws ResourceNotFoundException {
        User user = getById(userId);

        user.setEmail(email.orElse(user.getEmail()));
        user.setFirstName(firstName.orElse(user.getFirstName()));
        user.setLastName(lastName.orElse(user.getLastName()));

        if (Stream.of(email, firstName, lastName).anyMatch(Optional::isPresent)) {
            Instant timestamp = systemService.timestamp();
            user.setUpdatedAt(timestamp);
        }

        return userRepository.save(user);
    }

    @PreAuthorize("hasPermission(#userId, 'USER', 'read')")
    @Override
    public User getById(String userId) throws ResourceNotFoundException {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User with id=%s not found".formatted(userId)));
    }
}
