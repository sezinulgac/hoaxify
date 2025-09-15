import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
//import App from './App.jsx'
import { RouterProvider } from "react-router-dom";
import "./locales";
import "./styles.scss";
import router from "./router/index.js";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
