import {ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot} from '@angular/router';
import {inject} from "@angular/core";
import {AuthenticationService} from "../services/authentication.service";
import {Roles} from "../enums/roles";

export const AuthGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const roles: Roles[] = route.data['roles'] as Roles[] || [];
  return inject(AuthenticationService).hasPermission(roles);
};
