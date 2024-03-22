import { CATEGORIES } from "src/constants";
import { productImage2 } from "src/assets/images";

import "./style.scss";

const Home = () => {
  return (
    <>
      <div className="home-container">
        <div className="categories body-regular">
          <div className="categories-heading">Categories</div>
          <ul>
            {/* hardcoded for now */}
            {CATEGORIES.map((category, index) => (
              <li key={index}>{category}</li>
            ))}
          </ul>
        </div>

        <div className="highlighted-product">
          <div className="product-container">
            <div className="product-info body-semibold">
              <span className="product-name">Running shoes</span>
              <span className="price">Start From $59.00</span>
              <p className="body-regular">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Vestibulum hendrerit odio a erat lobortis auctor. Curabitur
                sodales pharetra placerat. Aenean auctor luctus tempus. Cras
                laoreet et magna in dignissim. Nam et tincidunt augue. Vivamus
                quis malesuada velit. In hac habitasse platea dictumst.
              </p>
              <button className="bid-btn body-bold">Bid Now</button>
            </div>
          </div>
          <div className="product-image">
            <img src={productImage2} alt="Product Image" />
          </div>
        </div>
      </div>
    </>
  );
};

export default Home;
