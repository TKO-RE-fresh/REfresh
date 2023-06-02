/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      
      spacing: {
        tableL: "400px",
        tableT: "150px",
        asideL: "150px",
        tableTd: "100px"
      }
    },
  },
  plugins: [require("daisyui")],
};
