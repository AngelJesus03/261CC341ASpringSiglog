/**
 * Tipos TypeScript que reflejan los DTOs del backend (HU01).
 * Spring Boot endpoints:
 *   POST /api/auth/login    -> AuthResponse
 *   POST /api/auth/registro -> AuthResponse
 *   GET  /api/auth/perfil   -> PerfilResponse
 */

export type Rol =
  | 'JEFE_LOGISTICA'
  | 'ASISTENTE_LOGISTICA'
  | 'TESORERO'
  | 'GERENTE_FINANCIERO'
  | 'GERENTE_GENERAL'
  | 'CONTADOR'
  | 'ADMIN';

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegistroRequest {
  nombre: string;
  email: string;
  password: string;
  rol: Rol;
}

export interface AuthResponse {
  token: string;
  email: string;
  nombre: string;
  rol: Rol;
  mensaje: string;
}

export interface PerfilResponse {
  id: number;
  nombre: string;
  email: string;
  rol: Rol;
  activo: boolean;
}
