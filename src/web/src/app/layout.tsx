import "./globals.css";
import { Open_Sans } from "next/font/google";
import Navigation from "./navigation";

const openSans = Open_Sans({ subsets: ["latin"] });

export const metadata = {
  title: "Simple Stats",
  description: "Live Server Statistics",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body className={openSans.className}>
        <Navigation />

        {children}
      </body>
    </html>
  );
}
