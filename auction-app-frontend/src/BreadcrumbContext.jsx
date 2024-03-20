import { createContext, useState, useContext, useEffect } from "react";
import { useLocation } from "react-router-dom";

import { ROUTES_MAP } from "src/constants";

const BreadcrumbContext = createContext();

export const useBreadcrumb = () => useContext(BreadcrumbContext);

export const BreadcrumbProvider = ({ children }) => {
  const [breadcrumbs, setBreadcrumbs] = useState({
    previous: null,
    current: null,
  });
  const location = useLocation();

  useEffect(() => {
    // ROUTES_MAP to get a label or "undefined" as a fallback
    const path = location.pathname;
    const label = ROUTES_MAP[path] || "Undefined";

    // update breadcrumbs if the path has changed
    if (path !== breadcrumbs.current?.path) {
      setBreadcrumbs({
        previous: breadcrumbs.current,
        current: { path, label },
      });
    }
  }, [location, breadcrumbs.current]);

  return (
    <BreadcrumbContext.Provider value={breadcrumbs}>
      {children}
    </BreadcrumbContext.Provider>
  );
};
