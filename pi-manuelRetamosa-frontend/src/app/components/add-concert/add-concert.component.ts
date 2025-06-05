import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { ConcertService } from '../../services/concertService';
import { ConcertDTO } from '../../models/concertDTO';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';



@Component({
  selector: 'app-add-concert',
  imports: [CommonModule, FormsModule],
  templateUrl: './add-concert.component.html',
  styleUrl: './add-concert.component.css'
})
export class AddConcertComponent implements OnChanges {
  @Input() mode: 'add' | 'edit' = 'add';
  @Input() concertToEdit: ConcertDTO | null = null;
  @Output() concertSaved = new EventEmitter<void>();
  day: number | null = null;
  month: string | null = null;
  days: number[] = Array.from({ length: 31 }, (_, i) => i + 1);
  months: string[] = ['enero','febrero','marzo','abril','mayo','junio','julio','agosto','septiembre','octubre','noviembre','diciembre'];
  place: string = '';
  urlTicketSale: string = '';
  errorMessages: string[] = [];

  constructor(private concertService: ConcertService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['concertToEdit']) {
      this.resetFormFields();
    }
  }

  private resetFormFields(): void {
    this.errorMessages = [];
    if (this.mode === 'edit' && this.concertToEdit) {
      const fullDate = this.concertToEdit.date;
      const parts = fullDate.split(' de ');
      if (parts.length === 2) {
        const d = parseInt(parts[0], 10);
        if (!isNaN(d)) {
          this.day = d;
        } else {
          this.day = null;
        }
        this.month = parts[1];
      } else {
        this.day = null;
        this.month = null;
      }
      this.place = this.concertToEdit.place;
      this.urlTicketSale = this.concertToEdit.urlTicketSale || '';
    } else {
      this.day = null;
      this.month = null;
      this.place = '';
      this.urlTicketSale = '';
    }
  }

  onSubmit(): void {
    this.errorMessages = [];
    if (this.errorMessages.length > 0) {
      return;
    }
    const dateString = `${this.day} de ${this.month}`;
    const concertPayload: ConcertDTO = {
      date: dateString,
      place: this.place.trim(),
      urlTicketSale: this.urlTicketSale.trim() || undefined
    };

    if (this.mode === 'add') {
      this.concertService.create(concertPayload).subscribe({
        next: _ => {
          this.emitAndClose();
        },
        error: err => {
          console.error('Error al crear concierto:', err);
          this.errorMessages.push('Error al crear concierto, inténtalo otra vez.');
        }
      });
    } else if (this.mode === 'edit' && this.concertToEdit && this.concertToEdit.id != null) {
      concertPayload.id = this.concertToEdit.id;
      this.concertService.update(this.concertToEdit.id, concertPayload).subscribe({
        next: _ => {
          this.emitAndClose();
        },
        error: err => {
          console.error('Error al actualizar concierto:', err);
          this.errorMessages.push('Error al actualizar concierto, inténtalo otra vez.');
        }
      });
    }
  }

  private emitAndClose(): void {
    const modalEl = document.getElementById('concert-modal');
    if (modalEl) {
      // @ts-ignore
      const modal = bootstrap.Modal.getInstance(modalEl);
      modal.hide();
    }
    this.concertSaved.emit();
  }
}
