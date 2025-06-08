import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild, ElementRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AlbumDTO } from '../../models/albumDTO';
import { AlbumService} from '../../services/albumService';

declare const bootstrap: any;

@Component({
  selector: 'app-add-album',
  imports: [CommonModule, FormsModule],
  templateUrl: './add-album.component.html',
  styleUrl: './add-album.component.css'
})

export class AddAlbumComponent implements OnChanges {
  @Input() mode: 'add' | 'edit' = 'add';
  @Input() albumToEdit: AlbumDTO | null = null;
  @Output() albumSaved = new EventEmitter<void>();

  @ViewChild('fileInput', { static: true })
  fileInput!: ElementRef<HTMLInputElement>;

  // campos del formulario
  title = '';
  yearPublication!: number;
  coverUrl = '';
  url = '';
  loadingCover = false;

  // control de estado / errores
  isSaving = false;
  errorMessages: string[] = [];

  constructor(private albumService: AlbumService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['mode'] || changes['albumToEdit']) {
      this.resetFormFields();
    }
  }

  public resetFormFields(): void {
    this.errorMessages = [];
    this.isSaving = false;

    if (this.mode === 'edit' && this.albumToEdit) {
      this.title = this.albumToEdit.title;
      this.yearPublication = this.albumToEdit.yearPublication;
      this.coverUrl = this.albumToEdit.coverUrl;
      this.url = this.albumToEdit.url;
    } else {
      this.title = '';
      this.yearPublication = undefined!;
      this.coverUrl = '';
      this.url = '';
      this.loadingCover = false;
      if (this.fileInput && this.fileInput.nativeElement) {
        this.fileInput.nativeElement.value = '';
      }
    }
  }

  triggerFileSelect(): void {
    this.fileInput.nativeElement.click();
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) {
      return;
    }
    const file = input.files[0];
    this.loadingCover = true;

    this.albumService.uploadCover(file).subscribe({
      next: url => {
        this.coverUrl = url;
        this.loadingCover = false;
        input.value = '';
      },
      error: err => {
        console.error('Error al subir cover:', err);
        this.errorMessages = [ err.error?.message || 'Error subiendo la portada' ];
        this.loadingCover = false;
        input.value = '';
      }
    });
  }

  onSubmit(): void {
    this.errorMessages = [];
    this.isSaving = true;

    const payload: AlbumDTO = {
      title: this.title.trim(),
      yearPublication: this.yearPublication,
      coverUrl: this.coverUrl,
      url: this.url.trim(),
      songs: []
    };

    let request$;
    if (this.mode === 'edit' && this.albumToEdit?.id != null) {
      payload.id = this.albumToEdit.id;
      request$ = this.albumService.update(this.albumToEdit.id, payload);
    } else {
      request$ = this.albumService.create(payload);
    }

    request$.subscribe({
      next: () => {
        this.closeAndEmit();
      },
      error: err => {
        const body = err.error;
        if (Array.isArray(body?.errors)) {
          this.errorMessages = body.errors;
        } else if (body?.message) {
          this.errorMessages = [body.message];
        } else {
          this.errorMessages = ['Error desconocido al guardar el álbum'];
        }
        this.isSaving = false;
      }
    });
  }

  private closeAndEmit(): void {
    const modalEl = document.getElementById('album-modal');
    if (modalEl) {
      const modal = bootstrap.Modal.getInstance(modalEl) || new bootstrap.Modal(modalEl);
      modal.hide();
    }
    this.albumSaved.emit();
  }
}
