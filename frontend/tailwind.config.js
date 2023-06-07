/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      spacing: {
        tableL: "300px",
        tableT: "80px",
        asideR: "1300px",
        asideW: "200px",
        tableTd: "100px",
        
        subModalBtn: "calc(24% + 4rem)",
      }
    },
  },
  plugins: [require("daisyui")],
};
