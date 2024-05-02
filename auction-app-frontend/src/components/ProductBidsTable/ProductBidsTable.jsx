import { HEADERS } from "src/constants";

import "./style.scss";

const ProductBidsTable = ({ emptyMessageComponent }) => {
  const hasItems = false;

  return (
    <div className="table">
      <div className="table-header body-bold">
        { HEADERS.map(header => (
          <div className={ `header-item ${header.id}` } key={ header.id }>
            { header.text }
          </div>
        )) }
      </div>
      { !hasItems && (
        <div className="table-empty">
          { emptyMessageComponent }
        </div>
      ) }
    </div>
  );
}

export default ProductBidsTable;
