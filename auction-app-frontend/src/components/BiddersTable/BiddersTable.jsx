import { useEffect, useState } from "react";
import moment from "moment";

import { findBidsByProductId } from "src/services/bidService";
import { BIDDERS_TABLE_ROWS, BIDDERS_TABLE_DEFAULT_PAGE_SIZE } from "src/constants";
import { BUTTON_LABELS, BUTTON_VARIANTS } from "src/constants";
import { userProfilePicture } from "src/assets/images";

import { Button, ButtonLoadingIndicator, ErrorComponent } from "src/components";

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
        return moment(dateString, "YYYY-MM-DD").format("D MMMM YYYY");
    };

    const formatProfilePicture = (profilePicture) => {
        return profilePicture || userProfilePicture;
    };

    if (error) return <ErrorComponent message={ error } />;

    return (
        <div className="bidders-table-container body-semibold">
        <table>
            <thead>
            <tr>
                { BIDDERS_TABLE_ROWS.map((row, index) => (
                    <th key={ row + index }>{ row.name }</th>
                )) }
            </tr>
            </thead>
            <tbody>
            { biddersList.map((bidder, index) => (
                <tr key={ bidder.id } className="bid-row">
                <td>
                    <div className="bidder-info">
                    <img src={ formatProfilePicture(bidder.user.profilePicture) } alt="profile picture" />
                    <span>{ bidder.user.firstName } { bidder.user.lastName }</span>
                    </div>
                </td>
                <td>{ formatDate(bidder.bidTime) }</td>
                <td className={ index === 0 ? 'first-bid' : 'other-bid' }>${ bidder.bidAmount }</td>
                </tr>
            )) }
            </tbody>
        </table>
        <div className="load-more-btn">
            { hasMore &&
                <Button 
                    label = { loading ? <ButtonLoadingIndicator /> : BUTTON_LABELS.LOAD_MORE }
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
