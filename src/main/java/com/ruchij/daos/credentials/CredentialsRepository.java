package com.ruchij.daos.credentials;

import com.ruchij.daos.credentials.models.Credentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<Credentials, String> {
    Optional<Credentials> findCredentialsByUserId(String userId);
}
