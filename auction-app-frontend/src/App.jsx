import "scss/index.scss";
import { WhiteNavbar } from "./components/WhiteNavbar";
import { BlackNavbar } from "./components/BlackNavbar";
import { Footer } from "./components/Footer";

const App = () => {
  return (
    <>
      <BlackNavbar />
      <WhiteNavbar />
      <Footer />
    </>
  );
};

export default App;
