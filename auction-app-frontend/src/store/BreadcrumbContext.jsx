import { createContext, useState, useContext, useEffect } from "react";
import { useLocation } from "react-router-dom";

import { ROUTES_MAP } from "src/constants";

const BreadcrumbContext = createContext();

export const useBreadcrumb = () => useContext(BreadcrumbContext);

export const BreadcrumbProvider = ({ children }) => {
  const [breadcrumbs, setBreadcrumbs] = useState({
    previous: null,
    current: null,
    title: null,
  });

  // state to keep track of the first render
  const [isFirstRender, setIsFirstRender] = useState(true);
  const location = useLocation();

  // function to set the title in the breadcrumb context
  const setTitle = (title) => {
    setBreadcrumbs((prev) => ({
      ...prev,
      title,
    }));
  };

  useEffect(() => {
    // ROUTES_MAP to get a label or "undefined" as a fallback
    const path = location.pathname;
    let label = ROUTES_MAP[path] || "Undefined";

    if (path.includes("/product-detail/")) {
      // generic label for product detail page
      label = "Product Detail";
    }

    if (path !== breadcrumbs.current?.path) {
      setBreadcrumbs((prev) => ({
        previous: !isFirstRender ? prev.current : null,
        current: { path, label }, // update the current breadcrumb
        title: label, // update the title
      }));
    }

    if (isFirstRender) {
      setIsFirstRender(false);
    }
  }, [location, isFirstRender]);

  return (
    <BreadcrumbContext.Provider value={{ ...breadcrumbs, setTitle }}>
      {children}
    </BreadcrumbContext.Provider>
  );
};
