import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { search } from "src/assets/icons";

import "./style.scss";

const Searchbar = () => {
  const [query, setQuery] = useState("");

  const navigate = useNavigate();

  const handleSearch = () => {
    const url = new URL(window.location.href);
    url.searchParams.set("search", query);

    if (location.pathname === '/shop/') {
      navigate(url.pathname + url.search);
    } else {
      navigate(`/shop/?search=${query}`);
    }
  };

  return (
    <div className="search-bar">
        <input
        type="text"
        className="search-input"
        placeholder="Try enter: Shoes"
        value={ query }
        onChange={ (event) => setQuery(event.target.value) }
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

export default Searchbar
