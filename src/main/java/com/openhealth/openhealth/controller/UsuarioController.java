package com.openhealth.openhealth.controller;

import com.openhealth.openhealth.dto.usuario.UsuarioCreateDTO;
import com.openhealth.openhealth.dto.usuario.UsuarioLoginDTO;
import com.openhealth.openhealth.dto.usuario.UsuarioResponseDTO;
import com.openhealth.openhealth.dto.usuario.UsuarioUpdateDTO;
import com.openhealth.openhealth.service.UsuarioService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @Operation(summary = "Criar um usuário", description = "Cria um novo usuário")
    @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao criar usuário")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<UsuarioResponseDTO> create(@RequestBody @Valid UsuarioCreateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Busca um usuário pelo ID")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @Operation(summary = "Buscar todos os usuários", description = "Busca todos os usuários cadastrados")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza um usuário existente")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar usuário")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Deleta um usuário existente")
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Realiza o login de um usuário")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao realizar login")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<String> login(@RequestBody @Valid UsuarioLoginDTO dto, HttpServletResponse response) {
        String jwt = service.login(dto.getUsername(), dto.getPassword());

        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
} 