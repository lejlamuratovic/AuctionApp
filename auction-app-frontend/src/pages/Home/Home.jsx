import "./style.scss";

import { CATEGORIES } from "src/constants";

const Home = () => {
  return (
    <>
      <div className="categories body-regular">
        <div className="categories-heading">Categories</div>
        <ul>
          {/* hardcoded for now */}
          {CATEGORIES.map((category, index) => (
            <li key={index}>{category}</li>
          ))}
        </ul>
      </div>
    </>
  );
};

export default Home;
