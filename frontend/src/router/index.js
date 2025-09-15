import { createBrowserRouter } from "react-router-dom";
import { SignUp } from "../pages/SignUp/index.jsx";
import { Home } from "../pages/Home/index.jsx";
import App from "../App.jsx";

export default createBrowserRouter([
  {
    path: "/",
    Component: App,
    children: [
      {
        path: "/",
        index: true,
        Component: Home,
      },
      {
        path: "/SignUp",
        Component: SignUp,
      },
    ],
  },
]);
