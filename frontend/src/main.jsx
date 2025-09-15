import { createRoot } from "react-dom/client";
//import App from './App.jsx'
import { RouterProvider } from "react-router-dom";
import "./locales";
import "./styles.scss";
import router from "./router/index.js";

createRoot(document.getElementById("root")).render(
    <RouterProvider router={router} />
);
