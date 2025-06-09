import {Component, OnInit} from '@angular/core';
import { AlbumService} from '../../services/albumService';
import { SongService} from '../../services/songService';
import { AlbumDTO } from '../../models/albumDTO';
import { SongDTO } from '../../models/songDTO';
import {CommonModule} from '@angular/common';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-music',
  imports: [CommonModule, TranslatePipe],
  templateUrl: './music.component.html',
  styleUrl: './music.component.css'
})
export class MusicComponent implements OnInit {
  lastLaunch: SongDTO | null = null;
  otherSingles: SongDTO[] = [];

  lastAlbum: AlbumDTO | null = null;
  otherAlbums: AlbumDTO[] = [];

  constructor(private songService: SongService, private albumService: AlbumService) {}

  ngOnInit(): void {
    this.songService.getAll().subscribe({
      next: (allSongs: SongDTO[]) => {
        allSongs.sort((a, b) =>
          (b.yearPublication! - a.yearPublication!) ||
          ((b.id!) - (a.id!))
        );
        this.lastLaunch = allSongs[0];
        this.otherSingles = allSongs
          .slice(1)
          .filter(s => !s.albumId);
      },
      error: err => console.error('Error al cargar las canciones:', err)
    });

    this.albumService.getAll().subscribe({
      next: (allAlbums: AlbumDTO[]) => {
        allAlbums.sort((a, b) =>
          (b.yearPublication! - a.yearPublication!) ||
          ((b.id!) - (a.id!))
        );
        this.lastAlbum = allAlbums[0];
        this.otherAlbums = allAlbums.slice(1);
      },
      error: err => console.error('Error al cargar los álbumes:', err)
    });
  }

  scrollSection(carouselClass: string, direction: number) {
    const containers = document.getElementsByClassName(carouselClass);
    if (!containers || containers.length === 0) return;
    const container = containers[0] as HTMLElement;
    const scrollAmount = window.innerWidth * direction;
    container.scrollBy({ left: scrollAmount, behavior: 'smooth' });
  }
}
