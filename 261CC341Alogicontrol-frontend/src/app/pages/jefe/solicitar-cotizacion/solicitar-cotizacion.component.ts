import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NavbarComponent } from '../../../shared/navbar/navbar.component';

@Component({
  selector: 'app-solicitar-cotizacion',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NavbarComponent],
  templateUrl: './solicitar-cotizacion.component.html',
  styleUrl: './solicitar-cotizacion.component.scss'
})
export class SolicitarCotizacionComponent {
  private fb = inject(FormBuilder);
  private router = inject(Router);

  enviado = signal(false);

  form = this.fb.nonNullable.group({
    tipoProducto: ['Laptop', Validators.required],
    modelo: ['Lenovo LOQ 15', Validators.required],
    cantidad: [84, [Validators.required, Validators.min(1)]],
    fechaLimite: ['2026-05-27', Validators.required],
    direccion: ['Habich 723', Validators.required],
    empresa: ['', [Validators.required, Validators.email]],
    mensaje: [''],
    politicas: [false, Validators.requiredTrue]
  });

  volver(): void {
    this.router.navigate(['/home']);
  }

  enviar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    // TODO: cuando exista el endpoint /api/cotizaciones se hará el POST real.
    console.log('Cotización enviada:', this.form.getRawValue());
    this.enviado.set(true);
    setTimeout(() => this.router.navigate(['/home']), 1800);
  }
}
