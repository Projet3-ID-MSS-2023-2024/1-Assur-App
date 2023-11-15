import { Component, OnInit } from '@angular/core';
import { Sinistre } from 'src/app/models/sinistre';
import { SinistreService } from 'src/app/services/sinistre.service';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-sinistre',
  templateUrl: './sinistre.component.html',
  styleUrls: ['./sinistre.component.css'],
  providers: [MessageService]
})
export class SinistreComponent implements OnInit{
sinistre!: Sinistre;
constructor(private sinistreService: SinistreService) { }

ngOnInit(): void {
  this.sinistre = new Sinistre();
}

onGetSinistrerById(){
  console.log(this.sinistre.id);

  this.sinistreService.getSinistre(this.sinistre.id).subscribe(sinistre => {
    this.sinistre = sinistre;
    this.sinistre.date = new Date(sinistre.date);
    console.log(sinistre);
  });
}

onCreateSinistre(){
  console.log(this.sinistre);

  const newSinistre = new Sinistre();
  newSinistre.id = this.sinistre.id;
  newSinistre.client = this.sinistre.client;
  newSinistre.description = this.sinistre.description;
  newSinistre.expert = this.sinistre.expert;
  newSinistre.montantEstime = this.sinistre.montantEstime;
  newSinistre.montantPaye = this.sinistre.montantPaye;
  newSinistre.status = "regler";
  newSinistre.photo = this.sinistre.photo;
  newSinistre.date = this.sinistre.date;

  this.sinistreService.createSinistre(newSinistre).subscribe(sinistre => {
    this.sinistreService.createSinistre(sinistre);
    console.log(sinistre);
  });

}

}
