import { Component, Input, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  /** Si es true, muestra el menú público (Inicio, Nosotros, etc.) */
  @Input() publico = false;
  /** Migas de pan: ej. "HomePage / Jefe Logístico" */
  @Input() breadcrumb: string | null = null;

  private auth = inject(AuthService);

  cerrarSesion(): void {
    this.auth.logout();
  }
}
