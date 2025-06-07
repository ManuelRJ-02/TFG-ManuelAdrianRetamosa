import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild, ElementRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AlbumDTO } from '../../models/albumDTO';
import { AlbumService} from '../../services/albumService';

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

  title = '';
  yearPublication!: number;
  coverUrl = '';
  url = '';
  loadingCover = false;

  constructor(private albumService: AlbumService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['mode'] || changes['albumToEdit']) {
      this.resetFormFields();
    }
  }

  public resetFormFields(): void {
    if (this.mode === 'edit' && this.albumToEdit) {
      this.title           = this.albumToEdit.title;
      this.yearPublication = this.albumToEdit.yearPublication;
      this.coverUrl        = this.albumToEdit.coverUrl;
      this.url             = this.albumToEdit.url;
    } else {
      this.title = '';
      this.yearPublication = undefined!;
      this.coverUrl = '';
      this.url = '';
      if (this.fileInput && this.fileInput.nativeElement) {
        this.fileInput.nativeElement.value = '';
      }
    }
  }

  triggerFileSelect(): void {
    if (this.fileInput && this.fileInput.nativeElement) {
      this.fileInput.nativeElement.click();
    }
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;
    const file = input.files[0];

    this.loadingCover = true;
    this.albumService.uploadCover(file).subscribe({
      next: url => {
        this.coverUrl     = url;
        this.loadingCover = false;
        input.value = '';
      },
      error: err => {
        console.error('Error al subir cover:', err);
        this.loadingCover = false;
        input.value = '';
      }
    });
  }

  onSubmit(): void {
    const payload: AlbumDTO = {
      title: this.title,
      yearPublication: this.yearPublication,
      coverUrl: this.coverUrl,
      url: this.url,
      songs: []
    };

    if (this.mode === 'edit' && this.albumToEdit?.id != null) {
      payload.id = this.albumToEdit.id;
      this.albumService.update(this.albumToEdit.id, payload)
        .subscribe(() => this.closeAndEmit());
    } else {
      this.albumService.create(payload)
        .subscribe(() => this.closeAndEmit());
    }
  }

  private closeAndEmit(): void {
    const modalEl = document.getElementById('album-modal');
    if (modalEl) {
      // @ts-ignore
      bootstrap.Modal.getInstance(modalEl).hide();
    }
    this.albumSaved.emit();
  }
}
