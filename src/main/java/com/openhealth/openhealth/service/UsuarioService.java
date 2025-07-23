package com.openhealth.openhealth.service;

import com.openhealth.openhealth.dto.usuario.UsuarioCreateDTO;
import com.openhealth.openhealth.dto.usuario.UsuarioResponseDTO;
import com.openhealth.openhealth.dto.usuario.UsuarioUpdateDTO;
import com.openhealth.openhealth.entity.Usuario;
import com.openhealth.openhealth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    public UsuarioResponseDTO create(UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario();
        String roleUpper = dto.role().toUpperCase();
        usuario.setUsername(dto.username());
        usuario.setPassword(passwordEncoder.encode(dto.password()));
        usuario.setRole("ROLE_" + roleUpper);
        repository.save(usuario);
        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getRole());
    }

    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getRole());
    }

    public List<UsuarioResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(u -> new UsuarioResponseDTO(u.getId(), u.getUsername(), u.getRole()))
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO update(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (dto.username() != null) usuario.setUsername(dto.username());
        if (dto.password() != null) usuario.setPassword(passwordEncoder.encode(dto.password()));
        if (dto.role() != null) usuario.setRole(dto.role());
        repository.save(usuario);
        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getRole());
    }

    public String login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtService.generateToken(userDetails);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
} 