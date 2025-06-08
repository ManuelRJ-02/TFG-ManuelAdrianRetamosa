import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SongDTO } from '../../models/songDTO';
import { SongService } from '../../services/songService';

declare const bootstrap: any;

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

  title!: string;
  yearPublication!: number;
  coverUrl!: string;
  url!: string;
  type!: string;
  duration!: string;
  trackNumber!: number;
  isSaving = false;
  errorMessages: string[] = [];

  constructor(private songService: SongService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['mode'] || changes['songToEdit']) {
      this.resetFormFields();
    }
  }

  private resetFormFields(): void {
    this.errorMessages = [];
    if (this.mode === 'edit' && this.songToEdit) {
      this.title           = this.songToEdit.title;
      this.yearPublication = this.songToEdit.yearPublication;
      this.coverUrl        = this.songToEdit.coverUrl    || '';
      this.url             = this.songToEdit.url         || '';
      this.type            = this.songToEdit.type        || '';
      this.duration        = this.songToEdit.duration    || '';
      this.trackNumber     = this.songToEdit.trackNumber;
    } else {
      this.title           = '';
      this.yearPublication = undefined!;
      this.coverUrl        = '';
      this.url             = '';
      this.type            = '';
      this.duration        = '';
      this.trackNumber     = undefined!;
      if (this.fileInput?.nativeElement) {
        this.fileInput.nativeElement.value = '';
      }
    }
  }

  triggerFileSelect(): void {
    this.fileInput.nativeElement.click();
  }

  onFileSelected(event: Event): void {
    const input = (event.target as HTMLInputElement);
    if (!input.files?.length) return;
    const file = input.files[0];
    this.songService.uploadCover(file).subscribe({
      next: url => {
        this.coverUrl = url;
        input.value = '';
      },
      error: err => {
        console.error('Error subiendo cover:', err);
        input.value = '';
      }
    });
  }

  onSubmit(): void {
    this.errorMessages = [];
    this.isSaving      = true;

    const payload: SongDTO = {
      title:           this.title.trim(),
      yearPublication: this.yearPublication,
      coverUrl:        this.coverUrl.trim(),
      url:             this.url.trim(),
      type:            this.type,
      duration:        this.duration,
      trackNumber:     this.trackNumber
    };

    const obs = (this.mode === 'edit' && this.songToEdit?.id != null)
      ? this.songService.update(this.songToEdit.id, payload)
      : this.songService.create(payload);

    obs.subscribe({
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
          this.errorMessages = ['Error desconocido al guardar la canción'];
        }
        this.isSaving = false;
      },
      complete: () => {
        this.isSaving = false;
      }
    });
  }

  private closeAndEmit(): void {
    const modalEl = document.getElementById('song-modal');
    if (modalEl) {
      // @ts-ignore
      const modal = bootstrap.Modal.getInstance(modalEl);
      modal.hide();
    }
    this.songSaved.emit();
  }
}
