import { NavLink, Link } from "react-router-dom";

import { NAV_ITEMS } from "src/constants";
import { logo } from "src/assets/images";
import { search } from "src/assets/icons";

import "./style.scss";

const Navbar = () => {
  return (
    <div className="navbar">
      <div className="app-logo">
        <Link to="/">
          <img src={ logo } alt="Auction App Logo" />
        </Link>
      </div>
      <div className="search-bar">
        <input
          type="text"
          className="search-input"
          placeholder="Try enter: Shoes"
        />
        <button className="search-button">
          <img src={ search } alt="Search Button" />
        </button>
      </div>
      <div className="navbar-items body-regular">
        { NAV_ITEMS.map((item) => (
          <NavLink
            to={ item.link }
            className={ ({ isActive }) =>
              isActive ? "navbar-item active" : "navbar-item"
            }
            key={ item.key }
          >
            { item.label }
          </NavLink>
        )) }
      </div>
    </div>
  );
};

export default Navbar;
