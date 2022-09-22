package com.ruchij.daos.credentials;

import com.ruchij.daos.credentials.models.Credentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends CrudRepository<Credentials, String> {
    Optional<Credentials> findCredentialsByUserId(String userId);
}
