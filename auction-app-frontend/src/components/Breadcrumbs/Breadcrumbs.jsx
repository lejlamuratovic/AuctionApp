import { Link } from "react-router-dom";

import { useBreadcrumb } from "src/store/BreadcrumbContext";

import { next } from "src/assets/icons";

import "./style.scss";

const Breadcrumbs = () => {
  const { title, previous, current } = useBreadcrumb();

  console.log({ title, previous, current });

  // if there's no previous breadcrumb don't display the navigation
  if (!previous) {
    return null;
  }

  return (
    <div className="breadcrumbs body-regular">
      <div className="breadcrumbs-left">{title}</div>
      {previous && (
        <div className="breadcrumbs-right">
          <Link to={previous.path}>{previous.label}</Link>
          <img src={next} alt="Next Page" />
          <span className="body-bold"> {current?.label}</span>
        </div>
      )}
    </div>
  );
};

export default Breadcrumbs;
