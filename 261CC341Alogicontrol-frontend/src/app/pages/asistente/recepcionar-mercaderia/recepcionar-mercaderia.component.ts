import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NavbarComponent } from '../../../shared/navbar/navbar.component';

interface Mercaderia {
  codigo: string;
  empresaOrigen: string;
}

@Component({
  selector: 'app-recepcionar-mercaderia',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NavbarComponent],
  templateUrl: './recepcionar-mercaderia.component.html',
  styleUrl: './recepcionar-mercaderia.component.scss'
})
export class RecepcionarMercaderiaComponent {
  private fb = inject(FormBuilder);
  private router = inject(Router);

  // Lista simulada (cuando exista el endpoint /api/recepciones se cargará del backend)
  mercaderias: Mercaderia[] = [
    { codigo: 'LaptopLOQ84', empresaOrigen: 'ABC' },
    { codigo: 'PantallaOLED103', empresaOrigen: 'XYZ' },
    { codigo: 'MouseLogitech34', empresaOrigen: 'Logitech Corp' }
  ];

  dropdownAbierto = signal(false);
  seleccionada = signal<Mercaderia | null>(null);
  resultadoMsg = signal<string | null>(null);

  form = this.fb.nonNullable.group({
    conforme: [true, Validators.required],
    comentario: ['']
  });

  toggleDropdown(): void {
    this.dropdownAbierto.update((v) => !v);
  }

  seleccionar(m: Mercaderia): void {
    this.seleccionada.set(m);
    this.dropdownAbierto.set(false);
    this.resultadoMsg.set(null);
  }

  validarCompra(): void {
    if (!this.seleccionada()) return;
    // TODO: POST /api/internamiento
    this.resultadoMsg.set('✓ Orden de internamiento emitida correctamente.');
    setTimeout(() => this.router.navigate(['/home']), 1800);
  }

  devolucion(): void {
    if (!this.seleccionada()) return;
    // TODO: POST /api/devoluciones
    this.resultadoMsg.set('↩ Devolución registrada. Se notificó al proveedor.');
    setTimeout(() => this.router.navigate(['/home']), 1800);
  }
}
