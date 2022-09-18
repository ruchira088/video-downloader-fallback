package com.ruchij.daos.credentials;

import com.ruchij.daos.credentials.models.Credentials;
import org.springframework.data.repository.CrudRepository;

public interface CredentialsRepository extends CrudRepository<Credentials, String> {
}
