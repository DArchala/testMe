package pl.archala.testme.service;

import org.springframework.stereotype.Service;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.security.Token;

import javax.persistence.EntityNotFoundException;

@Service
public class TokenService {

    private final TokenRepository tokenRepo;

    public TokenService(TokenRepository tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    public Token findByValue(String value) {
        return tokenRepo.findByValue(value).orElseThrow(() -> new EntityNotFoundException("Token not found"));
    }
}
