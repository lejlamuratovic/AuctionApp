import { Link, useNavigate } from "react-router-dom";

import { SocialMediaIcons } from "src/components";

import { ROUTE_PATHS } from "src/constants";
import { logoutUser } from "src/services/userService";
import { useUserName } from "src/store/UserNameContext";

import "./style.scss";

const Header = () => {
  const { userName, setUserName } = useUserName();
  const navigate = useNavigate();

  const onLogout = () => {
    setUserName(null);
    logoutUser();
    navigate(ROUTE_PATHS.LOGIN);
  }
  
  return (
    <div className="header">
      <SocialMediaIcons />
      <div className="user-status body-small-semibold">
        { userName ? (
          <>
            <span>Hi, { userName }</span>
            <span className="logout" onClick={ onLogout }> Logout </span>
          </>
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
