import "./navbarItems.scss";

const NavbarItems = ({ active }) => {
  const navItems = ["HOME", "SHOP", "MY ACCOUNT"];

  return (
    <div className="navbar-items body-small-regular">
      {navItems.map((item, index) => (
        <a
          href={`/${item.toLowerCase()}`}
          className={`navbar-item ${active === item ? "active body-bold" : ""}`}
          key={index}
        >
          {item}
        </a>
      ))}
    </div>
  );
};

export default NavbarItems;
