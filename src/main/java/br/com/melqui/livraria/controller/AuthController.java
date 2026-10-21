package br.com.melqui.livraria.controller;

import br.com.melqui.livraria.dto.request.LoginRequestDto;
import br.com.melqui.livraria.dto.request.RegisterRequestDto;
import br.com.melqui.livraria.dto.response.TokenResponseDto;
import br.com.melqui.livraria.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticação", description = "Cadastro de usuário e login (gera o token JWT)")
@SecurityRequirements
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Cadastra um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    @PostMapping("/register")
    public ResponseEntity<Void> registrar(@RequestBody @Valid RegisterRequestDto dto) {
        authService.registrar(dto.email(), dto.senha());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Faz login e devolve o token JWT", description = "Copie o token da resposta e cole no botão Authorize para usar as outras rotas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login feito; token na resposta"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "E-mail ou senha inválidos")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        String token = authService.login(dto.email(), dto.senha());
        return ResponseEntity.ok(new TokenResponseDto(token));
    }
}
