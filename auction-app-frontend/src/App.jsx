import "scss/index.scss";
import { Navbar } from "./components/Navbar";
import { Header } from "./components/Header";
import { Footer } from "./components/Footer";
import { AboutUs, PrivacyPolicy, TermsAndConditions } from "./pages";

const App = () => {
  return (
    <>
      <Header />
      <Navbar />
      {/* <AboutUs /> */}
      {/* <TermsAndConditions /> */}
      <PrivacyPolicy />
      <Footer />
    </>
  );
};

export default App;
