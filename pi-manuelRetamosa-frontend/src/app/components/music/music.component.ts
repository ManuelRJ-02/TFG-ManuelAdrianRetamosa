import {Component, OnInit} from '@angular/core';
import { AlbumService} from '../../services/albumService';
import { SongService} from '../../services/songService';
import { AlbumDTO } from '../../models/albumDTO';
import { SongDTO } from '../../models/songDTO';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-music',
  imports: [CommonModule],
  templateUrl: './music.component.html',
  styleUrl: './music.component.css'
})
export class MusicComponent implements OnInit {
  lastLaunch: SongDTO | null = null;
  otherSingles: SongDTO[] = [];

  lastAlbum: AlbumDTO | null = null;
  otherAlbums: AlbumDTO[] = [];

  constructor(
    private songService: SongService,
    private albumService: AlbumService
  ) {}

  ngOnInit(): void {
    // ======== 1) Cargar canciones ========
    this.songService.getAll().subscribe({
      next: (allSongs: SongDTO[]) => {
        if (allSongs && allSongs.length > 0) {
          // Buscar la canción con el ID más alto:
          let maxIdSong = allSongs[0];
          for (let s of allSongs) {
            // asumo que s.id siempre viene definido; si no, usar s.id ?? 0
            if ((s.id ?? 0) > (maxIdSong.id ?? 0)) {
              maxIdSong = s;
            }
          }
          this.lastLaunch = maxIdSong;

          // El resto de songs (sin álbum y sin lastLaunch.id):
          this.otherSingles = allSongs.filter(
            s => (s.id ?? -1) !== (this.lastLaunch?.id ?? -1) && !s.albumId
          );
        }
      },
      error: err => {
        console.error('Error al cargar las canciones:', err);
      }
    });

    // ======== 2) Cargar álbumes ========
    this.albumService.getAll().subscribe({
      next: (allAlbums: AlbumDTO[]) => {
        if (allAlbums && allAlbums.length > 0) {
          // Buscar el álbum con el ID más alto:
          let maxIdAlbum = allAlbums[0];
          for (let a of allAlbums) {
            if ((a.id ?? 0) > (maxIdAlbum.id ?? 0)) {
              maxIdAlbum = a;
            }
          }
          this.lastAlbum = maxIdAlbum;

          // El resto de álbumes excluyendo el lastAlbum.id
          this.otherAlbums = allAlbums.filter(
            a => (a.id ?? -1) !== (this.lastAlbum?.id ?? -1)
          );
        }
      },
      error: err => {
        console.error('Error al cargar los álbumes:', err);
      }
    });
  }

  scrollSection(carouselClass: string, direction: number) {
    // Obtener el primer elemento con esa clase:
    const containers = document.getElementsByClassName(carouselClass);
    if (!containers || containers.length === 0) return;
    const container = containers[0] as HTMLElement;
    const scrollAmount = window.innerWidth * direction;
    container.scrollBy({ left: scrollAmount, behavior: 'smooth' });
  }
}
