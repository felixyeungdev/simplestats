/** @type {import('next').NextConfig} */
const nextConfig = {
  output: "export",
  // async rewrites() {
  //   return [
  //     {
  //       source: "/bulk",
  //       destination: "http://192.168.0.77:25503/bulk", // Proxy to Backend
  //     },
  //   ];
  // },
  images: {
    remotePatterns: [
      {
        protocol: "https",
        hostname: "crafatar.com",
      },
    ],
  },
};

module.exports = nextConfig;
