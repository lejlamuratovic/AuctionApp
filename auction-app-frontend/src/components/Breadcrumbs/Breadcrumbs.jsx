import { Link } from "react-router-dom";

import { useBreadcrumb } from "src/BreadcrumbContext";

import { next } from "src/assets/icons";

import "./style.scss";

const Breadcrumbs = () => {
  const { previous, current } = useBreadcrumb();

  // if there is no previous breadcrumb, don't render anything
  if (!previous) {
    return null;
  }

  return (
    <div className="breadcrumbs body-regular">
      <div className="breadcrumbs-left">{current?.label}</div>
      {previous && (
        <div className="breadcrumbs-right">
          <Link to={previous.path}>{previous.label}</Link>
          <img src={next} alt="Next Page" />
          {current?.label}
        </div>
      )}
    </div>
  );
};

export default Breadcrumbs;
