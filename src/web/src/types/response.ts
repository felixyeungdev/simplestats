export interface BulkResponse {
  players: Player[];
  count: number;
}

export interface Player {
  uuid: string;
  name: string;
  general: GeneralStatistics;
}

export interface GeneralStatistics {
  [key: string]: number;
}
