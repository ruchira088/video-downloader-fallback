package com.ruchij.web.response;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public record ResultsList<T>(List<T> results, Optional<Pageable> pageable) {
}
