import { Component, OnInit } from '@angular/core';
import { ConcertService} from '../../services/concertService';
import { ConcertDTO} from '../../models/concertDTO';
import {CommonModule} from '@angular/common';
import {TranslatePipe} from '@ngx-translate/core';

function parseSpanishDate(spanishDate: string, defaultYear = 2025): Date {
  const lower = spanishDate.trim().toLowerCase();

  const mesesMap: { [key: string]: number } = {
    enero: 0, febrero: 1, marzo: 2, abril: 3, mayo: 4,
    junio: 5, julio: 6, agosto: 7, septiembre: 8, octubre: 9,
    noviembre: 10, diciembre: 11
  };

  const partes = lower.split(' de ');
  if (partes.length < 2) {
    return new Date(NaN);
  }

  const diaOpcion = partes[0].trim();
  const mesTexto = partes[1].trim();

  const mesNumber = mesesMap[mesTexto];
  if (mesNumber === undefined) {
    return new Date(NaN);
  }

  const primerPorcion = diaOpcion.split('-')[0];
  const diaNumber = parseInt(primerPorcion, 10);
  if (isNaN(diaNumber)) {
    return new Date(NaN);
  }

  return new Date(defaultYear, mesNumber, diaNumber);
}

@Component({
  selector: 'app-tour',
  imports: [CommonModule, TranslatePipe],
  templateUrl: './tour.component.html',
  styleUrl: './tour.component.css'
})
export class TourComponent implements OnInit {
  concerts: ConcertDTO[] = [];

  constructor(private concertService: ConcertService) {}

  ngOnInit(): void {
    this.concertService.getAll().subscribe({
      next: (data: ConcertDTO[]) => {
        this.concerts = data
          .map(c => ({ ...c, _parsedDate: parseSpanishDate(c.date) }))
          .filter(c => !isNaN(c._parsedDate.getTime()))
          .sort((a, b) => a._parsedDate.getTime() - b._parsedDate.getTime())
          .map(({ _parsedDate, ...c }) => c);
      },
      error: (err) => {
        console.error('Error al cargar los conciertos:', err);
      }
    });
  }

  isPast(concertDate: string): boolean {
    const fechaConcierto = parseSpanishDate(concertDate);
    if (isNaN(fechaConcierto.getTime())) {
      return false;
    }
    const hoy = new Date();
    hoy.setHours(0, 0, 0, 0);

    return fechaConcierto < hoy;
  }
}
