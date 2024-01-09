import {Component, HostListener, OnInit} from '@angular/core';
import {NgClass, NgOptimizedImage, Location, NgIf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {InsuranceType} from "../../../enums/insurance-type";
import {Roles} from "../../../enums/roles";

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    NgOptimizedImage,
    RouterLink,
    NgClass,
    NgIf
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})

export class SidebarComponent implements OnInit{
  visible: boolean = false;
  timeBeforeLogout : number = 600;
  interval: number | undefined;
  
  constructor(private location: Location,
              private authenticationService: AuthenticationService,
              private router: Router) {}

  ngOnInit(): void {
      this.startTimer()
    }

  active(path: string): boolean {
    return this.location.path(false).includes(path);
  }

  getRole(): string {
    return this.authenticationService.getUserRole();
  }

  logout(){
    this.authenticationService.logout();
    setTimeout(() => {
      this.router.navigate(['/']);
    }, 500);
  }
  
  toggle() {
    this.visible = !this.visible;
  }

  @HostListener('window:keypress', ["$event"])
  @HostListener('window:beforeunload', ["$event"])
  @HostListener('window:mousemove', ["$event"])
  mouseMoveWatcher(event:Event){
    this.resetTimer()
  }

  startTimer(){
    this.interval = setInterval(()=>{
      if(this.timeBeforeLogout > 0){
        this.timeBeforeLogout--;
      } else if(this.timeBeforeLogout == 0 && this.authenticationService.isLogged()){
        this.authenticationService.logout()
        location.reload()
      }
    }, 1000)
  }

  resetTimer(){
    this.timeBeforeLogout = 600;
  }
 
  protected readonly Roles = Roles;
}
