import { Component, OnInit, ViewChild } from '@angular/core';
import { ConcertService} from '../../services/concertService';
import { ConcertDTO } from '../../models/concertDTO';
import {CommonModule} from '@angular/common';
import {AddConcertComponent} from '../add-concert/add-concert.component';

@Component({
  selector: 'app-panel-admin-concert',
  imports: [CommonModule, AddConcertComponent],
  templateUrl: './panel-admin-concert.component.html',
  styleUrl: './panel-admin-concert.component.css'
})
export class PanelAdminConcertComponent implements OnInit {
  concerts: ConcertDTO[] = [];
  selectedConcert: ConcertDTO | null = null;
  @ViewChild(AddConcertComponent) concertModal!: AddConcertComponent;

  constructor(private concertService: ConcertService) { }

  ngOnInit(): void {
    this.loadConcerts();
  }

  loadConcerts(): void {
    this.concertService.getAll().subscribe({
      next: data => this.concerts = data,
      error: err => console.error('Error cargando conciertos:', err)
    });
  }

  onAddConcert(): void {
    this.selectedConcert = null;
    this.concertModal.mode = 'add';
    this.concertModal.concertToEdit = null;
    this.openModal();
  }

  onEditConcert(concert: ConcertDTO): void {
    this.selectedConcert = {...concert};
    this.concertModal.mode = 'edit';
    this.concertModal.concertToEdit = this.selectedConcert;
    this.openModal();
  }

  onDeleteConcert(id: number | undefined): void {
    if (id == null) return;
    if (!confirm('¿Estás seguro de que deseas eliminar este concierto?')) return;
    this.concertService.delete(id).subscribe({
      next: () => this.loadConcerts(),
      error: err => console.error('Error al eliminar:', err)
    });
  }

  onConcertSaved(): void {
    this.loadConcerts();
  }

  private openModal(): void {
    const modalEl = document.getElementById('concert-modal');
    if (modalEl) {
      // @ts-ignore
      const modal = new bootstrap.Modal(modalEl, {keyboard: false});
      modal.show();
    }
  }
}
