import { Link } from "react-router-dom";

import { ROUTE_PATHS } from "src/constants";
import { SocialMediaIcons } from "src/components";

import "./style.scss";

const Header = () => {
  // hardcoded for now
  const isLoggedIn = false;
  const userName = "John Doe";

  return (
    <div className="header">
      <SocialMediaIcons />
      <div className="user-status body-small-semibold">
        { isLoggedIn ? (
          <span>Hi, { userName }</span>
        ) : (
          <div className="user-status-links">
            <Link to={ ROUTE_PATHS.LOGIN }>
              <span>Login</span>
            </Link>
            <span className="span-connect"> or </span>
            <Link to={ ROUTE_PATHS.REGISTER }>
              <span>Create an account</span>
            </Link>
          </div>
        ) }
      </div>
    </div>
  );
};

export default Header;
