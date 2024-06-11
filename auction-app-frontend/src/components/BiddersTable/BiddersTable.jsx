import { useEffect, useState } from "react";

import { findBidsByProductId } from "src/services/bidService";
import { BIDDERS_TABLE_ROWS, BIDDERS_TABLE_DEFAULT_PAGE_SIZE } from "src/constants";
import { BUTTON_LABELS, BUTTON_VARIANTS } from "src/constants";

import { Button } from "src/components";

import "./style.scss";

const BiddersTable = ({ productId }) => {
    const [loading, setLoading] = useState();
    const [error, setError] = useState(null);
    const [biddersList, setBiddersList] = useState([]);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(BIDDERS_TABLE_DEFAULT_PAGE_SIZE);
    const [hasMore, setHasMore] = useState(true);

    const fetchBiddersList = () => {
        setLoading(true);

        findBidsByProductId(productId, page, size)
            .then(response => {
                setBiddersList((prevItems) => 
                    page === 0 
                        ? [...response.content]
                        : [...prevItems, ...response.content]
                );

                setHasMore(response.last !== true);
            })
            .catch(error => {
                setError(error);
                setLoading(false);
            })
            .finally(() => {
                setLoading(false);
            });
    };

    useEffect(() => {
        if(productId) fetchBiddersList();
    }, [productId, page]);

    const handlePageChange = (page) => {
        setPage((prevPage) => prevPage + 1);
    }

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return date.toISOString().split('T')[0]; // remove time part
    };

    return (
        <div className="bidders-table-container body-semibold">
        <table>
            <thead>
            <tr>
                { BIDDERS_TABLE_ROWS.map(row => (
                    <th key={ row }>{ row.name }</th>
                )) }
            </tr>
            </thead>
            <tbody>
            { biddersList.map(bidder => (
                <tr key={ bidder.id } className="bid-row">
                <td>
                    <div className="bidder-info">
                    <img src={ bidder.user.profilePicture } alt="profile picture" />
                    <span>{ bidder.user.firstName } { bidder.user.lastName }</span>
                    </div>
                </td>
                <td>{ formatDate(bidder.bidTime) }</td>
                <td>${ bidder.bidAmount }</td>
                </tr>
            )) }
            </tbody>
        </table>
        <div className="load-more-btn">
            { hasMore &&
                <Button 
                    label = { BUTTON_LABELS.LOAD_MORE } 
                    onButtonClick = { () => handlePageChange(page) } 
                    variant = { BUTTON_VARIANTS.FILLED }
                    className = "load-more-button"
                />
            }
        </div>
        </div>
    );
}

export default BiddersTable;
