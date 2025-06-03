export interface SongDTO {
  id?: number;
  title: string;
  yearPublication: number;
  coverUrl: string;
  url: string;
  duration: string;
  trackNumber: number;
  albumId?: number;
}
