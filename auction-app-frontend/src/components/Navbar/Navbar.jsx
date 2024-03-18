import { NAV_ITEMS } from "src/constants";
import appLogo from "assets/auction-app-logo.png";
import "./style.scss";

const Navbar = () => {
  // hardcoded for now
  const active = "HOME";

  return (
    <div className="navbar">
      <div className="app-logo">
        <img src={appLogo} alt="Auction App Logo" />
      </div>
      <div className="navbar-items body-regular">
        {NAV_ITEMS.map((item) => (
          <a
            href={item.link}
            className={`navbar-item ${
              active === item.label ? "active body-bold" : ""
            }`}
            key={item.key}
          >
            {item.label}
          </a>
        ))}
      </div>
    </div>
  );
};

export default Navbar;
