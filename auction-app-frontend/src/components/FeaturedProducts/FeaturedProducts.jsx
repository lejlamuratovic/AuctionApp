import "./style.scss";

import { ProductCard } from "src/components";

const FeaturedProducts = () => {
  return (
    <div className="featured-products-container">
        <h5> Featured Products </h5>
        <hr />
        <div className="featured-list">
            { products.map((product) => (
                <ProductCard 
                    key={ product.id } 
                    id={ product.id } 
                    productImages={ product.productImages } 
                    name={ product.name } 
                    startPrice={ product.startPrice } 
                />
            )) }
        </div>
    </div>
  )
}

export default FeaturedProducts
