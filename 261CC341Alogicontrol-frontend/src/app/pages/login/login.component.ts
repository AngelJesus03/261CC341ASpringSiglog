import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NavbarComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private auth = inject(AuthService);
  private router = inject(Router);

  loading = signal(false);
  errorMsg = signal<string | null>(null);

  form = this.fb.nonNullable.group({
    email: ['admin@xyz.com', [Validators.required, Validators.email]],
    password: ['Admin1234!', [Validators.required]]
  });

  onSubmit(): void {
    if (this.form.invalid) return;

    this.loading.set(true);
    this.errorMsg.set(null);

    this.auth.login(this.form.getRawValue()).subscribe({
      next: () => {
        this.loading.set(false);
        this.router.navigate(['/home']);
      },
      error: (err) => {
        this.loading.set(false);
        this.errorMsg.set(
          err.status === 401
            ? 'Credenciales inválidas. Verifica tu email y contraseña.'
            : 'No se pudo conectar con el servidor. ¿Está corriendo el backend?'
        );
      }
    });
  }
}
