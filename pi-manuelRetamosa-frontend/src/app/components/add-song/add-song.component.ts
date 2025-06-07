import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SongDTO } from '../../models/songDTO';
import { SongService } from '../../services/songService';

@Component({
  selector: 'app-add-song',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-song.component.html',
  styleUrls: ['./add-song.component.css']
})
export class AddSongComponent implements OnChanges {
  @Input() mode: 'add' | 'edit' = 'add';
  @Input() songToEdit: SongDTO | null = null;
  @Output() songSaved = new EventEmitter<void>();

  @ViewChild('fileInput', { static: true })
  fileInput!: ElementRef<HTMLInputElement>;

  title = '';
  yearPublication!: number;
  coverUrl = '';
  url = '';
  type = '';
  duration = '';
  trackNumber!: number;

  constructor(private songService: SongService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['mode'] || changes['songToEdit']) {
      this.resetFormFields();
    }
  }

  private resetFormFields(): void {
    if (this.mode === 'edit' && this.songToEdit) {
      // Carga todos los campos desde el DTO
      this.title = this.songToEdit.title;
      this.yearPublication = this.songToEdit.yearPublication;
      this.coverUrl = this.songToEdit.coverUrl || '';
      this.url = this.songToEdit.url;
      this.type = this.songToEdit.type;
      this.duration = this.songToEdit.duration;
      this.trackNumber = this.songToEdit.trackNumber;
    } else {
      // Vacía todos los campos
      this.title = '';
      this.yearPublication = undefined!;
      this.coverUrl = '';
      this.url = '';
      this.type = '';
      this.duration = '';
      this.trackNumber = undefined!;
      // Limpia también el valor del input file
      if (this.fileInput && this.fileInput.nativeElement) {
        this.fileInput.nativeElement.value = '';
      }
    }
  }

  /** Lanza el diálogo de selección de fichero */
  triggerFileSelect(): void {
    this.fileInput.nativeElement.click();
  }

  /** Se dispara al elegir fichero */
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) {
      return;
    }
    const file = input.files[0];
    // Sube a Cloudinary
    this.songService.uploadCover(file).subscribe({
      next: (url: string) => {
        this.coverUrl = url;
        // Una vez subido, limpia el input para futuras selecciones
        input.value = '';
      },
      error: err => {
        console.error('Error al subir cover:', err);
        // También limpiar input en caso de error
        input.value = '';
      }
    });
  }

  onSubmit(): void {
    const payload: SongDTO = {
      title: this.title,
      yearPublication: this.yearPublication,
      coverUrl: this.coverUrl,
      url: this.url,
      type: this.type,
      duration: this.duration,
      trackNumber: this.trackNumber
    };

    if (this.mode === 'edit' && this.songToEdit?.id != null) {
      payload.id = this.songToEdit.id;
      this.songService.update(this.songToEdit.id, payload)
        .subscribe(() => this.closeAndEmit());
    } else {
      this.songService.create(payload)
        .subscribe(() => this.closeAndEmit());
    }
  }

  private closeAndEmit(): void {
    const modalEl = document.getElementById('song-modal');
    if (modalEl) {
      // @ts-ignore
      bootstrap.Modal.getInstance(modalEl).hide();
    }
    this.songSaved.emit();
  }
}
