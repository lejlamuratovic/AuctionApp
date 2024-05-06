import { Navigate, useLocation } from "react-router-dom";

import { ROUTE_PATHS } from "src/constants";

const ProtectedRoutes = ({ children }) => {
    const location = useLocation();

    const accessToken = localStorage.getItem("accessToken");

    if (accessToken === null) {
        return <Navigate to={ROUTE_PATHS.LOGIN} state={{ from: location }} />;
    }

  return children; // return children if user is authenticated
};

export default ProtectedRoutes;
