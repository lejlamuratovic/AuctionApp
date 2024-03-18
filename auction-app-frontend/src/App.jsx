import "scss/index.scss";
import { WhiteNavbar } from "./components/WhiteNavbar";
import { BlackNavbar } from "./components/BlackNavbar";
import { Footer } from "./components/Footer";
import { AboutUs, PrivacyPolicy, TermsAndConditions } from "./pages";

const App = () => {
  return (
    <>
      {/* <BlackNavbar /> */}
      {/* <WhiteNavbar /> */}
      {/* <AboutUs /> */}
      {/* <TermsAndConditions /> */}
      <PrivacyPolicy />
      {/* <Footer /> */}
    </>
  );
};

export default App;
