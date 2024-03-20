import { Link } from "react-router-dom";

import { SocialMediaIcons } from "src/components";

import "./style.scss";

const Footer = () => {
  return (
    <div className="footer">
      <div className="footer-column body-regular">
        <div className="column-heading">
          <p>Auction</p>
        </div>
        <div className="column-content">
          <Link to="/about-us">About Us</Link>
          <Link to="/terms-and-conditions">Terms And Conditions</Link>
          <Link to="/privacy-policy">Privacy Policy</Link>
        </div>
      </div>

      <div className="footer-column body-regular">
        <div className="column-heading">
          <p>Get in touch</p>
        </div>
        <div className="column-content">
          <p>Call Us at +123 797-567-2535</p>
          <p>support@auction.com</p>
          <SocialMediaIcons />
        </div>
      </div>
    </div>
  );
};

export default Footer;
