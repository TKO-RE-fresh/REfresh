/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      height: {
        modalH: "calc(h-36 + 24px)"
      },
    },
  },
  plugins: [require("daisyui")],
};
