import * as React from "react";

import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Player } from "@/types/response";
import Image from "next/image";
import {
  ArrowBigDownDashIcon,
  ArrowUpIcon,
  BedDoubleIcon,
  BellIcon,
  BusIcon,
  CakeIcon,
  CircleSlashIcon,
  Clock,
  ClockIcon,
  Disc3Icon,
  EraserIcon,
  FastForwardIcon,
  FilterIcon,
  FishIcon,
  FlameIcon,
  Flower2Icon,
  FootprintsIcon,
  GridIcon,
  HammerIcon,
  HeartIcon,
  LibraryIcon,
  LogOutIcon,
  LucideIcon,
  MagnetIcon,
  MountainSnowIcon,
  MusicIcon,
  PackageIcon,
  PaintBucketIcon,
  PersonStandingIcon,
  PiggyBankIcon,
  PlaneIcon,
  SailboatIcon,
  ShieldAlertIcon,
  SkullIcon,
  SoupIcon,
  SparkleIcon,
  SwordIcon,
  SwordsIcon,
  TrashIcon,
  TrendingDownIcon,
  WavesIcon,
} from "lucide-react";
import { cn } from "@/utils";

export function PlayerCard({
  player,
  loading = false,
}: {
  player?: Player;
  loading?: boolean;
}) {
  const loadingStyle = loading
    ? "animate-pulse text-transparent bg-slate-200 rounded-sm"
    : "";

  return (
    <Card className="hover:shadow-md transition-all select-none">
      <CardHeader className="flex flex-row gap-4 items-center">
        {loading || !player ? (
          <div className={cn("w-16 h-16", loadingStyle)}></div>
        ) : (
          <Image
            className="w-16 h-16 rounded-sm"
            src={`https://crafatar.com/avatars/${player.uuid}`}
            alt={`User avatar of ${player.name}`}
            width={64}
            height={64}
          />
        )}
        <div>
          <CardTitle className={loadingStyle}>
            {player?.name || "eeeeeeeeeeeeeeeeeee"}
          </CardTitle>
          <CardDescription
            className={cn("text-xs whitespace-nowrap mt-1", loadingStyle)}
          >
            {player?.uuid || "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"}
          </CardDescription>
          <div className="flex items-center gap-2 mt-1">
            <span className="relative flex h-3 w-3">
              <span
                className={cn(
                  "animate-ping absolute inline-flex h-full w-full rounded-full opacity-75",
                  loading ? "bg-gray-200" : "bg-green-400"
                )}
              ></span>
              <span
                className={cn(
                  "relative inline-flex rounded-full h-3 w-3",
                  loading ? "bg-gray-300" : "bg-green-500"
                )}
              ></span>
            </span>
            <span className={cn("font-sm text-gray-700", loadingStyle)}>
              Online
            </span>
          </div>
        </div>
      </CardHeader>
      <CardContent>
        <div className="grid grid-cols-5 gap-2">
          {Object.entries(player?.general || { a: 1, b: 2, c: 3, d: 4, e: 5 })
            .filter(([key]) => !["minecraft:total_world_time"].includes(key))
            .sort((a, b) => b[1] - a[1])
            .slice(0, 5)
            .map(([key, value]) => {
              let title = key.split(":")[1] || key;
              let units = "";
              let divisor = 1;
              let Icon: LucideIcon | undefined = undefined;

              if (key.includes("one_cm")) {
                divisor = 100;
                units = "m";
                title = title.replace("one_cm", "");
              }

              if (key.includes("time")) {
                if (value > 60 * 60 * 24 * 20) {
                  divisor = 60 * 60 * 24 * 20;
                  units = "d";
                } else if (value > 60 * 60 * 20) {
                  divisor = 60 * 60 * 20;
                  units = "h";
                } else {
                  divisor = 60 * 20;
                  units = "m";
                }
              }

              if (key.includes("time")) Icon = ClockIcon;
              else if (key.includes("fly") || key.includes("aviate"))
                Icon = PlaneIcon;
              else if (key.includes("walk")) Icon = FootprintsIcon;
              else if (key.includes("sprint")) Icon = FastForwardIcon;
              else if (key.includes("damage") || key.includes("hit"))
                Icon = SwordsIcon;
              else if (key.includes("climb")) Icon = MountainSnowIcon;
              else if (key.includes("fall") || key.includes("drop"))
                Icon = TrendingDownIcon;
              else if (key.includes("sleep")) Icon = BedDoubleIcon;
              else if (key.includes("kill") || key.includes("death"))
                Icon = SkullIcon;
              else if (key.includes("boat")) Icon = SailboatIcon;
              else if (key.includes("swim")) Icon = WavesIcon;
              else if (key.includes("minecart")) Icon = BusIcon;
              else if (key.includes("jump")) Icon = ArrowUpIcon;
              else if (
                key.includes("chest") ||
                key.includes("barrel") ||
                key.includes("box")
              )
                Icon = PackageIcon;
              else if (key.includes("leave")) Icon = LogOutIcon;
              else if (key.includes("crafting")) Icon = GridIcon;
              else if (key.includes("crouch")) Icon = ArrowBigDownDashIcon;
              else if (key.includes("horse")) Icon = MagnetIcon;
              else if (key.includes("villager")) Icon = PersonStandingIcon;
              else if (key.includes("noteblock")) Icon = MusicIcon;
              else if (key.includes("fish")) Icon = FishIcon;
              else if (key.includes("raid")) Icon = ShieldAlertIcon;
              else if (key.includes("bell")) Icon = BellIcon;
              else if (key.includes("hopper")) Icon = FilterIcon;
              else if (key.includes("bred")) Icon = HeartIcon;
              else if (key.includes("anvil")) Icon = HammerIcon;
              else if (key.includes("drop") || key.includes("dispense"))
                Icon = TrashIcon;
              else if (key.includes("furnace") || key.includes("fire"))
                Icon = FlameIcon;
              else if (key.includes("pig")) Icon = PiggyBankIcon;
              else if (key.includes("lectern")) Icon = LibraryIcon;
              else if (key.includes("clean")) Icon = EraserIcon;
              else if (key.includes("enchant")) Icon = SparkleIcon;
              else if (key.includes("cake")) Icon = CakeIcon;
              else if (key.includes("fill")) Icon = PaintBucketIcon;
              else if (key.includes("cauldron") || key.includes("brew"))
                Icon = SoupIcon;
              else if (key.includes("record")) Icon = Disc3Icon;
              else if (key.includes("flower")) Icon = Flower2Icon;
              else Icon = CircleSlashIcon;

              // Replace underscores with spaces, capitalize first letter and trim whitespace
              title = title
                .replace(/_/g, " ")
                .replace(/^\w/, (c) => c.toUpperCase())
                .trim();

              // Format the value to 2 decimal places and commas where needed
              const formattedValue = (value / divisor).toLocaleString(
                undefined,
                {
                  minimumFractionDigits: 0,
                  maximumFractionDigits: 2,
                }
              );

              return (
                <div key={`${player?.uuid}-${key}`}>
                  <div
                    className={cn(
                      "flex items-center justify-center bg-slate-100 aspect-square border rounded-sm",
                      loadingStyle
                    )}
                  >
                    {Icon && (
                      <Icon
                        width={48}
                        height={48}
                        className={cn("text-slate-600", loadingStyle)}
                      />
                    )}
                  </div>
                  <div className="space-y-1 mt-1">
                    <span
                      className={cn("block text-sm font-bold", loadingStyle)}
                    >
                      {title}
                    </span>
                    <span className={cn("block text-xs", loadingStyle)}>
                      {formattedValue} {units}
                    </span>
                  </div>
                </div>
              );
            })}
        </div>
      </CardContent>
    </Card>
  );
}
