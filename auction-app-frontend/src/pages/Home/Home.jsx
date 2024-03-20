import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div>
      Home Page
      <br />
      <Link to="/product-detail/1">Product 1</Link>
    </div>
  );
};

export default Home;
