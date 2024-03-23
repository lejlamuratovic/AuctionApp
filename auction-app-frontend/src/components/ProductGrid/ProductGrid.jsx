import InfiniteScroll from "react-infinite-scroll-component";

import { ProductCard } from "src/components";

import "./style.scss";

const ProductGrid = ({ items, fetchMoreData, hasMore }) => {
  return (
    <InfiniteScroll
      dataLength={items.length}
      next={fetchMoreData}
      hasMore={hasMore}
      loader={<span className="body-semibold">Loading...</span>}
      className="product-grid"
    >
      {items.map((item, index) => (
        <ProductCard key={index} {...item} />
      ))}
    </InfiniteScroll>
  );
};

export default ProductGrid;
