import { useEffect, useState } from "react";

import { findBidsByProductId } from "src/services/bidService";
import { BIDDERS_TABLE_ROWS, BIDDERS_TABLE_DEFAULT_PAGE_SIZE } from "src/constants";

import "./style.scss";

const BiddersTable = ({ productId }) => {
    const [loading, setLoading] = useState();
    const [error, setError] = useState(null);
    const [biddersList, setBiddersList] = useState([]);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(BIDDERS_TABLE_DEFAULT_PAGE_SIZE);

    const fetchBiddersList = () => {
        setLoading(true);

        findBidsByProductId(productId, page, size)
            .then(response => {
                setBiddersList(response.content);
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
    }, [productId]);

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
        </div>
    );
}

export default BiddersTable;
