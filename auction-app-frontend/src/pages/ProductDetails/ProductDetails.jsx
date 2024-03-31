import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import { Tabs, LoadingComponent, ErrorComponent } from "src/components";
import { useBreadcrumb } from "src/store/BreadcrumbContext";

import { getProduct } from "src/services";

import { PRODUCT_DETAILS_TABS, PRODUCT_DETAILS_IMAGES } from "src/constants";

import "./style.scss";

const ProductDetails = () => {
  const [activeTab, setActiveTab] = useState(PRODUCT_DETAILS_TABS[0].id);
  const [product, setProduct] = useState(null);
  const [mainImage, setMainImage] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { id } = useParams();
  const { setTitle } = useBreadcrumb();

  const fetchProductDetails = () => {
    setLoading(true);

    getProduct(id)
      .then((productDetail) => {
        setProduct(productDetail);
        setMainImage(productDetail.imageUrl);
      })
      .catch((err) => {
        setError("Failed to fetch product details: " + err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const setActiveTabHandler = (tabId) => {
    setActiveTab(tabId);
  };

  const handleImageClick = (image) => {
    setMainImage(image);
  };

  useEffect(() => {
    fetchProductDetails();
  }, []);

  useEffect(() => {
    if (product) {
      setTitle(`${product.name}`);
    }
  }, [product, setTitle]);

  if (loading) return <LoadingComponent />;
  if (error) return <ErrorComponent message={error} />;

  return (
    <>
      <div className="product-details-container">
        <div className="product-details-images">
          <div className="main-image-container">
            <img src={mainImage} alt="Product" />
            <div className="other-images-container">
              {PRODUCT_DETAILS_IMAGES.slice(1).map((image, index) => (
                <div key={index} className="image-container">
                  <img
                    src={image}
                    alt={`Product ${index + 1}`}
                    onClick={() => handleImageClick(image)}
                  />
                </div>
              ))}
            </div>
          </div>
        </div>
        <div className="product-details">
          <div className="product-name">
            <span className="body-semibold">{product.name}</span>
            <span className="body-regular">
              Starts from <span className="price">{product.startPrice}</span>
            </span>
          </div>
          <div className="product-bid-details"></div>
          <div className="product-information">
            <Tabs
              tabs={PRODUCT_DETAILS_TABS}
              activeTab={activeTab}
              onTabClick={setActiveTabHandler}
            />
            {activeTab === "details" && (
              <div className="product-description">
                <p>{product.description}</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default ProductDetails;
