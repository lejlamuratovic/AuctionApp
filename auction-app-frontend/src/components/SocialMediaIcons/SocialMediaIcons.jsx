import facebook from "assets/facebook.svg";
import instagram from "assets/instagram.svg";
import twitter from "assets/twitter.svg";
import "./style.scss";

const SocialMediaIcons = () => {
  return (
    <div className="social-media-icons">
      <a href="https://www.facebook.com/">
        <img src={facebook} alt="facebook" />
      </a>
      <a href="https://www.instagram.com/">
        <img src={instagram} alt="instagram" />
      </a>
      <a href="https://www.twitter.com/">
        <img src={twitter} alt="twitter" />
      </a>
    </div>
  );
};

export default SocialMediaIcons;
