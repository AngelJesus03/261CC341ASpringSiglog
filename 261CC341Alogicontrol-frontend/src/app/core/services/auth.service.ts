import { Injectable, signal, computed, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AuthResponse, LoginRequest, PerfilResponse, Rol } from '../models/auth.model';

const TOKEN_KEY = 'logicontrol_token';
const USER_KEY = 'logicontrol_user';

interface UsuarioSesion {
  email: string;
  nombre: string;
  rol: Rol;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);

  /** Estado reactivo del usuario actual (signal) */
  private _usuario = signal<UsuarioSesion | null>(this.cargarUsuarioDeStorage());
  readonly usuario = this._usuario.asReadonly();
  readonly estaAutenticado = computed(() => this._usuario() !== null);

  /**
   * Llama a POST /api/auth/login y guarda el token + usuario.
   */
  login(credenciales: LoginRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${environment.apiUrl}/auth/login`, credenciales)
      .pipe(
        tap((res) => {
          localStorage.setItem(TOKEN_KEY, res.token);
          const usuario: UsuarioSesion = {
            email: res.email,
            nombre: res.nombre,
            rol: res.rol
          };
          localStorage.setItem(USER_KEY, JSON.stringify(usuario));
          this._usuario.set(usuario);
        })
      );
  }

  obtenerPerfil(): Observable<PerfilResponse> {
    return this.http.get<PerfilResponse>(`${environment.apiUrl}/auth/perfil`);
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    this._usuario.set(null);
    this.router.navigate(['/login']);
  }

  obtenerToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  private cargarUsuarioDeStorage(): UsuarioSesion | null {
    const raw = localStorage.getItem(USER_KEY);
    if (!raw) return null;
    try {
      return JSON.parse(raw) as UsuarioSesion;
    } catch {
      return null;
    }
  }
}
