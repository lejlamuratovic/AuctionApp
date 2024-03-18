import { SocialMediaIcons } from "../SocialMediaIcons";
import "./style.scss";

const Footer = () => {
  return (
    <div className="footer">
      <div className="footer-column body-regular">
        <div className="column-heading">
          <p>Auction</p>
        </div>
        <div className="column-content">
          <a href="/aboutus">About Us</a>
          <a href="/termsandconditions">Terms and Conditions</a>
          <a href="/privacypolicy">Privacy Policy</a>
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
