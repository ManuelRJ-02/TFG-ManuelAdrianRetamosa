export interface SongDTO {
  id?: number;
  title: string;
  yearPublication: number;
  coverUrl: string;
  url: string;
  type: string;
  duration: string;
  trackNumber: number;
  albumId?: number;
}
