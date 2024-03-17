import { SocialMediaIcons } from "../SocialMediaIcons";
import { UserStatus } from "../UserStatus";
import "./blackNavbar.scss";

const BlackNavbar = () => {
  // hardcoded for now
  const isLoggedIn = true;
  const userName = "John Doe";

  return (
    <div className="black-navbar">
      <SocialMediaIcons />
      <UserStatus isLoggedIn={isLoggedIn} userName={userName} />
    </div>
  );
};

export default BlackNavbar;
