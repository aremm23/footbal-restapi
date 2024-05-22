package by.artsem.footballrestapi.security.service;

import by.artsem.footballrestapi.exceptions.DataNotCreatedException;
import by.artsem.footballrestapi.exceptions.DataNotFoundedException;
import by.artsem.footballrestapi.security.model.MyUser;
import by.artsem.footballrestapi.security.model.Role;
import by.artsem.footballrestapi.security.model.dto.AuthenticationRequest;
import by.artsem.footballrestapi.security.model.dto.AuthenticationResponse;
import by.artsem.footballrestapi.security.model.dto.RegisterRequest;
import by.artsem.footballrestapi.security.repository.MyUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AuthenticationService {

    private final MyUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        MyUser user = MyUser.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DataNotCreatedException("Username is already taken");
        }
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(
                        () -> new DataNotFoundedException("Unknown username")
                );
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
