// Espelha o LoginRequestDto e o RegisterRequestDto da API (os dois têm e-mail e senha)
export interface Credenciais {
  email: string;
  senha: string;
}

// Espelha o TokenResponseDto da API (resposta do login)
export interface TokenResponse {
  token: string;
}
