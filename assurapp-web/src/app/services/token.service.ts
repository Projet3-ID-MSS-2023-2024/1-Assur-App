import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  saveToken(token: string){
    localStorage.setItem('bearer', token)
  }

  getToken(): string | null{
    return localStorage.getItem('bearer')
  }

  getTokenPayload(){
    const token = this.getToken()
    if(token){
      const elements = token.split('.')
      return JSON.parse(atob(elements[1]))
    }
    return null
  }

  getUserEmail(){
    const payload = this.getTokenPayload()
    if(payload){
      return payload.sub
    }
  }

  getUserId(){
    const payload = this.getTokenPayload()
    if(payload){
      return payload.id
    }
  }

  getUserRole(){
    const payload = this.getTokenPayload()
    if(payload){
      return payload.role.label
    }
  }

  isLogged(): boolean{
    const token = this.getToken()
    return !! token
  }


}
