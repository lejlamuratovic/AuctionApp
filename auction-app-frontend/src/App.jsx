import { Routes, Route } from "react-router-dom";

import { Navbar, Header, Footer, Breadcrumbs } from "src/components";
import {
  AboutUs,
  PrivacyPolicy,
  TermsAndConditions,
  Home,
  Shop,
  MyAccount,
} from "src/pages";

import { BreadcrumbProvider } from "src/BreadcrumbContext";

import "src/scss/index.scss";

const App = () => {
  return (
    <>
      <BreadcrumbProvider>
        <Header />
        <Navbar />
        <div className="main-content">
          <div className="container">
            <Breadcrumbs />
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/home" element={<Home />} />
              <Route path="/shop" element={<Shop />} />
              <Route path="/my-account" element={<MyAccount />} />
              <Route path="/about-us" element={<AboutUs />} />
              <Route path="/privacy-policy" element={<PrivacyPolicy />} />
              <Route
                path="/terms-and-conditions"
                element={<TermsAndConditions />}
              />
            </Routes>
          </div>
          <Footer />
        </div>
      </BreadcrumbProvider>
    </>
  );
};

export default App;
