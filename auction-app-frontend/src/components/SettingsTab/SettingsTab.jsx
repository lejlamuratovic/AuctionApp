import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { Button, ErrorComponent, LoadingComponent } from "src/components";

import { BUTTON_LABELS, ROUTE_PATHS, BUTTON_VARIANTS } from "src/constants";
import { useUser } from "src/store/UserContext";
import { getUser, deactivateAccount } from "src/services/userService";

import "./style.scss"

const SettingsTab = () => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [updateLoading, setUpdateLoading] = useState(false);
    const [error, setError] = useState(null);
    const [warning, setWarning] = useState(null);

    const { userId } = useUser();
    const navigate = useNavigate();
  
    const fetchUserInformation = () => {
      setLoading(true);
  
      getUser(userId)
        .then((response) => {
          setUser(response);
        })
        .catch((error) => {
          setError(error);
          setLoading(false);
        })
        .finally(() => {
          setLoading(false);
        });
    }
  
    const handleDeactivateAccount = () => {
      setUpdateLoading(true);

      deactivateAccount(userId)
        .then(() => {
          navigate(ROUTE_PATHS.HOME);

          setUpdateLoading(false);
          
          localStorage.clear();
        }).catch((error) => {
          if ((error.response.data.trace).startsWith("com.example.auctionapp.exceptions.authentication.CannotDeactivateAccount")) {
            setError("You have active products with bids, you cannot deactivate your account yet.")
          } else {
            setError(error.message);
          }

          setUpdateLoading(false);
        });
    }

    useEffect(() => {
      if (userId) fetchUserInformation();
    }, [userId]);

    if (loading) return <LoadingComponent />;

    return (
        <div className="settings-tab-container">
          { error && <ErrorComponent message={ error } /> }
            <div className="box-container body-semibold">
                <div className="box top-right">
                    <span className="box-heading">Contact Information</span>
                    <span className="box-description">This information can be edited on your profile.</span>
                    <span className="box-heading">Email <span className="box-information body-semibold">{ user?.email }</span></span>
                </div>
                <div className="box bottom-left">
                    <span className="box-heading">Account Information</span>
                    <span className="box-description">Do you want to deactivate account?</span>
                    <Button 
                      label={ updateLoading ? <LoadingComponent /> : BUTTON_LABELS.DEACTIVATE }
                      variant={ BUTTON_VARIANTS.OUTLINED } 
                      onButtonClick={ handleDeactivateAccount }
                    />
                </div>
            </div>
        </div>
    );
}

export default SettingsTab;
