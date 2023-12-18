import {Component, OnInit} from '@angular/core';
import {Insurance} from "../../../interfaces/insurance";
import {InsuranceService} from "../../../services/insurance.service";
import {RouterLink} from "@angular/router";
import {CurrencyPipe} from "@angular/common";

@Component({
  selector: 'app-insurances-dashboard',
  standalone: true,
  imports: [
    RouterLink,
    CurrencyPipe
  ],
  templateUrl: './insurances-dashboard.component.html',
  styleUrl: './insurances-dashboard.component.css'
})
export class InsurancesDashboardComponent implements OnInit {
  insurances: Insurance[] = [];
  data: Insurance[] = [];
  current = 1;
  max = 10;

  constructor(private insuranceService: InsuranceService) {}

  ngOnInit(): void {
    this.fetch();
  }

  fetch() {
    this.insuranceService.getAllInsurances().subscribe({
      next: data => {
        this.insurances = data;
        this.getData();
      },
      error: err => console.error(err)
    })
  }

  delete(id: number) {
    this.insuranceService.deleteInsurance(id).subscribe({
      next: data => this.fetch(),
      error: err => console.error(err)
    });
  }

  getData() {
    const start = (this.current - 1) * this.max;
    this.data = this.insurances.slice(start, start + this.max);
  }

  next() {
    if (this.current < this.getTotalPages()) {
      this.current++;
      this.getData()
    }
  }
  previous() {
    if (this.current > 1) {
      this.current--;
      this.getData()
    }
  }

  getTotalPages(): number {
    return Math.ceil(this.insurances.length / this.max)

  }

  isNext(): boolean {
    return this.current < this.getTotalPages();
  }

  isPrev(): boolean {
    return this.current > 1;
  }
}
