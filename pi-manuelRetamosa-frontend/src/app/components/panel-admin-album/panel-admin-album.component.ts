import { Component, OnInit, ViewChild } from '@angular/core';
import { AlbumService} from '../../services/albumService';
import { AlbumDTO } from '../../models/albumDTO';
import { CommonModule } from '@angular/common';
import { AddAlbumComponent } from '../add-album/add-album.component';

@Component({
  selector: 'app-panel-admin-album',
  imports: [CommonModule, AddAlbumComponent],
  templateUrl: './panel-admin-album.component.html',
  styleUrl: './panel-admin-album.component.css'
})
export class PanelAdminAlbumComponent implements OnInit {
  albums: AlbumDTO[] = [];
  selectedAlbum: AlbumDTO | null = null;
  @ViewChild(AddAlbumComponent) albumModal!: AddAlbumComponent;

  constructor(private albumService: AlbumService) {}

  ngOnInit(): void {
    this.loadAlbums();
  }

  loadAlbums(): void {
    this.albumService.getAll().subscribe({
      next: data => (this.albums = data),
      error: err => console.error('Error cargando álbumes:', err)
    });
  }

  onAddAlbum(): void {
    this.selectedAlbum     = null;
    this.albumModal.mode   = 'add';
    this.albumModal.albumToEdit = null;
    this.albumModal.resetFormFields();
    this.openModal();
  }

  onEditAlbum(album: AlbumDTO): void {
    this.selectedAlbum = { ...album };
    this.albumModal.mode = 'edit';
    this.albumModal.albumToEdit = this.selectedAlbum;
    this.openModal();
  }

  onDeleteAlbum(id: number | undefined): void {
    if (id == null) return;
    if (!confirm('¿Estás seguro de que deseas eliminar este álbum?')) return;
    this.albumService.delete(id).subscribe({
      next: () => this.loadAlbums(),
      error: err => console.error('Error al eliminar:', err)
    });
  }

  onAlbumSaved(): void {
    this.loadAlbums();
  }

  private openModal(): void {
    const modalEl = document.getElementById('album-modal');
    if (modalEl) {
      // @ts-ignore
      const modal = new bootstrap.Modal(modalEl, { keyboard: false });
      modal.show();
    }
  }
}
