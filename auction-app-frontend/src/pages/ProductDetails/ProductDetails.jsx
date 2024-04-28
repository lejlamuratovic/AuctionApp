import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import { Tabs, LoadingComponent, ErrorComponent } from "src/components";

import { useBreadcrumb } from "src/store/BreadcrumbContext";
import { getProduct } from "src/services";

import { PRODUCT_DETAILS_TABS } from "src/constants";

import "./style.scss";

const ProductDetails = () => {
  const [activeTab, setActiveTab] = useState(PRODUCT_DETAILS_TABS[0].id);
  const [product, setProduct] = useState(null);
  const [productImages, setProductImages] = useState([]);
  const [mainImage, setMainImage] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [timeLeft, setTimeLeft] = useState(0);

  const { id } = useParams();

  const { setTitle } = useBreadcrumb();

  const fetchInitialData = () => {
    setLoading(true);

    getProduct(id)
      .then((productDetail) => {
        setProduct(productDetail);
        console.log(productDetail);
        setMainImage(productDetail.productImages[0].imageUrl);
        // initially remove the first image as it is set as main image
        setProductImages(productDetail.productImages.slice(1));
      })
      .catch((err) => {
        setError("Failed to fetch initial data", err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchInitialData();
  }, [id]);

  useEffect(() => {
    if (product) {
      setTitle(`${product.name}`);
  
      const endDate = new Date(product.endDate);
      const now = new Date();
      const difference = endDate - now;
      let timeLeft = '';
  
      if (difference > 0) {
        const daysLeft = Math.floor(difference / (1000 * 60 * 60 * 24));
        const hoursLeft = Math.floor((difference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutesLeft = Math.floor((difference % (1000 * 60 * 60)) / (1000 * 60));
  
        if (daysLeft > 0) {
          timeLeft += `${daysLeft} day${daysLeft !== 1 ? 's' : ''}`;
        } else if (hoursLeft > 0) {
          timeLeft += `${hoursLeft} hour${hoursLeft !== 1 ? 's' : ''}`;
        } else if (minutesLeft > 0) {
          timeLeft += `${minutesLeft} minute${minutesLeft !== 1 ? 's' : ''}`;
        } else {
          timeLeft += 'Less than a minute';
        }
      } else {
        timeLeft += 'Expired';
      }
  
      setTimeLeft(timeLeft);
    }
  }, [product, setTitle]);

  const setActiveTabHandler = (tabId) => {
    setActiveTab(tabId);
  };

  const handleImageClick = (clickedImage) => {
    // find current main image in the product's image list
    const previousMainImage = product.productImages.find(
      (img) => img.imageUrl === mainImage
    );

    // set the clicked image as the new one
    setMainImage(clickedImage.imageUrl);

    const updatedImagesList = productImages
      .filter((img) => img.imageUrl !== clickedImage.imageUrl) // remove the clicked image
      .concat(previousMainImage); // add the previous main image back

    setProductImages(updatedImagesList);
  };

  if (loading) return <LoadingComponent />;
  if (error) return <ErrorComponent message={ error } />;

  return (
    <>
      <div className="product-details-container">
        <div className="product-details-images">
          <div className="main-image-container">
            <img src={ mainImage } alt="Product" />
            <div className="other-images-container">
              { productImages.map((image, index) => (
                <div
                  key={ `${image.imageUrl}-${index}` }
                  className="image-container"
                >
                  <img
                    src={ image.imageUrl }
                    alt={ `Product ${index + 1}` }
                    onClick={ () => handleImageClick(image) }
                  />
                </div>
              )) }
            </div>
          </div>
        </div>
        <div className="product-details">
          <div className="product-name">
            <span className="body-semibold">{ product.name }</span>
            <span className="body-regular">
              Starts from <span className="price">${ product.startPrice }</span>
            </span>
          </div>
          <div className="product-bid-details">
            <div className="product-bid-details-item">
              <span className="item-key">Highest Bid: </span>
              <span className="item-value">
                ${ product.highestBid === null ? "0" : product.highestBid }
              </span>
            </div>
            <div className="product-bid-details-item">
              <span className="item-key">Number of bids: </span>
              <span className="item-value">{ product.bidsCount }</span>
            </div>
            <div className="product-bid-details-item">
              <span className="item-key">Time left: </span>
              <span className="item-value">{ timeLeft }</span>
            </div>
          </div>
          <div className="product-information">
            <Tabs
              tabs={ PRODUCT_DETAILS_TABS }
              activeTab={ activeTab }
              onTabClick={ setActiveTabHandler }
            />
            { activeTab === "details" && (
              <div className="product-description">
                <p>{ product.description }</p>
              </div>
            ) }
          </div>
        </div>
      </div>
    </>
  );
};

export default ProductDetails;
