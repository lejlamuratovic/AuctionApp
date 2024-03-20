import React, { useEffect } from "react";
import { useLocation, Link, useNavigate } from "react-router-dom";
import { next } from "assets";
import { ROUTES_MAP } from "src/constants";
import "./style.scss";

const Breadcrumbs = () => {
  const location = useLocation();
  const navigate = useNavigate();

  // save the current path as previous for the next render
  const setPreviousPath = (currentPath) => {
    sessionStorage.setItem("previousPath", currentPath);
  };

  const previousPath = sessionStorage.getItem("previousPath") || "/";

  // update the previous path in whenever the path changes
  useEffect(() => {
    setPreviousPath(location.pathname);
  }, [location.pathname]);

  // get the page name from the path
  const getPageName = (path) => ROUTES_MAP[path] || "Unknown";

  // get the current page name
  const currentPage = getPageName(location.pathname);

  // get the previous page name, only if it's not the current page
  const prevPage =
    previousPath !== location.pathname ? getPageName(previousPath) : null;

  return (
    <div className="breadcrumbs body-regular">
      {/* if there is no previous page, don't show the name of current, else show */}
      <div className="breadcrumbs-left">
        {prevPage && <div> {currentPage} </div>}
      </div>
      <div className="breadcrumbs-right">
        {prevPage && (
          <>
            <Link to={previousPath} onClick={() => navigate(-1)}>
              {prevPage}
            </Link>
            <img src={next} alt="Next Icon" />
            <span className="body-bold"> {currentPage} </span>
          </>
        )}
      </div>
    </div>
  );
};

export default Breadcrumbs;
