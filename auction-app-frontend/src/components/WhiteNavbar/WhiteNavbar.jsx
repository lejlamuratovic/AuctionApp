import "./whiteNavbar.scss";
import appLogo from "assets/auction-app-logo.png";
import { NavbarItems } from "../NavbarItems";

const WhiteNavbar = () => {
  return (
    <div className="white-navbar">
      <div className="app-logo">
        <img src={appLogo} alt="Auction App Logo" />
      </div>
      <NavbarItems active="HOME" />
    </div>
  );
};

export default WhiteNavbar;
