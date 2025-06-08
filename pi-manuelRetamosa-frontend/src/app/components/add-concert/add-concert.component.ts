import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { ConcertService } from '../../services/concertService';
import { ConcertDTO } from '../../models/concertDTO';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

declare const bootstrap: any;

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
    if (changes['concertToEdit'] || changes['mode']) {
      this.resetFormFields();
    }
  }

  private resetFormFields(): void {
    this.errorMessages = [];
    if (this.mode === 'edit' && this.concertToEdit) {
      const parts = this.concertToEdit.date?.split(' de ');
      if (parts?.length === 2) {
        const parsed = parseInt(parts[0], 10);
        this.day = isNaN(parsed) ? null : parsed;
        this.month = parts[1];
      } else {
        this.day = null;
        this.month = null;
      }
      this.place = this.concertToEdit.place;
      this.urlTicketSale = this.concertToEdit.urlTicketSale ?? '';
    } else {
      this.day = null;
      this.month = null;
      this.place = '';
      this.urlTicketSale = '';
    }
  }

  onSubmit(): void {
    this.errorMessages = [];
    const dateString = this.day != null && this.month ? `${this.day} de ${this.month}` : '';
    const payload: ConcertDTO = {
      date: dateString,
      place: this.place.trim(),
      urlTicketSale: this.urlTicketSale.trim() || undefined,
      ...(this.mode === 'edit' && this.concertToEdit?.id != null ? { id: this.concertToEdit.id } : {})
    };

    if (this.mode === 'add') {
      this.concertService.create(payload).subscribe({
        next: () => this.emitAndClose(),
        error: err => {
          const body = err.error;
          this.errorMessages = Array.isArray(body.errors) ? body.errors : [ 'Error al crear concierto, inténtalo otra vez.' ];
        }
      });
    } else {
      if (!this.concertToEdit?.id) return;
      this.concertService.update(this.concertToEdit.id, payload).subscribe({
        next: () => this.emitAndClose(),
        error: err => {
          const body = err.error;
          this.errorMessages = Array.isArray(body.errors) ? body.errors : [ 'Error al actualizar concierto, inténtalo otra vez.' ];}
      });
    }
  }

  private emitAndClose(): void {
    const modalEl = document.getElementById('concert-modal');
    if (modalEl) {
      const modal = bootstrap.Modal.getInstance(modalEl);
      modal.hide();
    }
    this.concertSaved.emit();
  }
}
