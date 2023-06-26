"use client";

import { BulkResponse } from "@/types/response";
import { fetcher } from "@/utils";
import useSWR from "swr";
import { PlayerCard } from "./player-card";

export default function Home() {
  const { data, error, isLoading } = useSWR<BulkResponse>("/bulk", fetcher);

  return (
    <main className="max-w-7xl mx-auto py-8 px-4">
      <h1 className="text-4xl font-bold my-12">Online Players</h1>
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-4">
        {isLoading || !data
          ? [...Array(2)].map((_, i) => <PlayerCard key={i} loading />)
          : data?.players.map((player) => (
              <PlayerCard key={player.uuid} player={player} />
            ))}
      </div>
    </main>
  );
}
