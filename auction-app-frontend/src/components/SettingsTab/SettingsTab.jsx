import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { Button, ErrorComponent, LoadingComponent } from "src/components";

import { BUTTON_LABELS, ROUTE_PATHS, BUTTON_VARIANTS } from "src/constants";
import { useUser } from "src/store/UserContext";
import { getUser, deactivateAccount } from "src/services/userService";

import "./style.scss"
import { set } from "react-hook-form";

const SettingsTab = () => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [updateLoading, setUpdateLoading] = useState(false);
    const [error, setError] = useState(null);

    const { userId } = useUser();
    const navigate = useNavigate();
  
    const fetchUserInformation = () => {
      setLoading(true);
  
      getUser(userId)
        .then((response) => {
          setUser(response);
          setLoading(false);
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
          navigate(ROUTE_PATHS.LOGIN);
          setUpdateLoading(false);
        }).catch((error) => {
          setError(error.message);
          setUpdateLoading(false);
        });
    }

    useEffect(() => {
      if (userId) fetchUserInformation();
    }, [userId]);

    if (error) return <ErrorComponent error={ error } />;

    return (
        <div className="settings-tab-container">
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
                      label={updateLoading ? <LoadingComponent /> : BUTTON_LABELS.DEACTIVATE}
                      variant={BUTTON_VARIANTS.OUTLINED} 
                      onButtonClick={handleDeactivateAccount}
                    />
                </div>
            </div>
        </div>
    )
}

export default SettingsTab;
