import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { AuthService } from '../../core/services/auth.service';

interface OpcionRol {
  label: string;
  ruta: string;
  rolBackend?: string;
}

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  private router = inject(Router);
  readonly auth = inject(AuthService);

  desplegado = false;

  opciones: OpcionRol[] = [
    { label: 'Jefe Logísitico', ruta: '/jefe/solicitar-cotizacion', rolBackend: 'JEFE_LOGISTICA' },
    { label: 'Asistente Logísitico', ruta: '/asistente/recepcionar-mercaderia', rolBackend: 'ASISTENTE_LOGISTICA' },
    { label: 'Tesorero', ruta: '/home', rolBackend: 'TESORERO' },
    { label: 'Gerente Financiero', ruta: '/home', rolBackend: 'GERENTE_FINANCIERO' },
    { label: 'Generente General', ruta: '/home', rolBackend: 'GERENTE_GENERAL' }
  ];

  toggleDropdown(): void {
    this.desplegado = !this.desplegado;
  }

  seleccionarRol(op: OpcionRol): void {
    this.desplegado = false;
    this.router.navigate([op.ruta]);
  }
}
