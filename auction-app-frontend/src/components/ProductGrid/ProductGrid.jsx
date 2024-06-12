import { ProductCard } from "src/components";

import "./style.scss";

const ProductGrid = ({ items, className, showDescription  }) => {
  return (
    <div className={ `product-grid ${ className }` }>
      { items.map((item) => (
        <ProductCard key={ item.id } showDescription={ showDescription } { ...item } />
      )) }
    </div>
  );
};

export default ProductGrid;
