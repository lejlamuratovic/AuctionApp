import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { search } from "src/assets/icons";

import "./style.scss";

const Searchbar = () => {
  const [productName, setProductName] = useState("");

  const navigate = useNavigate();

  const handleSearch = () => {
    const url = new URL(window.location.href);
    url.searchParams.set("search", productName);

    location.pathname.includes("/shop/") ? navigate(url.pathname + url.search) : navigate(`/shop/?search=${productName}`);
  };

  return (
    <div className="search-bar">
        <input
          type="text"
          className="search-input"
          placeholder="Try enter: Shoes"
          value={ productName }
          onChange={ (event) => setProductName(event.target.value) }
          onKeyDown={ (event) => {
              if (event.key === "Enter") {
                  handleSearch();
              }
          } }
        />
        <button className="search-button">
            <img src={ search } alt="Search Button" />
        </button>
    </div>
  )
}

export default Searchbar;
