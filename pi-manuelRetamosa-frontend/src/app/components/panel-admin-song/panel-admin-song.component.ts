import { Component, OnInit, ViewChild } from '@angular/core';
import { SongService} from '../../services/songService';
import { SongDTO } from '../../models/songDTO';
import { CommonModule } from '@angular/common';
import { AddSongComponent } from '../add-song/add-song.component';

@Component({
  selector: 'app-panel-admin-song',
  standalone: true,
  imports: [CommonModule, AddSongComponent],
  templateUrl: './panel-admin-song.component.html',
  styleUrls: ['./panel-admin-song.component.css']
})
export class PanelAdminSongComponent implements OnInit {
  songs: SongDTO[] = [];
  selectedSong: SongDTO | null = null;
  @ViewChild(AddSongComponent) songModal!: AddSongComponent;

  constructor(private songService: SongService) { }

  ngOnInit(): void {
    this.loadSongs();
  }

  loadSongs(): void {
    this.songService.getAll().subscribe({
      next: data => this.songs = data,
      error: err => console.error('Error cargando canciones:', err)
    });
  }

  onAddSong(): void {
    this.selectedSong = null;
    this.songModal.mode = 'add';
    this.songModal.songToEdit = null;
    this.openModal();
  }

  onEditSong(song: SongDTO): void {
    this.selectedSong = { ...song };
    this.songModal.mode = 'edit';
    this.songModal.songToEdit = this.selectedSong;
    this.openModal();
  }

  onDeleteSong(id: number | undefined): void {
    if (id == null) return;
    if (!confirm('¿Estás seguro de que deseas eliminar esta canción?')) return;
    this.songService.delete(id).subscribe({
      next: () => this.loadSongs(),
      error: err => console.error('Error al eliminar:', err)
    });
  }

  onSongSaved(): void {
    this.loadSongs();
  }

  private openModal(): void {
    const modalEl = document.getElementById('song-modal');
    if (modalEl) {
      // @ts-ignore
      const modal = new bootstrap.Modal(modalEl, { keyboard: false });
      modal.show();
    }
  }
}
