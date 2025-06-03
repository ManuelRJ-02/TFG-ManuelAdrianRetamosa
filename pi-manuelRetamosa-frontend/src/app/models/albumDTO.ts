import { SongDTO } from './songDTO';

export interface AlbumDTO {
  id?: number;
  title: string;
  yearPublication: number;
  coverUrl: string;
  url: string;
  songs: SongDTO[];
}
