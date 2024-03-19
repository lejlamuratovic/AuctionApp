import "scss/index.scss";
import { Navbar, Header, Footer } from "components";
import { AboutUs, PrivacyPolicy, TermsAndConditions } from "./pages";

const App = () => {
  return (
    <>
      <Header />
      <Navbar />
      {/* <AboutUs /> */}
      {/* <TermsAndConditions /> */}
      {/* <PrivacyPolicy /> */}
      <Footer />
    </>
  );
};

export default App;
