import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  // Página pública: landing
  {
    path: '',
    loadComponent: () =>
      import('./pages/landing/landing.component').then((m) => m.LandingComponent)
  },
  // Login
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login/login.component').then((m) => m.LoginComponent)
  },

  // Rutas protegidas (requieren sesión)
  {
    path: 'home',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/home/home.component').then((m) => m.HomeComponent)
  },
  {
    path: 'jefe/solicitar-cotizacion',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/jefe/solicitar-cotizacion/solicitar-cotizacion.component').then(
        (m) => m.SolicitarCotizacionComponent
      )
  },
  {
    path: 'asistente/recepcionar-mercaderia',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './pages/asistente/recepcionar-mercaderia/recepcionar-mercaderia.component'
      ).then((m) => m.RecepcionarMercaderiaComponent)
  },

  // Fallback
  { path: '**', redirectTo: '' }
];
