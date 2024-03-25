import InfiniteScroll from "react-infinite-scroll-component";

import { ProductCard, LoadingComponent } from "src/components";

import "./style.scss";

const ProductGrid = ({ items, fetchMoreData, hasMore, loading }) => {
  return (
    <InfiniteScroll
      dataLength={items.length}
      next={fetchMoreData}
      hasMore={hasMore}
      loader={<LoadingComponent />}
      className="product-grid"
    >
      {items.map((item) => (
        <ProductCard key={item.id} {...item} />
      ))}
    </InfiniteScroll>
  );
};

export default ProductGrid;
